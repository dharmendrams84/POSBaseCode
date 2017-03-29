#!/bin/sh

STARTDIR=`pwd`
MYDIR=`dirname $0`
cd $MYDIR
MYDIR=`pwd`

DEPSOK=0 ; export DEPSOK

if [ "$APPSERVER" == "websphere" ] ; then
	if [ ! -f ${MYDIR}/external-lib/db2jcc.jar ] ; then
		echo "ERROR: Could not find ${MYDIR}/external-lib/db2jcc.jar. Please download"
		echo "this file and place it in the external-lib directory before running this installer."
		DEPSOK=1; export DEPSOK
	fi
	if [ ! -f ${MYDIR}/external-lib/db2jcc_license_cu.jar ] ; then
		echo "ERROR: Could not find ${MYDIR}/external-lib/db2jcc_license_cu.jar. Please download"
		echo "this file and place it in the external-lib directory before running this installer."
		DEPSOK=1; export DEPSOK
	fi
fi

if [ ${DEPSOK} == 1 ] ; then
    echo "See ${MYDIR}/external-lib/README.txt for more details."
fi

cd $STARTDIR

