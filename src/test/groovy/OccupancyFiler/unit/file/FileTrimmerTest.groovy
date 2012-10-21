package OccupancyFiler.unit.file

import OccupancyFiler.file.FileTrimmer
import spock.lang.Specification

class FileTrimmerTest extends Specification {
    private final testFile = new File('foo')

    def clean() {
        testFile.delete()
    }

    def "removeFirstLine works as advertised"() {
        given:
        testFile.text = "a\nb\nc"

        when:
        new FileTrimmer().removeFirstLine(testFile)

        then:
        testFile.text == 'b\nc'
    }
}
