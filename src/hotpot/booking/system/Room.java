package hotpot.booking.system;

import java.util.Random;

public class Room extends Package{
    RoomList roomList = RoomList.getInstance();
    private int pax;
    private int roomNumber;
    static Random genNum = new Random();
    
    public Room(int capacity, double basePrice){
        this.pax = capacity;
        super.setBasePrice(basePrice);
        this.roomNumber = generateRoomNum();
    }
    
    private Integer generateRoomNum(){
        Integer n = null;
        
        int repeat = 1;
        while(repeat == 1){
            n = genNum.nextInt(0001, 9999);
            for(Room r: roomList.availableRooms){
                if(n == r.getRoomNumber()){
                    n = genNum.nextInt(0001, 9999);
                }
            }
            
            for(Room r: roomList.bookedRooms){
                if(n == r.getRoomNumber()){
                    n = genNum.nextInt(0001, 9999);
                }
            }
            repeat = 0;
        }
        return n;
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