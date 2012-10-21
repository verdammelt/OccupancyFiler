package OccupancyFiler.filer

class OccupancyFiler {
    static int main(String[] argv) {
        def files = new FilesInDirectory(new File(argv[1]))
        def mover = new FileMover(new File(argv[2]))

        new OccupancyFiler().performFiling(files, mover, new FileRenamer(new SequenceNumber()))
    }

    int performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer) {
        files.each { mover.move(renamer.rename(it)) }

        0
    }
}

