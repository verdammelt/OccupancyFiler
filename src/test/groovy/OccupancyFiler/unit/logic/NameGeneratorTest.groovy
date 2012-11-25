package OccupancyFiler.unit.logic

import OccupancyFiler.arguments.environment.DeployedEnvironment
import OccupancyFiler.arguments.environment.YearSource
import OccupancyFiler.logic.NameGenerator
import OccupancyFiler.logic.SequenceNumberFormatter
import spock.lang.Specification

class NameGeneratorTest extends Specification {
    def "produces new name for the file"() {
        given:
        int seqNum = 2345
        def yearSource = Mock(YearSource)
        yearSource.thisYear >> 1970
        def environment = new DeployedEnvironment('Deployed_Occupancy')
        def formatter = Mock(SequenceNumberFormatter)
        formatter.format(seqNum) >> 'xxxx2345'

        when:
        def name = new NameGenerator(environment, yearSource, formatter).generateName(seqNum)

        then:
        name == 'Deployed_Occupancy.Boston.xxxx2345.1970.csv'
    }
}
