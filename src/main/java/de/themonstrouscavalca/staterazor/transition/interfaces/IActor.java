package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Defines an effect that happens as a result of a transition (beyond the change of state)
 * provided an initial state, the act call will return a changecontext reflecting the modifications
 * made by the act call.
 +
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The vent context class
 */
@FunctionalInterface
public interface IActor<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>{
    IChangeContext<M , S, E, X> act(S toState, IInitialContext<M, S, E ,X> initialContext);
}
