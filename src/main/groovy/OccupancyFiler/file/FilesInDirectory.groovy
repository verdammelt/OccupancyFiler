package OccupancyFiler.file

import OccupancyFiler.file.exception.InputDirectoryNotFound

import static OccupancyFiler.file.FileExists.ensureFile

class FilesInDirectory implements Iterable<File> {
    private final File directory

    @SuppressWarnings("GroovyUnreachableStatement")
    FilesInDirectory(File directory) {
        this.directory = ensureFile(directory) { File file ->
            throw new InputDirectoryNotFound(file)
            file
        }
    }

    @Override
    Iterator<File> iterator() {
        directory.listFiles().iterator()
    }
}
