package OccupancyFiler.unit.file

import OccupancyFiler.file.FileMover
import spock.lang.Specification
import OccupancyFiler.file.exception.OutputDirectoryNotFound

class FileMoverTest extends Specification {
    final File testOutput = new File('testOutput')
    final File testFile = new File("foo")

    def setup() {
        testOutput.mkdir()
        testFile.createNewFile()
    }

    def cleanup() {
        testFile.delete()
        testOutput.deleteDir()
    }

    def "can move a file from one directory to another"() {
        given:
        def mover = new FileMover(testOutput)

        when:
        mover.move(testFile)

        then:
        testOutput.listFiles()*.name.contains(testFile.name)
    }

    def "throws useful exception if target directory was not found"() {
        given:
        def file = Mock(File)
        file.exists() >> false

        when:
        new FileMover(file)

        then:
        thrown(OutputDirectoryNotFound)
    }
}
