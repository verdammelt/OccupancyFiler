package OccupancyFiler.unit.file;


import OccupancyFiler.file.FilesInDirectory
import spock.lang.Specification

public class FilesInDirectoryTest extends Specification {
    def "exposes an iterator of the files in the directory"() {
        given:
        def directory = Mock(File)
        directory.listFiles() >> [new File('a'), new File('b'), new File('c')]

        expect:
        new FilesInDirectory(directory).collect { it.name } == ['a', 'b', 'c']
    }
}
