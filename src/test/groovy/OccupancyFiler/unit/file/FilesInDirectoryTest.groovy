package OccupancyFiler.unit.file;


import OccupancyFiler.file.FilesInDirectory
import spock.lang.Specification
import OccupancyFiler.file.exception.InputDirectoryNotFound

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
