package Entity;

public enum typeOfPayment {
    CREDIT_CARD(0),
    VISA(1),
    PAYPAL(2),
    WIRE_TRANSFER(3);

    private int value;
    typeOfPayment(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
