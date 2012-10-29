package OccupancyFiler

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

class ArgumentParser {
    FilesInDirectory files
    FileMover mover
    FileRenamer renamer
    SequenceNumber sequenceNumber
    FileTrimmer trimmer

    ArgumentParser(ArgumentReader args) {
        this.files = new FilesInDirectory(args.inputDirectory)
        this.mover = new FileMover(args.outputDirectory)
        sequenceNumber = new SequenceNumber(args.seqNumFile)
        this.renamer = new FileRenamer(new DeployedEnvironment(args.environment),
                sequenceNumber, new YearSource())
        this.trimmer = new FileTrimmer()
    }
}
