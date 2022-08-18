package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Monitor a change in state. When an event happens determine whether a suitable transition was found, whether it was
 * permitted (determined by the IGate gate check) and whether it was successful (determined by the IAffect affect)
 * bundle the result with the changed context in an (IChangeContext instance)
 *
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The event context class
 */
public interface IMonitorChange<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>{
    boolean transitionFound();
    boolean transitionPermitted();
    boolean transitionSuccessful();
    T getTransition();
    IChangeContext<M, S, E, X> getChangeContext();
    boolean isEmpty();
}
