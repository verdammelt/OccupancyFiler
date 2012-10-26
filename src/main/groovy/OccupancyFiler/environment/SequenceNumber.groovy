package OccupancyFiler.environment

import static OccupancyFiler.file.FileExists.ensureFile

class SequenceNumber {
    private final File sequenceNumberFile

    SequenceNumber(File sequenceNumberFile) {
        this.sequenceNumberFile = ensureFile(sequenceNumberFile) { File file ->
            file.text = "0"
            file
        }
    }

    int next() {
        sequenceNumberFile.text.toInteger() + 1
    }

    void commit() {
        sequenceNumberFile.text = next().toString()
    }
}
