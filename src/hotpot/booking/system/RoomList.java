package hotpot.booking.system;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 *
 * @author HP
 */
public class RoomList {
    List<Room> availableRooms;
    List<Room> bookedRooms;
    
    
    private RoomList() {
        availableRooms = new ArrayList();
        bookedRooms = new ArrayList();
    }
    
    public static RoomList getInstance() {
        return RoomListHolder.INSTANCE;
    }
    
    private static class RoomListHolder {

        private static final RoomList INSTANCE = new RoomList();
    }
    
    public void open(Room r){
        availableRooms.add(r);
    }
    
    public void book(Room r){
        availableRooms.remove(r);
        bookedRooms.add(r);
    }
    
    public void drop(Room r){
        availableRooms.add(r);
        bookedRooms.remove(r);
    }
    
    public void free(Room r){
        bookedRooms.remove(r);
    }
    
    public boolean compare(){
        try{
            for(Room a: availableRooms){
                for(Room b: bookedRooms){
                    if(b.equals(a)){
                        availableRooms.remove(b);
                    }
                }
            }
        }catch(ConcurrentModificationException e){
            System.out.println("All rooms have been currently booked. Please contact us for more details or retry again later\n");
            UserMain.repeatMain = 1;
            return false;
        }
        return true;
    }
}