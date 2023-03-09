package de.themonstrouscavalca.staterazor.state.interfaces;

/**
 * Represents a state within a StateMachine, either static or dynamically generated.
 */
public interface IState{
    /**
     * A name to identify the state
     * @return The name of the state
     */
    String name();
}
