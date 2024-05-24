package codex.lb04.Message;

/**
 * enum of all message types
 */
public enum MessageType {

    /**
     * network messages
     **/
    GENERIC_MESSAGE,                //generic message
    PING,                           //ping message sent from server to see if client is alive
    PONG,                           //pong message sent from client to server to show that it is alive (sent by automatic response)
    LOGIN_REQUEST,                  //login request message
    LOGIN_REPLY,                    //login reply message based on game conditions
    CHAT_MESSAGE,
    ERROR,                          //error message signals a generic error in the game

    /**
     * game messages
     **/
    PICK_SECRET_OBJECTIVE,              //message sent when the player picks a secret objective
    PICK_RESOURCE_CARD,                 //message sent when the player picks a resource card to draw
    PICK_GOLD_CARD,                     //message sent when the player picks a gold card to draw
    PICK_INITIAL_CARD_SIDE,             //message sent when the player picks the side of the initial card
    PLACE_CARD,                         //message sent when the player places a card on the board
    END_TURN,                           //message sent when the player ends his turn
    FLIP_CARD,                          //message sent when the player flips a card
    DEAD_CLIENT,                        //message sent when a client is dead
    START_TURN,                         //message sent to the new active player
    PLAYERS_CONNECTED,                  //message that contains the clients connected sent when a client connects to the game
    CREATE_GAME,                        //message sent when a client creates a game or automatically when the max number of players is reached
    INVALID_INPUT,                      //message sent when the input is invalid

    /**
     * messages to update the view
     */
    READY,                              //message sent when the player is ready, after instantiating the board
    UPDATE_GOLD,                        //message sent to a player to update the gold cards
    DRAW_BOARD,                         //message sent to a player to draw the board
    UPDATE_RESOURCE,                    //message sent to a player to update the resource cards visible
    UPDATE_HAND,                        //message sent to a player to update his hand
    UPDATE_COMMON_OBJECTIVES,           //message sent to a player at the start of the game to display the common objectives
    UPDATE_INITIAL_CARD_DISPLAY,        //message sent to a player at the start of the game to display the initial card
    UPDATE_SECRET_OBJECTIVE_TO_CHOOSE,  //message sent to a player to display the secret objectives to choose from
    UPDATE_SECRET_OBJECTIVE,            //message sent to a player to display the secret objective that he has chosen
    UPDATE_POINTS,                      //message sent to a player to update his points after every time he places a card
    WINNERS                             //message sent to all players to show the winners
}