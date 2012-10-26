package OccupancyFiler.file

import OccupancyFiler.file.exception.InputDirectoryNotFound

class FilesInDirectory implements Iterable<File> {
    private final File directory

    @SuppressWarnings("GroovyUnreachableStatement")
    FilesInDirectory(File directory) {
        this.directory = FileExists.ensure(directory) { File file ->
            throw new InputDirectoryNotFound(file)
            file
        }
    }

    @Override
    Iterator<File> iterator() {
        directory.listFiles().iterator()
    }
}
