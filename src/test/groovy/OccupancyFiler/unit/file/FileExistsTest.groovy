package OccupancyFiler.unit.file

import spock.lang.Specification

import static OccupancyFiler.file.FileEnsurer.ifDoesNotExist

class FileExistsTest extends Specification {
    def "closure called if file does not exist"() {
        given:
        def called = false
        def nonExistant = Mock(File)
        nonExistant.exists() >> false

        when:
        ifDoesNotExist(nonExistant) { File file -> called = true; file }

        then:
        called
    }

    def "closure not called if file exists"() {
        given:
        def called = false
        def existant = Mock(File)
        existant.exists() >> true

        when:
        ifDoesNotExist(existant) { File file -> called = true; file }

        then:
        !called
    }

    def "returns the result of the closure"() {
        given:
        def nonExistant = Mock(File)
        nonExistant.exists() >> false
        def otherFile = Mock(File)

        expect:
        ifDoesNotExist(nonExistant) { File file -> otherFile} == otherFile
    }
}
