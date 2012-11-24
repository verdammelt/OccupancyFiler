package OccupancyFiler

import OccupancyFiler.arguments.ArgumentParser

import static OccupancyFiler.utilities.Logger.log

class Filer {
    private final ArgumentParser arguments

    Filer(ArgumentParser arguments) {
        this.arguments = arguments
    }

    void performFiling() {
        arguments.sequenceNumber.doWithNextNumber { int sequenceNumber ->
            fileWithSequenceNumber(sequenceNumber)
        }
    }

    private void fileWithSequenceNumber(int sequenceNumber) {
        def logStart = {File file -> log("processing ${file?.absolutePath}"); file }
        def logTrimmed = {File file -> log("trimmed..."); file }
        def logRenamed = {File file -> log("renamed to ${file?.name}"); file }
        def logMoved = { File file -> log("moved to ${file?.absolutePath}"); file }
        def logDone = { File file -> log("processing completed")}

        def trim = arguments.trimmer.&trimTopLines >> logTrimmed
        def rename = arguments.renamer.&rename.curry(sequenceNumber) >> logRenamed
        def move = arguments.mover.&move >> logMoved

        def process = logStart >> trim >> rename >> move >> logDone

        arguments.files.each process
    }
}

