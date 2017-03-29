@echo off
REM  JMS test receiver conduit

set JAVA_OPTIONS=-Djava.security.auth.login.config=/eys60/jaas.conf

rem set the conduit script to load for POS client
set CONDUIT_CONFIG=classpath://config/conduit/JMSTestReceiverConduit.xml


rem Execute common client start script
call ClientConduit.bat %1
