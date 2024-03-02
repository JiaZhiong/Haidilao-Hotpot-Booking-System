package hotpot.booking.system;

import java.io.*;
import java.util.ArrayList;

public abstract class ObjectState {
    static final File savePath = new File("src\\hotpot\\booking\\system\\haidilao.ser");
    static ObjectOutputStream objOut = null;
    static ObjectInputStream objIn = null;
    
    final static void saveState(ArrayList<ArrayList<?>> arrList) throws IOException{
        try{
            objOut = new ObjectOutputStream(new FileOutputStream(savePath));
            objOut.writeObject(arrList);
        }catch(IOException e){
            System.out.println("An error occured while attempting to save state");
        }finally{
            objOut.flush();
            objOut.close();
        }
    }
    
    final static ArrayList<ArrayList<Object>> restoreState() throws IOException{
        ArrayList<ArrayList<Object>> objArr = null;
        try{
            objIn = new ObjectInputStream(new FileInputStream(savePath));
            objArr = (ArrayList<ArrayList<Object>>) objIn.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("An error occured while attempting to restore state");
        }finally{
            objIn.close();
        }
        return objArr;
    }
    
    final static ArrayList<ArrayList<?>> compileArrList(){
        ArrayList<ArrayList<?>> dataState = new ArrayList();
        dataState.add(Room.availableRooms);
        dataState.add(Menu.menus);
        dataState.add(User.userList);
        dataState.add(User.bookings);
        dataState.add(Room.bookedRooms);
        return dataState;
    }
    
    final static void retrieveState() throws IOException{
        ArrayList<ArrayList<Object>> dataRestore = new ArrayList(5); //initialize array for data reading
        ArrayList<ArrayList<?>> dataState = compileArrList(); //get the array lineup
        dataRestore = restoreState();
        
        for(ArrayList<?> dataset : dataRestore){
            System.out.println(dataset);
        }
    }
}