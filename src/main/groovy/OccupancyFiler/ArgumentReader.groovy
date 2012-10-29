package OccupancyFiler

class ArgumentReader {
    final File seqNumFile
    final File inputDirectory
    final File outputDirectory
    final String environment
    final boolean helpWanted

    int printUsage() {
        println "May given up to 4 arguments:"
        println "#1 - sequence number file (defaults to './seqNum.txt')"
        println "#2 - intput directory (defaults to './input')"
        println "#3 - output directory (defaults to './output')"
        println "#4 - environment (defaults to 'Staging_Occupancy')"
        0
    }

    ArgumentReader(String[] argv) {
        helpWanted = (argv.length == 1 && argv[0].equals('--helpWanted'))

        seqNumFile = new File(getOrDefault(argv, 0, 'seqNum.txt'))
        inputDirectory = new File(getOrDefault(argv, 1, 'input'))
        outputDirectory = new File(getOrDefault(argv, 2, 'output'))
        environment = getOrDefault(argv, 3, 'Staging_Occupancy')
    }

    String getOrDefault(String[] args, int argIdx, String defaultValue) {
        args.length > argIdx ? args[argIdx] : defaultValue
    }
}
