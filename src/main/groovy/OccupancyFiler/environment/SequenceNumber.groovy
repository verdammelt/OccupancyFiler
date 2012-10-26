package OccupancyFiler.environment

import OccupancyFiler.file.FileExists

class SequenceNumber {
    private final File sequenceNumberFile

    SequenceNumber(File sequenceNumberFile) {
        this.sequenceNumberFile = FileExists.ensure(sequenceNumberFile) { File file ->
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
