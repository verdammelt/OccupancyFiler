package OccupancyFiler.unit.environment

import OccupancyFiler.environment.SequenceNumber
import spock.lang.Specification

class SequenceNumberTest extends Specification {
    final File testFile = new File('foobar')
    final File otherFile = new File('otherFile')

    def cleanup() {
        testFile.delete()
        otherFile.delete()
    }

    def "initializes the sequence number from the given file"() {
        given:
        testFile.text = '42'
        def seqNum = new SequenceNumber(testFile)

        expect:
        seqNum.next() == 43
    }

    def "calling next multiple times returns the same value each time"() {
        given:
        testFile.text = '42'
        def seqNum = new SequenceNumber(testFile)

        when:
        seqNum.next()
        seqNum.next()

        then:
        seqNum.next() == 43
    }

    def "new number can be committed"() {
        given:
        testFile.text = '42'
        def seqNum = new SequenceNumber(testFile)

        when:
        seqNum.next()
        seqNum.commit()

        then:
        testFile.text == '43'
    }

    def "creates sequence number file if it does not exist and sequence number starts at zero"() {
        given:
        otherFile.delete()

        when:
        new SequenceNumber(otherFile).commit()

        then:
        otherFile.text == '1'
    }
}
