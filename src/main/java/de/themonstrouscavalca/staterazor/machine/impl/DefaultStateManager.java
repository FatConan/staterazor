package de.themonstrouscavalca.staterazor.machine.impl;

import de.themonstrouscavalca.staterazor.context.impl.InitialContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IManageStates;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.GateAndActor;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;

import java.util.List;
import java.util.Optional;

public class DefaultStateManager<M extends IStateMachine<S, E, X>, T extends ITransition<M, S, E, X>, S extends IState, E extends IEvent, X>
    implements IManageStates<M ,T, S, E, X>{

    private M machine;
    private String name;
    private S state;
    private ITransitionMap<M, T, S, E, X> transitions;

    public String name(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public S state(){
        return state;
    }

    public void setState(S state){
        this.state = state;
    }

    public void setTransitions(ITransitionMap<M, T, S, E, X> transitions){
        this.transitions = transitions;
    }

    public M getMachine(){
        return machine;
    }

    public void setMachine(M machine){
        this.machine = machine;
    }

    @Override
    public ITransitionMap<M, T, S, E, X> getTransitions(){
        return this.transitions;
    }

    protected InitialContext<M, S, E, X> initialContext(E event, X eventContext){
        InitialContext<M, S, E, X> initialContext = new InitialContext();
        initialContext.setMachine(this.machine);
        initialContext.setFromState(this.state());
        initialContext.setEvent(event);
        initialContext.setEventContext(eventContext);
        return initialContext;
    }

    protected IMonitorChange<M, S, E, X> handleEvent(E event, X eventContext){
        InitialContext<M, S, E, X> initialContext = this.initialContext(event, eventContext);

        List<T> selected = this.getTransitions()
                .forEventAndState(event, initialContext.getFromState());

        Optional<T> selectedOpt =
                selected.stream().filter(t -> this.getTransitions()
                                .get(t).gate().permit(initialContext))
                        .findFirst();

        if(selectedOpt.isPresent()){
            GateAndActor<M, S, E, X> gateAndActor = this.getTransitions().get(selectedOpt.get());
            this.setState(selectedOpt.get().getToState(this.machine, initialContext.getFromState(), event, eventContext));
            IChangeContext<M, S,E, X> changeContext = gateAndActor.getActor().act(initialContext.getFromState(), initialContext);
        }

        return null;
    }

    public IMonitorChange<M, S, E, X> onEvent(E event, X eventContext){
        return this.handleEvent(event, eventContext);
    }

    public IMonitorChange<M, S, E, X> onEvent(E event){
        return this.onEvent(event, null);
    }
}
