package hotpot.booking.system;

import java.util.ArrayList;

public class Menu extends Package{
    private String menuName;
    ArrayList<String> item = new ArrayList<>();
    
    public Menu(String menuName, double basePrice){
        this.menuName = menuName;
        this.setBasePrice(basePrice);
    }
    
    public Menu(String menuName){
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public void addItem(){
        
    }
    
    public void removeItem(){
        
    }
}