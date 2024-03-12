package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "menuDetails")
public class MenuList extends ArrayList<Menu> implements JsonState{
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

    @JsonGetter
    public List<Menu> getMenus() {
        return menus;
    }

    @JsonSetter
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
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