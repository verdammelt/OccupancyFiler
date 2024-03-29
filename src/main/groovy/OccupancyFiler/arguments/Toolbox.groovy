package OccupancyFiler.arguments

import OccupancyFiler.arguments.environment.DeployedEnvironment
import OccupancyFiler.arguments.environment.YearSource
import OccupancyFiler.io.FileDeleter
import OccupancyFiler.io.FileReader
import OccupancyFiler.io.FileWriter
import OccupancyFiler.io.FilesInDirectory
import OccupancyFiler.logic.*

class Toolbox {
    private final FilesInDirectory files
    private final SequenceNumber sequenceNumber
    private final FileLinesTrimmer trimmer
    private final FileReader reader
    private final FileWriter writer
    private final FileDeleter deleter
    private final FilePathGenerator filePathGenerator

    Toolbox(ArgumentReader args) {
        NameGenerator nameGenerator =
            new NameGenerator(new DeployedEnvironment(args.environment),
                    new YearSource(),
                    new SequenceNumberFormatter())
        TargetDirectory targetDirectory =
            new TargetDirectory(args.outputDirectory.absolutePath)

        this.files = new FilesInDirectory(args.inputDirectory)
        this.reader = new FileReader()
        this.writer = new FileWriter()

        this.sequenceNumber = new SequenceNumber(args.seqNumFile.absolutePath,
                this.reader.read(args.seqNumFile),
                this.writer)
        this.trimmer = new FileLinesTrimmer(args.numLinesToTrim)
        this.deleter = new FileDeleter()
        this.filePathGenerator = new FilePathGenerator(targetDirectory, nameGenerator)
    }

    FilesInDirectory getFiles() { files }

    SequenceNumber getSequenceNumber() { sequenceNumber }

    FileLinesTrimmer getTrimmer() { trimmer }

    FileReader getReader() { reader }

    FileWriter getWriter() { writer }

    FileDeleter getDeleter() { deleter }

    FilePathGenerator getFilePathGenerator() { filePathGenerator }
}
