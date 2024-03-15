package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({"menuName", "basePrice"})
public class Menu{
    private String menuName = "";
    private double basePrice = 0.0;
    
    public Menu(String menuName, double basePrice){
        this.menuName = menuName;
        this.basePrice = basePrice;
    }
    
    private Menu(){
        
    }

    @JsonGetter
    public String getMenuName() {
        return menuName;
    }

    @JsonProperty("menuName")
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
}