@echo off
REM /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *    
REM 
REM   Copyright (c) 1998-2001 360Commerce, Inc.    All Rights Reserved.
REM 
REM      Header:   $KW=@(#); $FN=oracle/retail/stores/pos/config/startup/CollapsedConduitFFProd.bat; $EKW;
REM      Revision: $KW=@(#); $Ver=pos_4.0.0:67; $EKW;
REM      Date:     $KW=@(#); $ChkD=2001/02/01 14:08:25; $EKW;
REM      Author:   $KW=@(#); $Own=mmann; $EKW;
REM 
REM * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */   

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Establish the install directory here using EYSHOME
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SET OLDCLASSPATH=%CLASSPATH%

SET CLASSPATH=%CLASSPATH%;c:\eysjpos

SET CLASSPATH=%CLASSPATH%;$EPSONDEVICECFG_JAR$
SET CLASSPATH=%CLASSPATH%;$EPSONJPOS4WIN_JAR$
SET CLASSPATH=%CLASSPATH%;$EPSONPORTCFG_JAR$