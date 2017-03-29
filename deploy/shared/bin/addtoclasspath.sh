#/bin/sh

addtoclasspath()
{
	# add the passed in dir to the local classpath
	LCP=$1

	# find all the jars in just this dir and add them also
	if [ `uname` = "Linux" ]
	
	then
		for i in `find $1 -maxdepth 1 -name "*.jar"`
		do
			LCP=$LCP:$i
		done
	else
		for i in `find $1 -name "*.jar"`
		do
			LCP=$LCP:$i
		done
	fi

	# apply the local classpath to the system
	CLASSPATH=$CLASSPATH:$LCP
}
