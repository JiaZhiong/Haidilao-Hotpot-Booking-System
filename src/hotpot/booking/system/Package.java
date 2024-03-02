package hotpot.booking.system;

import java.io.Serializable;

public abstract class Package extends ObjectState implements Serializable{
    private static final long serialVersionUID = 1L;
    private double basePrice = 0.0;

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}