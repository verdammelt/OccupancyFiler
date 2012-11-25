package OccupancyFiler.unit.io

import OccupancyFiler.io.FileWriter
import OccupancyFiler.logic.FileLines
import spock.lang.Specification

class FileWriterTest extends Specification {
    private final static String TEST_FILE_NAME = 'textfile.txt'

    def cleanup() {
        new File(TEST_FILE_NAME).delete()
    }

    def "writes lines to file with given name"() {
        def lines = ['a', 'b', 'c']
        when:
        new FileWriter().write(TEST_FILE_NAME, new FileLines(lines))

        then:
        new File(TEST_FILE_NAME).readLines() == lines
    }

    def "overwrites already existing file"() {
        given:
        new File(TEST_FILE_NAME).text = 'old text'

        when:
        new FileWriter().write(TEST_FILE_NAME, new FileLines([]))

        then:
        new File(TEST_FILE_NAME).text == ''
    }
}
