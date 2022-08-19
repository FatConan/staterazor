package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

public class ChangeMonitor<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X> implements IMonitorChange<M, T, C, S, E, X>{

    private T transition = null;
    private boolean transitionFound = false;
    private boolean transitionPermitted = false;
    private boolean transitionSuccessful = false;
    private IChangeContext<M, S, E, X> changeContext = null;

    public static final ChangeMonitor EMPTY;
    static {
        @SuppressWarnings("rawtypes")
        final ChangeMonitor empty = new ChangeMonitor<>();
        EMPTY = empty;
    }

    @SuppressWarnings("unchecked")
    public static <M extends IStateMachine<S, E, X>,
            T extends ITransition<M, C, S, E, X>,
            C extends ITransitionScope,
            S extends IState, E extends IEvent, X> ChangeMonitor<M, T, C, S, E, X> empty() {
        return (ChangeMonitor<M, T, C, S, E, X>) EMPTY;
    }


    @SuppressWarnings("varargs")
    public static  <M extends IStateMachine<S, E, X>,
            T extends ITransition<M, C, S, E, X>,
            C extends ITransitionScope,
            S extends IState, E extends IEvent, X>ChangeMonitor<M, T, C, S, E, X> of(T transition, IChangeContext<M, S, E, X> context){
        ChangeMonitor<M, T, C, S, E, X> monitor = new ChangeMonitor<>();
        if(transition != null){
            monitor.transition = transition;
            monitor.transitionFound = true;
        }
        monitor.changeContext = context;
        return monitor;
    }

    @Override
    public boolean transitionFound(){
        return transitionFound;
    }

    @Override
    public boolean transitionPermitted(){
        return transitionPermitted;
    }

    @Override
    public boolean transitionSuccessful(){
        return transitionSuccessful;
    }

    @Override
    public T getTransition(){
        return transition;
    }

    @Override
    public IChangeContext<M, S, E, X> getChangeContext(){
        return changeContext;
    }

    @Override
    public boolean isEmpty(){
        return this == EMPTY;
    }


}
