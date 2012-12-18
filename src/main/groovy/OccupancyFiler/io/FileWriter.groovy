package OccupancyFiler.io

import OccupancyFiler.logic.FileLines

class FileWriter {
    File write(String pathName, FileLines lines) {
        def file = new File(pathName)
        file.text = lines.toString()
        file
    }
}
