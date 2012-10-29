package OccupancyFiler.file

class FileTrimmer {
    final int numLinesToSkip

    FileTrimmer(int numLinesToSkip) {
        this.numLinesToSkip = numLinesToSkip
    }

    File trimTopLines(File file) {
        def lines = file.readLines()
        if (lines.size() > numLinesToSkip) {
            file.text = lines[numLinesToSkip..-1].join('\n')
        } else {
            file.text = ""
        }
        file
    }
}
