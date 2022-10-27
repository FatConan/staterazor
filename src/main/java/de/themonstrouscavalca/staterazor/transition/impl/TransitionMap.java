package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.*;

import java.util.*;
import java.util.stream.Collectors;

public class TransitionMap<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>
        implements ITransitionMap<M,T,C,S,E,X>{

    private final Map<E, List<T>> eventsToTransitionsMap = new HashMap<>();
    private final Map<T, GateAndActor<M,T,C,S,E,X>> transitionsToGatedActorMap = new LinkedHashMap<>();

    @Override
    public Set<E> getDefinedEvents(){
        return this.eventsToTransitionsMap.keySet();
    }

    @Override
    public GateAndActor<M,T,C,S,E,X> get(T transition){
        return this.transitionsToGatedActorMap.getOrDefault(transition, null);
    }

    @Override
    public List<T> forEventAndState(E event, S state){
        return this.eventsToTransitionsMap.getOrDefault(event, Collections.emptyList())
                .stream().filter(t -> t.matchesFromState(state)).collect(Collectors.toList());
    }

    @Override
    public List<T> forState(S state){
        return this.eventsToTransitionsMap.values()
                .stream().flatMap(Collection::stream).filter(t -> t.matchesFromState(state)).collect(Collectors.toList());
    }

    private void addByEvent(E event, T transition){
        if(this.eventsToTransitionsMap.containsKey(event)){
            this.eventsToTransitionsMap.get(event).add(transition);
        }else{
            this.eventsToTransitionsMap.put(event, new ArrayList<>(Collections.singletonList(transition)));
        }
    }

    private void addByTransition(T transition, IGate<M,T,C,S,E,X> gate, IActor<M,T,C,S,E,X> actor){
        this.transitionsToGatedActorMap.put(transition, new GateAndActor<>(gate, actor));
    }

    @Override
    public void add(T transition, IGate<M,T,C,S,E,X> gate, IActor<M,T,C,S,E,X> actor){
        this.addByEvent(transition.getEvent(), transition);
        this.addByTransition(transition, gate, actor);
    }
}
