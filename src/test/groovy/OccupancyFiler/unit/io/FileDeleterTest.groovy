package OccupancyFiler.unit.io

import OccupancyFiler.io.FileDeleter
import spock.lang.Specification

class FileDeleterTest extends Specification {
    private final File testFile = new File('testFile')

    void setup() {
        testFile.text = "create the file"
    }

    void cleanup() {
        testFile.delete()
    }

    def "deletes the file"() {
        when:
        new FileDeleter().delete(testFile)

        then:
        !testFile.exists()
    }

    def "does not complain if file doesn't exist"() {
        given:
        def nonExistantFile = new File("does not exist")

        when:
        new FileDeleter().delete(nonExistantFile)

        then:
        noExceptionThrown()
        !nonExistantFile.exists()
    }

    def "does not complain if the file is null"() {
        when:
        new FileDeleter().delete(null)

        then:
        noExceptionThrown()
    }
}
