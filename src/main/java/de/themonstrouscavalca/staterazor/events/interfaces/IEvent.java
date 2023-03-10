package de.themonstrouscavalca.staterazor.events.interfaces;

/**
 * Defines an event that triggers a transition, or a gate check and transition
 * */
public interface IEvent{
    /**
     * The only requirement of an event is that has a unique name to distinguish it. Unless there is some
     * specific reason not to do so an events can be represented by a standard enum
     *
     * @return A string representation of the event
     */
    String name();
}
