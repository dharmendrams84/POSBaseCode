@echo off
if "%1" == "" goto :USAGE
if "%2" == "load_purge_procedures" GOTO :DONETESTING
if NOT "%2" == "" goto :USAGE2

:DONETESTING
rem PRE-REQUISITE: Java must be in your path.  This script
rem must be run in the pos/bin directory

rem ANT Must be in the path.  First check on the normal installation path,
rem and if it is not there, check on the extraction path
SET ANT_HOME=..\..\Common\apache-ant
IF NOT EXIST %ANT_HOME% SET ANT_HOME=..\..\thirdparty\dist\apache-ant
IF EXIST %ANT_HOME% SET JAVA_HOME=$JRE_LOCATION$

SET PATH=%JAVA_HOME%;%ANT_HOME%\bin;%PATH%

SETLOCAL

SET CLASSPATH=.;
rem CTR database build
rem call ant -f db.xml base_data_ctr
echo ======================
echo Initializing Database with "%1" dataset 
echo ======================

IF [%1]==[empty] SET CREATE_TARGET=create_empty_db
IF [%1]==[minimum] SET CREATE_TARGET=create_minimum_db
IF [%1]==[sample] SET CREATE_TARGET=create_sample_db

IF [%1]==[qa_data] SET CREATE_TARGET=create_qatest_db
IF [%1]==[QA_DATA] SET CREATE_TARGET=create_qatest_db
IF [%1]==[load_qa] SET CREATE_TARGET=create_qatest_db
IF [%1]==[load_unittest] SET CREATE_TARGET=create_unittest_db

IF [%CREATE_TARGET%]==[] GOTO USAGE 

rem %2 is used for data purging
call ant -f db.xml %CREATE_TARGET% %2

ENDLOCAL

GOTO END

:USAGE
echo USAGE:
echo dbbuild.bat [dataset]
echo [dataset] can be empty, minimum, sample.  
echo The 'empty' dataset contains no data, only scripts to build schema will be run.
echo The 'minimum' dataset contains the enough data to perform basic application functions. 
echo The 'sample' dataset contains data useful for demonstrating most application functions.
GOTO END

:USAGE2
if "%2" == "load_purge_procedures" GOTO :END
echo dbbuild.bat [dataset] [data purge]
echo data_purge must be load_purge_procedures

:END