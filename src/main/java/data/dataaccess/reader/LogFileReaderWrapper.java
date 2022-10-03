package data.dataaccess.reader;

public class LogFileReaderWrapper implements Runnable {

    private final LogFileReader logFileReader;
    private DynamicLogFileReaderConsumer logLineConsumer;

    public LogFileReaderWrapper(LogFileReader logFileReader, DynamicLogFileReaderConsumer logLineConsumer) {
        this.logFileReader = logFileReader;
        this.logLineConsumer = logLineConsumer;
    }

    @Override
    public void run() {
        logFileReader.readDynamic((a) -> {}, logLineConsumer);
    }
}
