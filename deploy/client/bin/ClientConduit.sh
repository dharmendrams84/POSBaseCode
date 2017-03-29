#!/bin/sh
# Script for starting POS client.

# if a fully qualified or relative path is used to 
# specify the current script, change to the directory 
# specified.  Dn't do anything if already in the bin
# directory  ( or if executable is on the path rather
# than being specified )

EXE_DIR=`dirname $0`
if [ -n "$EXE_DIR" -a "$EXE_DIR" != "." ]
then
  echo "changing to $EXE_DIR directory"
  cd $EXE_DIR
else
  echo "no need to change the current directory"
fi

export PATH=$PATH:.

# Set JVM classpath
if [ "${POSENV_SET}" = "" ]; then
  . posenv.sh
fi

# Java min and max heap memory sizing options supported by Sun and IBM.
# These values may need to be increased for your environment.
JAVA_MEM_OPTIONS="-Xms84m -Xmx128m"
JAVA_OPTIONS=${JAVA_MEM_OPTIONS}

# Set the extensions dirs used by the classloader.
JAVA_OPTIONS=${JAVA_OPTIONS}" -Djava.ext.dirs=${EXT_DIRS}"

# Add more JAVA_OPTIONS by adding more lines like this debug settings example
#JAVA_OPTIONS="-client -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=7777,server=y,suspend=y "${JAVA_OPTIONS}

#JIT disabled using -Djava.compiler=NONE for convenience only as part of device testing.
#Disabling JIT has performance impacts, so this option should be removed after IBM releases JPOS version compatible
#with IBM JDK 1.5. This temp workaround is needed only for Linux for the time being.
JAVA_OPTIONS=${JAVA_OPTIONS}" -Djava.compiler=NONE"

# If debug, then turn on additional console output.
if [ "$1" = "debug" ]; then
  JAVA_OPTIONS=${JAVA_OPTIONS}" -DCONSOLE_LOGGING"
fi

# set This is used for testing accuracy of the resource bundles.
if [ "$1" = "i18n" ]; then
  JAVA_OPTIONS=${JAVA_OPTIONS}" -DBUNDLE_TESTING"
fi

# set the location conduit script to load for POS client
if [ "${CONDUIT_CONFIG}" = "" ]; then
  CONDUIT_CONFIG=classpath://config/conduit/POSClientTierloader.xml
fi

# Run the java main class.
echo ${CLASSPATH}
COMMAND="java ${JAVA_OPTIONS} oracle.retail.stores.foundation.config.TierLoader ${CONDUIT_CONFIG}"
echo $COMMAND
$COMMAND
