package OccupancyFiler.filer

class SequenceNumber {
    private final File sequenceNumberFile

    SequenceNumber(File sequenceNumberFile) {
        this.sequenceNumberFile = sequenceNumberFile
    }

    int next() {
        sequenceNumberFile.text.toInteger() + 1
    }

    void commit() {
        sequenceNumberFile.text = next().toString()
    }
}
