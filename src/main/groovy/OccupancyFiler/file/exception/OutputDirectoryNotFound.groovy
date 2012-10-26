package OccupancyFiler.file.exception

class OutputDirectoryNotFound extends Exception {
    OutputDirectoryNotFound(File directory) {
        super("Output directory '${directory.name}' was not found")
    }

}
