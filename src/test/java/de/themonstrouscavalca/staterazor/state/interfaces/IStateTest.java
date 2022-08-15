package de.themonstrouscavalca.staterazor.state.interfaces;

import de.themonstrouscavalca.staterazor.testentities.TrafficLightStateMachine;
import de.themonstrouscavalca.staterazor.testentities.TrafficLightingEvent;
import de.themonstrouscavalca.staterazor.testentities.TrafficLightingState;
import junit.framework.TestCase;

import java.util.Objects;

import static org.junit.Assert.assertNotEquals;

public class IStateTest extends TestCase{
    //A test state class that represents a class (this isn't the ideal way to do this, it'd be better to use an enum
    static class FooState implements IState{
        private final String name;

        public FooState(String name){
            this.name = name;
        }

        @Override
        public String name(){
            return this.name;
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            FooState foo = (FooState) o;
            return Objects.equals(name, foo.name);
        }

        @Override
        public int hashCode(){
            return Objects.hash(name);
        }
    }

    public void testName(){
        FooState A = new FooState("A");
        FooState B = new FooState("B");
        FooState A_Match = new FooState("A");
        assertEquals("Checking Names", "A", A.name());
        assertEquals("Checking Names", "B", B.name());
        assertEquals("Checking Names", "A", A_Match.name());
        assertEquals("Comparing states", A, A);
        assertEquals("Comparing states", A, A_Match);
        assertNotEquals("Comparing states", A, B);

        TrafficLightingState stop = TrafficLightingState.STOP;
        TrafficLightingState go = TrafficLightingState.GO;
        TrafficLightingState stopMatch = TrafficLightingState.STOP;
        assertEquals("Checking Names", "STOP", stop.name());
        assertEquals("Checking Names", "GO", go.name());
        assertEquals("Checking Names", stop, stopMatch);

        TrafficLightStateMachine tlsm = TrafficLightStateMachine.instance();
        assertEquals("Initial traffic light state", TrafficLightingState.STOP, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.READY, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.GO, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.WARN, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.STOP, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.READY, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.GO, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.WARN, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.STOP, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.READY, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.GO, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.DEACTIVATE);
        assertEquals("Initial traffic light state", TrafficLightingState.OFF, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.OFF, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.RESET);
        assertEquals("Initial traffic light state", TrafficLightingState.OFF, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.ACTIVATE);
        assertEquals("Initial traffic light state", TrafficLightingState.STOP, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.READY, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.GO, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.NEXT);
        assertEquals("Initial traffic light state", TrafficLightingState.WARN, tlsm.state());
        tlsm.onEvent(TrafficLightingEvent.RESET);
        assertEquals("Initial traffic light state", TrafficLightingState.STOP, tlsm.state());


    }
}