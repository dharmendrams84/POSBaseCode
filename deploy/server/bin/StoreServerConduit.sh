#!/bin/sh
# Script for starting POS server.

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
if [ "$POSENV_SET" = "" ]; then
  . posenv.sh
fi

# Java min and max heap memory sizing options supported by Sun and IBM.
# These values may need to be increased for your environment.
# E.g. servers with heavy XML processing could set '-Xms512m -mx1g'.
JAVA_MEM_OPTIONS="-Xms256m -Xmx512m"
JAVA_OPTIONS=${JAVA_MEM_OPTIONS}

# Set the extensions dirs used by the classloader.
JAVA_OPTIONS=${JAVA_OPTIONS}" -Djava.ext.dirs=${EXT_DIRS}"

# Apache Xerces is the preferred XML parser
JAVA_OPTIONS=${JAVA_OPTIONS}" -Djavax.xml.parsers.DocumentBuilderFactory=org.apache.xerces.jaxp.DocumentBuilderFactoryImpl"
JAVA_OPTIONS=${JAVA_OPTIONS}" -Djavax.xml.transform.TransformerFactory=org.apache.xalan.processor.TransformerFactoryImpl"

# Add more JAVA_OPTIONS by adding more lines like this debug settings example
#JAVA_OPTIONS="-client -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=7778,server=y,suspend=y "${JAVA_OPTIONS}

# If debug, then turn on additional console output.
if [ "$1" = "debug" ]; then
  JAVA_OPTIONS=${JAVA_OPTIONS}" -DCONSOLE_LOGGING"
fi

# set the location conduit script to load for POS server
if [ "${CONDUIT_CONFIG}" = "" ]; then
  CONDUIT_CONFIG=classpath://config/conduit/POSServerTierloader.xml
fi


# Run the java main class.
echo ${CLASSPATH}
COMMAND="java ${JAVA_OPTIONS} oracle.retail.stores.foundation.config.TierLoader ${CONDUIT_CONFIG}"
echo $COMMAND
$COMMAND
