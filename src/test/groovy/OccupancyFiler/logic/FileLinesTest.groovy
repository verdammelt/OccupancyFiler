package OccupancyFiler.logic

import spock.lang.Specification
import spock.lang.Unroll

class FileLinesTest extends Specification {
    def "can return first line or default value if none"() {
        expect:
        new FileLines(['a', 'b', 'c']).fetchFirst() == 'a'
        new FileLines([]).fetchFirst('default') == 'default'
    }

    @Unroll("Dropping #numToDrop lines from #inputLines leaves #outputLines")
    def "can chop off the first n lines"() {
        expect:
        new FileLines(inputLines).dropFirstLines(numToDrop) ==
                new FileLines(outputLines)

        where:
        inputLines      | numToDrop | outputLines
        ['a', 'b', 'c'] | 0         | ['a', 'b', 'c']
        ['a', 'b', 'c'] | 1         | ['b', 'c']
        ['a', 'b', 'c'] | 2         | ['c']
        ['a', 'b', 'c'] | 3         | []
        ['a', 'b', 'c'] | 4         | []
        ['a', 'b', 'c'] | 500       | []
    }

    def "has a useful toString"() {
        expect:
        new FileLines(['a', 'b', 'c']).toString() == 'a\nb\nc'
    }
}
