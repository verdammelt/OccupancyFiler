package OccupancyFiler

import OccupancyFiler.arguments.ArgumentReader
import OccupancyFiler.arguments.Toolbox
import OccupancyFiler.logic.Filer

import static OccupancyFiler.utilities.Logger.log

class FilerMain {
    static void main(String[] argv) {
        withLoggedException {
            withToolboxBuiltFromArguments(argv) { Toolbox toolbox ->
                new Filer(toolbox).performFiling()
            }
        }
    }

    static void withToolboxBuiltFromArguments(String[] argv, Closure task) {
        def arguments = new ArgumentReader(argv)

        if (arguments.helpWanted) {
            arguments.printUsage()
        } else {
            task.call(new Toolbox(arguments))
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
