package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "roomDetails")
public class RoomList{
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
}