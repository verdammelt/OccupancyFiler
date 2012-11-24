package OccupancyFiler.file

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumberFormatter
import OccupancyFiler.environment.YearSource

class FileRenamer {
    private final DeployedEnvironment deployedEnvironment
    private final YearSource yearSource
    private final SequenceNumberFormatter formatter

    FileRenamer(DeployedEnvironment deployedEnvironment,
                YearSource yearSource,
                SequenceNumberFormatter sequenceNumberFormatter) {
        this.deployedEnvironment = deployedEnvironment
        this.yearSource = yearSource
        this.formatter = sequenceNumberFormatter
    }

    File rename(int seqNumber, File file) {
        def newFile = new File(getNewName(seqNumber))
        newFile << file.asWritable()
        file.delete()
        newFile
    }

    private String getNewName(int seqNumber) {
        [
                prefix,
                'Boston',
                formatSequenceNumber(seqNumber),
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

    private String formatSequenceNumber(int seqNum) {
        formatter.format(seqNum)
    }
}
