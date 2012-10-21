package OccupancyFiler.filer

import spock.lang.Specification

class FileRenamerTest extends Specification {

    def cleanup() {
        new File(".")
                .listFiles()
                .findAll { it.name.startsWith('Staging_Occupancy.Boston') }
                .each { it.delete() }
    }

    def "renames the given file to the correct format"() {
        given:
        def file = new File('foobar')
        file.createNewFile()
        def seqNum = Mock(SequenceNumber)
        seqNum.next() >> 2345

        when:
        def renamedFile = new FileRenamer(seqNum).rename(file)

        then:
        renamedFile.name == 'Staging_Occupancy.Boston.00002345.2012.csv'
    }

    def "sets the year token to the current year"() { }

    def "sets the file prefix correctly according to the environment"() {
        // staging vs. prod
    }
}
