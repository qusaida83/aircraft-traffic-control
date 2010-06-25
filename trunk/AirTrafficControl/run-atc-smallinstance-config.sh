#!/bin/bash

# this script runs the atc jar with a configuration to solve small instance of the problem.

INSTANCES="airland1.txt airland2.txt airland3.txt  airland4.txt  airland5.txt  airland6.txt  airland7.txt  airland8.txt";


# execution for little instances
for file in $INSTANCES
do
	echo "execuções para a instancia $file" >> results_linst/$file;

	for i in 1 2 3 4 5
	do
	    echo "#$i" >> results_linst/$file;
	    java -jar atc.jar resources/$file 500 0.3 0.1 300 >> results_linst/$file;
	done
done


# execution for big instances
for file in $INSTANCES
do
	echo "execuções para a instancia $file" >> results_binst/$file;

	for i in 1 2 3 4 5
	do
	    echo "#$i" >> results_binst/$file;
	    java -jar atc.jar resources/$file 1000 0.3 0.1 500 >> results_binst/$file;
	done
done
