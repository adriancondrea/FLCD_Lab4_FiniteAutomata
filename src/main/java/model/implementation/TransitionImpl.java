package model.implementation;

import model.State;
import model.Transition;

public class TransitionImpl implements Transition {
    private final String action;
    private final State nextState;

    public TransitionImpl(String action, State nextState) {
        this.action = action;
        this.nextState = nextState;
    }

    @Override
    public boolean isPossible(CharSequence c) {
        return this.action.equalsIgnoreCase(String.valueOf(c));
    }

    @Override
    public State getNextState() {
        return this.nextState;
    }

    @Override
    public String getAction() {
        return action;
    }
}
