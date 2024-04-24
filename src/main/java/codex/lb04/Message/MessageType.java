package codex.lb04.Message;

/**
 * enum of all message types
 */
public enum MessageType {

    //network messages
    GENERIC_MESSAGE,    //generic message
    OK_MESSAGE,         //message sent form the server after receiving any message correctly
    PING,               //ping message sent from server to see if client is alive
    PONG,               //pong message sent from client to server to show that it is alive
    LOGIN_REQUEST,      //login request message
    LOGIN_REPLY,        //login reply message based on game conditions
    LOGOUT_REQUEST,     //logout request message e.g. when pressing the exit button
    LOGOUT_REPLY,       //logout reply message based on game conditions
    ERROR,               //error message signals a generic error in the game

    //game messages
    PICK_SECRET_OBJECTIVE,
    PICK_RESOURCE_CARD,
    PICK_GOLD_CARD,
}