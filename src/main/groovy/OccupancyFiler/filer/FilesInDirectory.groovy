package OccupancyFiler.filer

class FilesInDirectory implements Iterable<File> {
    private final File directory

    FilesInDirectory(File directory) {
        this.directory = directory
    }

    @Override
    Iterator<File> iterator() {
        directory.listFiles().iterator()
    }
}
