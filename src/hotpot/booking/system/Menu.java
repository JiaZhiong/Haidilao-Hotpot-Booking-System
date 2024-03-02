package hotpot.booking.system;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu extends Package{
    private static final long serialVersionUID = 1L;
    
    private String menuName;
    static ArrayList<Menu> menus = new ArrayList<>();
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