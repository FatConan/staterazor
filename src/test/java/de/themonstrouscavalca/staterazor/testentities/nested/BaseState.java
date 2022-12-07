package de.themonstrouscavalca.staterazor.testentities.nested;

import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public enum BaseState implements IState{
    STOPPED,
    DRAWING,
    MOVING
}

