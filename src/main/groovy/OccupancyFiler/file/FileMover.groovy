package OccupancyFiler.file

class FileMover {
    private final File targetDirectory

    FileMover(File targetDirectory) {
        this.targetDirectory = targetDirectory
    }
    void move(File file) {
        file.renameTo(new File(targetDirectory, file.name))
    }
}
