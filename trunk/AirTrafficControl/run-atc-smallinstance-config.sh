#!/bin/bash

# this script runs the atc jar with a configuration to solve small instance of the problem.

INSTANCES="airland1.txt airland2.txt airland3.txt  airland4.txt  airland5.txt  airland6.txt  airland7.txt  airland8.txt";

for file in $INSTANCES
do
	echo "execuções para a instancia $file" >> results/$file;

	for i in 1 2 3 #4 5 6 7 8 9 10
	do
	    echo "#$i" >> results/$file;
	    java -jar atc.jar resources/$file >> results/$file;
	done
done
