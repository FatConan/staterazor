package de.themonstrouscavalca.staterazor.tests;

import de.themonstrouscavalca.staterazor.testentities.nested.EventContext;
import de.themonstrouscavalca.staterazor.testentities.nested.NestedEvent;
import de.themonstrouscavalca.staterazor.testentities.nested.NestedState;
import de.themonstrouscavalca.staterazor.testentities.nested.TopState;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import junit.framework.TestCase;

public class StateContextTest extends TestCase{

    private void updateTopState(IMonitorChange<TopState, Transition<TopState, TransitionScope, NestedState, NestedEvent, EventContext>,
            TransitionScope, NestedState, NestedEvent, EventContext> result, TopState topState){
        if(result.transitionSuccessful()){
            topState.addActions(result.getChangeContext().getEventContext().getStrings());
        }
    }

    public void testDrawing(){
        TopState topState = TopState.instance();
        IMonitorChange<TopState, Transition<TopState, TransitionScope, NestedState, NestedEvent, EventContext>,
                TransitionScope, NestedState, NestedEvent, EventContext> result;

        this.updateTopState(topState.onEvent(NestedEvent.DOWN, EventContext.move(10)), topState);
        this.updateTopState(topState.onEvent(NestedEvent.LEFT, EventContext.move(50)), topState);
        this.updateTopState(topState.onEvent(NestedEvent.PEN_DOWN, new EventContext()), topState);
        this.updateTopState(topState.onEvent(NestedEvent.ROTATE_ANTICLOCKWISE, EventContext.move(45)), topState);
        this.updateTopState(topState.onEvent(NestedEvent.UP, EventContext.move(100)), topState);
        this.updateTopState(topState.onEvent(NestedEvent.RIGHT, EventContext.move(100)), topState);
        this.updateTopState(topState.onEvent(NestedEvent.PEN_UP, new EventContext()), topState);

        System.out.println(String.join(", ", topState.getActions()));
    }
}
