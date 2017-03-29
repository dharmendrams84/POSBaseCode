::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: Copyright (c) 2001, 2010 Oracle. All Rights Reserved.
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
::
::   POSENV.BAT
::
:: Sets up the environment needed for the Oracle Retail POS application.
::
:: NOTE: the value of the variable PLATFORM must be set to ""
:: if not running on IBM SUREPOS 750 hardware.
::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::

rem set PLATFORM=SUREPOS750
if "%PLATFORM%" == "SUREPOS750" call sp750.bat

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Sets the "java.home" System property at runtime
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET JAVA_HOME=$JRE_LOCATION$

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Set the common environment
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
call ..\..\common\common_env.bat

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Set the runtime path
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET PATH=.;%JAVA_HOME%\bin;c:\pos\ibmjpos;%PATH%;

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Sets the "java.class.path" System property at runtime
::
:: Sets the pos/bin/ dir and the pos/ dir for finding
:: properties and config files.
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=.;..;..\config

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set POS extensions directories
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET EXT_DIRS=%JAVA_HOME%\lib\ext
SET EXT_DIRS=%EXT_DIRS%;%COMMON_PATH%\lib\ext

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set POS patches directory
::
:: Using the addtoclasspath script, any directory
:: (p001-p999) will be included in the classpath. Also,
:: all jars in the (p00-1-p999) will be included
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
call addtoclasspath.bat ..\patches

rem Sets environment for fujitsu
rem call fujitsuenv.bat

rem sets environment for epson
rem call epson.bat

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set pos lib directory and locale
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
call addtoclasspath.bat ..\lib
call addtoclasspath.bat ..\lib\locales

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set jpos lib classes
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=%CLASSPATH%;$JPOSLIB_CLASSPATH$

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set common lib directory
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
call addtoclasspath.bat %COMMON_PATH%\lib

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   DigitalPersona fingerprint classes
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=$DPFINGERPRINT_CLASSPATH$;%CLASSPATH%

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Set jpos.xml directory in classpath
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=$JPOSXML_CLASSPATH$;%CLASSPATH%

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   HP Environment classes
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=$HP_ENV_CLASSPATH$;%CLASSPATH%

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   IBM Environment classes
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET CLASSPATH=$IBM_ENV_CLASSPATH$;%CLASSPATH%

:: pass the classpath to a log file for records
echo %CLASSPATH% > ..\logs\classpath.log
