package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IGenerateState;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Representing a transition within a StateMachine between States
 */
public interface ITransition<
        M extends IStateMachine<S, E, X>,
        C extends ITransitionScope,
        S extends IState,
        E extends IEvent, X> extends Comparable<ITransition<M, C, S, E, X>>{

    String name();
    String description();
    boolean isInternal();
    boolean isExternal();
    boolean isDynamic();
    boolean isStatic();
    boolean multipleOrigin();
    boolean singleOrigin();

    boolean matchesFromState(S state);

    C getScope();

    S getFromState();
    ITransitionStates<S> getFromStates();
    IGenerateState<M, S, E, X> getToStateGenerator();
    S getToState(M machine, S fromState, E event, X eventContext);
    S getToState();
    E getEvent();

    @Override
    default int compareTo(ITransition<M, C, S, E, X> o){
        return this.name().compareTo(o.name());
    }
}
