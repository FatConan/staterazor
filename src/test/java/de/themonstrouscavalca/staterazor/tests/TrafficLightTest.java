package de.themonstrouscavalca.staterazor.tests;

import de.themonstrouscavalca.staterazor.testentities.TrafficLightStateMachine;
import de.themonstrouscavalca.staterazor.testentities.TrafficLightingEvent;
import de.themonstrouscavalca.staterazor.testentities.TrafficLightingState;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;
import junit.framework.TestCase;

public class TrafficLightTest extends TestCase{
    public void testLightsSequence(){
        TrafficLightStateMachine trafficLights = TrafficLightStateMachine.instance();
        for(Transition<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object> t: trafficLights.potentialTransitions()){
            System.out.println(t.getEvent());
            System.out.println(t.name());
        }
    }
}
