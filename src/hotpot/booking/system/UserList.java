package hotpot.booking.system;

import java.util.ArrayList;
import java.util.List;

public class UserList{
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
}