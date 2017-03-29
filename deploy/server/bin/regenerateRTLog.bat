@echo off

rem ===========================================================================
rem Copyright (c) 2007 Oracle Corporation, Redwood Shores, CA, USA
rem                       All rights reserved.
rem ===========================================================================
rem $Log: $
rem ===========================================================================

rem * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
rem This script launchs the ReSA RTLog Batch Regeneration Process.
rem It should be executed if an RTLog file needs to be re-transmitted to ReSA 
rem and the original RTLog batch file is no longer available.
rem * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

rem Set up the criteria file name. It is an optional input parameter.
if "%1" == "" (
  set CRITERIA_FILE=config/rtlogRegenerationCriteria.properties 
) else (
  set CRITERIA_FILE=%1
)

set JAVA_OPTIONS=-Drtlog.regeneration.criteria.file=%CRITERIA_FILE%

rem set the conduit script to load for POS client
set CONDUIT_CONFIG=classpath://config/conduit/RegenerateRTLogConduit.xml classpath://config/rmihost.xml


rem Execute common client start script
call ClientConduit.bat %1
