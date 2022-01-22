import java.util.*;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:Variable
// this class is for the variable - Global replacement memory 
//includes all abstract method
// this memory's frames are all free to the processor. 
//If page needs to be replace, use FIFO order within the entire memory frames.
// and take 6 unit time when replacing a page.

public class Variable extends Memory<Processor> {

    private FrameInfo [] frames;      // array of FrameInfo() for the variable allocation with global replacement scope
    private int numFrames ; 

    @Override
    public void setMemory(int numFrames) {
        this.numFrames = numFrames;        // set numFrames
    }

    @Override
    public void setMemoryType(LinkedList<Processor> readyQ) {
        type = "Variable-Global Replacement";
        this.frames = new FrameInfo [numFrames];        // initial the partition for each processor

    }

    @Override
    public boolean checkMemory(Processor p , int page) {
        boolean pageFound = false;

        // search the entire memory frame and check wherethere the process of a processor is 
        // in the main mamory

        for(int i = 0; i <numFrames ; i ++){
            if(frames[i] != null && frames[i].getID() == p.getId() && frames[i].getpage() == page){
                pageFound = true;
                break;
            }      
            
        }
        return pageFound;
    }

    @Override
    public void addPage(Processor p, int page) {
         boolean isFull = true; // initial the mememory is not full     

        for(int i = 0; i <frames.length ; i ++){
            if(frames[i] == null ){
                frames[i] = new FrameInfo(p.getIdString(), page);
                
                isFull = false;     // the memory is not full 
                break;
            }      
            
        }

        if(isFull){  //if memory is full ,  use FIFO replacement policy
            
            // remove the first in page, and re-order the array
            for(int i = 0 ; i < numFrames-1 ; i++ ){
                frames[i] = frames[i+1] ;   // switch next frames up one array position
            }
            // add the page and processor to the last of the frames array 
            frames[numFrames-1] = new FrameInfo(p.getIdString(), page);
        }

    }
    @Override
    // this method is to clear the frames if one of the processor is finished all its process.
    public void clearFrame(Processor p) {

        for(int i = 0; i < frames.length ; i ++){
            if( frames[i] != null && (frames[i].getID() == p.getId())){ 
                // if the array has the current processor's page, remove it and set to null 
                frames[i] = null;   // set frame free
            }
        }

        FrameInfo [] temp = new FrameInfo [numFrames];     // a tempory array of the FrameInfo
        int tempCounter = 0;
        // after free the frames, re-order the frames's order using FIFO
        for(int i = 0; i < frames.length ; i ++){
            if( frames[i] != null){ 
                temp[tempCounter] = frames[i];
                tempCounter++;
            }
        }
        frames = temp;

    }




    
}
