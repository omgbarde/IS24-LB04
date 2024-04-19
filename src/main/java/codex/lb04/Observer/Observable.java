package codex.lb04.Observer;

import codex.lb04.Message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * observable class that is extended by classes that need to be observed.
 * contains methods to add and remove observers and to notify them.
 */
public class Observable {
    private final List<Observer> observerList = new ArrayList<>();

    /**
     * adds observer to the list.
     * @param observer the observer you want to add.
     */
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    /**
     * removes observer from the list.
     * @param observer the observer you want to remove.
     */
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * notify all observers by calling the update method and sends them the {@link Message}.
     * @param message the message to be forwarded.
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observerList) {
            observer.update(message);
        }
    }
}