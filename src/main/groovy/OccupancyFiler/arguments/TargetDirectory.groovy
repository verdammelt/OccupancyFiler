package OccupancyFiler.arguments

import groovy.transform.Immutable

@Immutable
class TargetDirectory {
    String path

    static TargetDirectory target(String path) {
        new TargetDirectory(path ?: '.')
    }
}
