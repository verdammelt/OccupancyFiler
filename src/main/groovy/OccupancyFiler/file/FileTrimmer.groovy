package OccupancyFiler.file

class FileTrimmer {
    File removeFirstLine(File file) {
        file.text = file.readLines()[1..-1].join('\n')
        file
    }
}
