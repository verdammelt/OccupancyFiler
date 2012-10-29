package OccupancyFiler

import OccupancyFiler.arguments.ArgumentParser

import static OccupancyFiler.utilities.Logger.log

class Filer {
    private final ArgumentParser arguments

    Filer(ArgumentParser arguments) {
        this.arguments = arguments
    }

    void performFiling() {
        def logProcessing = {File file -> log("processing ${file?.absolutePath}"); file }
        def logTrimmed = {File file -> log("trimmed..."); file }
        def logRenamed = {File file -> log("renamed to ${file?.name}"); file }
        def logMoved = { File file -> log("moved to ${file?.absolutePath}"); file }

        def trim = arguments.trimmer.&trimTopLines >> logTrimmed
        def rename = arguments.renamer.&rename >> logRenamed
        def move = arguments.mover.&move >> logMoved
        def incrSeqNum = { File file -> arguments.sequenceNumber.commit(); file }

        def process = logProcessing >> trim >> rename >> move >> incrSeqNum

        arguments.files.each process
    }
}

