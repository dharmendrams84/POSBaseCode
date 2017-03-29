. posenv.sh

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

export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$PATH
export CLASSPATH=.

#Run the database build script
chmod a+x $ANT_HOME/bin/ant
$ANT_HOME/bin/ant -f db.xml base_data_ctr

