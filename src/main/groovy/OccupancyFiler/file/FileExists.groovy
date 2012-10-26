package OccupancyFiler.file

class FileExists {
    static File ensure(File file, Closure<File> ifNotExists) {
        file.exists() ? file : ifNotExists.call(file)
    }
}
