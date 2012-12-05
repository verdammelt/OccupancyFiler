package OccupancyFiler.logic

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class FileLines {
    private final List<String> lines

    FileLines(List<String> lines) {
        this.lines = lines
    }

    String fetchFirst(String defaultValue = null) {
        lines.empty ? defaultValue : lines.first()
    }


    FileLines dropFirstLines(int numLinesToDrop) {
        new FileLines(lines.drop(numLinesToDrop))
    }

    @Override
    public String toString() {
        lines.join('\n')
    }
}
