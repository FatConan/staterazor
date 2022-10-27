package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

import java.util.List;

/**
 * The IManageStatea
 * @param <M>
 * @param <T>
 * @param <S>
 * @param <E>
 * @param <X>
 */
public interface IManageStates<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X> extends IState{
    S state();
    void setState(S state);
    IMonitorChange<M, T, C, S, E, X> onEvent(E event, X eventContext);
    IMonitorChange<M, T, C, S, E, X> onEvent(E event);
    ITransitionMap<M, T, C, S, E, X> getTransitions();
    List<T> potentialTransitions();
    List<T> potentialTransitions(C scope);
    List<T> validTransitions(X eventContext);
    List<T> validTransitions(X eventContext, C scope);
}
