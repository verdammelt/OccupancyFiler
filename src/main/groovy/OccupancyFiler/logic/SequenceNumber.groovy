package OccupancyFiler.logic

import OccupancyFiler.io.FileWriter

class SequenceNumber {
    private final String initialText
    private final FileWriter writer
    private final String sequenceNumberFilePath

    SequenceNumber(String seqNumFilePath, FileLines lines, FileWriter writer) {
        this.sequenceNumberFilePath = seqNumFilePath
        this.initialText = (lines.lines ?: ['0']).first()
        this.writer = writer
    }

    void doWithNextNumber(Closure task) {
        def nextNumber = initialText.toInteger() + 1
        task(nextNumber)
        writer.write(sequenceNumberFilePath,
                new FileLines([nextNumber.toString()]))
    }
}
