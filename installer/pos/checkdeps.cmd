@echo off

set DEPSOK=0
set MYDIR=%CD%


:depschk
if "%DEPSOK%" == "1" goto :readme
goto EOF


:readme
echo See %MYDIR%\external-lib\README.txt for more details.

:EOF
