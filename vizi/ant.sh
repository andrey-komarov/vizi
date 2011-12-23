#!/bin/sh

#CLASSPATH=`find meta/bin/lib -maxdepth 3 -name '*.jar' -printf '%p:'`$CLASSPATH
CLASSPATH=./meta/vizi-0.4b7.jar:./meta/classes-1.1.8.jar:./meta/bin/lib/SchemaValidatorTask.jar:$CLASSPATH
CLASSPATH=`find $JDK_HOME -maxdepth 3 -name '*.jar' -printf '%p:'`$CLASSPATH
CLASSPATH=`find $ANT_HOME -maxdepth 3 -name '*.jar' -printf '%p:'`$CLASSPATH

export CLASSPATH

echo $CLASSPATH

exec \
java -classpath "$CLASSPATH" \
org.apache.tools.ant.Main $*
