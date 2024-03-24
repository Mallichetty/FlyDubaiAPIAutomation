package services.logger;

import org.apache.log4j.Logger;

public class Log
{
    static final Logger log = Logger.getLogger(Log.class.getName());
    public static void info(String message)
    {
        log.info(message);
    }

    public static void warn(String message)
    {
        log.warn(message);
    }

    public static void error(String message)
    {
        log.error(message);
    }

}
