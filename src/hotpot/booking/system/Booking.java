package hotpot.booking.system;

import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

@JsonPropertyOrder(value = {"seatId", "menu", "room", "bookedDateTimeStr", "dueDateTimeStr", "paid"}, alphabetic = true)
@JsonIgnoreProperties({"bookedDateTime", "dueDateTime", "menuPkg", "roomNumber"})
public class Booking{
    static final Scanner input = new Scanner(System.in);
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    static LocalDateTime cal = LocalDateTime.now();
    static RoomList roomList = RoomList.getInstance();
    static MenuList menuList = MenuList.getInstance();
    static BookingList bookingList = BookingList.getInstance();
    
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
        this.menuPkg = m.getMenuName();
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

    public void setMenuPkg(User u) {
        int repeat = 1;
        
        do{
            String select = "";
            
            System.out.println("\nEnter 'haidilao' to exit to Editing Menu");
            while(!menuList.menus.containsKey(select)){
                try{
                    //print menus with index
                    System.out.println("Select a new menu package: \nMenu Name\t Price");
                    
                    menuList.menus.forEach((name, m) -> {
                        if(!this.menuPkg.equals(name)){
                            System.out.printf(m.getMenuName() + " \t RM%.2f" + "\n", m.getBasePrice());
                        }
                    });
                    
                    select = input.next();
                    if(select.equalsIgnoreCase(UserMain.CANCEL_STR)){
                        UserMain.repeatMain = 1;
                        return;
                    }else if(select.equals(this.menu.getMenuName())){
                        System.out.println("\nThis menu is unavailable, " + select + " is already your menu package. Please choose another menu.\n");
                        select = "";
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter your selection again:");
                   input.nextLine();
                }
            }
            
            if(menuList.menus.containsKey(select)){
                this.menu = menuList.menus.get(select);
                this.menuPkg = this.menu.getMenuName();
                repeat = 0;
                UserMain.repeatMain = 1;
                System.out.println("\nChanges saved successfully!");
            }else{
                return;
            }
            
        }while(repeat == 1);
        
        User.repeatEditing = 1;
    }
    
    public void setRoomPkg(User u) {
        int repeat = 1;
        
        do{
            int select = User.DEFAULT_SELECT;
            //int i = 1;
            System.out.println("(Enter a number 0 or lesser to exit to main");
            
            while(!roomList.rooms.containsKey(select)){
                try{
                    //print menus with index
                    System.out.println("\nSelect a new room package: \nRoom Number\t Capacity\t Price");
                    
                    roomList.rooms.forEach((num, r) -> {
                        if(!this.room.equals(r)){
                            System.out.printf(r.getRoomNumber() + "\t\t " + r.getPax() + "\t\t RM%.2f" + "\n", r.getBasePrice());
                        }
                    });
                    
                    select = input.nextInt();
                    if(select <= UserMain.CANCEL_INT){
                        UserMain.repeatMain = 1;
                        return;
                    }else if(select == this.room.getRoomNumber()){
                        System.out.println("\nThis menu is unavailable, " + select + " is already your room package. Please choose another room.");
                        select = User.DEFAULT_SELECT;
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter your selection again: ");
                   input.nextLine();
                }
            }
            
            if(roomList.rooms.containsKey(select)){
                this.room = roomList.rooms.get(select);
                this.roomNumber = this.room.getRoomNumber();
                repeat = 0;
                UserMain.repeatMain = 1;
                System.out.println("\nChanges saved successfully!");
            }else{
                return;
            }
            
        }while(repeat == 1);
        
        User.repeatEditing = 1;
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
    
    @JsonSetter
    public void setRoom(Room r){
        this.room = r;
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
                return "\n\tMenu: " + this.menu.getMenuName() + "\n\tSeat Number: " + this.seatId + "\n\tRoom Number: " + this.room.getRoomNumber() + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + this.dueDateTimeStr;
            }
            case 'v' -> {
                return "\n\tMenu: " + this.menu.getMenuName() + "\n\tSeat Number: " + this.seatId + "\n\tRoom Number: " + this.room.getRoomNumber() + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + ((this.dueDateTimeStr == null) ? "N/A" : this.dueDateTimeStr) + "\n\tPaid: " + ((this.paid) ? "Yes" : "No");
            }
            case 'a' -> {
                return "\n\tUser: " + this.user.getName() + "\n\tSeat Number: " + this.seatId + "\n\tMenu: " + this.menu.getMenuName() + "\n\tRoom Number: " + this.room.getRoomNumber() + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + ((this.dueDateTimeStr == null) ? "N/A" : this.dueDateTimeStr) + "\n\tPaid: " + ((this.paid) ? "Yes" : "No");
            }
            default -> {
                return null;
            }
        }
    }
}