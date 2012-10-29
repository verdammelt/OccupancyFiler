package OccupancyFiler.unit.arguments

import OccupancyFiler.arguments.ArgumentReader
import spock.lang.Specification

class ArgumentReaderTest extends Specification {

    def "if the only argument is --help - then raise the help flag"() {
        given:
        def args = new ArgumentReader(['--help'] as String[])

        expect:
        args.helpWanted
    }

    def "default values for all arguments"() {
        given:
        def emptyArgs = [] as String[]
        def args = new ArgumentReader(emptyArgs)

        expect:
        args.seqNumFile.path == new File("seqNum.txt").path
        args.inputDirectory.path == new File("input").path
        args.outputDirectory.path == new File("output").path
        args.environment == "Staging_Occupancy"
        args.numLinesToTrim == 1
    }

    def "reads values for arguments from the command line - in order"() {
        given:
        String[] cliArgs = ['fooSeq.txt', 'input-directory', 'output-directory', 'the_env', '56']
        def args = new ArgumentReader(cliArgs)

        expect:
        args.seqNumFile.path == new File('fooSeq.txt').path
        args.inputDirectory.path == new File('input-directory').path
        args.outputDirectory.path == new File('output-directory').path
        args.environment == 'the_env'
        args.numLinesToTrim == 56
    }
}
