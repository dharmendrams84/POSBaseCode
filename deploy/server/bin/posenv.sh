#!/bin/sh

echo posenv.sh sets all the global variables for running ORPOS
export PATH=$PATH:.

# Sets the "java.home" System property at runtime
JAVA_HOME=$JRE_LOCATION$

# Set the common environment
. ../../common/common_env.sh

# Set the Ant home location
ANT_HOME=$DOLLAR$COMMON_PATH/apache-ant

export JAVA_HOME ANT_HOME

. addtoclasspath.sh

# Set the runtime path
PATH=$DOLLAR$JAVA_HOME/bin:$DOLLAR$ANT_HOME/bin:$DOLLAR$PATH
export PATH

# Set ORPOS extensions directories
EXT_DIRS=$DOLLAR$JAVA_HOME/lib/ext
EXT_DIRS=$DOLLAR$EXT_DIRS:$DOLLAR$COMMON_PATH/lib/ext:$WAS_JARS:$MQ_JARS

# Set the "java.class.path" System property at runtime
CP=.:..:../config

# ISD classes
# ISD jars need to be in the classpath proper and
# cannot be in common/lib/ext.
# REM ISD INSTALLCP=$DOLLAR$CP:$DOLLAR$COMMON_PATH/lib/isd/$ISD_IMSRTRIBSPECSDK_JAR$
# REM ISD INSTALLCP=$DOLLAR$CP:$DOLLAR$COMMON_PATH/lib/isd/$ISD_MSPCOMMAPI_JAR$
# REM ISD INSTALLCP=$DOLLAR$CP:$DOLLAR$COMMON_PATH/lib/isd/$ISD_ISDCRYPT_JAR$

# DB Classes
CP=$DOLLAR$CP:$USER_MAGIC_FOLDER_2$/$DB_JDBC_JAR$

CLASSPATH=$DOLLAR$CLASSPATH:$DOLLAR$CP

# Set the patches directory
addtoclasspath ../patches

# Set the pos lib directory and locale
addtoclasspath ../lib
addtoclasspath ../lib/locales

# Set the common lib directory
addtoclasspath $DOLLAR$COMMON_PATH/lib


export CLASSPATH EXT_DIRS
echo $DOLLAR$CLASSPATH

# pass the classpath to a log file for records
echo $DOLLAR$CLASSPATH > ../logs/classpath.log
echo posenv done
