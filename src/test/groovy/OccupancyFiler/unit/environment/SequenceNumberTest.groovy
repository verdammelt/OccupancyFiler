package OccupancyFiler.unit.environment

import OccupancyFiler.io.FileWriter
import OccupancyFiler.logic.FileLines
import OccupancyFiler.logic.SequenceNumber
import spock.lang.Specification

class SequenceNumberTest extends Specification {
    final File testFile = new File('foobar')
    final String testFilePath = "/tmp/foobar"

    @SuppressWarnings("GroovyResultOfAssignmentUsed")
    def "creates sequence number file if it does not exist and sequence number starts at zero"() {
        given:
        def writer = Mock(FileWriter)
        def seqNum = new SequenceNumber(testFilePath, new FileLines([]), writer)

        int caughtNumber = -1

        when:
        seqNum.doWithNextNumber { int number ->
            caughtNumber = number
        }

        then:
        caughtNumber == 1
        1 * writer.write(testFilePath, new FileLines(['1']))
    }

    @SuppressWarnings("GroovyResultOfAssignmentUsed")
    def "initializes the sequence number from the given file and commits it after use"() {
        given:
        def writer = Mock(FileWriter)
        def seqNum = new SequenceNumber(testFilePath, new FileLines(['42']), writer)

        int caughtNumber = -1

        when:
        seqNum.doWithNextNumber { int number ->
            caughtNumber = number
        }

        then:
        caughtNumber == 43
        1 * writer.write(testFilePath, new FileLines(['43']))
    }

    @SuppressWarnings(["GroovyUnusedCatchParameter", "GroovyEmptyCatchBlock", "GroovyLocalVariableNamingConvention"])
    def "does not commit if task throws an exception"() {
        given:
        def writer = Mock(FileWriter)
        def seqNum = new SequenceNumber(testFilePath, new FileLines(['13']), writer)
        def workToDo = { int unused -> throw new Exception("boom") }

        when:
        try { seqNum.doWithNextNumber workToDo } catch (Exception) {}

        then:
        0 * writer.write(_,_)
    }
}
