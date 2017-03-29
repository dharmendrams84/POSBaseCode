#!/bin/sh
# This script is only to be run in the pos/bin directory of an install
# Running it anywhere else will not work.
#

if [ ! $1 ]
then
	echo USAGE:
	echo dbbuild.sh [dataset]
	echo [dataset] can be empty, minimum, sample.  
	echo The 'empty' dataset contains no data, only scripts to build schema will be run.
	echo The 'minimum' dataset contains the enough data to perform basic application functions. 
	echo The 'sample' dataset contains data useful for demonstrating most application functions.
else

#Set up the ANT ENVIRONMENT
CUR_DIR=`pwd`
if [ ! -d $ANT_HOME ]; then
	# ANT_HOME already set, but not to a directory.  Try
	# to set it to the default value
	ANT_HOME=$CUR_DIR/../../common/apache-ant
	# If default value is not found, set it to the build location
	if [ ! -d $ANT_HOME ]; then
		ANT_HOME=$CUR_DIR/../../thirdparty/dist/apache-ant
	# If default value is found, then run posenv.sh to set up all env variables
	else 
		. ./posenv.sh
	fi
	echo "Using Ant at $ANT_HOME"
else
	# ANT_HOME may be an empty string, if it is then set it
	if [ -z $ANT_HOME ]; then
		ANT_HOME=$CUR_DIR/../../common/apache-ant
		if [ ! -d $ANT_HOME ]; then
			ANT_HOME=$CUR_DIR/../../thirdparty/dist/apache-ant
		else
			. ./posenv.sh
		fi
	fi
	echo "Using Ant at $ANT_HOME"
fi

#Run the database build script
chmod a+x $ANT_HOME/bin/ant
$ANT_HOME/bin/ant -f db.xml create_$1_db 
fi

