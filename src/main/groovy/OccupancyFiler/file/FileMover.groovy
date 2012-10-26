package OccupancyFiler.file

import OccupancyFiler.file.exception.OutputDirectoryNotFound

import static OccupancyFiler.file.FileExists.ensureFile

class FileMover {
    private final File targetDirectory

    @SuppressWarnings("GroovyUnreachableStatement")
    FileMover(File targetDirectory) {
        this.targetDirectory = ensureFile(targetDirectory) {File file ->
            throw new OutputDirectoryNotFound(file)
            file
        }
    }
    void move(File file) {
        file.renameTo(new File(targetDirectory, file.name))
    }
}
