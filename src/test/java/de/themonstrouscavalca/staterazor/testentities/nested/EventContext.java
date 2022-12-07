package de.themonstrouscavalca.staterazor.testentities.nested;

import java.util.ArrayList;
import java.util.List;

public class EventContext{
    private List<String> strings = new ArrayList<>();
    private Integer rotation;
    private Integer move;

    public Integer getRotation(){
        return rotation;
    }

    public void setRotation(Integer rotation){
        this.rotation = rotation;
    }

    public Integer getMove(){
        return move;
    }

    public void setMove(Integer move){
        this.move = move;
    }

    public List<String> getStrings(){
        return strings;
    }

    public void setStrings(List<String> strings){
        this.strings = strings;
    }

    public void addString(String string){
        this.strings.add(string);
    }

    public static EventContext move(int move){
        EventContext context = new EventContext();
        context.setMove(move);
        return context;
    }

    public static EventContext rotate(int rotation){
        EventContext context = new EventContext();
        context.setRotation(rotation);
        return context;
    }
}
