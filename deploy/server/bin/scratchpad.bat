rem PRE-REQUISITE: Java must be in your path.  This script
rem must be run in the pos/bin directory

rem ANT Must be in the path.  First check on the normal installation path,
rem and if it is not there, check on the extraction path
SET ANT_HOME=..\..\common\apache-ant
IF NOT EXIST %ANT_HOME% SET ANT_HOME=..\..\thirdparty\dist\apache-ant
IF NOT EXIST %JAVA_HOME% SET JAVA_HOME=C:\j2sdk1.5.0_22\jre

SET PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%PATH%

SET CLASSPATH=.;
rem CTR scratchpad database build
call ant -f db.xml base_data_ctr
