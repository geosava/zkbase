# Introduction #

ZKBase supports logging by using [log4j](http://logging.apache.org/log4j/).

**Note:** The configuration is heavily inspired by [Appfuse](http://appfuse.org/display/APF/Home).

# Usage #

To create a logger as member variable, use the following code (or similar):
```
	private final Log log = LogFactory.getLog(getClass());
```

Now, the logger can be used, e.g.:
```
	log.info("Adding user");
```

As default, the log is appended to the console (see configuration for details).

# Configuration #

The configuration for log4j can be found in `src/main/resources/log4j.xml`. Here is an excerpt of this file:
```
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

    <appender name="CONSOLE" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern"
                value="[zkbase] %p [%t] %c{1}.%M(%L) | %m%n"/>
        </layout>
    </appender>

  (...)

    <logger name="org.zkbase">
        <level value="INFO"/>
    </logger>

    <root>
        <level value="WARN"/>
        <appender-ref ref="CONSOLE"/>
    </root>

</log4j:configuration>
```
The first section defines a [ConsoleAppender](http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/ConsoleAppender.html) named `CONSOLE`. This appender logs all output on the console (`System.out` as default). Log outputs are formatted with a [PatternLayout](http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html).

The second section defines a logger for the package `org.zkbase` and its components. All log statements with level `info` or higher will be logged.

The third section defines the root logger, which logs at `warn` and higher levels. The `CONSOLE` appender from the first section is used as output target. As appenders are additive by default, all sub loggers will use this appender. For details, see [http://logging.apache.org/log4j/1.2/manual.html log4k manual (section "Appenders and Layouts").


# Maven Integration #

Currently, log4j version 1.2.14 is used. There already is a stable version 1.2.15. However, this version has dependencies to several libraries that are not available in the default Maven repository. If you want to use 1.2.15, there are [workarounds](http://onemanwenttomow.wordpress.com/2007/12/31/maven2-log4j-and-jmx-dependencies/) to do this.