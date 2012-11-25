package OccupancyFiler.unit.logic;


import OccupancyFiler.arguments.Toolbox
import OccupancyFiler.io.FileDeleter
import OccupancyFiler.io.FileReader
import OccupancyFiler.io.FileWriter
import OccupancyFiler.io.FilesInDirectory
import spock.lang.Specification
import OccupancyFiler.logic.*

public class FilerTest extends Specification {
    private static final int TEST_SEQ_NUM = 42

    def "uses SequenceNumber doWithNextNumber to get the job done for each file"() {
        given:
        def files = mockFilesInDirectory([new File('a'), new File('b')])
        def seqNum = Mock(SequenceNumber)

        when:
        new Filer(makeMockToolbox(files, seqNum, Mock(FileDeleter))).performFiling()

        then:
        2 * seqNum.doWithNextNumber(_)
    }

    def "deletes files after processing"() {
        def files = mockFilesInDirectory([new File('a'), new File('b')])
        def seqNum = Mock(SequenceNumber)
        def deleter = Mock(FileDeleter)

        when:
        new Filer(makeMockToolbox(files, seqNum, deleter)).performFiling()

        then:
        1 * deleter.delete(new File('a'))
        1 * deleter.delete(new File('b'))
    }

    def "reads each file, trims the headers off the lines, then writes the lines to a new filename"() {
        given:
        FileReader reader = Mock(FileReader)
        FileLines linesA = new FileLines(['a', 'b', 'c'])
        reader.read(new File('a')) >> linesA

        def trimmer = Mock(FileLinesTrimmer)
        def trimmedLines = new FileLines(['b', 'c'])
        trimmer.trimTopLines(linesA) >> trimmedLines

        def pathGenerator = Mock(FilePathGenerator)
        pathGenerator.generatePath(TEST_SEQ_NUM) >> '/tmp/foo/newFile.txt'

        def writer = Mock(FileWriter)

        when:
        fileWithArgs(new File('a'), makeMockToolbox(trimmer, reader, writer, pathGenerator))

        then:
        1 * writer.write('/tmp/foo/newFile.txt', trimmedLines)
    }

    private FilesInDirectory mockFilesInDirectory(listOfFiles) {
        def files = Mock(FilesInDirectory)
        files.iterator() >> listOfFiles.iterator()
        files
    }

    private Toolbox makeMockToolbox(FilesInDirectory files,
                                    SequenceNumber sequenceNumber,
                                    FileDeleter deleter) {
        def toolbox = Mock(Toolbox)
        toolbox.files >> files
        toolbox.sequenceNumber >> sequenceNumber
        toolbox.deleter >> deleter

        toolbox
    }

    private Toolbox makeMockToolbox(
                                    FileLinesTrimmer trimmer,
                                    FileReader reader,
                                    FileWriter writer,
                                    FilePathGenerator pathGenerator) {
        def toolbox = Mock(Toolbox)
        toolbox.trimmer >> trimmer
        toolbox.reader >> reader
        toolbox.writer >> writer
        toolbox.filePathGenerator >> pathGenerator
        toolbox
    }

    @SuppressWarnings("GroovyAccessibility")
    private void fileWithArgs(File file, Toolbox args) {
        new Filer(args).processFileWithSequenceNumber(file, TEST_SEQ_NUM)
    }
}
