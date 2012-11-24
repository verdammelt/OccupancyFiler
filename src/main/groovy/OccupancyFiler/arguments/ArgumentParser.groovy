package OccupancyFiler.arguments

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.SequenceNumberFormatter
import OccupancyFiler.environment.YearSource
import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

class ArgumentParser {
    private final FilesInDirectory files
    private final FileMover mover
    private final FileRenamer renamer
    private final SequenceNumber sequenceNumber
    private final FileTrimmer trimmer

    ArgumentParser(ArgumentReader args) {
        this.files = new FilesInDirectory(args.inputDirectory)
        this.mover = new FileMover(args.outputDirectory)
        this.sequenceNumber = new SequenceNumber(args.seqNumFile)
        this.renamer = new FileRenamer(new DeployedEnvironment(args.environment),
                new YearSource(),
                new SequenceNumberFormatter(sequenceNumber.next()))
        this.trimmer = new FileTrimmer(args.numLinesToTrim)
    }

    FilesInDirectory getFiles() { files }
    FileMover getMover() { mover }
    FileRenamer getRenamer() { renamer }
    SequenceNumber getSequenceNumber() { sequenceNumber }
    FileTrimmer getTrimmer() { trimmer }
}
