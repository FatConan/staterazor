package de.themonstrouscavalca.staterazor.machine.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

import java.util.List;

/**
 * The IManageStates - Define an interface for handling changes to a state machine and for interrogating the state machine to
 * determine the available transitions.
 *
 * @param <M> Defines the state machine and extends IStateMachine
 * @param <T> Defines the Transition and extends ITransition
 * @param <C> Defines the TransitionScope and extends ITransitionScope
 *          A refinement to the transition that allows us to label them with a "scope" to refine when they're available
 * @param <S> Defines the state machine states and extends IState
 * @param <E> Defines the events an extends IEvent
 * @param <X> Defines the unbounded event context
 */
public interface IManageStates<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X> extends IState{

    /**
     * Get the current state of the state machine
     * @return the current state
     */
    S state();

    /**
     * Set the current state of the state machine
     * @param state The state to be set as current
     */
    void setState(S state);

    /**
     * Process the effect of triggering the provided event with the provided context on the state machine
     * @param event The triggered event
     * @param eventContext The accompanying context
     * @return An IMonitorChange object representing the updated state machine and any other recorded effects of the
     * triggered state change
     */
    IMonitorChange<M, T, C, S, E, X> onEvent(E event, X eventContext);

    /**
     * Process the effect of triggering the provided event with no additional context on the state machine
     * @param event The triggered event
     * @return An IMonitorChange object representing the updated state machine and any other recorded effects of the
     * triggered state change
     */
    IMonitorChange<M, T, C, S, E, X> onEvent(E event);

    /**
     * Get a map of the state machine's transitions, the map allows us to fins transitions by state and event
     * @return An ITransitionMap of the state machine's transitions
     */
    ITransitionMap<M, T, C, S, E, X> getTransitions();

    /**
     * List the potential transitions from the current state of the state machine
     * @return A list of potential transitions
     */
    List<T> potentialTransitions();

    /**
     * List the potential transitions from the current state of the state machine, but limit them to the provided
     * scope.
     * @param scope A scope to limit the potential transitions
     * @return A filtered list of potential transitions
     */
    List<T> potentialTransitions(C scope);

    /**
     * Filter the potential transitions to those satisfied by the potential eventContext when coupled to the transitions
     * trigger event.
     * @param eventContext The speculative event content used in conjunction with the transition trigger event to test the transition gate
     * @return a filtered list of potential transitions that can be satisfied by the provided event context.
     */
    List<T> validTransitions(X eventContext);

    /**
     * Filter the potential transitions by scope and then further to those satisfied by the potential eventContext when coupled to the transitions
     * trigger event
     * @param eventContext The speculative event content used in conjunction with the transition trigger event to test the transition gate
     * @param scope The scope by which to prefilter the potential transitions before testing with the provided event context
     * @return a filtered list of potential transitions in scope that can be satisfied by the provided event context.
     */
    List<T> validTransitions(X eventContext, C scope);

    /**
     * Validate a specific transition against the provided event context
     * @param transition The specific transition to be tested
     * @param eventContext The event context it will be tested against
     * @return boolean of whether the transition's gate test is passed or not.
     */
    Boolean validateTransition(T transition, X eventContext);
}
