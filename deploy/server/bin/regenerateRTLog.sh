#!/bin/sh

# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
#
# Copyright (c) 2007, 2010, Oracle and/or its affiliates. All rights reserved. 
#   $Log: $
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
# This script launchs the ReSA RTLog Batch Regeneration Process.
# It should be executed if an RTLog file needs to be re-transmitted to ReSA 
# and the original RTLog batch file is no longer available.
# * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

# Set up the environment
. posenv.sh
# . dbenv.bat

# Set up the criteria file name. It is an optional input parameter.
if ["$1" == ""]
then
	CRITERIA_FILE=config/rtlogRegenerationCriteria.properties
else
	CRITERIA_FILE=$1
fi

echo $CRITERIA_FILE

# Verify if the Criteria File Exists 
# Executes the Process
java -Xmx64m -Xms48m -Drtlog.regeneration.criteria.file=$CRITERIA_FILE oracle.retail.stores.foundation.config.TierLoader classpath://config/conduit/regenerateRTLogConduit.xml classpath://config/rmihost.xml
