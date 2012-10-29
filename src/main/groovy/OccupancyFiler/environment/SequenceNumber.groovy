package OccupancyFiler.environment

import static OccupancyFiler.file.FileEnsurer.ifDoesNotExist

class SequenceNumber {
    private final File sequenceNumberFile
    private String currentText

    SequenceNumber(File sequenceNumberFile) {
        this.sequenceNumberFile = ifDoesNotExist(sequenceNumberFile) { File file ->
            file.text = "0"
            file
        }
        this.currentText = this.sequenceNumberFile.text
    }

    int next() {
        def next = this.sequenceNumberFile.text.toInteger() + 1
        currentText = next.toString()
        next
    }

    void commit() {
        sequenceNumberFile.text = currentText
    }
}
