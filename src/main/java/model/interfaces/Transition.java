package model.interfaces;

public interface Transition {
    boolean isPossible(CharSequence c);
    State getNextState();
    String getAction();
}
