package OccupancyFiler

import OccupancyFiler.file.FileMover
import OccupancyFiler.file.FileRenamer
import OccupancyFiler.file.FileTrimmer
import OccupancyFiler.file.FilesInDirectory

import static OccupancyFiler.Logger.log

class Filer {
    void performFiling(FilesInDirectory files, FileMover mover, FileRenamer renamer, FileTrimmer trimmer) {
        def process =
            {File file -> log("processing ${file?.absolutePath}"); file } >>
                    trimmer.&removeFirstLine >>
                    {File file -> log("trimmed..."); file } >>
                    renamer.&rename >>
                    {File file -> log("renamed to ${file?.name}"); file } >>
                    mover.&move >>
                    { File file -> log("moved to ${file?.absolutePath}"); file }

        files.each process
    }
}

