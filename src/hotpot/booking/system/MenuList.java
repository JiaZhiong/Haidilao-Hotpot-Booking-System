package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@JsonRootName(value = "menuDetails")
public class MenuList extends HashMap<String, Menu>{
    private static File savePath = new File("src\\hotpot\\booking\\system\\Menus.json");
    HashMap<String, Menu> menus = null;
    
    private MenuList() {
        menus = new HashMap();
    }
    
    public static MenuList getInstance() {
        return MenuListHolder.INSTANCE;
    }
    
    private static class MenuListHolder {

        private static final MenuList INSTANCE = new MenuList();
    }
    
    public void record(String s, Menu m){
        menus.put(s, m);
    }
    
    public void drop(String s){
        menus.remove(s);
    }
    
    protected void saveMenus() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.writerWithDefaultPrettyPrinter().writeValue(savePath, menus);
    }
    
    protected void retrieveMenus() throws IOException{
        JsonMapper mapper = new JsonMapper();
        
        if(savePath.exists()){
            menus = mapper.readValue(savePath, new TypeReference<HashMap<String, Menu>>(){});
        }
    }
}