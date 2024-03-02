package hotpot.booking.system;

public class Item {
    public String itemName;
    public double pricePerItem;
    public char itemType;
    //M - main course, A - appetizer, S - sides, 

    public Item(String itemName, double pricePerItem, char itemType) {
        this.itemName = itemName;
        this.pricePerItem = pricePerItem;
        this.itemType = itemType;
    }
    
    public Item(){
        this.itemName = "Item";
        this.pricePerItem = 0.0;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public void setItemType(char itemType) {
        this.itemType = itemType;
    }
}