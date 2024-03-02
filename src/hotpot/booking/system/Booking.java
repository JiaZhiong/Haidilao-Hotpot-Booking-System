package hotpot.booking.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Booking extends ObjectState implements Serializable{
    private static final long serialVersionUID = 1L;
    static final transient Scanner input = new Scanner(System.in);
    
    private String menuPkg;
    private int roomNumber;
    private boolean paid = false;
    private User user;
    private Menu menu;
    private Room room;
    private LocalDateTime bookedDateTime; //date of booking
    private LocalDateTime dueDateTime; //due date for user to pay
    private String bookedDateTimeStr, dueDateTimeStr; //string versions of the calendar objects
    
    public Booking(User u, int roomPkg, boolean paid, Menu m, Room r){
        this.menu = m;
        this.room = r;
        this.user = u;
        this.menuPkg = getMenuPkgName(this.menu);
        this.roomNumber = roomPkg;
        this.paid = paid;
        this.bookedDateTime = LocalDateTime.now();
        this.dueDateTime = calculateDatePayment(bookedDateTime);
        this.bookedDateTimeStr = toStringDate(bookedDateTime);
        this.dueDateTimeStr = toStringDate(dueDateTime);
    }
    
    private LocalDateTime calculateDatePayment(LocalDateTime bookingEvent){
        LocalDateTime dueEvent = (!this.paid) ? bookingEvent.plusDays(3) : null;
        return dueEvent;
    }
    
    private String toStringDate(LocalDateTime cal){
        if(cal == null){
            return null;
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
            return cal.format(formatter);
        }
    }
    
    private String getMenuPkgName(Menu m){
        return m.getMenuName();
    }
    
    public void setMenuPkg(User u) {
        int repeat = 1;
        
        do{
            int select = User.DEFAULT_SELECT;
            int i = 1;
            
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("Enter a number 0 or lesser to exit to Editing Menu\nSelect a new menu package: \nIndex\t Menu Name\t Price");
                    for(Menu menuBooking : Menu.menus){
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
            
            for(Menu menuBooking: Menu.menus){
                if(select == Menu.menus.indexOf(menuBooking)){
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
            boolean roomStatus = u.checkRooms();
            if(!roomStatus){
                System.out.println("There are no other available rooms for now. Please retry or contact us for more details.\n");
                UserMain.repeatMain = 1;
                return;
            }
            
            int select = User.DEFAULT_SELECT;
            int i = 1;
            
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("(Enter a number 0 or lesser to exit to main)\n\nIndex\t Room Number\t Capacity\t Price");
                    
                    for(Room roomBooking : Room.availableRooms){
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
            
            Room.bookedRooms.remove(this.room);
            Room.availableRooms.add(this.room);
            
            for(Room roomBooking : Room.availableRooms){
                if(select == Room.availableRooms.indexOf(roomBooking)){
                    this.room = roomBooking;
                    this.roomNumber = roomBooking.getRoomNumber();
                    newRoom = roomBooking;
                    repeat = 0;
                }
            }
            
        }while(repeat == 1);
        
        Room.availableRooms.remove(newRoom);
        Room.bookedRooms.add(newRoom);
        
        User.repeatEditing = 1;
    }
    
    public void setUser(User u){
        this.user = u;
    }
    
    public User getUser(){
        return user;
    }
    
    public Menu getMenu(){
        return menu;
    }
    
    public Room getRoom(){
        return room;
    }
    
    public LocalDateTime getDueDate(){
        return dueDateTime;
    }
    
    public boolean isPaid(){
        return paid;
    }
    
    public void setPaid(boolean p){
        this.paid = p;
    }
    
    public String toString(char type){
        switch(type){
            case 'f' -> {
                return "\n\tMenu: " + this.menuPkg + "\n\tRoom Number: " + this.roomNumber + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + this.dueDateTimeStr;
            }
            case 'v' -> {
                return "\n\tMenu: " + this.menuPkg + "\n\tRoom Number: " + this.roomNumber + "\n\tDate & Time of Booking: " + this.bookedDateTimeStr + 
                    "\n\tPayment Due Date: " + ((this.dueDateTimeStr == null) ? "N/A" : this.dueDateTimeStr) + "\n\tPaid: " + ((this.paid) ? "Yes" : "No");
            }
            default -> {
                return null;
            }
        }
    }
}