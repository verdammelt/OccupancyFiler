package OccupancyFiler

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
        new FileDeleter(testFile).delete()

        then:
        !testFile.exists()
    }

    def "does not complain if file doesn't exist"() {
        given:
        def nonExistantFile = new File("does not exist")

        when:
        new FileDeleter(nonExistantFile).delete()

        then:
        noExceptionThrown()
        !nonExistantFile.exists()
    }
}
