package codex.lb04.Message;

/**
 * enum of all message types
 */
public enum MessageType {

    /**
     * network messages
     **/
    GENERIC_MESSAGE,                //generic message
    START_GAME,                     //message sent from the server to the clients to start the game
    OK_MESSAGE,                     //message sent form the server after receiving any message correctly
    PING,                           //ping message sent from server to see if client is alive
    PONG,                           //pong message sent from client to server to show that it is alive
    LOGIN_REQUEST,                  //login request message
    LOGIN_REPLY,                    //login reply message based on game conditions
    LOGOUT_REQUEST,                 //logout request message e.g. when pressing the exit button
    LOGOUT_REPLY,                   //logout reply message based on game conditions
    ERROR,                          //error message signals a generic error in the game

    /**
     * game messages
     **/
    PICK_SECRET_OBJECTIVE,          //message sent when the player picks a secret objective
    PICK_RESOURCE_CARD,             //message sent when the player picks a resource card to draw
    PICK_GOLD_CARD,                 //message sent when the player picks a gold card to draw
    PICK_INITIAL_CARD_SIDE,         //message sent when the player picks the side of the initial card
    PLACE_CARD,                     //message sent when the player places a card on the board
    END_TURN,                       //message sent when the player ends his turn
    FLIP_CARD,                      //message sent when the player flips a card
    DEAD_CLIENT,                    //message sent when a client is dead
    START_TURN,                     //message sent to the new active player
    GAME_STATE,                     //message sent when an update in the game state occurs
    PLAYERS_CONNECTED,              //message that contains the clients connected sent when a client connects to the game
    CREATE_GAME,                    //message sent when a client creates a game or automatically when the max number of players is reached
    QUIT, INVALID_INPUT,                   //message sent when the input is invalid

    /**
     * messages for the view
     */
    DRAW_CARD,
    UPDATE_GOLD,
}