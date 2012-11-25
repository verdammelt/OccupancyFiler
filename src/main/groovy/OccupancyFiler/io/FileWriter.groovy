package OccupancyFiler.io

import OccupancyFiler.logic.FileLines

class FileWriter {
    void write(String pathName, FileLines lines) {
        new File(pathName).text = lines.lines.join('\n')
    }
}
