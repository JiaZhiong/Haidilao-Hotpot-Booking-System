package hotpot.booking.system;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements BookingFunctions{
    static int repeatEditing = 1;
    static BookingList bookingList = BookingList.getInstance();
    static UserList userList = UserList.getInstance();
    static RoomList roomList = RoomList.getInstance();
    static MenuList menuList = MenuList.getInstance();
    
    //constants for print type
    static final char VIEW = 'v';
    static final char FILTER = 'f';
    
    //constants for exiting operations
    static final int DEFAULT_SELECT = -1;
    
    static final Scanner input = new Scanner(System.in);
    private String name = "User";
    ArrayList<Booking> bookingPayables = new ArrayList(); //array to store bookings that are not paid
    
    //constructor
    public User(String username){
        this.name = username;
    }
    
    public String getName(){
        return name;
    }

    @Override
    public void addBooking(User u) {
        //user selections
        int roomSelect = -1;
        Boolean paySelect = false;
        
        //store objects of selection
        Menu menuObj = null;
        Room roomObj = null;
        
        //loop counter
        int repeat = 1;
        
        //check whether there are available rooms
        if(!roomList.compare()){
            return;
        }
        
        System.out.println("Enter a number 0 or lesser to exit to main\nSelect a menu package (via number)");
        
        //USER INPUT FOR MENU PACKAGE
        do{
            int select = DEFAULT_SELECT;
            
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("Index\t Menu Name\t Price");
                    menuList.menus.forEach((menu) -> {
                        System.out.printf((menuList.menus.indexOf(menu) + 1) + ".\t " + menu.getMenuName() + "\t RM%.2f" + "\n", menu.getBasePrice());
                    });
                    
                    select = input.nextInt() - 1;
                    if(select < UserMain.CANCEL_INT){
                        System.out.println("1");
                        UserMain.repeatMain = 1;
                        return;
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            for(Menu menu: menuList.menus){
                if(select == menuList.menus.indexOf(menu)){
                    menuObj = menu;
                    repeat = 0;
                }
            }
        }while(repeat == 1);
        
        repeat = 1;
        System.out.println((repeat == 1) ? "Enter a number 0 or lesser to exit to main\nSelect a room package (via number)" : "");
        
        //USER INPUT FOR ROOM PACKAGE
        do{
            if(!roomList.compare()){
                return;
            }
            
            int select = DEFAULT_SELECT;
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("Index\t Room Number\t Capacity\t Price");
                    
                    roomList.availableRooms.forEach((room) -> {
                        System.out.printf((roomList.availableRooms.indexOf(room) + 1) + ".\t " + room.getRoomNumber() + "\t\t " + room.getPax() + "\t\t RM%.2f" + "\n", room.getBasePrice());
                    });
                    
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
            
            for(Room room: roomList.availableRooms){                
                if(select == roomList.availableRooms.indexOf(room)){
                    roomObj = room;
                    repeat = 0;
                    break;
                }
            }
            roomList.book(roomList.availableRooms.get(select));
            
            
            if(roomSelect == -1){
                System.out.println("\nPlease enter a valid room package listed below: ");
            }
        }while(repeat == 1);
        
        repeat = 1;
        //USER INPUT FOR BOOKING PAYMENT
        System.out.println("""

                   ATTENTION DEAR USER! 
                   !!Make sure you confirm your booking details before making payment. If booking has been paid, you will be unable to edit booking details!!""");
        System.out.println("Do you wish to pay now or later (now - y, later - n): ");
        
        do{
            String select = input.next();
                switch (select) {
                    case "y" -> {
                        System.out.printf("\nYour total amount is RM%.2f\nPayment Successful! Thank you for booking!\n", BookingFunctions.payBooking(menuObj, roomObj)); //CALL payBooking() METHOD TO CALCULATE FULL TOTAL
                        paySelect = true;
                        repeat = 0;
                }
                    case "n" -> {
                        System.out.println("\n!!Payments will have to made in 72 hours!!");
                        repeat = 0;
                }
                    default -> {
                        System.out.println("Please enter yes or no: ");
                    }
                }
            
        }while(repeat == 1);
        

        //add booking to the global booking list
        bookingList.record(new Booking(this, paySelect, menuObj, roomObj));
        
        System.out.println("\nBooking Successfully Placed! You can view bookings to confirm the due date of payment if you chose to pay later>\nHave a nice day!\n");
        UserMain.repeatMain = 1;
    }

    @Override
    public void editBooking(User u) {
        Booking selectedBooking = null;
        
        int repeat = 1;
        do{
            sortBooking();
            
            this.bookingPayables.forEach(booking -> {
                System.out.println("\n" + (bookingList.bookings.indexOf(booking) + 1) + booking.toString(VIEW));
            });

            int bookingSelect = User.DEFAULT_SELECT;
            while(bookingSelect < UserMain.CANCEL_INT){
                try{
                    System.out.println("\nSelect a booking to edit:");
                    bookingSelect = input.nextInt() - 1;
                }catch(InputMismatchException e){
                   System.out.println("Invalid response. Please enter again: ");
                   input.nextLine();
                }
            }
            
            for(Booking booking : this.bookingPayables){
                if(bookingSelect == bookingList.bookings.indexOf(booking)){
                    selectedBooking = booking;
                    repeat = 0;
                }
            }
            
        }while(repeat == 1);
        
        String editOption;
        
        System.out.println("""
                                                        !ATTENTION DEAR USER!
                            (TYPING "haidilao" WILL CANCEL SELECTED OPERATION AND RETURN YOU BACK TO MAIN)""");
        System.out.println("");
        do{
            System.out.println("""
                                                                                  Editing Menu
                                                                          --Edit Booking Information--
                               [1. Change Menu Package    2. Change Room Package    3. Transfer Booking to Friend (Another User)    4. Cancel Booking    Q. Back to Main]""");
            editOption = input.next();
            
            switch(editOption){
                case "1" -> {
                    selectedBooking.setMenuPkg(this);
                    System.out.println("\nChanges saved successfully!");
                    repeatEditing = 0;
                }
                case "2" -> {
                    selectedBooking.setRoomPkg(this);
                    System.out.println("\nChanges saved successfully!");
                    repeatEditing = 0;
                }
                case "3" -> {
                    System.out.println("\nSelect a user to transfer booking to: ");
                    User newUser = null;
                    
                    while(newUser == null){
                        newUser = verifyUser();
                    }
                    
                    boolean repeatConfirm = true;
                    String confirm;
                    System.out.println("Are you sure? Enter yes to confirm or no to cancel:");
                    do{
                        confirm = input.next().toLowerCase();
                        switch(confirm){
                            case "yes" -> {
                                repeatConfirm = false;
                                u.transferBooking(u, newUser, selectedBooking);
                                repeatEditing = 0;
                            }
                            case "no" -> {
                                repeatConfirm = false;
                                repeatEditing = 1;
                                return;
                            }
                            default -> {
                                System.out.println("Please enter yes or no: ");
                            }
                        }
                    }while(repeatConfirm);
                }
                case "4" -> {
                    this.cancelBooking(u, selectedBooking);
                }
                case "q", "Q" -> {
                    repeatEditing = 0;
                    UserMain.repeatMain = 1;
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
            
        }while(repeatEditing == 1);
    }
    
    @Override
    public void cancelBooking(User u, Booking b) {
        boolean repeatConfirm = true;
        String confirm;
        System.out.println("Are you sure? Enter yes to confirm or no to cancel>>");
        do{
            confirm = input.next().toLowerCase();
            switch(confirm){
                case "yes" -> {
                    repeatConfirm = false;
                }
                case "no" -> {
                    repeatConfirm = false;
                    repeatEditing = 1;
                    return;
                }
                default -> {
                    System.out.println("Please enter yes or no: ");
                }
            }
        }while(repeatConfirm);
        
        bookingList.drop(b);
        roomList.drop(b.getRoom());
        
        System.out.println("Booking Successfully Cancelled!");
        UserMain.repeatMain = 1;
    }

    @Override
    public void filterBooking(User u) {
        LocalDateTime cal = LocalDateTime.now();
        
        sortBooking();
        if(bookingPayables.isEmpty()){
            System.out.println("No bookings due for payment found for this user. Please make a booking!\n");
            UserMain.repeatMain = 1;
            return;
        }
        
        for(Booking booking: bookingPayables){
            System.out.println("");
            if(cal.isAfter(booking.getDueDate())){
                System.out.println("\n\t[This booking has passed the due date of payment(!).]");
                //check whether the list of unpaid bookings will be empty if current booking is the only element
                if(bookingPayables.size() - 1 >= 1){
                    roomList.free(booking.getRoom());
                    bookingList.drop(booking);
                    System.out.println("Bookings with oustanding balance from \'" + u.getName() + "\':");
                }else{
                    booking.toString(FILTER);
                    
                    //check whether booking is overdue
                    System.out.println("No bookings found for this user. Please make a booking!\n");
                    UserMain.repeatMain = 1;
                    return;
                }
            }else{
                System.out.println((bookingPayables.indexOf(booking) + 1) + booking.toString(FILTER));
            }
        }
        
        System.out.println("");
        
        int select = User.DEFAULT_SELECT;
        while(select < UserMain.CANCEL_INT){
            try{
                System.out.println("Please select a booking to pay oustanding amount:");
                
                select = input.nextInt() - 1;
                if(select < UserMain.CANCEL_INT){
                    UserMain.repeatMain = 1;
                    return;
                }
            }catch(InputMismatchException e){
               System.out.println("Invalid response. Please enter again: ");
               input.nextLine();
            }
        }
        
        for(Booking booking : bookingList.bookings){
            if(select == bookingList.bookings.indexOf(booking)){
                System.out.printf("Paid RM%.2f. Thank you cooperating by paying before the due date!\n", BookingFunctions.payBooking(booking.getMenu(), booking.getRoom()));
                booking.setPaid(true);
            }
        }
        
        UserMain.repeatMain = 1;
        System.out.println("");
    }
    
    @Override
    public void viewBooking(User u) {
        ArrayList<Booking> userBookings = new ArrayList();
        
        bookingList.bookings.forEach((booking) -> {
            if(booking.getUser().equals(this)){
                userBookings.add(booking);
            }
        });
        
        System.out.println((!userBookings.isEmpty()) ? "\nBookings from \'" + u.getName() + "\':" : "\nNo bookings found under this user.");
        
        userBookings.forEach((userBooking) -> {
            System.out.println((userBookings.indexOf(userBooking) + 1) + userBooking.toString(VIEW));
        });
        
        System.out.println("");
        UserMain.repeatMain = 1;
    }
    
    private void transferBooking(User oldUser, User newUser, Booking bookingTransfer){
        if(bookingTransfer != null){
            bookingTransfer.setUser(newUser);
        }else{
            System.out.println("\nTransfer failed. Please try again");
            repeatEditing = 1;
            return;
        }
        
        System.out.println("Transfer to " + newUser.getName() + " successful!");
    }
    
    private ArrayList<Booking> sortBooking(){
        bookingPayables.clear();
        bookingList.bookings.forEach((booking) -> {
            if(booking.getUser().equals(this) && !booking.isPaid()){
                bookingPayables.add(booking);
            }
        });
        
        return bookingPayables;
    }
    
    private User verifyUser(){
        int repeat = 1;
        while(repeat == 1){
            String userInput = input.next();
            for(User selectedUser: userList.users){
                if(selectedUser.getName().equals(userInput)){
                    repeatEditing = 0;
                    return selectedUser;
                }
            }
            repeat = 0;
        }
        System.out.println("Could not find user\nPlease re-enter user name: ");
        return null;
    }
}