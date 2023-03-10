package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 *
 * @param <S> The state type of the state machine's states
 * @param <E> The event type for triggering state machine transitions
 * @param <X> An unbounded type that represents the event context, the metadata sent along with the event for use by the
 *           transition gates.
 */
public interface IStateMachine<S extends IState, E extends IEvent, X>{

}
