package OccupancyFiler.filer

import spock.lang.Specification

class FileRenamerTest extends Specification {
    private File testFile

    def setup() {
        testFile = new File('foobar')
        testFile.createNewFile()
    }

    def cleanup() {
        testFile.delete()
        new File(".")
                .listFiles()
                .findAll { it.name.contains('Occupancy.Boston') }
                .each { it.delete() }
    }

    def "renames the given file to the correct format"() {
        given:
        testFile.createNewFile()
        def seqNum = Mock(SequenceNumber)
        seqNum.next() >> 2345
        def yearSource = Mock(YearSource)
        yearSource.thisYear >> 1970
        def environment = Mock(DeployedEnvironment)
        environment.name >> 'Production_Occupancy'

        when:
        def renamedFile = new FileRenamer(environment, seqNum, yearSource).rename(testFile)

        then:
        renamedFile.name == 'Production_Occupancy.Boston.00002345.1970.csv'
    }

    def "sets the file prefix correctly according to the environment"() {
        // staging vs. prod
    }
}
