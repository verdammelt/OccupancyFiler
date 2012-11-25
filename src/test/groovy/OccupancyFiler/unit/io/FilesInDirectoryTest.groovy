package OccupancyFiler.unit.io;


import OccupancyFiler.io.FilesInDirectory
import OccupancyFiler.io.exception.InputDirectoryNotFound
import spock.lang.Specification

public class FilesInDirectoryTest extends Specification {
    def "exposes an iterator of the files in the directory"() {
        given:
        def directory = Mock(File)
        directory.listFiles() >> [new File('a'), new File('b'), new File('c')]
        directory.exists() >> true

        expect:
        new FilesInDirectory(directory).collect { it.name } == ['a', 'b', 'c']
    }

    def "throws useful exception of directory does not exist"() {
        when:
        new FilesInDirectory(new File("does not exist"))

        then:
        def exception = thrown(InputDirectoryNotFound)
        exception.message.contains("does not exist")
    }
}
