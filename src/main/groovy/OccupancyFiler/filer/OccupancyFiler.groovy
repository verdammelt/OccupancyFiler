package OccupancyFiler.filer

class OccupancyFiler {
    static int main(String[] argv) {
        def inputDir = new File(argv[1])
        def outputDir = new File(argv[2])

        def files = new FilesInDirectory(inputDir)
        def mover = new FileMover()

        new OccupancyFiler().performFiling(files, outputDir, mover, new FileRenamer())
    }

    int performFiling(FilesInDirectory files, File outputDir, FileMover mover, FileRenamer renamer) {
        files.each { mover.move(renamer.rename(it), outputDir) }

        0
    }
}

