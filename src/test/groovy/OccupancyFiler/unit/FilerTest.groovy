package OccupancyFiler.unit;


import OccupancyFiler.Filer
import OccupancyFiler.arguments.ArgumentParser
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory
import spock.lang.Specification

public class FilerTest extends Specification {
    def "uses SequenceNumber doWithNextNumber to get the job done"() {
        given:
        def seqNum = Mock(SequenceNumber)

        when:
        new Filer(makeMockArgs(null, null, null, null, seqNum)).performFiling()

        then:
        1 * seqNum.doWithNextNumber(_)
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "moves all input files to the output directory"() {
        given:
        def files = mockFilesInDirectory([Mock(File), Mock(File)])
        def mover = Mock(FileMover)
        def renamer = Mock(FileRenamer)
        renamer.rename(_,_) >> new File('renamed')

        when:
        fileWithArgs(makeMockArgs(files, mover, renamer, Mock(FileTrimmer), Mock(SequenceNumber)))

        then:
        2 * mover.move(new File('renamed'))
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "renames each file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def renamer = Mock(FileRenamer)
        def trimmer = Mock(FileTrimmer)
        trimmer.trimTopLines(_) >> {File file -> file}

        when:
        fileWithArgs(makeMockArgs(files, Mock(FileMover), renamer, trimmer, Mock(SequenceNumber)))

        then:
        1 * renamer.rename(_,new File('a'))
    }

    def "chops the header off the file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def trimmer = Mock(FileTrimmer)

        when:
        fileWithArgs(makeMockArgs(files, Mock(FileMover), Mock(FileRenamer), trimmer, Mock(SequenceNumber)))

        then:
        1 * trimmer.trimTopLines(new File('a'))
    }

    private FilesInDirectory mockFilesInDirectory(listOfFiles) {
        def files = Mock(FilesInDirectory)
        files.iterator() >> listOfFiles.iterator()
        files
    }

    private ArgumentParser makeMockArgs(FilesInDirectory files,
                                        FileMover mover,
                                        FileRenamer renamer,
                                        FileTrimmer trimmer,
                                        SequenceNumber sequenceNumber) {
        def argParser = Mock(ArgumentParser)
        argParser.files >> files
        argParser.mover >> mover
        argParser.renamer >> renamer
        argParser.trimmer >> trimmer
        argParser.sequenceNumber >> sequenceNumber
        argParser
    }

    @SuppressWarnings("GroovyAccessibility")
    private void fileWithArgs(ArgumentParser args) {
        new Filer(args).fileWithSequenceNumber(42)
    }
}
