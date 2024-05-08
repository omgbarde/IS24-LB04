package codex.lb04.Observer;

import codex.lb04.Message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * observable class that is extended by classes that need to be observed.
 * contains methods to add and remove observers and to notify them.
 */
public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * adds observer to the list.
     *
     * @param observer the observer you want to add.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * removes observer from the list.
     *
     * @param observer the observer you want to remove.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * notifies all the observers in the list.
     *
     * @param message the message you want to send to the observers.
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}