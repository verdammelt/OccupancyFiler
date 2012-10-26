package OccupancyFiler.utils

import spock.lang.Specification

import static OccupancyFiler.file.FileExists.ensureFile

class FileExistsTest extends Specification {
    def "closure called if file does not exist"() {
        given:
        def called = false
        def nonExistant = Mock(File)
        nonExistant.exists() >> false

        when:
        ensureFile(nonExistant) { File file -> called = true; file }

        then:
        called
    }

    def "closure not called if file exists"() {
        given:
        def called = false
        def existant = Mock(File)
        existant.exists() >> true

        when:
        ensureFile(existant) { File file -> called = true; file }

        then:
        !called
    }

    def "returns the result of the closure"() {
        given:
        def nonExistant = Mock(File)
        nonExistant.exists() >> false
        def otherFile = Mock(File)

        expect:
        ensureFile(nonExistant) { File file -> otherFile} == otherFile
    }
}
