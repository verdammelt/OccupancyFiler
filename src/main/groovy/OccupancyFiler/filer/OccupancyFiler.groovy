package OccupancyFiler.filer

class OccupancyFiler {
    static int main(String[] argv) {
        def inputDir = new File(argv[1])
        def outputDir = new File(argv[2])

        def files = new FilesInDirectory(inputDir)
        def mover = new FileMover()

        files.each { mover.move(it, outputDir) }

        0
    }
}

