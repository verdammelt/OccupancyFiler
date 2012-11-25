package OccupancyFiler.unit.arguments

import spock.lang.Specification

import static OccupancyFiler.arguments.TargetDirectory.target

class TargetDirectoryTest extends Specification {
    def "holds onto the path"() {
        expect:
        target('/tmp/foo').path == '/tmp/foo'
    }

    def "defaults to ."() {
        expect:
        target(null).path == '.'
        target('').path == '.'
    }
}
