package jacksondemo;

import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@JsonPropertyOrder(value = {"seatId", "menu", "room", "bookedDateTimeStr", "dueDateTimeStr", "paid"}, alphabetic = true)
@JsonIgnoreProperties({"bookedDateTime", "dueDateTime", "menuPkg", "roomNumber"})
public class Booking{
    static final Scanner input = new Scanner(System.in);
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    static LocalDateTime cal = LocalDateTime.now();
    static RoomList roomList = RoomList.getInstance();
    static MenuList menuList = MenuList.getInstance();
    
    private String seatId;
    private String menuPkg;
    private int roomNumber;
    private boolean paid = false;
    private User user;
    private Menu menu;
    private Room room;
    private LocalDateTime bookedDateTime = null; //date of booking
    private LocalDateTime dueDateTime; //due date for user to pay
    private String bookedDateTimeStr, dueDateTimeStr; //string versions of the calendar objects
    
    public Booking(User u, String s, boolean p, Menu m, Room r){
        this.menu = m;
        this.room = r;
        this.user = u;
        this.menuPkg = getMenuPkgName(this.menu);
        this.roomNumber = r.getRoomNumber();
        this.paid = p;
        this.bookedDateTime = cal;
        this.dueDateTime = calculateDatePayment(bookedDateTime);
        this.bookedDateTimeStr = toStringDate(bookedDateTime);
        this.dueDateTimeStr = toStringDate(dueDateTime);
        this.seatId = s;
    }
    
    private Booking(){
        
    }
    
    private LocalDateTime calculateDatePayment(LocalDateTime bookingEvent){
        LocalDateTime dueEvent = (!this.paid) ? bookingEvent.plusDays(3) : null;
        return dueEvent;
    }
    
    private String toStringDate(LocalDateTime c){
        if(c == null){
            return null;
        }else{
            return c.format(formatter);
        }
    }
    
    /*
    public void setMenuPkg(User u) {
        int repeat = 1;
        
        do{
            int select = User.DEFAULT_SELECT;
            int i = 1;
            
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("Enter a number 0 or lesser to exit to Editing Menu\nSelect a new menu package: \nIndex\t Menu Name\t Price");
                    for(Menu menuBooking : menuList.menus){
                        if(this.menu != menuBooking){
                            System.out.printf(i + ".\t " + menuBooking.getMenuName() + " \t RM%.2f" + "\n", menuBooking.getBasePrice());
                            i++;
                        }
                    }
                    
                    select = input.nextInt() - 1;
                    if(select < UserMain.CANCEL_INT){
                        UserMain.repeatMain = 1;
                        return;
                    }
                    
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            for(Menu menuBooking: menuList.menus){
                if(select == menuList.menus.indexOf(menuBooking)){
                    this.menu = menuBooking;
                    this.menuPkg = menuBooking.getMenuName();
                    repeat = 0;
                }
            }
            
        }while(repeat == 1);
        
        User.repeatEditing = 1;
    }
    
    public void setRoomPkg(User u) {
        int repeat = 1;
        Room newRoom = null;
        
        do{
            int select = User.DEFAULT_SELECT;
            int i = 1;
            
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("(Enter a number 0 or lesser to exit to main)\n\nIndex\t Room Number\t Capacity\t Price");
                    
                    for(Room roomBooking : roomList.rooms){
                        if(this.room != roomBooking){
                            System.out.printf(i + ".\t " + roomBooking.getRoomNumber() + "\t\t " + roomBooking.getPax() + "\t\t RM%.2f" + "\n", roomBooking.getBasePrice());
                            i++;
                        }
                    }
                    
                    select = input.nextInt() - 1;
                    if(select < UserMain.CANCEL_INT){
                        UserMain.repeatMain = 1;
                        return;
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again: ");
                   input.nextLine();
                }
            }
            
            for(Room roomBooking : roomList.rooms){
                if(select == roomList.rooms.indexOf(roomBooking)){
                    this.room = roomBooking;
                    this.roomNumber = roomBooking.getRoomNumber();
                    newRoom = roomBooking;
                    repeat = 0;
                }
            }
            
        }while(repeat == 1);
        
        User.repeatEditing = 1;
    }
    */
    
    @JsonGetter
    private String getMenuPkgName(Menu m){
        return m.getMenuName();
    }
    
    @JsonSetter
    public void setUser(User u){
        this.user = u;
    }
    
    @JsonGetter
    public User getUser(){
        return user;
    }
    
    @JsonGetter
    public Menu getMenu(){
        return menu;
    }
    
    @JsonSetter
    public void setMenu(Menu m){
        this.menu = m;
    }
    
    @JsonGetter
    public Room getRoom(){
        return room;
    }
    
    @JsonGetter
    public LocalDateTime getDueDate(){
        return dueDateTime;
    }
    
    @JsonProperty("dueDate")
    public void setDueDateStr(LocalDateTime cal){
        this.dueDateTimeStr = toStringDate(cal);
    }
    
    @JsonGetter
    public LocalDateTime getBookedDate(){
        return bookedDateTime;
    }
    
    @JsonProperty("bookedDate")
    public void setBookedDateStr(LocalDateTime cal){
        this.bookedDateTimeStr = toStringDate(cal);
    }
    
    @JsonProperty("seatId")
    public String getSeatId() {
        return seatId;
    }

    @JsonProperty("seatId")
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
    
    @JsonGetter
    public int getRoomNumber() {
        return roomNumber;
    }
    
    @JsonGetter
    public boolean isPaid(){
        return paid;
    }
    
    @JsonSetter
    public void setPaid(boolean p){
        this.paid = p;
    }
    
    public String toString(char type){
        switch(type){
            case 'f' -> {
                return "\n\tMenu: " + this.menuPkg + "\n\tSeat Number: " + this.seatId + "\n\tRoom Number: " + this.roomNumber + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + this.dueDateTimeStr;
            }
            case 'v' -> {
                return "\n\tMenu: " + this.menuPkg + "\n\tSeat Number: " + this.seatId + "\n\tRoom Number: " + this.roomNumber + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + ((this.dueDateTimeStr == null) ? "N/A" : this.dueDateTimeStr) + "\n\tPaid: " + ((this.paid) ? "Yes" : "No");
            }
            default -> {
                return null;
            }
        }
    }
}