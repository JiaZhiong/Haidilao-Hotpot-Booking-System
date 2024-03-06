package hotpot.booking.system;

import java.util.ArrayList;
import java.util.List;

public class BookingList {
    List<Booking> bookings;
    
    private BookingList() {
        bookings = new ArrayList();
    }
    
    public static BookingList getInstance() {
        return BookingListHolder.INSTANCE;
    }
    
    private static class BookingListHolder {
        
        private static final BookingList INSTANCE = new BookingList();
    }
    
    public void record(Booking b){
        bookings.add(b);
    }
    
    public void drop(Booking b){
        bookings.remove(b);
    }
}