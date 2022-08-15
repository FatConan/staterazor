package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;

/**
 * The IManageStatea
 * @param <M>
 * @param <T>
 * @param <S>
 * @param <E>
 * @param <X>
 */
public interface IManageStates<M extends IStateMachine<S, E, X>, T extends ITransition<M, S, E, X>, S extends IState, E extends IEvent, X> extends IState{
    S state();
    void setState(S state);
    IMonitorChange<M, S, E, X> onEvent(E event, X eventContext);
    IMonitorChange<M, S, E, X> onEvent(E event);
    ITransitionMap<M, T, S, E, X> getTransitions();
}
