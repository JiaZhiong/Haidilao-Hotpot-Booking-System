package hotpot.booking.system;

import java.util.ArrayList;
import java.util.Random;

public class Room extends Package{
    private static final long serialVersionUID = 1L;
    
    private int pax;
    private int roomNumber;
    static ArrayList<Room> availableRooms = new ArrayList<>();
    static ArrayList<Room> bookedRooms = new ArrayList<>();
    transient static Random genNum = new Random();
    
    public Room(int capacity, double basePrice){
        this.pax = capacity;
        super.setBasePrice(basePrice);
        this.roomNumber = genNum.nextInt(0001, 9999);
    }

    public void setPax(int pax) {
        this.pax = pax;
    }
    
    public int getPax() {
        return pax;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}