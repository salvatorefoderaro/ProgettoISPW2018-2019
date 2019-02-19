package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfPayment {
    CREDIT_CARD(0),
    VISA(1),
    PAYPAL(2),
    WIRE_TRANSFER(3);

    private int value;
    typeOfPayment(int value){
        this.value = value;
    }

    public static typeOfPayment valueOf(int inVal){
        for (typeOfPayment type : values())
            if (type.getValue() == inVal )
                return type;
        return null;
    }

    public int getValue(){
        return this.value;
    }
}
