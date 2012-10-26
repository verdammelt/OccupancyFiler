package OccupancyFiler

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

import static OccupancyFiler.Logger.log

class Filer {
    static void main(String[] argv) {
        validateArguments(argv)

        def sequenceNumber = new SequenceNumber(new File(argv[0]))
        def files = new FilesInDirectory(new File(argv[1]))
        def mover = new FileMover(new File(argv[2]))
        def environment = new DeployedEnvironment(argv[3])

        def renamer = new FileRenamer(environment, sequenceNumber, new YearSource())

        int retVal = new Filer().performFiling(files, mover, renamer, new FileTrimmer())

        sequenceNumber.commit()

        retVal
    }

    static void validateArguments(String[] argv) {
        if (argv.length != 4) {
            def message = """Must supply four arguments
                             1) sequence number file
                             2) input directory
                             3) output directory
                             4) environment (e.g Staging_Occupancy) """
            throw new IllegalArgumentException(message)
        }
    }

    int performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        files.each {
            log("processing ${it?.absolutePath}")
            trimmer.removeFirstLine(it)
            def newFile = renamer.rename(it)
            log("moving to ${newFile?.absoluteFile}")
            mover.move(newFile)
        }

        0
    }
}

