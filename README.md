# Staterazor
Staterazor is a library for creating state machines especially of the sort that might be a bit dynamic in their definition so 
that each of the states nay not be fully determined in advance, but informed by some process or database. 

## Concepts
StateMachine - A collection of States connected by Transitions 
State - A stable condition of a process
Transition - A declared path to a change of state for a process
Gate - Should a Transition exist, a Gate is the check that determines if it's permitted or not
Actions - Tasks that should be performed as a result of a successful Transition occurring 