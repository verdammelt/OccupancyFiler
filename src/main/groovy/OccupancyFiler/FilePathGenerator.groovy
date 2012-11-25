package OccupancyFiler

import OccupancyFiler.file.NameGenerator

class FilePathGenerator {
    private final TargetDirectory targetDirectory
    private final NameGenerator nameGenerator

    FilePathGenerator(TargetDirectory targetDirectory, NameGenerator nameGenerator) {
        this.targetDirectory = targetDirectory
        this.nameGenerator = nameGenerator
    }

    String generatePath(int sequenceNumber) {
        new File(targetDirectory.path, nameGenerator.generateName(sequenceNumber))
    }
}
