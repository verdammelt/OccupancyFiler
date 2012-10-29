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

    void performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        def process =
            {File file -> log("processing ${file?.absolutePath}"); file } >>
                    trimmer.&removeFirstLine >>
                    {File file -> log("trimmed..."); file } >>
                    renamer.&rename >>
                    {File file -> log("renamed to ${file?.name}"); file } >>
                    mover.&move >>
                    { File file -> log("moved to ${file?.absolutePath}"); file }

        files.each process
    }
}

