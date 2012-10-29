package OccupancyFiler.file

class FileTrimmer {
    final int numLinesToSkip

    FileTrimmer(int numLinesToSkip) {
        this.numLinesToSkip = numLinesToSkip
    }

    File trimTopLines(File file) {
        file.text = file.readLines().drop(numLinesToSkip).join('\n')
        file
    }
}
