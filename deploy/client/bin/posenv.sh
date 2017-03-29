#!/bin/sh

echo posenv.sh sets all the global variables for running ORPOS
export PATH=$PATH:.

# Sets the "java.home" System property at runtime
JAVA_HOME=$JRE_LOCATION$

export JAVA_HOME

. addtoclasspath.sh

# Set the common environment
. ../../common/common_env.sh

PATH=$DOLLAR$JAVA_HOME/bin:$DOLLAR$PATH
export PATH

# Set the "java.class.path" System property at runtime
CP=.:..:../config

# Set ORPOS extensions directories
EXT_DIRS=$DOLLAR$JAVA_HOME/lib/ext
EXT_DIRS=$DOLLAR$EXT_DIRS:$DOLLAR$COMMON_PATH/lib/ext:$WAS_JARS:$MQ_JARS

# Set the patches directory
addtoclasspath ../patches

# Set the pos lib directory and locale
addtoclasspath ../lib
addtoclasspath ../lib/locales

# Jpos clases
CP=$DOLLAR$CP:$JPOSLIB_CLASSPATH$

# Set the common lib directory
addtoclasspath $DOLLAR$COMMON_PATH/lib

# DigitalPersona fingerprint classes
CP=$DPFINGERPRINT_CLASSPATH$:$DOLLAR$CP

# HP Environment classes
CP=$HP_ENV_CLASSPATH$:$DOLLAR$CP

# IBM Environment classes
CP=$IBM_ENV_CLASSPATH$:$DOLLAR$CP

# set jpos.xml directory in classpath
CP=$JPOSXML_CLASSPATH$:$DOLLAR$CP

CLASSPATH=$DOLLAR$CP:$DOLLAR$CLASSPATH

export CLASSPATH EXT_DIRS
echo $DOLLAR$CLASSPATH

# pass the classpath to a log file for records
echo $DOLLAR$CLASSPATH > ../logs/classpath.log
echo posenv done
