package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/** A state machine may be used as a state itself so it'll also extend IState **/
public interface IStateMachine<S extends IState, E extends IEvent, X>{

}
