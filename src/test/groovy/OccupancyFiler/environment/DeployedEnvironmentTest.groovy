package OccupancyFiler.environment

import spock.lang.Specification

class DeployedEnvironmentTest extends Specification {
    def "stores name"() {
        expect:
        new DeployedEnvironment("foo").name == "foo"
    }
}
