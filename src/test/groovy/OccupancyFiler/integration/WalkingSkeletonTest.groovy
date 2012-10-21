package OccupancyFiler.integration

import OccupancyFiler.Filer
import spock.lang.Specification

class WalkingSkeletonTest extends Specification {

    private static final File TEST_OUTPUT_DIR = new File("testOutput.dir")
    private static final File TEST_INPUT_DIR = new File("testInput.dir")
    private static final File TEST_ARCHIVE_DIR = new File("testArchive.dir")
    private static final ArrayList<String> DATA_LINES = [
            "U,100380,2737,027092,6/30/2012,30,30,",
            "D,100381,1187,018802,6/30/2012,132,100,",
            "U,100382,2290,013033,6/30/2012,69,69,",
            "U,100383,134,015674,6/30/2012,56,55,"
    ]
    private static final String HEADER_ROW = "U,rowID,Client Property ID,Client Partnership ID,Month End Date,Total Units,Occupied Units,Qualified Units"
    private static final String SEQ_NUM_FILE = 'seqNum.txt'

    def setup() {
        TEST_ARCHIVE_DIR.mkdir()
        TEST_INPUT_DIR.mkdir()
        TEST_OUTPUT_DIR.mkdir()
    }

    def cleanup() {
        TEST_ARCHIVE_DIR.deleteDir()
        TEST_INPUT_DIR.deleteDir()
        TEST_OUTPUT_DIR.deleteDir()
    }

    def "Processes a file correctly"() {
        given: "a file ready to process"
        setupTestFile('Staging_Occupancy.Boston.00001xxx.2012.csv.xls', HEADER_ROW, DATA_LINES)

        and: "the sequence number file is set up"
        setSequenceNumberTo(42)

        when: "the Filer is run"
        def returnValue = runFiler()

        then: "it was successful"
        returnValue == 0

        and: "moved to the correct location and filename changed corrected rightly"
        !filesIn(TEST_INPUT_DIR.path)*.name.contains('Staging_Occupancy.Boston.00000043.2012.csv')
        filesIn(TEST_OUTPUT_DIR.path)*.name.contains('Staging_Occupancy.Boston.00000043.2012.csv')

        and: "the file header line is removed"
        !contentsOfOutputFile('Staging_Occupancy.Boston.00000043.2012.csv').contains(HEADER_ROW)
        contentsOfOutputFile('Staging_Occupancy.Boston.00000043.2012.csv') == DATA_LINES

        and: "the sequence number has been updated"
        sequenceNumberIsNow(43)
    }

    void setupTestFile(String inputFileName, String header, ArrayList<String> dataLines) {
        def file = new File(TEST_INPUT_DIR, inputFileName)
        file.text = ([header] + dataLines).join("\n")
    }

    void setSequenceNumberTo(int seqNum) {
        new File(SEQ_NUM_FILE).text = "$seqNum"
    }

    boolean sequenceNumberIsNow(int seqNum) {
        new File(SEQ_NUM_FILE).text == "$seqNum"
    }

    def runFiler() {
        new Filer().main(SEQ_NUM_FILE,
                TEST_INPUT_DIR.path,
                TEST_OUTPUT_DIR.path,
                TEST_ARCHIVE_DIR.path)
    }

    def List<File> filesIn(String directory) {
        new File(directory).listFiles()
    }

    def contentsOfOutputFile(String fileName) {
        new File(TEST_OUTPUT_DIR, fileName).text.split("\n")
    }

    def cleanDirectory(String directory) {
        new File(directory).deleteDir()
        new File(directory).mkdir()
    }
}
