package de.themonstrouscavalca.staterazor.context.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;

/**
 * Defines the initial state of play prior to performing a transition
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The event context class
 */
public interface IInitialContext<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>{
    M getMachine();
    S getFromState();
    E getEvent();
    X getEventContext();

    void setMachine(M machine);
    void setFromState(S state);
    void setEvent(E event);
    void setEventContext(X eventContext);

    boolean hasMachine();
    boolean hasFromState();
    boolean hasEvent();
    boolean hasEventContext();
}
