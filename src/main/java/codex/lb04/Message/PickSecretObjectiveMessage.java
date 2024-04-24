package codex.lb04.Message;

public class PickSecretObjectiveMessage extends Message{

    private Integer cardPick;

    public PickSecretObjectiveMessage(String username  , Integer cardPick){
        super(username , MessageType.PICK_SECRET_OBJECTIVE);
        this.cardPick = cardPick;
    }

    public Integer getCardPick(){
        return cardPick;
    }

    public String getUsername(){
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "PositionMessage{" +
                "nickname=" + getUsername() +
                ", pick="  + cardPick +
                '}';
    }
}
