package OccupancyFiler.logic

import OccupancyFiler.arguments.environment.DeployedEnvironment
import OccupancyFiler.arguments.environment.YearSource

class NameGenerator {
    private final DeployedEnvironment deployedEnvironment
    private final YearSource yearSource
    private final SequenceNumberFormatter formatter

    NameGenerator(DeployedEnvironment deployedEnvironment,
                YearSource yearSource,
                SequenceNumberFormatter sequenceNumberFormatter) {
        this.deployedEnvironment = deployedEnvironment
        this.yearSource = yearSource
        this.formatter = sequenceNumberFormatter
    }

    String generateName(int seqNum) {
        [
                prefix,
                'Boston',
                formatSequenceNumber(seqNum),
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
