package OccupancyFiler

import static OccupancyFiler.Logger.log

class Filer {
    private final ArgumentParser arguments

    Filer(ArgumentParser arguments) {
        this.arguments = arguments
    }

    void performFiling() {
        def process =
            {File file -> log("processing ${file?.absolutePath}"); file } >>
                    arguments.trimmer.&removeFirstLine >>
                    {File file -> log("trimmed..."); file } >>
                    arguments.renamer.&rename >>
                    {File file -> log("renamed to ${file?.name}"); file } >>
                    arguments.mover.&move >>
                    { File file -> log("moved to ${file?.absolutePath}"); file }

        arguments.files.each process
    }
}

