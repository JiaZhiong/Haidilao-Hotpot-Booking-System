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
                return;
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
    protected static void adminBooking(){
        for(Booking b: bookingList.bookings){
            System.out.println(b.toString('a'));
        }
    }
    
    protected static void adminUser(){
        System.out.println("\nRegistered Users");
        userList.users.forEach((name, user) -> {
            System.out.println(user.getName());
        });
        
        System.out.println("\n");
    }
}