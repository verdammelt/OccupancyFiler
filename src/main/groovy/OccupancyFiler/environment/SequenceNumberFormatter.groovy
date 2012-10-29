package OccupancyFiler.environment

import java.text.DecimalFormat

class SequenceNumberFormatter {
    private final int seqNum

    SequenceNumberFormatter(int seqNum) {
        this.seqNum = seqNum
    }

    String format() {
        new DecimalFormat("00000000").format(seqNum)
    }
}
