package de.themonstrouscavalca.staterazor.testentities.nested;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultNestedStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;

import java.util.ArrayList;
import java.util.List;

public class TopState extends DefaultNestedStateManager<
        TopState,
        NestedState,
        Transition<TopState, TransitionScope, NestedState, NestedEvent, EventContext>,
        Transition<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>,
        NestedState,
        BaseState,
        TransitionScope,
        TransitionScope,
        NestedEvent, EventContext>
        implements IStateMachine<NestedState, NestedEvent, EventContext>{

    public static class Builder extends AbstractDefaultStateMachineBuilder<TopState,
            Transition<TopState, TransitionScope, NestedState, NestedEvent, EventContext>,
            TransitionScope, NestedState, NestedEvent, EventContext>{

        @Override
        public TopState build(){
            return new TopState(this);
        }
    }

    private List<String> actions = new ArrayList<>();

    public TopState(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.setTransitions(builder.transitions);
        this.setMachine(this);
    }

    public static TopState instance(){
        return new Builder()
                .initialState(NestedState.MOVING)
                .transition(new Transition.Builder<TopState, TransitionScope, NestedState, NestedEvent, EventContext>()
                                .from(NestedState.MOVING)
                                .to(NestedState.DRAWING)
                                .on(NestedEvent.PEN_DOWN)
                                .name("Pen Down")
                                .build(),
                        (t, ic) -> true,
                        (t, s, ic) -> {
                            ic.getEventContext().addString("DRAWING");
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<TopState, TransitionScope, NestedState, NestedEvent, EventContext>()
                                .from(NestedState.DRAWING)
                                .to(NestedState.MOVING)
                                .on(NestedEvent.PEN_UP)
                                .name("Pen Up")
                                .build(),
                        (t, ic) -> true,
                        (t, s, ic) -> {
                            ic.getEventContext().addString("MOVING");
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .build();
    }

    public List<String> getActions(){
        return actions;
    }

    public void setActions(List<String> actions){
        this.actions = actions;
    }

    public void addActions(List<String> actions){
        this.actions.addAll(actions);
    }
}
