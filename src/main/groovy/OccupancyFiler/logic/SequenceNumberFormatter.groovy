package OccupancyFiler.logic

import java.text.DecimalFormat

class SequenceNumberFormatter {
    String format(int seqNum) {
        new DecimalFormat("00000000").format(seqNum)
    }
}
