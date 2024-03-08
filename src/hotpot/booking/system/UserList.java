package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserList implements JsonState{
    @JsonBackReference
    List<User> users;
    
    private UserList(){
        users = new ArrayList();
    }
    
    public static UserList getInstance(){
        return UserListHolder.INSTANCE;
    }
    
    private static class UserListHolder {
        
        private static final UserList INSTANCE = new UserList();
    }
    
    public void add(User u){
        users.add(u);
    }
    
    public void remove(User u){
        users.remove(u);
    }
    
    @Override
    public void serialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deserialize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}