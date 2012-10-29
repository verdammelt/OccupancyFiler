package OccupancyFiler.unit.environment

import OccupancyFiler.environment.SequenceNumberFormatter
import spock.lang.Specification
import spock.lang.Unroll

class SequenceNumberFormatterTest extends Specification {
    @Unroll("#seqNum formats to '#formattedValue'")
    def "formats correctly"() {
        given:
        def formatter = new SequenceNumberFormatter(seqNum)

        expect:
        formatter.format() == formattedValue

        where:
        seqNum     | formattedValue
        0          | '00000000'
        1          | '00000001'
        10         | '00000010'
        100        | '00000100'
        12345678   | '12345678'
        1234567890 | '1234567890'
    }
}
