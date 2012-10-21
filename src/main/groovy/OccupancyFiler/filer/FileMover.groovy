package OccupancyFiler.filer

class FileMover {
    void move(File file, File targetDirectory) {
        file.renameTo(new File(targetDirectory, file.name))
    }
}
