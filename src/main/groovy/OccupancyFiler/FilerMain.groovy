package OccupancyFiler

import static OccupancyFiler.Logger.log

class FilerMain {
    static void main(String[] argv) {
        withLoggedException {
            def arguments = new ArgumentReader(argv)

            if (arguments.helpWanted) {
                arguments.printUsage()
            } else {
                def argumentParser = new ArgumentParser(arguments)

                new Filer(argumentParser).performFiling()

                argumentParser.sequenceNumber.commit()
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
