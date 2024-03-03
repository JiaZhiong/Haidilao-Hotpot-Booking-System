package hotpot.booking.system;

import java.io.*;
import java.util.ArrayList;

public abstract class ObjectState {
    static final File saveFile = new File("src\\hotpot\\booking\\system\\haidilao.ser");
    static ObjectOutputStream objOut = null;
    static ObjectInputStream objIn = null;
    
    final static void saveState(ArrayList<ArrayList<?>> arrList) throws IOException{
        try{
            objOut = new ObjectOutputStream(new FileOutputStream(saveFile));
            objOut.writeObject(arrList);
        }catch(IOException e){
            System.out.println("An error occured while attempting to save state");
        }finally{
            objOut.flush();
            objOut.close();
        }
    }
    
    final static ArrayList<ArrayList<?>> restoreState() throws IOException{
        ArrayList<ArrayList<?>> objArr = null;
        if(!saveFile.isFile()){
            return null;
        }
        try{
            objIn = new ObjectInputStream(new FileInputStream(saveFile));
            objArr = (ArrayList<ArrayList<?>>) objIn.readObject();
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
    
    final static void retrieveState(ArrayList<ArrayList<?>> dataRestore, Integer index) throws IOException{
        if(dataRestore == null){
            return;
        }
        
        for(ArrayList<?> dataset : dataRestore){
            int i = 0;
            
        }
        /*  OLD CODE
        ArrayList<ArrayList<Object>> dataRestore = new ArrayList(5); //initialize array for data reading
        ArrayList<ArrayList<?>> dataState = compileArrList(); //get the array lineup
        dataRestore = restoreState();
        
        for(ArrayList<?> dataset : dataRestore){
            System.out.println(dataset);
        }
*/
    }
}