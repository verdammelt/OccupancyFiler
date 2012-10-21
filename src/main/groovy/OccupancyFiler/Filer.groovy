package OccupancyFiler

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

class Filer {
    static void main(String[] argv) {
        def sequenceNumber = new SequenceNumber(new File(argv[0]))
        def files = new FilesInDirectory(new File(argv[1]))
        def mover = new FileMover(new File(argv[2]))

        def renamer = new FileRenamer(new DeployedEnvironment(), sequenceNumber, new YearSource())

        int retVal = new Filer().performFiling(files, mover, renamer, new FileTrimmer())

        sequenceNumber.commit()

        retVal
    }

    int performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        files.each {
            trimmer.removeFirstLine(it)
            mover.move(renamer.rename(it))
        }

        0
    }
}

