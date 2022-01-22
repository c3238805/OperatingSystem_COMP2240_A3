
import java.util.*;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:Processor
// getter and setter for all the processor's variable


public class Processor {
    private ArrayList<Integer> pages;
    
    private final int MaxPage  = 50; 
    private String id; // id of the processor
    private boolean status;    // boolean variable for processor's status
    private int faults ;        //variable for faults count
    private ArrayList<Integer> faultTime = new ArrayList<>() ;   // linkedlist to store the processor in order
    private int readyby;  // variable for when the processor is ready to read from memory
    private int quantumFinish ;     // recorde the Time when the processor finished its quantum 
    private String name;    // variable for processor's name 

    public Processor(int id , String name){
        pages = new ArrayList<>(MaxPage);       // set the page size of the processor
        this.id = Integer.toString(id);   //initial id for the processor
        this.status = true; // initial the processor's status = true
        this.name = name;       // initial processor's name
    }

    public Processor(String dispatch){
        this.id = dispatch;
    }
    public Processor(){

    }

    // ===============setter===================================
    

    public void setBlock(){
        status = false ;    //set status false   (eg. page flase)
    }
    public void setUnBlock(){
        status = true;
    }

    public void setPage(int page){
        pages.add(page);         // 

    }  
    public void addFaults(){
        faults++;
    }
    public void addfaultTime(int currentTime){

        faultTime.add(currentTime);     // store current time for processor's faultTime
    }
    public void removeFinishPage(){
        pages.remove(0);    // remove the top page of the arrayList
    }
    public void setReadyBy(int current, int TimeUnit){
        this.readyby = current + TimeUnit;
    }
    public void setquantumFinished (int current){
        this.quantumFinish  = current ; 
    }
    // ===============getter===================================

    public int getId(){
        return Integer.parseInt(this.id); 
    }
    public String getIdString(){
        return this.id;
    }
    public int getPages(int pagePosition){
        return pages.get(pagePosition);    //return the first page of the arraylist
    }
    public boolean getstatus(){
        return status; // return processor's status 
    }
    public ArrayList<Integer> getAllpages(){
        return pages;       // return arrayList pages
    }
    public ArrayList<Integer> getfaultTime(){
        return faultTime;
    }
    public int getPagesSize(){
        return pages.size();
    }
    public int getFaults() {
        return faults;
    }

    public int getreadyBy() {
        return readyby;
    }
    public int getQuantumFinished(){
        return quantumFinish;
    }
    public String getName(){
        return name;        // return processor's name
    }


}
