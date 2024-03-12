package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "roomDetails")
public class RoomList extends ArrayList<Room> implements JsonState{
    List<Room> rooms;
    
    private RoomList() {
        rooms = new ArrayList();
    }
    
    public static RoomList getInstance() {
        return RoomListHolder.INSTANCE;
    }
    
    private static class RoomListHolder {

        private static final RoomList INSTANCE = new RoomList();
    }
    
    public void open(Room r){
        rooms.add(r);
    }

    @JsonGetter
    public List<Room> getRooms() {
        return rooms;
    }

    @JsonSetter
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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