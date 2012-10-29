package OccupancyFiler.unit.environment

import OccupancyFiler.environment.DeployedEnvironment
import spock.lang.Specification

class DeployedEnvironmentTest extends Specification {
    def "stores name"() {
        expect:
        new DeployedEnvironment("foo").name == "foo"
    }
}
