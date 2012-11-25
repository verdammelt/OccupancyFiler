package OccupancyFiler

class FileReader {
    private final File file

    FileReader(File file) {
        this.file = file
    }

    FileLines read() {
        new FileLines(file.exists() ? file.readLines() : [])
    }
}
