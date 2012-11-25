package OccupancyFiler.environment

import OccupancyFiler.FileLines
import OccupancyFiler.FileReader
import OccupancyFiler.FileWriter

class SequenceNumber {
    private final File sequenceNumberFile
    private String initialText

    private final FileWriter writer

    SequenceNumber(File sequenceNumberFile, FileReader reader, FileWriter writer) {
        this.sequenceNumberFile = sequenceNumberFile
        this.initialText = getFileLines(reader, sequenceNumberFile).first()
        this.writer = writer
    }

    private List<String> getFileLines(FileReader reader, File sequenceNumberFile) {
        reader.read(sequenceNumberFile).lines ?: ['0']
    }

    void doWithNextNumber(Closure task) {
        def next1 = initialText.toInteger() + 1
        def next = next1
        task(next)
        writer.write(sequenceNumberFile.absolutePath, new FileLines([next1.toString()]))
    }
}
