package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Defines a collection of states (or meta states eg. ALL, NONE, NOT(x)) from which a state machine may transition
 * We'll always to transition to a singular state (whether static or generated), but we may transition from numerous
 * states
  */
public interface ITransitionStates<S extends IState>{
    boolean isWildcard();
    boolean isInverse();
    boolean matches(S state);
}
