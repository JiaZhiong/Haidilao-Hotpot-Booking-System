package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import java.util.Random;

@JsonPropertyOrder({"roomNumber", "pax", "basePrice"})
public class Room{
    RoomList roomList = RoomList.getInstance();
    private int pax;
    private int roomNumber;
    private double basePrice = 0.0;
    static Random genNum = new Random();
    
    public Room(int capacity, double basePrice){
        this.pax = capacity;
        this.basePrice = basePrice;
        this.roomNumber = generateRoomNum();
    }
    
    private Room(int roomNumber){
        this.roomNumber = roomNumber;
    }
    
    private Room(){
        
    }
    
    private Integer generateRoomNum(){
        Integer n = null;
        
        int repeat = 1;
        while(repeat == 1){
            n = genNum.nextInt(1000, 9999);
            for(Room r: roomList.rooms){
                if(n == r.getRoomNumber()){
                    n = genNum.nextInt(1000, 9999);
                }
            }
            repeat = 0;
        }
        return n;
    }

    @JsonSetter
    public void setPax(int pax) {
        this.pax = pax;
    }
    
    @JsonGetter
    public int getPax() {
        return pax;
    }
    
    @JsonGetter
    public double getBasePrice() {
        return basePrice;
    }

    @JsonSetter
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    
    @JsonSetter
    public void setRoomNumber(int rNum) {
        this.roomNumber = rNum;
    }
    
    @JsonGetter
    public int getRoomNumber() {
        return roomNumber;
    }
}