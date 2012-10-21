package OccupancyFiler.filer

import org.apache.commons.io.FileUtils

import java.text.DecimalFormat

class FileRenamer {
    private final SequenceNumber sequenceNumber

    FileRenamer(SequenceNumber sequenceNumber) {
        this.sequenceNumber = sequenceNumber
    }
    File rename(File file) {
        def newFile = new File(newName)
        FileUtils.copyFile(file, newFile)
        file.delete()
        newFile
    }

    private String getNewName() {
        def formatter = new DecimalFormat("00000000")
        'Staging_Occupancy.Boston.' + formatter.format(sequenceNumber.next()) + '.2012.csv'
    }
}
