import java.util.logging.*;

public class MyLoggerFormatter extends Formatter{
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + "\n";
    }
}
