$CMD &
sleep $TIMEOUT
b=`jobs | grep 'Running' | wc -c`
if [ $b -ne 0 ]
then
kill %1
fi