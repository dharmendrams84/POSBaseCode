#!/bin/sh
# Start store-enterprise server configuration

# set the location conduit script to load for POS server
export CONDUIT_CONFIG="classpath://config/conduit/CorporateServerConduit.xml classpath://config/rmihost.xml"

. StoreServerConduit.sh $1
