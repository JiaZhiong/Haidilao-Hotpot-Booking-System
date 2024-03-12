package jacksondemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserMain{
    static Scanner input = new Scanner(System.in);
    static String userInput, userOption; //userInput for general user input while userOption for selecting options
    static int repeatMain = 1;
    static final String CANCEL_STR = "HAIDILAO";
    static final int CANCEL_INT = 0;
    static UserList userList = UserList.getInstance();
    static RoomList roomList = RoomList.getInstance();
    static BookingList bookingList = BookingList.getInstance();
    static MenuList menuList = MenuList.getInstance();
    
    public static void main(String[] args) throws JsonProcessingException, IOException{

        /*
        ObjectMapper mapperO = new ObjectMapper();
        mapperO.findAndRegisterModules();
        userList.users.add(new User("jake"));
        menuList.menus.add(new Menu("Cannibalistic", 99999));
        roomList.open(new Room(400, 1000));
        bookingList.record(new Booking(userList.users.get(0), "f4", false, menuList.menus.get(0), roomList.rooms.get(0)));
        
        System.out.println(mapperO.writerWithDefaultPrettyPrinter().writeValueAsString(bookingList.bookings));
*/
        JsonMapper mapperJ = new JsonMapper();
        mapperJ.findAndRegisterModules();
        //mapperJ.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //mapperJ.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        bookingList.bookings = mapperJ.readValue("""
                                           [ {
                                             "seatId" : "f4",
                                             "menu" : "Cannibalistic",
                                             "room" : 2855,
                                             "paid" : false,
                                             "bookedDate" : [ 2024, 3, 12, 23, 55, 8, 556840700 ],
                                             "dueDate" : [ 2024, 3, 15, 23, 55, 8, 556840700 ],
                                             "user" : "jake"
                                           } ]
                                           """, new TypeReference<List<Booking>>(){});
        
        bookingList.bookings.forEach((userBooking) -> {
            System.out.println(userBooking.toString('v'));
        });
    }
    
        /*
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
                        userList.add(new User(userInput));
                        System.out.println("User registered sucessfully!\n");
                    }
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
    
    */
    
    
    //initialize objects manually
    //private static void initObjects(){
        /* //DE-SERIALIZING PROCESS
        1. Rooms
        2. Menu
        3. User
        4. Bookings
        *//*
        userList.add(new User("jer"));
        userList.add(new User("coll"));
        menuList.record(new Menu("Seafood", 200.00));
        menuList.record(new Menu("Vegetarian", 150.00));
        roomList.open(new Room(12, 475.00));
        roomList.open(new Room(8, 350.00));
        roomList.open(new Room(5, 120.00));
        //bookingList.record(new Booking(userList.users.get(0), false, menuList.menus.get(0), roomList.availableRooms.get(0)));
        //bookingList.record(new Booking(userList.users.get(0), true, menuList.menus.get(1), roomList.availableRooms.get(1)));
        //roomList.bookedRooms.add(roomList.availableRooms.get(0));
        //roomList.bookedRooms.add(roomList.availableRooms.get(2));
    }
            
    private static Boolean checkUserName(String input){
        for(User user: User.userList.users){
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
            for(User selectedUser: userList.users){
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

    */
}