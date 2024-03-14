package hotpot.booking.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserMain{
    private static final File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
    static final Scanner input = new Scanner(System.in);
    static String userInput, userOption; //userInput for general user input while userOption for selecting options
    static int repeatMain = 1;
    static final String CANCEL_STR = "HAIDILAO";
    static final int CANCEL_INT = 0;
    static UserList userList = UserList.getInstance();
    static RoomList roomList = RoomList.getInstance();
    static BookingList bookingList = BookingList.getInstance();
    static MenuList menuList = MenuList.getInstance();
    
    public static void main(String[] args) throws IOException{
        retrieveState();
        
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
                        userList.register(new User(userInput));
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
    
    
    //initialize objects manually
    private static void initObjects(){
        /* //DE-SERIALIZING PROCESS
        1. Rooms
        2. Menu
        3. User
        4. Bookings
        */
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
    
    public static void saveState() throws JsonProcessingException, IOException{
        //File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
        
        ObjectMapper mapperO = new ObjectMapper();
        mapperO.findAndRegisterModules();
        
        mapperO.writerWithDefaultPrettyPrinter().writeValue(savePath, bookingList.bookings);
        
        System.out.println("Saved successfully");
    }
    
    private static void retrieveState() throws IOException{
        //File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
        JsonMapper mapperJ = new JsonMapper();
        mapperJ.findAndRegisterModules();
        bookingList.bookings = mapperJ.readValue(savePath, new TypeReference<List<Booking>>(){});
        
        restoreState();
        User.updateSeat();
        System.out.println("Loaded successfully");
    }
    
    //add objects into respective holder classes and check for duplicates
    private static void restoreState(){
        for(Booking b: bookingList.bookings){
            if(!userList.users.isEmpty()){
                for(User u: userList.users){
                    if(u.getName().equals(b.getUser().getName())){
                        b.setUser(u);
                        break;
                    }else if(userList.users.indexOf(u) == userList.users.size() - 1){
                        userList.register(b.getUser());
                    }
                }
            }else{
                userList.register(b.getUser());
            }
            
            if(!menuList.menus.isEmpty()){
                Menu newMenu = null;
                for(Menu m: menuList.menus){
                    if(m.getMenuName().equals(b.getMenu().getMenuName())){
                        newMenu = m;
                        break;
                    }else if(menuList.menus.indexOf(m) == menuList.menus.size() - 1){
                        menuList.record(b.getMenu());
                    }
                }
                
                if(newMenu != null){
                    b.setMenu(newMenu);
                }
            }else{
                menuList.record(b.getMenu());
            }
            
            if(!roomList.rooms.isEmpty()){
                Room newRoom = null;
                for(Room r: roomList.rooms){
                    if(r.getRoomNumber() == b.getRoomNumber()){
                        newRoom = r;
                        break;
                    }else if(roomList.rooms.indexOf(r) == roomList.rooms.size() - 1){
                        break;
                    }
                }
                
                if(newRoom != null){
                    b.setRoom(newRoom);
                }else{
                    roomList.open(b.getRoom());
                }
            }else{
                roomList.open(b.getRoom());
            }
        }
    }
}