package jacksondemo;

import com.fasterxml.jackson.annotation.*;
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
    
    //constants for seat layout
    final static int ROWS = 10;
    final static int COLS = 12;
    final static char OCCU = 'X';
    final static char FREE = '*';
    
    //constants for print type
    static final char VIEW = 'v';
    static final char FILTER = 'f';
    
    //constants for exiting operations
    static final int DEFAULT_SELECT = -1;
    static private char[][] seatLayout = new char[ROWS][COLS];
    
    static final Scanner input = new Scanner(System.in);
    private String name = "User";
    ArrayList<Booking> bookingPayables = new ArrayList(); //array to store bookings that are not paid
    
    @JsonCreator
    public User(String username){
        this.name = username;
    }
    
    private User(){
        
    }
    
    @JsonValue
    public String getName(){
        return name;
    }

    @JsonSetter
    private void setName(String name) {
        this.name = name;
    }

    /*
    @Override
    public void addBooking(User u) {
        //user selections
        String seatSelect = null;
        Boolean paySelect = false;
        
        //store objects of selection
        Menu menuObj = null;
        Room roomObj = null;
        
        //loop counter
        int repeat = 1;
        
        //USER INPUT FOR SEAT SELECTION
        do{
            char rowSelect = Character.MIN_VALUE;
            int colSelect = -1;
            
            fillArrayDefaultVal();
            displaySeatLayout();
            try{
                do{
                    System.out.println("Please select row [A-J]: ");
                    rowSelect = Character.toLowerCase(input.next().charAt(0));
                    if(rowSelect == '0'){
                        UserMain.repeatMain = 1;
                        return;
                    }
                }while(rowSelect < 'a' || rowSelect > 'j');
                
                do{
                    System.out.println("Please select column [1-12]: ");
                    colSelect = input.nextInt() - 1;
                    if(colSelect < UserMain.CANCEL_INT){
                        UserMain.repeatMain = 1;
                        return;
                    }
                }while(colSelect < 0 || colSelect > 11 );
            }catch(InputMismatchException e){
                System.out.println("\nInput not recognized as seat selection. Please enter again:");
                input.nextLine();
            }
            
            String select = String.valueOf(rowSelect); //convert char to string
            select = select.concat(Integer.toString(colSelect)); //convert integer to string

            seatSelect = verifySeat(select);
            repeat = (seatSelect == null) ? 1 : 0;
        }while(repeat == 1);
        
        
        System.out.println("\nEnter a number 0 or lesser to exit to main\nSelect a menu package (via number)");
        
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
            int select = DEFAULT_SELECT;
            while(select < UserMain.CANCEL_INT){
                try{
                    //print menus with index
                    System.out.println("Index\t Room Number\t Capacity\t Price");
                    
                    roomList.rooms.forEach((room) -> {
                        System.out.printf((roomList.rooms.indexOf(room) + 1) + ".\t " + room.getRoomNumber() + "\t\t " + room.getPax() + "\t\t RM%.2f" + "\n", room.getBasePrice());
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
            
            for(Room room: roomList.rooms){                
                if(select == roomList.rooms.indexOf(room)){
                    roomObj = room;
                    repeat = 0;
                    break;
                }
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
        bookingList.record(new Booking(this, seatSelect, paySelect, menuObj, roomObj));
        
        System.out.println("\nBooking Successfully Placed! You can view bookings to confirm the due date of payment if you chose to pay later>\nHave a nice day!\n");
        UserMain.repeatMain = 1;
    }

    @Override
    public void editBooking(User u) {
        Booking selectedBooking = null;
        
        int repeat = 1;
        do{
            sortBooking();
            if(bookingPayables.isEmpty()){
                System.out.println("No bookings due for payment found for this user. Please make a booking!\n");
                UserMain.repeatMain = 1;
                return;
            }
            
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
                               [1. Change Menu Package    2. Change Room Package    3. Change Seat Number    4. Transfer Booking to Friend (Another User)    5. Cancel Booking    Q. Back to Main]""");
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
                case "3" ->{
                    char rowSelect = Character.MIN_VALUE;
                    int colSelect = -1;
                    
                    System.out.println("Select a new seat number: ");
                    displaySeatLayout();
                    
                    try{
                        do{
                            System.out.println("Please select row [A-J]: ");
                            rowSelect = Character.toLowerCase(input.next().charAt(0));
                            if(rowSelect == '0'){
                                UserMain.repeatMain = 1;
                                return;
                            }
                        }while(rowSelect < 'a' || rowSelect > 'j');

                        do{
                            System.out.println("Please select column [1-12]: ");
                            colSelect = input.nextInt() - 1;
                            if(colSelect < UserMain.CANCEL_INT){
                                UserMain.repeatMain = 1;
                                return;
                            }
                        }while(colSelect < 0 || colSelect > 11 );
                    }catch(InputMismatchException e){
                        System.out.println("\nInput not recognized as seat selection. Please enter again:");
                        input.nextLine();
                    }
                    
                    String select = String.valueOf(rowSelect);
                    select = select.concat(Integer.toString(colSelect));
                    
                    resetSeat(selectedBooking.getSeatNumber()); //reset old seat first
                    
                    String newSeat = verifySeat(select); //assign new seat
                    
                    repeatEditing = 0;
                    UserMain.repeatMain = 1;
                }
                case "4" -> {
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
                case "5" -> {
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
        
        resetSeat(b.getSeatNumber());
        bookingList.drop(b);
        
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
        UserMain.repeatMain = 1;
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
    
    private void displaySeatLayout(){
        System.out.println("\n   1  2  3   4  5  6    7  8  9  10 11 12");
        for (int i = 0; i < seatLayout.length; i++) {
            for (int j = 0; j < seatLayout[0].length; j++) {
                if(i%2 == 0 && j == 0){
                    System.out.print((i == 0) ? getRowLetter(i) + " [" + seatLayout[i][j] + "]" : "\n" + getRowLetter(i) + " [" + seatLayout[i][j] + "]");
                }else if(j != COLS){
                    if((j + 1) == COLS){
                        System.out.print("[" + seatLayout[i][j] + "]\n");
                    }else if(j == 0){
                        System.out.print(getRowLetter(i) + " [" + seatLayout[i][j] + "]");
                        //System.out.print(i + "  [" + FREE + "]");
                    }else if((j + 1) % 3 == 0){
                        System.out.print((j == 5) ? "[" + seatLayout[i][j] + "]  " : "[" + seatLayout[i][j] + "] ");
                    }else{
                        System.out.print("[" + seatLayout[i][j] + "]");
                    }
                }
            }
        }
        
        System.out.println("\n* - unoccupied seat | X - occupied seat");
    }
    
    private String verifySeat(String seat){
        if(!seat.matches("[a-j]{1}[0-9]{1,2}$")){
            System.out.println("Seat number is invalid. Please enter another seat");
            return null;
        }
        
        int rowInt = getRowNumber(Character.toString(seat.charAt(0)));
        int colInt = Integer.parseInt(seat.substring(1));
        
        if(seatLayout[rowInt][colInt] == OCCU){
            System.out.println("\nThis seat is occupied! Please select another");
            return null;
        }
        
        seatLayout[rowInt][colInt] = OCCU;
        return seat;
    }
    
    private void resetSeat(String seat){
        if(!seat.matches("[a-j]{1}[0-9]{1,2}$")){
            return;
        }
        
        int rowInt = getRowNumber(Character.toString(seat.charAt(0)));
        int colInt = Integer.parseInt(seat.substring(1));
        
        seatLayout[rowInt][colInt] = FREE;
        System.out.println("Seat reset successful!\n");
    }
    
    private void fillArrayDefaultVal(){
        for (char[] seatLayout1 : seatLayout) {
            for (int j = 0; j < seatLayout1.length; j++) {
                if (seatLayout1[j] == 0) {
                    seatLayout1[j] = FREE;
                }else{
                    //leave value as it is
                }
            }
        }
    }
    
    private String getRowLetter(int i){
        switch(i){
            case 0 -> {
                return "A";
            }
            case 1 -> {
                return "B";
            }
            case 2 -> {
                return "C";
            }
            case 3 -> {
                return "D";
            }
            case 4 -> {
                return "E";
            }
            case 5 -> {
                return "F";
            }
            case 6 -> {
                return "G";
            }
            case 7 -> {
                return "H";
            }
            case 8 -> {
                return "I";
            }
            case 9 -> {
                return "J";
            }
            default ->{
                return null;
            }
        }
    }
    
    private int getRowNumber(String s){
        switch(s){
            case "a" -> {
                return 0;
            }
            case "b" -> {
                return 1;
            }
            case "c" -> {
                return 2;
            }
            case "d" -> {
                return 3;
            }
            case "e" -> {
                return 4;
            }
            case "f" -> {
                return 5;
            }
            case "g" -> {
                return 6;
            }
            case "h" -> {
                return 7;
            }
            case "i" -> {
                return 8;
            }
            case "j" -> {
                return 9;
            }
            default ->{
                return -1;
            }
        }
    }
    */

    @Override
    public void addBooking(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void editBooking(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cancelBooking(User u, Booking b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void filterBooking(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void viewBooking(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}