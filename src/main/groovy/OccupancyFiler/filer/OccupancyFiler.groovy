package OccupancyFiler.filer

class OccupancyFiler {
    static int main(String[] argv) {
        def inputDir = new File(argv[1])
        def outputDir = new File(argv[2])

        inputDir.listFiles().each { it.renameTo(new File(outputDir, it.name))}

        0
    }
}
