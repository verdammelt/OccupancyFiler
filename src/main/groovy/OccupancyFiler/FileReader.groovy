package OccupancyFiler

class FileReader {
    FileLines read(File file) {
        new FileLines(file.exists() ? file.readLines() : [])
    }
}
