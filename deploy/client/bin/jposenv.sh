#!/bin/sh

echo setjpos.sh sets all the javapos specific stuff

JAVAPOS=simulated

if [ $JAVAPOS = simulated ]; then
    echo "Simulated settings"
    JPOS=$JARTHIRD
    CP=$JARTHIRD/jclall.jar
    CP=$CP:$JARTHIRD/jpos1911.jar
    JPOS=$CP
fi

#JPOS specifics like path and stuff can be set here
if [ $JAVAPOS = wincor ]; then
    echo "Wincor settings"
    JPOS=/usr/local/javapos
    CP=$JPOS/jcl/xerces.jar
    CP=$JPOS/sun/comm.jar:$CP
    CP=$JPOS/epson/lib/epsonJposService152.jar:$CP
    CP=$JPOS/epson/lib/epsonJposServiceCommon.jar:$CP
    CP=$JPOS/wn/lib/WNJavaPOSServices.jar:$CP
    CP=$JPOS/wn/lib/WNCommAPI.jar:$CP
    CP=$JPOS/wn/lib/WNJavaPOSControls.jar:$CP
    CP=$JPOS/wn/properties:$CP
    JPOS=$CP
    export LD_LIBRARY_PATH=.:/usr/local/javapos/wn/bin:$LD_LIBRARY_PATH
    setserial /dev/ttyS2 irq 10
    setserial /dev/ttyS3 irq 11 uart 16650 port 0x0270 skip_test
fi

if [ $JAVAPOS = ibmjpos ]; then
    echo "IBM Java POS settings"
fi

export JPOS
