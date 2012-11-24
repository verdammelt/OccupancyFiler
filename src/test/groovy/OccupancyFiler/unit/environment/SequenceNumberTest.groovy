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

    def "number not changed if commit called but not next"() {
        given:
        testFile.text = '42'
        def seqNum = new SequenceNumber(testFile)

        when:
        seqNum.commit()

        then:
        testFile.text == '42'
    }

    def "creates sequence number file if it does not exist and sequence number starts at zero"() {
        given:
        otherFile.delete()

        when:
        new SequenceNumber(otherFile).commit()

        then:
        otherFile.text == '0'
    }

    @SuppressWarnings("GroovyResultOfAssignmentUsed")
    def "calls the given closure with the next sequence number and commits when done"() {
        given:
        testFile.text = "13"
        def seqNum = new SequenceNumber(testFile)
        def capturedSequenceNumber = -1
        def workToDo = {int sequenceNumber -> capturedSequenceNumber = sequenceNumber }

        when:
        seqNum.doWithNextNumber workToDo

        then:
        capturedSequenceNumber == 14
        seqNum.next() == 15
    }

    @SuppressWarnings(["GroovyUnusedCatchParameter", "GroovyEmptyCatchBlock", "GroovyLocalVariableNamingConvention"])
    def "does not commit if task throws an exception"() {
        given:
        testFile.text = "13"
        def seqNum = new SequenceNumber(testFile)
        def workToDo = { int unused -> throw new Exception("boom") }

        when:
        try { seqNum.doWithNextNumber workToDo } catch (Exception) {}

        then:
        seqNum.next() == 14
    }
}
