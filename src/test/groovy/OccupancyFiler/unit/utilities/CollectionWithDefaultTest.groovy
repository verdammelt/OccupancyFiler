package OccupancyFiler.unit.utilities

import OccupancyFiler.utilities.DefaultingCollection
import spock.lang.Specification

class CollectionWithDefaultTest extends Specification {
    def "can get items from the collection - defaulting if idx out of bounds"() {
        given:
        def collection = new DefaultingCollection([1, 2, 3])

        expect:
        collection.getAt(idx, 666) == value

        where:
        idx | value
        0   | 1
        1   | 2
        2   | 3
        3   | 666
        500 | 666
    }
}
