package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public enum DynamicInternalWorkingState implements IState{
    START,
    PROGRESS,
    COMPLETE
}
