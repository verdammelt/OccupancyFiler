package OccupancyFiler.file

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.SequenceNumberFormatter
import OccupancyFiler.environment.YearSource

class FileRenamer {
    private final DeployedEnvironment deployedEnvironment
    private final YearSource yearSource
    private final SequenceNumberFormatter formatter
    private final SequenceNumber sequenceNumber

    FileRenamer(DeployedEnvironment deployedEnvironment, YearSource yearSource, SequenceNumberFormatter sequenceNumberFormatter,
                SequenceNumber seqNumber) {
        this.deployedEnvironment = deployedEnvironment
        this.yearSource = yearSource
        this.formatter = sequenceNumberFormatter
        this.sequenceNumber = seqNumber
    }

    File rename(File file) {
        def newFile = new File(newName)
        newFile << file.asWritable()
        file.delete()
        newFile
    }

    private String getNewName() {
        [
                prefix,
                'Boston',
                nextSequenceNumber,
                thisYear,
                'csv'
        ].join('.')
    }

    private String getPrefix() {
        deployedEnvironment.name
    }

    private int getThisYear() {
        yearSource.thisYear
    }

    private String getNextSequenceNumber() {
        formatter.format(sequenceNumber.next())
    }
}
