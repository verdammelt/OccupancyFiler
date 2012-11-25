package OccupancyFiler

class FileDeleter {
    private final File file

    FileDeleter(File file) {
        this.file = file
    }

    void delete() {
        file.delete()
    }
}
