package OccupancyFiler.file

import OccupancyFiler.environment.DeployedEnvironment
import OccupancyFiler.environment.SequenceNumber
import OccupancyFiler.environment.YearSource

import java.text.DecimalFormat

class FileRenamer {
    private final DeployedEnvironment deployedEnvironment
    private final SequenceNumber sequenceNumber
    private final YearSource yearSource

    FileRenamer(DeployedEnvironment deployedEnvironment, SequenceNumber sequenceNumber, YearSource yearSource) {
        this.deployedEnvironment = deployedEnvironment
        this.sequenceNumber = sequenceNumber
        this.yearSource = yearSource
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
        def formatter = new DecimalFormat("00000000")
        def seqnum = formatter.format(sequenceNumber.next())
        seqnum
    }
}
