package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@JsonRootName(value = "userDetails")
public class UserList extends HashMap<String, User>{
    private static File savePath = new File("src\\Users.json");
    HashMap<String, User> users;
    
    private UserList(){
        users = new HashMap();
    }
    
    public static UserList getInstance(){
        return UserListHolder.INSTANCE;
    }
    
    private static class UserListHolder {
        
        private static final UserList INSTANCE = new UserList();
    }
    
    public void register(String s, User u){
        users.put(s, u);
    }
    
    public void remove(String s){
        users.remove(s);
    }
    
    protected void saveUsers() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.writerWithDefaultPrettyPrinter().writeValue(savePath, users);
    }
    
    protected void retrieveUsers() throws IOException{
        JsonMapper mapper = new JsonMapper();
        
        if(savePath.exists()){
            users = mapper.readValue(savePath, new TypeReference<HashMap<String, User>>(){});
        }
    }
}