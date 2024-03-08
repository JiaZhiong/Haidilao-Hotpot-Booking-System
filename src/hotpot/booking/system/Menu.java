package hotpot.booking.system;

import java.util.ArrayList;

public class Menu extends Package{
    private String menuName;
    ArrayList<Item> item = new ArrayList<>();
    
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
    
    public void addItem(Item i){
        item.add(i);
    }
    
    public void removeItem(Item i){
        item.remove(i);
    }
}