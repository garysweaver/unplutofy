#!/bin/bash

# check for unplutofy
if [ -n "$UNPLUTOFY_HOME" ] ; then

    export CLASSPATH=$CLASSPATH:$UNPLUTOFY_HOME/target/unplutofy.jar

    java unplutofy.commandline.UnplutofyCommand $1 $2 $3 $4 $5 $6 $7 $8 $9

else

    echo "Please set UNPLUTOFY_HOME environment variable."

fi
