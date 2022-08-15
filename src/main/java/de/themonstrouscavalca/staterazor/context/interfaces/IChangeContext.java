package de.themonstrouscavalca.staterazor.context.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Defines the state of play after a transition
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The vent context class
 */
public interface IChangeContext<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>
        extends IInitialContext<M, S, E, X>{
    S getToState();
    void setToState(S state);
    boolean hasToState();
}
