package OccupancyFiler.io.exception

class InputDirectoryNotFound extends Exception {
    InputDirectoryNotFound(File directory) {
        super("Input directory '${directory.name}' was not found")
    }
}
