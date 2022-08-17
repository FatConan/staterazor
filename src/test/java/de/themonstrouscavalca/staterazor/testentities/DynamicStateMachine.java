package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultNestedStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionStates;
import javafx.animation.TransitionBuilder;

public class DynamicStateMachine extends DefaultNestedStateManager<
        DynamicStateMachine, //StateMachine class
        DynamicState,
        Transition<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>,
        Transition<DynamicState, TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>,
        DynamicState,
        DynamicInternalWorkingState,
        TransitionScope,
        TransitionScope,
        DynamicEvent, DynamicEventContext> //State, Event and Event context classes
        implements IStateMachine<DynamicState, DynamicEvent, DynamicEventContext>{

    static class Builder extends AbstractDefaultStateMachineBuilder<DynamicStateMachine,
            Transition<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>,
            TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>{

        DynamicStateType type = null;
        DynamicState previousState = null;

        @Override
        public DynamicStateMachine build(){
            return new DynamicStateMachine(this);
        }
    }

    DynamicStateMachine(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.setTransitions(builder.transitions);
    }

    public static DynamicStateMachine instance(){
        DynamicStateMachine machine = new Builder()
                .name("Dynamic Builder")
                .initialState(null)
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                        .from(TransitionStates.wildcard())
                        .to((m, e, x) -> {
                            DynamicState current = m.state();
                            DynamicState added = DynamicState.instance(x.getStateType(), current);
                            if(current != null){
                                current.setNext(added);
                            }
                            return added;
                        })
                        .on(DynamicEvent.ADD)
                        .build(),
                        (ic) -> {
                            DynamicEventContext context = ic.getEventContext();
                            DynamicState current = ic.getMachine().state();
                            return context.getStateType() != null &&
                                    (current == null || current.state().equals(DynamicInternalWorkingState.COMPLETE));
                        },
                        ChangeContext::new
                )
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                        .from(TransitionStates.wildcard())
                        .to((m, e, x) -> {
                            DynamicState current = m.state();
                            return current.getPrevious();
                        })
                        .on(DynamicEvent.PREVIOUS)
                        .build(),
                        (ic) -> {
                            DynamicState current = ic.getMachine().state();
                            return current != null && current.getPrevious() != null;
                        },
                        ChangeContext::new
                )
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                                .from(TransitionStates.wildcard())
                                .to((m, e, x) -> {
                                    DynamicState current = m.state();
                                    return current.getNext();
                                })
                                .on(DynamicEvent.NEXT)
                                .build(),
                        (ic) -> {
                            DynamicState current = ic.getMachine().state();
                            return current != null && current.getNext() != null;
                        },
                        ChangeContext::new
                )
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                                .from(TransitionStates.wildcard())
                                .to((m, e, x) -> {
                                    DynamicState current = m.state();
                                    while(current.getPrevious() != null){
                                        current = current.getPrevious();
                                    }
                                    return current;
                                })
                                .on(DynamicEvent.START)
                                .build(),
                        (ic) -> {
                            DynamicState current = ic.getMachine().state();
                            return current != null;
                        },
                        ChangeContext::new
                )
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                                .from(TransitionStates.wildcard())
                                .to((m, e, x) -> {
                                    DynamicState current = m.state();
                                    while(current.getNext() != null){
                                        current = current.getNext();
                                    }
                                    return current;
                                })
                                .on(DynamicEvent.END)
                                .build(),
                        (ic) -> {
                            DynamicState current = ic.getMachine().state();
                            return current != null;
                        },
                        ChangeContext::new
                )
                .transition(new Transition.Builder<DynamicStateMachine, TransitionScope, DynamicState, DynamicEvent, DynamicEventContext>()
                                .from(TransitionStates.wildcard())
                                .to((m, e, x) -> {
                                    DynamicState current = m.state();
                                    DynamicState previous = current.getPrevious();
                                    DynamicState next = current.getNext();

                                    if(previous != null){
                                        previous.setNext(next);
                                    }
                                    if(next != null){
                                        next.setPrevious(previous);
                                    }

                                    current = null;

                                    return previous;
                                })
                                .on(DynamicEvent.REMOVE)
                                .build(),
                        (ic) -> {
                            DynamicState current = ic.getMachine().state();
                            return current != null;
                        },
                        ChangeContext::new
                )
                .build();
        machine.setMachine(machine);
        return machine;
    }
}
