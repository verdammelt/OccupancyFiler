package OccupancyFiler.filer

import spock.lang.Specification

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
}
