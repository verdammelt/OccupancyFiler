package OccupancyFiler.io

import OccupancyFiler.io.exception.InputDirectoryNotFound

import static FileEnsurer.ifDoesNotExist

class FilesInDirectory implements Iterable<File> {
    private final File directory

    @SuppressWarnings("GroovyUnreachableStatement")
    FilesInDirectory(File directory) {
        this.directory = ifDoesNotExist(directory) { File file ->
            throw new InputDirectoryNotFound(file)
            file
        }
    }

    @Override
    Iterator<File> iterator() {
        directory.listFiles().iterator()
    }
}
