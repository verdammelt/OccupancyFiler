package OccupancyFiler.unit.file

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumberFormatter
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileRenamer
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
        def yearSource = Mock(YearSource)
        yearSource.thisYear >> 1970
        def environment = new DeployedEnvironment('Production_Occupancy')

        when:
        def renamedFile = new FileRenamer(environment, yearSource, new SequenceNumberFormatter()).rename(2345, testFile)

        then:
        renamedFile.name == 'Production_Occupancy.Boston.00002345.1970.csv'
    }
}
