package OccupancyFiler

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
        def reader = new FileReader(testFile)

        expect:
        reader.read() == new FileLines(lines)
    }

    def "empty file has no lines"() {
        given:
        setupFileWithLines([])
        def reader = new FileReader(testFile)

        expect:
        reader.read() == new FileLines([])
    }

    def "non-existant file has no lines"() {
        given:
        def reader = new FileReader(new File('bogus file'))
        expect:
        reader.read() == new FileLines([])
    }

    void setupFileWithLines(List<String> lines) {
        testFile.text = lines.join('\n')
    }
}
