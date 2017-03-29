@echo off
rem Script for starting POS client.

rem Set JVM classpath.
if "%POSENV_SET%" == "" call posenv.bat

rem Java min and max heap memory sizing options supported by Sun and IBM.
rem These values may need to be increased for your environment.
set JAVA_MEM_OPTIONS=-Xms84m -Xmx128m
set JAVA_OPTIONS=%JAVA_MEM_OPTIONS%

rem Set the extensions dirs used by the classloader.
set JAVA_OPTIONS=%JAVA_OPTIONS% -Djava.ext.dirs="%EXT_DIRS%"

rem Add more JAVA_OPTIONS lines like this example debug settings
rem set JAVA_OPTIONS=-client -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=7777,server=y,suspend=y %JAVA_OPTIONS%

rem If more debug is desired, then turn on additional console output.
if "%1" == "debug" (
  set JAVA_OPTIONS=%JAVA_OPTIONS% -DCONSOLE_LOGGING
)

rem This is used for testing accuracy of the resource bundles.
if "%1" == "i18n" (
  set JAVA_OPTIONS=%JAVA_OPTIONS% -DBUNDLE_TESTING
)

rem Set the conduit script to load for POS client
if "%CONDUIT_CONFIG%" == "" (
  set CONDUIT_CONFIG=classpath://config/conduit/POSClientTierloader.xml
)

rem Run the java main class.
set COMMAND=java %JAVA_OPTIONS% oracle.retail.stores.foundation.config.TierLoader %CONDUIT_CONFIG%
echo %COMMAND%
%COMMAND%
