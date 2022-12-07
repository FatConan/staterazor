package de.themonstrouscavalca.staterazor.testentities.nested;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionStates;

public class NestedState extends DefaultStateManager<
        NestedState,
        Transition<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>,
        TransitionScope, BaseState, NestedEvent, EventContext>
        implements IStateMachine<BaseState, NestedEvent, EventContext>{

    public static class Builder extends AbstractDefaultStateMachineBuilder<NestedState,
            Transition<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>,
            TransitionScope, BaseState, NestedEvent, EventContext>{

        TopState topState;

        public Builder topState(TopState topState){
            this.topState = topState;
            return this;
        }

        @Override
        public NestedState build(){
            return new NestedState(this);
        }
    }

    public NestedState(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.setTransitions(builder.transitions);
        this.setMachine(this);
    }

    public static NestedState MOVING =
        new Builder()
                .initialState(BaseState.STOPPED)
                //region  START - Start a personal development (Create one if it doesn't exist)
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.DOWN)
                                .name("Move Down")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE DOWN (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.UP)
                                .name("Move Up")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE UP (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.LEFT)
                                .name("Move Left")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE LEFT (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.RIGHT)
                                .name("Move Right")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE RIGHT (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.ROTATE_CLOCKWISE)
                                .name("Move Left")
                                .build(),
                        (t, ic) -> ic.getEventContext().getRotation() != null && ic.getEventContext().getRotation() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE ROTATE CLOCKWISE (%d)", ic.getEventContext().getRotation()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.ROTATE_ANTICLOCKWISE)
                                .name("Rotate Anticlockwise")
                                .build(),
                        (t, ic) -> ic.getEventContext().getRotation() != null && ic.getEventContext().getRotation() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("MOVE ROTATE ANTICLOCKWISE (%d)", ic.getEventContext().getRotation()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(BaseState.MOVING)
                                .to(BaseState.STOPPED)
                                .on(NestedEvent.STOP)
                                .name("Stopped")
                                .build(),
                        (t, ic) -> true,
                        (t, s, ic) -> {
                            ic.getEventContext().addString("MOVE STOPPED");
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .build();

    public static NestedState DRAWING =
        new Builder()
                .initialState(BaseState.STOPPED)
                //region  START - Start a personal development (Create one if it doesn't exist)
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.DOWN)
                                .name("Draw Down")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW DOWN (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.UP)
                                .name("Draw Up")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW UP (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.LEFT)
                                .name("Draw Left")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW LEFT (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.RIGHT)
                                .name("Draw Right")
                                .build(),
                        (t, ic) -> ic.getEventContext().getMove() != null && ic.getEventContext().getMove() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW RIGHT (%d)", ic.getEventContext().getMove()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.ROTATE_CLOCKWISE)
                                .name("Draw Left")
                                .build(),
                        (t, ic) -> ic.getEventContext().getRotation() != null && ic.getEventContext().getRotation() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW ROTATE CLOCKWISE (%d)", ic.getEventContext().getRotation()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(TransitionStates.wildcard())
                                .to(BaseState.MOVING)
                                .on(NestedEvent.ROTATE_ANTICLOCKWISE)
                                .name("DRAW Anticlockwise")
                                .build(),
                        (t, ic) -> ic.getEventContext().getRotation() != null && ic.getEventContext().getRotation() != 0,
                        (t, s, ic) -> {
                            ic.getEventContext().addString(String.format("DRAW ROTATE ANTICLOCKWISE (%d)", ic.getEventContext().getRotation()));
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .transition(new Transition.Builder<NestedState, TransitionScope, BaseState, NestedEvent, EventContext>()
                                .from(BaseState.MOVING)
                                .to(BaseState.STOPPED)
                                .on(NestedEvent.STOP)
                                .name("Stopped")
                                .build(),
                        (t, ic) -> true,
                        (t, s, ic) -> {
                            ic.getEventContext().addString("DRAW STOPPED");
                            return new ChangeContext<>(t, s, ic);
                        }
                )
                .build();
}
