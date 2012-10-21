package OccupancyFiler

import OccupancyFiler.filer.*

class OccupancyFiler {
    static int main(String[] argv) {
        def sequenceNumber = new SequenceNumber(new File(argv[0]))
        def files = new FilesInDirectory(new File(argv[1]))
        def mover = new FileMover(new File(argv[2]))

        def renamer = new FileRenamer(new DeployedEnvironment(), sequenceNumber, new YearSource())

        new OccupancyFiler().performFiling(files, mover, renamer, new FileTrimmer())
    }

    int performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        files.each {
            trimmer.removeFirstLine(it)
            mover.move(renamer.rename(it))
        }

        0
    }
}

