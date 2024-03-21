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
    //**NOTES**
    //Any file ending with List is a holder class
    //Json files are stored in default package or src folder
    
    //TO RUN ADMIN INTERFACE, RUN AdminMain.java manually

    private static final File savePath = new File("src\\Haidilao.json");
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
        initData();
        
        //bookingList.record(new Booking(userList.users.get("jer"), "a4", false, menuList.menus.get("Vegetarian"), Room.getInstance()));
        
        //bookingList.record(new Booking(userList.users.get("coll"), "h5", false, menuList.menus.get("Seafood"), Room.getInstance()));
        
        
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
                    System.out.println("[Make sure your user name has at least 3 or less than 20 characters & no symbols except _ but ONLY BETWEEN characters]\nPlease enter a user name: ");
                    Boolean valid = false; //status of whether userInput can be registered into system
                    while(!valid){
                        userInput = input.next().trim();
                        valid = checkUserName(userInput);
                        if(valid == null){
                            break;
                        }
                    }
                    
                    if(valid == null){
                        System.out.println("Registration failed!\n");
                    }else{
                        userList.register(userInput, new User(userInput));
                        userList.saveUsers();
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
                case "Q", "q" -> {
                    System.out.println("Quitted\n");
                    storeData();
                    repeatMain = 0;
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
        }while(repeatMain == 1);
    }
    
    //initialize objects manually
    protected static void initObjects(){
        menuList.record("Seafood", new Menu("Seafood", 200.00));
        menuList.record("Vegetarian", new Menu("Vegetarian", 150.00));
        roomList.open(new Room(12, 475.00));
        roomList.open(new Room(8, 350.00));
        roomList.open(new Room(5, 120.00));
    }
    
    protected static void initData() throws IOException{
        userList.retrieveUsers();
        menuList.retrieveMenus();
        roomList.retrieveRooms();
        retrieveState(); //bookings
        
        //initObjects(); //initialize objects because user cannot create menus or rooms
    }
    
    
    protected static void storeData() throws IOException{
        userList.saveUsers();
        menuList.saveMenus();
        roomList.saveRooms();
        saveState();
    }
    
    protected static Boolean checkUserName(String input){
        if(input.matches("^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")){
            if(!userList.users.containsKey(input)){
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
        return true;
    }
    
    protected static User verifyUser(){
        int repeat = 1;
        while(repeat == 1){
            if(userList.users.containsKey(userInput)){
                repeatMain = 0;
                return userList.users.get(userInput);
            }
            repeat = 0;
        }
        System.out.println("Could not find user\n");
        return null;
    }
    
    protected static void saveState() throws JsonProcessingException, IOException{
        //File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
        
        ObjectMapper mapperO = new ObjectMapper();
        mapperO.findAndRegisterModules();
        
        mapperO.writerWithDefaultPrettyPrinter().writeValue(savePath, bookingList.bookings);
        
        System.out.println("Saved successfully");
    }
    
    protected static void retrieveState() throws IOException{
        //File savePath = new File("src\\hotpot\\booking\\system\\Haidilao.json");
        JsonMapper mapperJ = new JsonMapper();
        mapperJ.findAndRegisterModules();
        
        if(savePath.length() > 0 || savePath.exists()){
            bookingList.bookings = mapperJ.readValue(savePath, new TypeReference<List<Booking>>(){});
        }else{
            String json = "[ ]";
            bookingList.bookings = mapperJ.readValue(json, new TypeReference<List<Booking>>(){});
        }
        

        /*
        String json = """
                      [ {
                        "seatId" : "h5",
                        "menu" : {
                          "menuName" : "Seafood",
                          "basePrice" : 200.0
                        },
                        "room" : {
                          "roomNumber" : 10000,
                          "pax" : 8,
                          "basePrice" : 100.0
                        },
                        "paid" : false,
                        "bookedDate" : [ 2024, 3, 15, 12, 33, 31, 860294500 ],
                        "dueDate" : [ 2024, 3, 18, 12, 33, 31, 860294500 ],
                        "user" : "coll"
                      } ]
                      """;
        
        //bookingList.bookings = mapperJ.readValue(json, new TypeReference<List<Booking>>(){});
        */
        if(!bookingList.bookings.isEmpty()){
            updateState();
            User.updateSeat();
        }
        System.out.println("Loaded successfully");
    }
    
    //add objects into respective holder classes and check for duplicates
    private static void updateState(){
        for(Booking b: bookingList.bookings){
            
            //check for user duplicates
            if(!userList.users.isEmpty()){
                if(userList.users.containsKey(b.getUser().getName())){
                    b.setUser(userList.users.get(b.getUser().getName()));
                }else{
                    userList.register(b.getUser().getName(), b.getUser());
                }
            }else{
                userList.register(b.getUser().getName(), b.getUser());
            }
            
            //check for menu duplicates
            if(!menuList.menus.isEmpty()){
                if(menuList.menus.containsKey(b.getMenu().getMenuName())){
                    b.setMenu(menuList.menus.get(b.getMenu().getMenuName()));
                }else{
                    menuList.record(b.getMenu().getMenuName(), b.getMenu());
                }
            }else{
                menuList.record(b.getMenu().getMenuName(), b.getMenu());
            }
            
            //check for room duplicates
            if(!roomList.rooms.isEmpty()){
                if(roomList.rooms.containsKey(b.getRoomNumber())){
                    b.setRoom(roomList.rooms.get(b.getRoomNumber()));
                }else{
                    roomList.open(b.getRoom());
                }
            }else{
                roomList.open(b.getRoom());
            }
        }
    }
}