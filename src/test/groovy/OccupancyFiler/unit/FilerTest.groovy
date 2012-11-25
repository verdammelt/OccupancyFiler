package OccupancyFiler.unit;


import OccupancyFiler.arguments.Toolbox
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.file.FileLinesTrimmer
import OccupancyFiler.file.FilesInDirectory
import OccupancyFiler.file.NameGenerator
import spock.lang.Specification
import OccupancyFiler.*

public class FilerTest extends Specification {
    private static final int TEST_SEQ_NUM = 42

    def "uses SequenceNumber doWithNextNumber to get the job done for each file"() {
        given:
        def files = mockFilesInDirectory([new File('a'), new File('b')])
        def seqNum = Mock(SequenceNumber)

        when:
        new Filer(makeMockToolbox(files, null, null, seqNum, null, null, Mock(FileDeleter), new TargetDirectory('.'))).performFiling()

        then:
        2 * seqNum.doWithNextNumber(_)
    }

    def "deletes files after processing"() {
        def files = mockFilesInDirectory([new File('a'), new File('b')])
        def seqNum = Mock(SequenceNumber)
        def deleter = Mock(FileDeleter)

        when:
        new Filer(makeMockToolbox(files, null, null, seqNum, null, null, deleter, new TargetDirectory('.'))).performFiling()

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

        def renamer = Mock(NameGenerator)
        renamer.generateName(TEST_SEQ_NUM) >> 'newFile.txt'

        def writer = Mock(FileWriter)

        def directory = new TargetDirectory('/tmp/foo')

        when:
        fileWithArgs(new File('a'), makeMockToolbox(null, renamer, trimmer, Mock(SequenceNumber), reader, writer, Mock(FileDeleter), directory))

        then:
        1 * writer.write('/tmp/foo/newFile.txt', trimmedLines)
    }

    private FilesInDirectory mockFilesInDirectory(listOfFiles) {
        def files = Mock(FilesInDirectory)
        files.iterator() >> listOfFiles.iterator()
        files
    }

    private Toolbox makeMockToolbox(FilesInDirectory files,
                                    NameGenerator renamer,
                                    FileLinesTrimmer trimmer,
                                    SequenceNumber sequenceNumber,
                                    FileReader reader,
                                    FileWriter writer,
                                    FileDeleter deleter,
                                    TargetDirectory targetDirectory) {
        def toolbox = Mock(Toolbox)
        toolbox.files >> files
        toolbox.renamer >> renamer
        toolbox.trimmer >> trimmer
        toolbox.sequenceNumber >> sequenceNumber
        toolbox.reader >> reader
        toolbox.writer >> writer
        toolbox.deleter >> deleter
        toolbox.targetDirectory >> targetDirectory
        toolbox
    }

    @SuppressWarnings("GroovyAccessibility")
    private void fileWithArgs(File file, Toolbox args) {
        new Filer(args).fileWithSequenceNumber(file, TEST_SEQ_NUM)
    }
}
