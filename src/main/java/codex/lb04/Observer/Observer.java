package codex.lb04.Observer;

import codex.lb04.Message.Message;

/**
 * Observer interface. It supports a generic method of update.
 */
public interface Observer {
    void update(Message message);
}
