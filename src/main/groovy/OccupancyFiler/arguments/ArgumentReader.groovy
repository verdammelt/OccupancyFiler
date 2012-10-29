package OccupancyFiler.arguments

import OccupancyFiler.utilities.DefaultingCollection

class ArgumentReader {
    final File seqNumFile
    final File inputDirectory
    final File outputDirectory
    final String environment
    final boolean helpWanted
    final int numLinesToTrim

    int printUsage() {
        println "May given up to 4 arguments:"
        println "#1 - sequence number file (defaults to './seqNum.txt')"
        println "#2 - intput directory (defaults to './input')"
        println "#3 - output directory (defaults to './output')"
        println "#4 - environment (defaults to 'Staging_Occupancy')"
        println "#5 - number of lines to trim from top (defaults to 1)"
        0
    }

    ArgumentReader(String[] argv) {
        def args = new DefaultingCollection<String>(argv.toList())

        helpWanted = args.getAt(0, '').equals('--help')

        seqNumFile = new File(args.getAt(0, 'seqNum.txt'))
        inputDirectory = new File(args.getAt(1, 'input'))
        outputDirectory = new File(args.getAt(2, 'output'))
        environment = args.getAt(3, 'Staging_Occupancy')
        numLinesToTrim = Integer.parseInt(args.getAt(4, '1'))
    }
}
