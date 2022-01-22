/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:ProceFrameInfossor
// this class is for storing the processor's page and id in the Variable memory type
// includes page and procesoorID getter and setter


public class FrameInfo {
    private String processorId;
    private int page;

    public FrameInfo(String processorId , int page){
        this.processorId = processorId;
        this.page = page;
    }

    public String getStringID(){
        return this.processorId;
    }
    public int getID(){
        return Integer.parseInt(this.processorId);
    }

    public int getpage(){
        return this.page;
    }


}
