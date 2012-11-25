package OccupancyFiler.logic

class FileLinesTrimmer {
    final int numLinesToSkip

    FileLinesTrimmer(int numLinesToSkip) {
        this.numLinesToSkip = numLinesToSkip
    }

    FileLines trimTopLines(FileLines lines) {
        new FileLines(lines.lines.drop(numLinesToSkip))
    }
}
