package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionStates;

public class TrafficLightStateMachine extends DefaultStateManager<TrafficLightStateMachine, //StateMachine class
        Transition<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>, //Transition class
        TransitionScope, TrafficLightingState, TrafficLightingEvent, Object> //State, Event and Event context classes
        implements IStateMachine<TrafficLightingState, TrafficLightingEvent, Object>{

    static class Builder extends AbstractDefaultStateMachineBuilder<TrafficLightStateMachine,
            Transition<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>,
            TransitionScope,
            TrafficLightingState,
            TrafficLightingEvent, Object>{

        @Override
        public TrafficLightStateMachine build(){
            return new TrafficLightStateMachine(this);
        }
    }

    TrafficLightStateMachine(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.setTransitions(builder.transitions);
    }

    public static TrafficLightStateMachine instance(){
        return new Builder()
                .name("Traffic Light State Machine")
                .initialState(TrafficLightingState.STOP)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TrafficLightingState.OFF)
                                .to(TrafficLightingState.STOP)
                                .on(TrafficLightingEvent.ACTIVATE)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TrafficLightingState.STOP)
                                .to(TrafficLightingState.READY)
                                .on(TrafficLightingEvent.NEXT)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TrafficLightingState.READY)
                                .to(TrafficLightingState.GO)
                                .on(TrafficLightingEvent.NEXT)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TrafficLightingState.GO)
                                .to(TrafficLightingState.WARN)
                                .on(TrafficLightingEvent.NEXT)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TrafficLightingState.WARN)
                                .to(TrafficLightingState.STOP)
                                .on(TrafficLightingEvent.NEXT)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TransitionStates.not(TrafficLightingState.OFF))
                                .to(TrafficLightingState.STOP)
                                .on(TrafficLightingEvent.RESET)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<TrafficLightStateMachine, TransitionScope, TrafficLightingState, TrafficLightingEvent, Object>()
                                .from(TransitionStates.wildcard())
                                .to(TrafficLightingState.OFF)
                                .on(TrafficLightingEvent.DEACTIVATE)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .build();

    }
}
