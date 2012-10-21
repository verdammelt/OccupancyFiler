package OccupancyFiler.filer;

import spock.lang.Specification;

public class OccupancyFilerTest extends Specification {
    def "moves all input files to the output directory"() {
        given:
        def files = mockFilesInDirectory([Mock(File), Mock(File)])
        def mover = Mock(FileMover)
        def outputDir = Mock(File)

        when:
        new OccupancyFiler().performFiling(files, outputDir, mover, Mock(FileRenamer))

        then:
        2 * mover.move(_, outputDir)
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
        new OccupancyFiler().performFiling(files, Mock(File), Mock(FileMover), renamer)

        then:
        1 * renamer.rename(new File('a'))
    }
}
