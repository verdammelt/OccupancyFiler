package OccupancyFiler.file

class FileTrimmer {
    void removeFirstLine(File file) {
        file.text = file.readLines()[1..-1].join('\n')
    }
}
