package de.themonstrouscavalca.staterazor.machine.impl;

import de.themonstrouscavalca.staterazor.context.impl.InitialContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IManageStates;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.GateAndActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

import java.util.List;
import java.util.Optional;

public class DefaultNestedStateManager<
        MT extends IStateMachine<ST, E, X>,
        MS extends IStateMachine<SS, E, X>,
        TT extends ITransition<MT, CT, ST, E, X>,
        TS extends ITransition<MS, CS, SS, E, X>,
        ST extends IManageStates<MS, TS, CS, SS, E, X>,
        SS extends IState,
        CT extends ITransitionScope,
        CS extends ITransitionScope,
        E extends IEvent, X>
        implements IManageStates<MT ,TT, CT, ST, E, X>{

    private MT machine;
    private String name;
    private ST state;
    private ITransitionMap<MT, TT, CT, ST, E, X> transitions;

    public String name(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ST state(){
        return state;
    }

    public void setState(ST state){
        this.state = state;
    }

    public void setTransitions(ITransitionMap<MT, TT, CT, ST, E, X> transitions){
        this.transitions = transitions;
    }

    public MT getMachine(){
        return machine;
    }

    public void setMachine(MT machine){
        this.machine = machine;
    }

    @Override
    public ITransitionMap<MT, TT, CT, ST, E, X> getTransitions(){
        return this.transitions;
    }

    protected InitialContext<MT, ST, E, X> initialContext(E event, X eventContext){
        InitialContext<MT, ST, E, X> initialContext = new InitialContext();
        initialContext.setMachine(this.machine);
        initialContext.setFromState(this.state());
        initialContext.setEvent(event);
        initialContext.setEventContext(eventContext);
        return initialContext;
    }

    protected IMonitorChange<MT, ST, E, X> handleEvent(E event, X eventContext){
        if(this.state() != null){
            IMonitorChange<MS, SS, E, X> passDown = this.state().onEvent(event, eventContext);
            if(passDown != null){
                return null;
            }
        }

        InitialContext<MT, ST, E, X> initialContext = this.initialContext(event, eventContext);

        List<TT> selected = this.getTransitions()
                .forEventAndState(event, initialContext.getFromState());

        Optional<TT> selectedOpt =
                selected.stream().filter(t -> this.getTransitions()
                                .get(t).gate().permit(initialContext))
                        .findFirst();

        if(selectedOpt.isPresent()){
            GateAndActor<MT, ST, E, X> gateAndActor = this.getTransitions().get(selectedOpt.get());
            this.setState(selectedOpt.get().getToState(this.machine, initialContext.getFromState(), event, eventContext));
            IChangeContext<MT, ST, E, X> changeContext = gateAndActor.getActor().act(initialContext.getFromState(), initialContext);
        }

        return null;
    }

    public IMonitorChange<MT, ST, E, X> onEvent(E event, X eventContext){
        return this.handleEvent(event, eventContext);
    }

    public IMonitorChange<MT, ST, E, X> onEvent(E event){
        return this.onEvent(event, null);
    }
}
