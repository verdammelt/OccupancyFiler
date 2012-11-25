package OccupancyFiler

import OccupancyFiler.arguments.Toolbox

import static OccupancyFiler.utilities.Logger.log

class Filer {
    private final Toolbox toolbox

    Filer(Toolbox toolbox) {
        this.toolbox = toolbox
    }

    void performFiling() {
        log("Processing all files in input directory")
        toolbox.files.each { File file ->
            toolbox.sequenceNumber.doWithNextNumber { int sequenceNumber ->
                fileWithSequenceNumber(file, sequenceNumber)
            }
            deleteFile(file)
        }
        log("Processing completed.")
    }

    private void deleteFile(File file) {
        log("deleting old file ${file.name}")
        toolbox.deleter.delete(file)
        log("deleted.")
    }

    private void fileWithSequenceNumber(File file, int sequenceNumber) {
        constructProcessFn(sequenceNumber).call(file)
    }

    private Closure constructProcessFn(int sequenceNumber) {
        def logStart = {File file -> log("processing ${file?.absolutePath}"); file }
        def logRead = {FileLines lines -> log("read..."); lines}
        def logTrimmed = {FileLines lines -> log("trimmed..."); lines }
        def logRenamed = {File file -> log("renamed to ${file?.name}"); file }

        Closure<FileLines> read = toolbox.reader.&read
        Closure<FileLines> trim = toolbox.trimmer.&trimTopLines
        Closure rename = toolbox.writer.&write.curry(getNewFileName(sequenceNumber))

        logStart >>
                read >> logRead >>
                trim >> logTrimmed >>
                rename >> logRenamed
    }

    // TODO: pull this into another helper class
    private String getNewFileName(int sequenceNumber) {
        toolbox.filePathGenerator.generatePath(sequenceNumber)
    }
}

