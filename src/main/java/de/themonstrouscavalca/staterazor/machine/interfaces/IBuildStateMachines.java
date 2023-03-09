package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * An Interface for defining builders for state machines.
 * @param <M>
 * @param <S>
 * @param <E>
 * @param <X>
 */
public interface IBuildStateMachines<M extends IStateMachine<S, E, X>,
        S extends IState, E extends IEvent, X>{
    M build();
}
