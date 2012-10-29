package OccupancyFiler.unit;


import OccupancyFiler.ArgumentParser
import OccupancyFiler.Filer
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory
import spock.lang.Specification

public class FilerTest extends Specification {
    def "moves all input files to the output directory"() {
        given:
        def files = mockFilesInDirectory([Mock(File), Mock(File)])
        def mover = Mock(FileMover)
        def renamer = Mock(FileRenamer)
        renamer.rename(_) >> new File('renamed')

        when:
        fileWithArgs(makeMockArgs(files, mover, renamer, Mock(FileTrimmer)))

        then:
        2 * mover.move(new File('renamed'))
    }

    def "renames each file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def renamer = Mock(FileRenamer)
        def trimmer = Mock(FileTrimmer)
        trimmer.removeFirstLine(_) >> {File file -> file}

        when:
        fileWithArgs(makeMockArgs(files, Mock(FileMover), renamer, trimmer))

        then:
        1 * renamer.rename(new File('a'))
    }

    def "chops the header off the file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def trimmer = Mock(FileTrimmer)

        when:
        fileWithArgs(makeMockArgs(files, Mock(FileMover), Mock(FileRenamer), trimmer))

        then:
        1 * trimmer.removeFirstLine(new File('a'))
    }

    private FilesInDirectory mockFilesInDirectory(listOfFiles) {
        def files = Mock(FilesInDirectory)
        files.iterator() >> listOfFiles.iterator()
        files
    }

    private ArgumentParser makeMockArgs(FilesInDirectory files,
                                        FileMover mover,
                                        FileRenamer renamer,
                                        FileTrimmer trimmer) {
        def args = Mock(ArgumentParser)
        args.files >> files
        args.mover >> mover
        args.renamer >> renamer
        args.trimmer >> trimmer
        args
    }

    private void fileWithArgs(ArgumentParser args) {
        new Filer(args).performFiling()
    }
}
