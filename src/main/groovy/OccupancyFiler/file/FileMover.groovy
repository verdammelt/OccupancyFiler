package OccupancyFiler.file

import OccupancyFiler.file.exception.OutputDirectoryNotFound

import static OccupancyFiler.Logger.log
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
        def newFile = new File(targetDirectory, file.name)
        log("moving to ${newFile.absolutePath}")
        file.renameTo(newFile)
    }
}
