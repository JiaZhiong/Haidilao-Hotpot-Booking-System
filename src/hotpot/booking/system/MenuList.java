package hotpot.booking.system;

import java.util.ArrayList;
import java.util.List;

public class MenuList implements JsonState{
    List<Menu> menus = null;
    
    private MenuList() {
        menus = new ArrayList();
    }
    
    public static MenuList getInstance() {
        return MenuListHolder.INSTANCE;
    }
    
    private static class MenuListHolder {

        private static final MenuList INSTANCE = new MenuList();
    }
    
    public void record(Menu m){
        menus.add(m);
    }
    
    public void drop(Menu m){
        menus.remove(m);
    }
    
    @Override
    public void serialize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deserialize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}