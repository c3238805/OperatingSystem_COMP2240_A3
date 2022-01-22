import java.util.*;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:Fixed
// this class is for the Fixed - local replacement memory 
//includes all abstract method
// this memory's frames are equally divided for each processor, each processor hold a partition. 
//If page needs to be replace, use FIFO order within its own processor's memory partition.
// and take 6 unit time when replacing a page.

public class Fixed extends Memory<Processor> {

    private int frames;
    private int MaxFrame_parition;
    private int [][] partition;   // integer array for the divided partition

    public Fixed(){        
    }

    @Override
    public void setMemory(int frames) {
        this.frames = frames;       // set total frames
        
    }
    
    @Override
    public void setMemoryType(LinkedList<Processor> readyQ) {
        type = "Fixed-Local Replacement";
        MaxFrame_parition = this.frames / readyQ.size();        // max frame for each partition
        this.partition = new int [readyQ.size()][Math.round(MaxFrame_parition)];        // initial the partition for each processor
        
    }

    @Override
    public boolean checkMemory(Processor p ,int page) {
        boolean pageFound = false;

        for(int i = 0; i <MaxFrame_parition ; i ++){
            
            if(partition[p.getId()-1][i] == page){
                pageFound = true; // page found in the main memory 
                break;
            }
        }
    
        return pageFound;       // return boolean pageFound
    }

    @Override
    public void addPage(Processor p , int page) {       // use FIFO replacement policies
        
        //if memory is not full, add the page
        if(partition[p.getId()-1].length < MaxFrame_parition){
            
            for(int i = 0 ; i < partition[p.getId()-1].length ; i++){
                if(partition[p.getId()-1][i] == 0){     // if frame is avaliable 
                    partition[p.getId()-1][i] = page;
                }
            }
            
        }
        else {      //if memory is full ,  use FIFO replacement policy
            // remove the first in page, and re-order the array
            for(int i = 0 ; i < MaxFrame_parition-1 ; i++ ){
                partition[p.getId()-1][i] = partition[p.getId()-1][i+1];
            }
            // add the page to the last of the partition array 
            partition[p.getId()-1][MaxFrame_parition-1] = page; 

        }
        
    }

    @Override
    public void clearFrame(Processor p) {
        // in fixed memory type, does not do anything this method
        
    }
    



}
