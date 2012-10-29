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

    File move(File file) {
        def newFile = new File(targetDirectory, file.name)
        file.renameTo(newFile)
        newFile
    }
}
