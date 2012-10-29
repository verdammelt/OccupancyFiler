package OccupancyFiler.unit;


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
        new Filer().performFiling(files, mover, renamer, Mock(FileTrimmer))

        then:
        2 * mover.move(new File('renamed'))
    }

    FilesInDirectory mockFilesInDirectory(listOfFiles) {
        def files = Mock(FilesInDirectory)
        files.iterator() >> listOfFiles.iterator()
        files
    }

    def "renames each file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def renamer = Mock(FileRenamer)
        def trimmer = Mock(FileTrimmer)
        trimmer.removeFirstLine(_) >> {File file -> file}

        when:
        new Filer().performFiling(files, Mock(FileMover), renamer, trimmer)

        then:
        1 * renamer.rename(new File('a'))
    }

    def "chops the header off the file"() {
        given:
        def files = mockFilesInDirectory([new File('a')])
        def trimmer = Mock(FileTrimmer)

        when:
        new Filer().performFiling(files, Mock(FileMover), Mock(FileRenamer), trimmer)

        then:
        1 * trimmer.removeFirstLine(new File('a'))
    }
}
