package OccupancyFiler.file

import OccupancyFiler.FileLines

class FileTrimmer {
    final int numLinesToSkip

    FileTrimmer(int numLinesToSkip) {
        this.numLinesToSkip = numLinesToSkip
    }

    File trimTopLines(File file) {
        file.text = file.readLines().drop(numLinesToSkip).join('\n')
        file
    }

    FileLines trimTopLines(FileLines lines) {
        new FileLines(lines.lines.drop(numLinesToSkip))
    }
}
