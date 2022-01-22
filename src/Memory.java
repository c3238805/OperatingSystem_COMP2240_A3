import java.util.LinkedList;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:Memory
// this class is the abstract class for Fixed and variable memeory type


public abstract class Memory<T> {
    public String type ;
    // set memory frames
    public abstract void setMemory(int frames);

    public abstract void setMemoryType(LinkedList<Processor> readyQ);

    public abstract boolean checkMemory(Processor p ,int page);

    public abstract void addPage(Processor p , int page);

    public abstract  void clearFrame(Processor p);
    
    
}
