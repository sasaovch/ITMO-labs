#!/bin/sh
$CMD &
echo "cmd done $CMD"
echo $TIMEOUT
jobs && echo Yes | wc
jobs | grep '1'
jobs | wc
b=`jobs | grep "Running" | wc -c`
if [ $b -ne 0 ]
then
echo Yes
else
echo No
echo $b
fi
