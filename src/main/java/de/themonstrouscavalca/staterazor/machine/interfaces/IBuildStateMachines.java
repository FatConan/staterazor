package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public interface IBuildStateMachines<M extends IStateMachine<S, E, X>,
        S extends IState, E extends IEvent, X>{
    M build();
}
