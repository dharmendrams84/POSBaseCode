#!/bin/sh
# Start POS client and server in a single JVM

# set the location conduit script to load for POS client
export CONDUIT_CONFIG=classpath://config/conduit/POSCollapsedFFTierloader.xml

. ClientConduit.sh $1
