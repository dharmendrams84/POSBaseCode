@echo off
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: Copyright (c) 1998-2001 360Commerce, Inc. All Rights Reserved.
::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
::
::   SP750.BAT
::
::   sp750.bat is hardware specific for the
::   IBM SurePOS750 platform. AIP calls are
::   device driver functions.
::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::

echo stopping POS devices...
aipctrl stop

echo starting POS devices...
aipctrl start
call aipstart