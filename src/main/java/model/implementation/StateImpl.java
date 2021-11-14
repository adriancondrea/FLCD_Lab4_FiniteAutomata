package model.implementation;

import model.interfaces.State;
import model.interfaces.Transition;

import java.util.ArrayList;
import java.util.List;

public class StateImpl implements State {

    private final List<Transition> transitions;
    private boolean isFinal;


    public StateImpl(boolean isFinal) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
    }


    public StateImpl() {
        this(false);
    }


    @Override
    public State addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }


    @Override
    public State performTransition(CharSequence c) throws Exception {
        return transitions.stream()
                .filter(t -> t.isPossible(c))
                .map(Transition::getNextState)
                .findAny()
                .orElseThrow(() -> new Exception(String.format("Input not accepted: %s", c)));
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public void setFinal(boolean value){
        this.isFinal = value;
    }

    @Override
    public List<Transition> getTransitions() {
        return transitions;
    }
}
