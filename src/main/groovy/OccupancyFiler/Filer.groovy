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
        withLoggedException {
            def arguments = new ArgumentParser(argv)

            if (arguments.helpWanted) {
                arguments.printUsage()
            } else {
                def sequenceNumber = new SequenceNumber(arguments.seqNumFile)
                def files = new FilesInDirectory(arguments.inputDirectory)
                def mover = new FileMover(arguments.outputDirectory)
                def environment = new DeployedEnvironment(arguments.environment)

                def renamer = new FileRenamer(environment, sequenceNumber, new YearSource())

                int retVal = new Filer().performFiling(files, mover, renamer, new FileTrimmer())

                sequenceNumber.commit()

                retVal
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

    int performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        files.each {
            log("processing ${it?.absolutePath}")
            trimmer.removeFirstLine(it)
            def newFile = renamer.rename(it)
            mover.move(newFile)
        }

        0
    }
}

