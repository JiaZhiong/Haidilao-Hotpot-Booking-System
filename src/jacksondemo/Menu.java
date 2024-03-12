package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;

@JsonPropertyOrder({"menuName", "basePrice"})
public class Menu{
    private String menuName;
    private double basePrice = 0.0;
    ArrayList<Item> item = new ArrayList<>();
    
    public Menu(String menuName, double basePrice){
        this.menuName = menuName;
        this.basePrice = basePrice;
    }
    
    @JsonCreator
    private Menu(String menuName){
        this.menuName = menuName;
    }

    @JsonValue
    public String getMenuName() {
        return menuName;
    }

    @JsonProperty("menu")
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    @JsonGetter
    public double getBasePrice() {
        return basePrice;
    }

    @JsonSetter
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    
    /*
    public void addItem(Item i){
        item.add(i);
    }
    
    public void removeItem(Item i){
        item.remove(i);
    }
    */
}