package OccupancyFiler

import OccupancyFiler.arguments.ArgumentReader
import OccupancyFiler.arguments.Toolbox

import static OccupancyFiler.utilities.Logger.log

class FilerMain {
    static void main(String[] argv) {
        withLoggedException {
            def arguments = new ArgumentReader(argv)

            if (arguments.helpWanted) {
                arguments.printUsage()
            } else {
                new Filer(new Toolbox(arguments)).performFiling()
            }
        }
    }

    static def withLoggedException(Closure task) {
        try {
            task.call()
        } catch (Exception exception) {
            log("(Exception:${exception.class.simpleName}) ${exception.message}")
        }
    }
}
