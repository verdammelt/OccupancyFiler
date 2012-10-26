package OccupancyFiler.file

import OccupancyFiler.file.exception.OutputDirectoryNotFound

class FileMover {
    private final File targetDirectory

    @SuppressWarnings("GroovyUnreachableStatement")
    FileMover(File targetDirectory) {
        this.targetDirectory = FileExists.ensure(targetDirectory) {File file ->
            throw new OutputDirectoryNotFound(file)
            file
        }
    }
    void move(File file) {
        file.renameTo(new File(targetDirectory, file.name))
    }
}
