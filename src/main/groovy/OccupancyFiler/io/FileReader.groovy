package OccupancyFiler.io

import OccupancyFiler.logic.FileLines

class FileReader {
    FileLines read(File file) {
        new FileLines(file.exists() ? file.readLines() : [])
    }
}
