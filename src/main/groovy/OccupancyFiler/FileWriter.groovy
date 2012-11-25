package OccupancyFiler

class FileWriter {
    void write(String pathName, FileLines lines) {
        new File(pathName).text = lines.lines.join('\n')
    }
}
