package OccupancyFiler.logic

import OccupancyFiler.arguments.Toolbox

import static OccupancyFiler.utilities.Logger.log

class Filer {
    private final Toolbox toolbox

    Filer(Toolbox toolbox) {
        this.toolbox = toolbox
    }

    void performFiling() {
        log("Processing all files in input directory")
        toolbox.files.each this.&processAFile
        log("Processing completed.")
    }

    private void processAFile(File file) {
        toolbox.sequenceNumber.doWithNextNumber { int sequenceNumber ->
            processFileWithSequenceNumber(file, sequenceNumber)
        }
        deleteFile(file)
    }

    private void deleteFile(File file) {
        log("deleting old file ${file.name}")
        toolbox.deleter.delete(file)
        log("deleted.")
    }

    private void processFileWithSequenceNumber(File file, int sequenceNumber) {
        constructProcessFn(sequenceNumber).call(file)
    }

    private Closure constructProcessFn(int sequenceNumber) {
        def logStart = {File file -> log("processing ${file?.absolutePath}"); file }
        def logRead = {FileLines lines -> log("read..."); lines}
        def logTrimmed = {FileLines lines -> log("trimmed..."); lines }
        def logWriting = {File file -> log("writing to ${file?.name}"); file }

        Closure<FileLines> read = toolbox.reader.&read
        Closure<FileLines> trim = toolbox.trimmer.&trimTopLines
        Closure write = toolbox.writer.&write.curry(toolbox.filePathGenerator.generatePath(sequenceNumber))

        logStart >>
                read >> logRead >>
                trim >> logTrimmed >>
                write >> logWriting
    }
}

