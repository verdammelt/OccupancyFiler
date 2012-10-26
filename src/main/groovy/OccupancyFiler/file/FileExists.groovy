package OccupancyFiler.file

class FileExists {
    static File ensureFile(File file, Closure<File> ifNotExists) {
        file.exists() ? file : ifNotExists.call(file)
    }
}
