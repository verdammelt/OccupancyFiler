package OccupancyFiler.unit.logic

import OccupancyFiler.arguments.TargetDirectory
import OccupancyFiler.logic.FilePathGenerator
import OccupancyFiler.logic.NameGenerator
import spock.lang.Specification

class FilePathGeneratorTest extends Specification {
    def "combines the target directory with the new filename"() {
        given:
        def targetDirectory = new TargetDirectory('/tmp/foo')
        def nameGenerator = Mock(NameGenerator)
        nameGenerator.generateName(42) >> 'theFileName.42'

        def generator = new FilePathGenerator(targetDirectory, nameGenerator)

        when:
        def path = generator.generatePath(42)

        then:
        path == '/tmp/foo/theFileName.42'
    }
}
