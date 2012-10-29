package OccupancyFiler.utilities

class DefaultingCollection<T> {
    private final Collection<T> collection

    DefaultingCollection(Collection<T> collection) {
        this.collection = collection
    }

    T getAt(int index, T defaultValue) {
        (index >= collection.size()) ? defaultValue : collection[index] as T
    }
}
