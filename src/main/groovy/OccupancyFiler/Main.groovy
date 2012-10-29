package OccupancyFiler

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

import static OccupancyFiler.Logger.log

class Main {
    static void main(String[] argv) {
        withLoggedException {
            def arguments = new ArgumentReader(argv)

            if (arguments.helpWanted) {
                arguments.printUsage()
            } else {
                def sequenceNumber = new SequenceNumber(arguments.seqNumFile)
                def files = new FilesInDirectory(arguments.inputDirectory)
                def mover = new FileMover(arguments.outputDirectory)
                def environment = new DeployedEnvironment(arguments.environment)

                def renamer = new FileRenamer(environment, sequenceNumber, new YearSource())

                new Filer().performFiling(files, mover, renamer, new FileTrimmer())

                sequenceNumber.commit()
            }
        }
    }

    static def withLoggedException(Closure task) {
        try {
            task.call()
        } catch (Exception exception) {
            log("(Exception:${exception.class.simpleName}) ${exception.message}")
        }
    }
}
