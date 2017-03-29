@echo off
rem Script for starting POS server.

rem Set JVM classpath.
if "%POSENV_SET%" == "" call posenv.bat

rem Java min and max heap memory sizing options supported by Sun and IBM.
rem These values may need to be increased for your environment.
rem E.g. servers with heavy XML processing could set '-Xms512m -mx1g'.
set JAVA_MEM_OPTIONS=-Xms256m -Xmx512m
set JAVA_OPTIONS=%JAVA_MEM_OPTIONS%

rem Set the extensions dirs used by the classloader.
set JAVA_OPTIONS=%JAVA_OPTIONS% -Djava.ext.dirs="%EXT_DIRS%"

rem Apache Xerces is the preferred XML parser
set JAVA_OPTIONS=%JAVA_OPTIONS% -Djavax.xml.parsers.DocumentBuilderFactory=org.apache.xerces.jaxp.DocumentBuilderFactoryImpl
set JAVA_OPTIONS=%JAVA_OPTIONS% -Djavax.xml.transform.TransformerFactory=org.apache.xalan.processor.TransformerFactoryImpl

rem Add more JAVA_OPTIONS lines like this example debug settings
rem set JAVA_OPTIONS=-client -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=7778,server=y,suspend=y %JAVA_OPTIONS%

rem If more debug is desired, then turn on additional console output.
if "%1" == "debug" (
  set JAVA_OPTIONS=%JAVA_OPTIONS% -DCONSOLE_LOGGING
)

rem Set the location conduit script to load for POS server
if "%CONDUIT_CONFIG%" == "" (
  set CONDUIT_CONFIG=classpath://config/conduit/POSServerTierloader.xml
)


rem Run the java main class.
set COMMAND=java %JAVA_OPTIONS% oracle.retail.stores.foundation.config.TierLoader %CONDUIT_CONFIG%
echo %COMMAND%
%COMMAND%
