@echo off

REM This script is meant for running the POS on a single machine.  A
REM single argument may be supplied when the default behavior is not
REM desired.  The potential argument values are:
REM       playback:     Use POSRunner scripts for POS inputs
REM       record:       Gather inputs to POS for POSRunner into outputFile

REM This is only for use with the POSRunner simulator
if "%1" == "playback" (
  set JAVA_OPTIONS=-DtraceEventPort=5019
)

REM This is only for use with the POSRunner simulator
if "%1" == "record" (
  set JAVA_OPTIONS=-DPOSrunner.record=outputFile
  set JAVA_OPTIONS=%JAVA_OPTIONS% -DPOSrunner.recordMode=rapid
)

rem set the conduit script to load for POS client
set CONDUIT_CONFIG=classpath://config/conduit/CollapsedConduitFFSim.xml

rem Execute common client start script
call ClientConduit.bat %1
