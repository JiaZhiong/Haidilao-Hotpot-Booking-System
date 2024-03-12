package jacksondemo;

public interface BookingFunctions {
    public void addBooking(User u);
    public void editBooking(User u);
    public void cancelBooking(User u, Booking b);
    public void filterBooking(User u);
    public void viewBooking(User u);
    
    static double payBooking(Menu m, Room r) {
        return (m.getBasePrice() + r.getBasePrice());
    }
}