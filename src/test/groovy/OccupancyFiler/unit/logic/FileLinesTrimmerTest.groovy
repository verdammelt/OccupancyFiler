package OccupancyFiler.unit.logic

import OccupancyFiler.logic.FileLines
import OccupancyFiler.logic.FileLinesTrimmer
import spock.lang.Specification
import spock.lang.Unroll

class FileLinesTrimmerTest extends Specification {
    @Unroll("#numLines")
    def "trimTopLines trims the first lines - as many as requested from FileLines"(int numLines, List<String> expectedLines) {
        given:
        def testLines = new FileLines(['1', '2', '3'])
        def trimmer = new FileLinesTrimmer(numLines)

        expect:
        trimmer.trimTopLines(testLines) == new FileLines(expectedLines)

        where:
        numLines | expectedLines
        1        | ['2', '3']
        2        | ['3']
        3        | []
    }
}
