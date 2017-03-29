@echo off
rem Start store-enterprise server configuration

if "%1" == "debug" (
  set CONDUIT_CONFIG=classpath://config/conduit/CorporateServerConduit.xml classpath://config/ntier_rmihost.xml
) else (
  set CONDUIT_CONFIG=classpath://config/conduit/CorporateServerConduit.xml classpath://config/rmihost.xml
)


rem Execute common server start script
call StoreServerConduit.bat %1
