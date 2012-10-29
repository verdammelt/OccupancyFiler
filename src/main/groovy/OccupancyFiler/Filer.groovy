package OccupancyFiler

import static OccupancyFiler.Logger.log

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

        def trim = arguments.trimmer.&trimTopLines
        def rename = arguments.renamer.&rename
        def move = arguments.mover.&move
        def incrementSequenceNumber = { File file -> arguments.sequenceNumber.commit(); file }

        def process = logProcessing >>
                trim >> logTrimmed >>
                rename >> logRenamed >>
                move >> logMoved >>
                incrementSequenceNumber

        arguments.files.each process
    }
}

