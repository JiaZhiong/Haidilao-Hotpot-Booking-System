package hotpot.booking.system;

import java.io.IOException;

public class AdminMain extends UserMain{
    private static String adminInput, adminOption;
    static int repeatAdmin = 1;
    
    public static void main(String[] args) throws IOException {
        initData();
        
        do{
            System.out.println("""
                                  Welcome to the Admin Dashboard!
                               1. View Menus          2. View Rooms
                               3. View Bookings       4. View Users
                               5. Notify Users
                                             Q. Quit""");
            adminOption = input.next();
            
            switch(adminOption){
                case "1" -> {
                    Admin.adminMenu();
                    repeatAdmin = 0;
                }
                case "2" -> {
                    Admin.adminRoom();
                    repeatAdmin = 0;
                }
                case "3" -> {
                    Admin.adminBooking();
                    repeatAdmin = 0;
                }
                case "4" -> {
                    Admin.adminUser();
                }
                case "5" -> {
                    
                }
                case "Q", "q" -> {
                    System.out.println("Quitted\n");
                    storeData();
                    repeatAdmin = 0;
                }
                default -> System.out.println("Sorry. We did not get that... please select your option again\n");
            }
            
        }while(repeatAdmin == 1);
    }
}
