@echo off
REM /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *    
REM 
REM Copyright (c) 1998, 2010, Oracle and/or its affiliates. 
REM All rights reserved. 
REM 
REM * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */   

SET OLDCLASSPATH=%CLASSPATH%

SET CLASSPATH=%CLASSPATH%;c:\jcl2.0.1
SET CLASSPATH=%CLASSPATH%;c:\jcl2.0.1\res
SET CLASSPATH=%CLASSPATH%;c:\jcl2.0.1\lib\jcl.jar
SET CLASSPATH=%CLASSPATH%;c:\jcl2.0.1\lib\jpos15.jar
SET CLASSPATH=%CLASSPATH%;c:\javapos\hypercom_javapos
set classpath=%classpath%;\fjlinuxclient
