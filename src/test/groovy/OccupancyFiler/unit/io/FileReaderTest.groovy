package OccupancyFiler.unit.io

import OccupancyFiler.io.FileReader
import OccupancyFiler.logic.FileLines
import spock.lang.Specification

class FileReaderTest extends Specification {

    private final File testFile = new File('testfile')

    def cleanup() {
        testFile.delete()
    }

    def "reads the lines from the file"() {
        def lines = ['a', 'b', 'c']
        given:
        setupFileWithLines(lines)
        expect:
        new FileReader().read(testFile) == new FileLines(lines)
    }

    def "empty file has no lines"() {
        given:
        setupFileWithLines([])
        expect:
        new FileReader().read(testFile) == new FileLines([])
    }

    def "non-existant file has no lines"() {
        expect:
        new FileReader().read(new File('bogus file')) == new FileLines([])
    }

    void setupFileWithLines(List<String> lines) {
        testFile.text = lines.join('\n')
    }
}
