package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.machine.impl.DefaultNestedStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;

public class DynamicStateMachine extends DefaultNestedStateManager<
        DynamicStateMachine, //StateMachine class
        DynamicState,
        Transition<DynamicStateMachine, DynamicState, DynamicEvent, DynamicEventContext>,
        Transition<DynamicState, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>,
        DynamicState,
        DynamicInternalWorkingState,
        DynamicEvent, DynamicEventContext> //State, Event and Event context classes
        implements IStateMachine<DynamicState, DynamicEvent, DynamicEventContext>{

}
