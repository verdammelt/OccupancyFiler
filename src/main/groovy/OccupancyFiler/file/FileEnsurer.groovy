package OccupancyFiler.file

class FileEnsurer {
    static File ifDoesNotExist(File file, Closure<File> ifNotExists) {
        file.exists() ? file : ifNotExists.call(file)
    }
}
