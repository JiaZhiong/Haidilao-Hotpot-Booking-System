package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@JsonRootName(value = "roomDetails")
public class RoomList extends HashMap<Integer, Room>{
    private static File savePath = new File("src\\Rooms.json");
    HashMap<Integer, Room> rooms;
    
    private RoomList() {
        rooms = new HashMap();
    }
    
    public static RoomList getInstance() {
        return RoomListHolder.INSTANCE;
    }
    
    private static class RoomListHolder {

        private static final RoomList INSTANCE = new RoomList();
    }
    
    public void open(Room r){
        rooms.put(r.getRoomNumber(), r);
    }
    
    public void close(Integer i){
        rooms.remove(i);
    }
    
    protected void saveRooms() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        
       mapper.writerWithDefaultPrettyPrinter().writeValue(savePath, rooms);
    }
    
    protected void retrieveRooms() throws IOException{
        JsonMapper mapper = new JsonMapper();
        
        if(savePath.exists()){
            rooms = mapper.readValue(savePath, new TypeReference<HashMap<Integer, Room>>(){});
        }
    }
}