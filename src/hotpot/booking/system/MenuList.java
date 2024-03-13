package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "menuDetails")
public class MenuList{
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
}