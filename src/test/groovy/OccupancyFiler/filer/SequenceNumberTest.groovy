package OccupancyFiler.filer

import spock.lang.Specification

class SequenceNumberTest extends Specification {
    final File testFile = new File('foobar')

    def cleanup() {
        testFile.delete()
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
}
