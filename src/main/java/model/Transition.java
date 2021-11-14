package model;

public interface Transition {
    boolean isPossible(CharSequence c);
    State getNextState();
    String getAction();
}
