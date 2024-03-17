package hotpot.booking.system;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin {
    private static final Scanner input = new Scanner(System.in);
    
    private static BookingList bookingList = BookingList.getInstance();
    private static UserList userList = UserList.getInstance();
    private static RoomList roomList = RoomList.getInstance();
    private static MenuList menuList = MenuList.getInstance();
    
    
    //ADMIN OPERATIONS FOR MENUS
    protected static void adminMenu() throws IOException{
        int repeatMenu = 1;
        String adminOption = "";
        
        if(menuList.menus.isEmpty()){
            System.out.println("\nNo menus found.");
            Admin.recordMenu();
        }
        
        do{
            menuList.retrieveMenus();
            System.out.println("\nMenu Records: \nMenu Name    \tPrice");
            menuList.menus.forEach((name, m) -> {
                System.out.printf(name + "    \tRM%.2f\n", m.getBasePrice());
            });
            
            System.out.println("Operations: \n1. Create New Menu    2. Remove Menu    Q. Back to Admin Dashboard");
            
            adminOption = input.next();
            
            switch(adminOption){
                case "1" -> {
                    recordMenu();
                }
                case "2" -> {
                    dropMenu();
                }
                case "Q", "q" -> {
                    System.out.println("");
                    
                    repeatMenu = 0;
                    AdminMain.repeatAdmin = 1;
                    AdminMain.main(null);
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
            
        }while(repeatMenu == 1);
    }
    
    //Method to initialize Menu objects
    private static void recordMenu() throws IOException{
        int repeat = 1;
        String nameSelect = "";
        double priceSelect = -1;
        
        System.out.println("\nEnter 'haidilao' to exit operation");
        
        //MENU NAME SELECTION
        do{
            while(nameSelect.equals("")){
                try{
                    //print menus with index
                    System.out.println("Enter menu name: ");

                    nameSelect = input.next();
                    if(nameSelect.equalsIgnoreCase(UserMain.CANCEL_STR)){
                        repeat = 0;
                        adminMenu();
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            if(menuList.menus.containsKey(nameSelect)){
                System.out.println("\nThis menu name is taken.");
                nameSelect = "";
            }else{
                repeat = 0;
            }
        }while(repeat == 1);
        
        repeat = 1;
        System.out.println("\nEnter 0 to exit operation");
        
        //MENU PRICE SELECTION
        do{
            while(priceSelect < UserMain.CANCEL_INT || priceSelect >= 1500.00){
                try{
                    System.out.println("Enter menu price (Max = RM1499.99): ");
                    
                    priceSelect = input.nextDouble();
                    if(priceSelect == UserMain.CANCEL_INT){
                        repeat = 0;
                        adminMenu();
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            repeat = 0;
        }while(repeat == 1);
        
        menuList.record(nameSelect.trim(), new Menu(nameSelect, priceSelect));
        System.out.println("New menu package recorded!");
        menuList.saveMenus();
    }
    
    //Method to remove Menus
    private static void dropMenu() throws IOException{
        int repeat = 1;
        
        do{
            System.out.println("\nEnter 'haidilao' to exit operation");
            String menuSelect = "";
            while(!menuList.menus.containsKey(menuSelect)){
                try{
                    System.out.print("Select a menu package to delete via menu name: ");
                    
                    menuSelect = input.next().trim();
                    if(menuSelect.equalsIgnoreCase(UserMain.CANCEL_STR)){
                        repeat = 0;
                        adminMenu();
                        break;
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            if(menuList.menus.containsKey(menuSelect)){
                menuList.drop(menuSelect);
                menuList.saveMenus();
                repeat = 0;
                adminMenu();
            }else{
                System.out.println("Could not find any menu package with the name '" + menuSelect + "'.");
                menuSelect = "";
            }
        }while(repeat == 1);
    }
    
    
    //ADMIN OPERATIONS FOR ROOMS
    protected static void adminRoom() throws IOException{
        int repeatRoom = 1;
        String adminOption = "";
        
        if(roomList.rooms.isEmpty()){
            System.out.println("No rooms found");
            openRoom();
        }
        
        do{
            roomList.retrieveRooms();
            System.out.println("\nRoom Records: \nRoom Number\t Capacity\t Price");
            roomList.rooms.forEach((num, r) -> {
                System.out.printf(r.getRoomNumber() + "\t\t " + r.getPax() + "\t\t RM%.2f" + "\n", r.getBasePrice());
            });
            
            System.out.println("Operations: \n1. Open New Room    2. Close Room    Q. Back to Admin Dashboard");
            
            adminOption = input.next();
            
            switch(adminOption){
                case "1" -> {
                    openRoom();
                }
                case "2" -> {
                    closeRoom();
                }
                case "Q", "q" -> {
                    System.out.println("");
                    
                    repeatRoom = 0;
                    AdminMain.repeatAdmin = 1;
                    AdminMain.main(null);
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
            
        }while(repeatRoom == 1);
    }
    
    //Method to initialize Room objects
    private static void openRoom() throws IOException{
        int repeat = 1;
        int paxNum = -1;
        double priceSelect = -1;
        
        System.out.println("\nEnter 0 to exit operation");
        //ROOM PAX SELECTION
        do{
            while(paxNum <= UserMain.CANCEL_INT || paxNum > 25){
                try{
                    System.out.println("Enter room capacity (Max - 25 people): ");
                    
                    paxNum = input.nextInt();
                    if(paxNum == UserMain.CANCEL_INT){
                        repeat = 0;
                        adminRoom();
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            repeat = 0;
            
        }while(repeat == 1);
        
        repeat = 1;
        
        //ROOM PRICE SELECTION
        do{
            while(priceSelect < UserMain.CANCEL_INT || priceSelect >= 500.00){
                try{
                    System.out.println("Enter room price (Max = RM499.99): ");
                    
                    priceSelect = input.nextDouble();
                    if(priceSelect == UserMain.CANCEL_INT){
                        repeat = 0;
                        adminRoom();
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
                
                repeat = 0;
            }
        }while(repeat == 1);
        
        roomList.open(new Room(paxNum, priceSelect));
        System.out.println("New room package opened!");
        roomList.saveRooms();
    }
    
    //Method to remove Rooms
    private static void closeRoom() throws IOException{
        int repeat = 1;
        
        do{
            System.out.println("\nEnter 0 to exit operation");
            
            int roomSelect = -1;
            while(!roomList.rooms.containsKey(roomSelect)){
                try{
                    System.out.print("Select a room to close via room number: ");
                    
                    roomSelect = input.nextInt();
                    if(roomSelect == UserMain.CANCEL_INT){
                        repeat = 0;
                        adminRoom();
                        break;
                    }
                }catch(InputMismatchException e){
                   System.out.println("\nInvalid response. Please enter again:");
                   input.nextLine();
                }
            }
            
            if(roomList.rooms.containsKey(roomSelect)){
                roomList.close(roomSelect);
                roomList.saveRooms();
                repeat = 0;
                return;
            }else{
                System.out.println("Could not find any room with the number '" + roomSelect + "'.");
                roomSelect = -1;
            }
        }while(repeat == 1);
    }
    
    
    //ADMIN OPERATIONS FOR BOOKINGS
    protected static void adminBooking() throws IOException{
        if(bookingList.bookings.isEmpty()){
            System.out.println("\nNo bookings placed by users.");
            AdminMain.repeatAdmin = 1;
            AdminMain.main(null);
        }
        
        int repeatBooking = 1;
        String adminOption = "";
        
        do{ 
            UserMain.retrieveState();
            System.out.println("Booking Records: ");
            for(Booking b: bookingList.bookings){
                System.out.println((bookingList.bookings.indexOf(b) + 1) + b.toString('a'));
            }
            System.out.println("Operations: \n1. Remove Booking    Q. Back to Admin Dashboard");
            
            adminOption = input.next();
            switch(adminOption){
                case "1" -> {
                    dropBooking();
                }
                case "Q", "q" -> {
                    System.out.println("");
                    
                    repeatBooking = 0;
                    AdminMain.repeatAdmin = 1;
                    AdminMain.main(null);
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
        }while(repeatBooking == 1);
    }
    
    private static void dropBooking() throws IOException{
        int repeat = 1;
        Booking bookingSelect = null;
        
        do{
            System.out.println("\nEnter 0 to exit operation");
            
            int select = -1;
            while(select < UserMain.CANCEL_INT){
                try{
                    System.out.println("\nSelect a booking to remove:");
                    
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
                    bookingSelect = booking;
                }
            }
        
            if(bookingSelect == null){
                System.out.println("\nBooking deletion failed");
            }else{
                bookingList.bookings.remove(bookingSelect);
                System.out.println("\nBooking successfully deleted!");
                repeat = 0;
                UserMain.saveState();
                adminBooking();
                return;
            }
            
        }while(repeat == 1);
    }
    
    //ADMIN OPERATIONS FOR USER
    protected static void adminUser() throws IOException{
        int repeatUser = 1;
        
        do{
            String adminOption = "";
            
            userList.retrieveUsers();
            
            System.out.println("\nRegistered Users");
            userList.users.forEach((name, user) -> {
                System.out.println(user.getName());
            });

            System.out.println("Operations: \n1. Register New User    2. Delete User    Q. Back to Admin Dashboard");
            adminOption = input.next();
            
            switch(adminOption){
                case "1" -> {
                    registerUser();
                }
                case "2" -> {
                    deleteUser();
                }
                case "Q", "q" -> {
                    System.out.println("");
                    
                    repeatUser = 0;
                    AdminMain.repeatAdmin = 1;
                    AdminMain.main(null);
                }
            }
        }while(repeatUser == 1);
    }
    
    private static void registerUser() throws IOException{
        String adminInput = "";
        
        System.out.println("\nEnter 'haidilao' to exit operation");
        System.out.println("[Make sure your user name has at lesat 3 or less than 20 characters & no symbols except _ but ONLY BETWEEN characters]\nPlease enter a user name: ");
        Boolean valid = false; //status of whether userInput can be registered into system
        while(!valid){
            adminInput = input.next().trim();
            valid = UserMain.checkUserName(adminInput);
            if(valid == null){
                break;
            }
        }

        if(valid == null){
            System.out.println("Registration failed!\n");
        }else{
            userList.register(adminInput, new User(adminInput));
            userList.saveUsers();
            System.out.println("User registered sucessfully!\n");
        }
    }
    
    private static void deleteUser() throws IOException{
        int repeat = 1;
        
        do{
            String adminInput = "";
            while(!userList.users.containsKey(adminInput)){
                try{
                    System.out.println("Enter user name to remove user: ");

                    adminInput = input.next();
                    if(adminInput.equalsIgnoreCase(UserMain.CANCEL_STR));
                }catch(InputMismatchException e){
                   System.out.println("Invalid response. Please enter again: ");
                   input.nextLine();
                }
            }
            
            if(userList.users.containsKey(adminInput)){
                userList.remove(adminInput);
                userList.saveUsers();
                System.out.println("User successfully deleted");
                repeat = 0;
                adminUser();
            }
            
        }while(repeat == 1);
    }
}