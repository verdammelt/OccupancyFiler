package OccupancyFiler.unit.file

import OccupancyFiler.file.FileTrimmer
import spock.lang.Specification
import spock.lang.Unroll

class FileTrimmerTest extends Specification {
    private final testFile = new File('foo')

    def cleanup() {
        testFile.delete()
    }

    @Unroll("#numLines")
    def "trimTopLines trims the first lines - as many as requested"(int numLines, String expectedText) {
        given:
        testFile.text = "1\n2\n3"
        def trimmer = new FileTrimmer(numLines)

        expect:
        trimmer.trimTopLines(testFile).text == expectedText

        where:
        numLines | expectedText
        0 | "1\n2\n3"
        1 | "2\n3"
        2 | "3"
        3 | ""
        4 | ""
        500 | ""
    }
}
