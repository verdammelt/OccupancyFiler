package OccupancyFiler.filer;

import spock.lang.Specification;

public class OccupancyFilerTest extends Specification {
    def "moves all input files to the output directory"() {
        given:
        def files = mockFilesInDirectory([Mock(File), Mock(File)])
        def mover = Mock(FileMover)
        def renamer = Mock(FileRenamer)
        renamer.rename(_) >> new File('renamed')

        when:
        new OccupancyFiler().performFiling(files, mover, renamer)

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

        when:
        new OccupancyFiler().performFiling(files, Mock(FileMover), renamer)

        then:
        1 * renamer.rename(new File('a'))
    }
}
