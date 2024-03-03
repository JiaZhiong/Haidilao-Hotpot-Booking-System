package hotpot.booking.system;

import java.io.IOException;
import java.util.Scanner;

public class UserMain extends ObjectState{
    static String userInput, userOption; //userInput for general user input while userOption for selecting options
    static int repeatMain = 1;
    static final String CANCEL_STR = "HAIDILAO";
    static final int CANCEL_INT = 0;
    
    
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        retrieveState(restoreState(), null);
        //initObjects();
        //saveState(compileArrList());
        
        System.out.println("""
                                                        !ATTENTION DEAR USER!
                            (TYPING "haidilao" WILL CANCEL SELECTED OPERATION AND RETURN YOU BACK TO MAIN)""");
        System.out.println("");
        do{
            System.out.println("""
                                                            ____Welcome to Haodilao Hotpot Booking System____ 
                                                        What can we do for you? Choose one of the options below: 
                               [1: Register an User    2: Make a Booking    3: Make Payment    4. View Bookings    5. Edit Booking    Q: Quit]""");
            userOption = input.next();

            switch(userOption){
                case "1" -> {
                    System.out.println("[Make sure your user name has at lesat 3 or less than 20 characters & no symbols except _ but ONLY BETWEEN characters]\nPlease enter a user name: ");
                    Boolean valid = false; //status of whether userInput can be registered into system
                    while(!valid){
                        userInput = input.next().trim();
                        valid = checkUserName(userInput);
                        if(valid == null){
                            break;
                        }
                    }
                    
                    if(valid == null){
                        System.out.println("User registered failed!\n");
                    }else{
                        User.userList.add(new User(userInput));
                        System.out.println("User registered sucessfully!\n");
                    }
                    
                    saveState(compileArrList());
                }
                case "2" -> {
                    System.out.println("Select a user [Enter a registered user name (case-sensitive)]: ");
                    userInput = input.next();
                    if(userInput.equalsIgnoreCase(CANCEL_STR)){
                        break;
                    }
                    
                    User selectedUser = verifyUser();
                    if(selectedUser != null){
                        selectedUser.addBooking(selectedUser);
                        saveState(compileArrList());
                    }
                }
                case "3" -> {
                    System.out.println("Select a user [Enter a registered user name (case-sensitive)]: ");
                    userInput = input.next();
                    if(userInput.equalsIgnoreCase(CANCEL_STR)){
                        break;
                    }
                    
                    User selectedUser = verifyUser();
                    if(selectedUser != null){
                        selectedUser.filterBooking(selectedUser);
                        saveState(compileArrList());
                    }
                }
                case "4" -> {
                    System.out.println("Select a user [Enter a registered user name (case-sensitive)]: ");
                    userInput = input.next();
                    if(userInput.equalsIgnoreCase(CANCEL_STR)){
                        break;
                    }
                    
                    User selectedUser = verifyUser();
                    if(selectedUser != null){
                        selectedUser.viewBooking(selectedUser);
                        saveState(compileArrList());
                    }
                }
                case "5" -> {
                    System.out.println("Select a user [Enter a registered user name (case-sensitive)]: ");
                    userInput = input.next();
                    if(userInput.equalsIgnoreCase(CANCEL_STR)){
                        break;
                    }
                    
                    User selectedUser = verifyUser();
                    if(selectedUser != null){
                        selectedUser.editBooking(selectedUser);
                        saveState(compileArrList());
                    }
                }
                case "6" -> {
                    System.out.println("You are not allowed here?!");
                }
                case "Q", "q" -> {
                    System.out.println("Quitted\n");
                    repeatMain = 0;
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
        }while(repeatMain == 1);
        
    }
    
    //initialize objects manually
    private static void initObjects(){
        /* //DE-SERIALIZING PROCESS
        1. Available Rooms
        2. Menu
        3. User
        4. Bookings
        5. Booked Rooms
        */
        User.userList.add(new User("jer"));
        User.userList.add(new User("coll"));
        Menu.menus.add(new Menu("Seafood", 200.00));
        Menu.menus.add(new Menu("Vegetarian", 150.00));
        Room.availableRooms.add(new Room(12, 475.00));
        Room.availableRooms.add(new Room(8, 350.00));
        Room.availableRooms.add(new Room(5, 120.00));
        //User.bookings.add(new Booking(User.userList.get(0), 0110, false, Menu.menus.get(0), Room.availableRooms.get(0)));
        //User.bookings.add(new Booking(User.userList.get(0), 0110, true, Menu.menus.get(1), Room.availableRooms.get(2)));
        //Room.bookedRooms.add(Room.availableRooms.get(0));
        //Room.bookedRooms.add(Room.availableRooms.get(2));
    }
            
    private static Boolean checkUserName(String input){
        for(User user: User.userList){
            if(input.matches("^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")){
                if(!input.equals(user.getName())){
                    if(input.equalsIgnoreCase("Haidilao")){
                        return null;
                    }
                    // ---> exit to return true for user name check on final line
                }else{
                    System.out.println("User name is already registered!\nPlease enter another user name: ");
                    return false;
                }
            }else{
                System.out.println("User name format is invalid!\nYour user name should consist of 3 or more characters with no symbols except \"_\", but ONLY BETWEEN characters");
                return false;
            }
        }
        return true;
    }
    
    private static User verifyUser(){
        int repeat = 1;
        while(repeat == 1){
            for(User selectedUser: User.userList){
                if(selectedUser.getName().equals(userInput)){
                    repeatMain = 0;
                    return selectedUser;
                }
            }
            repeat = 0;
        }
        System.out.println("Could not find user\n");
        return null;
    }
}