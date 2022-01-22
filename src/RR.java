import java.util.*;
import java.io.*;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:RR
// round robin algorithm for the processor
// Round robin algorithem
// step 1 : check all processor first page to see if Processor's first process is in the Main memory
// step 2 : if processor's first page is in the main memory, add into RR_order 
//          if processor's first page is not in the main memory, add into faultQ
// step 3 : run processors in the RR_order, after a processor finished its run, remove the first page in the processor and reomve processor of the RR_order
// step 4 : if RR_order is empty and Processor's page is not empty, repeat step 1 - 3 until all processor's page is empty(means the processor finished its process)

public class RR implements Comparator<Processor>{

    private LinkedList<Processor> Processor = new LinkedList<>();    // variable for processors
    private LinkedList<Processor> readyQ = new LinkedList<>();    // variable for processors
    private int q ;         // quantum of the algorithm

    private Processor dispatch;     // variable for page dispatch
    private LinkedList<Processor> faultQ = new LinkedList<>();    // variable for processors
    private LinkedList<Processor> complete = new LinkedList<>();    // linkedList to store the complete processor
    private ArrayList<Processor> RR_Timeslots = new ArrayList<>();    // arrayList of time slot for RR algorithm.

    public RR(){
    }

    public void setquantum(int q){
        this.q = q ;    // set the algorithm quantum
    }
    public void addProcessor(LinkedList<Processor> Processor ) {
        this.Processor = Processor;     // add all processor into FCFS class, and store it as variable
    }

    public Processor getP(int index) {
       
        return Processor.get(index);        // return input processor
    }

    public  void algorithm(Memory type){
        
        // first loop though all processor in the input-order readyQ  to check whether the page is in memory
        // start by adding the processors into RR_order
        //the memory is empty when T = 0;
        // each element of RR_Timeslots repersent one unit of time
        
        //RR's algorithm
         
            // starting by inputing processor into readyQ or faultQ       
            while(!Processor.isEmpty()){    
                // if the page is not in the main memory
                // block the process , issue an page fault then placing an I/O request
                if(!type.checkMemory(Processor.getFirst(), Processor.getFirst().getPages(0))){
                    // if processor's page not found in the main memory

                    // block the processor and load the page into memory 
                    Processor.getFirst().setBlock();
                    // add page fault and fault times
                    Processor.getFirst().addFaults();
                    Processor.getFirst().addfaultTime(RR_Timeslots.size());   
                    Processor.getFirst().setReadyBy(RR_Timeslots.size(), 6 );       // loading page need 6 unit of time

                    // add to fault queueu
                    faultQ.add( Processor.remove());  // add blocked processor into faultQ

                }
                else {  // if page is in the memory
                     // add to readyQ queueu
                     readyQ.add( Processor.remove());  // add blocked processor into faultQ
                }
            } 
            //================================================================================

        // run round robin readyQ until it empty
        while(!readyQ.isEmpty()){
                      
            int quantum = q; 
            for(int i = 0 ; i< quantum ; i++ ){   //each processor allow to run 3 quantum
                // the processor sill try to loop though the quantum and check if
                // current page is in the main mamory, if page found , excution 1 unit of time.
                if( readyQ.getFirst().getPagesSize() == 0 ){
                    // clear up frames for the memory (only for variable flobal replacement)
                    if(type.type == "Variable-Global Replacement"){
                        type.clearFrame(readyQ.getFirst());     // free memory space when a processor done
                    }
                    complete.add(readyQ.remove()); // remove finished processor
                    // check if any other processor is ready at current time
                    loopAllProcessor(type);   
                    break;
                }

                if( readyQ.getFirst().getPagesSize() != 0 && type.checkMemory(readyQ.getFirst(), readyQ.getFirst().getPages(0))){ 
                    // each quantum can run one page instruction       
                    RR_Timeslots.add(readyQ.getFirst());  // add processor into timeslots
                    readyQ.getFirst().removeFinishPage() ; // delete finished page in the processor's instruction 

                    if(i == quantum-1 ){
                        //current process page is up to 3 quantum, record the time when processor finished
                        // its quantum
                        readyQ.getFirst().setquantumFinished( RR_Timeslots.size());

                            // check if any other processor is ready at current time
                            loopAllProcessor(type);
                            readyQ.add(readyQ.remove());    // add finished its quantum processor back to the bottom of readQ               
                    }
                }
                else{   // when page not found in the main memory
                    // block the processor and load the page into memory 
                    readyQ.getFirst().setBlock();
                    // add page fault and fault times
                    readyQ.getFirst().addFaults();
                    readyQ.getFirst().addfaultTime(RR_Timeslots.size());    // add current time as faultTime
                    readyQ.getFirst().setReadyBy(RR_Timeslots.size(), 6 );       // loading page need 6 unit of time

                    // add to fault queueu
                    faultQ.add( readyQ.remove());  // add processor into faultQ
                    // check if any other processor is ready 
                    loopAllProcessor(type);   

                    break;
                }
            }
            
        }
    }   

    // this method is to loop through all processor in the faultQ and check if any processor is 
    // qualify to be in the readyQ ()
    public void loopAllProcessor(Memory type){
        
        // check if other processor also ready same or before current time
        LinkedList<Processor> temp = new LinkedList<>();
        for(int i = 0 ; i < faultQ.size() ; i++){
            // if a processor in faultQ is ready at the same time of before the current Time unit
            if(faultQ.get(i).getreadyBy() <= RR_Timeslots.size()){  
                faultQ.get(i).setUnBlock();     // unblock the current processor
                // if page is not found in the main memory, add in the page
                if(!type.checkMemory(faultQ.get(i), faultQ.get(i).getPages(0))){ 
                    
                    // I/O request and bring the page into main memory
                    type.addPage(faultQ.get(i),  faultQ.get(i).getPages(0));
                    
                }
                // add to readyQ queue and remove it from faultQ
                readyQ.add(faultQ.get(i));
                temp.add(faultQ.get(i));        // tempory store current processor in the temp LinkedList 
            
            }
        }
        // remove processor in faultQ
        for(Processor p : temp){
            faultQ.remove(p);       //remove processor that has been moved to readyQ
        }

    }

    public boolean checkAllComplete(Memory type){
        // check if other processor also ready same or before current time
        LinkedList<Processor> temp = new LinkedList<>();

        boolean isCompleted = true;
        Processor nextArrive = new Processor();  // variable for next arrive processor

       // if RR_order is empty but there is processor in the faultQ
        if(readyQ.isEmpty() && !faultQ.isEmpty()){
            isCompleted = false;
            
            nextArrive = faultQ.removeFirst();  // remove the first processor of the faultQ

            //add timeslots till when the next arrive processor is ready
            dispatch = new Processor("dispatch");
            
            int timeslotsSize = RR_Timeslots.size();
            for(int i = 0; i<(nextArrive.getreadyBy() - timeslotsSize)  ; i++){
                RR_Timeslots.add(dispatch);    // add dispatch time untill next processor is ready to go next
            }

            // add into RR_order
            if(!type.checkMemory(nextArrive, nextArrive.getPages(0)) && RR_Timeslots.get(RR_Timeslots.size()-1) != nextArrive){ 
                // I/O request and bring the page into main memory
                type.addPage(nextArrive,  nextArrive.getPages(0));
            }

            // add to RR_order queue
            readyQ.add(nextArrive);
            // check if other processor is ready before current time
            loopAllProcessor(type);  

        }
        else if (!readyQ.isEmpty()){
            isCompleted = false;
        }
        
        return isCompleted;
    }

    public int getTotalFaults(Processor p){

        // re-order the complete LinkedList
        Collections.sort(complete, new RR());   // sort the complete list i
        
       return p.getFaults();    // retrun procfessor's number of faults
        
    }

    public ArrayList<Integer> getTotalFaultsTime(Processor p){

        // re-order the complete LinkedList
        Collections.sort(complete, new RR());
        return p.getfaultTime();
    }

    public  int getTurnaroundTime(Processor p){
        int tr = 0;
        // re-order the complete LinkedList
        Collections.sort(complete, new RR());
       
        for(int i=RR_Timeslots.size();i>0; i--){
            if( (!RR_Timeslots.get(i-1).getIdString().matches("dispatch") ) && RR_Timeslots.get(i-1).getId() == p.getId()){
                tr = i;  // find when the processor acturally finished 
                
                break;
            }
        }
        
        return tr;
       
    }

    // this method is to print out the summery of the project.
    public void Sum(String memoryType){
        System.out.println("FIFO - "+memoryType+" Replacement:");
        System.out.println("PID  Process Name      Turnaround Time  # Faults  Fault Times        ");

        Collections.sort(complete, new RR());
        
        for(Processor p : complete){
            //System.out.println(p.getId() + "    " + getTurnaroundTime(p) + "      " +getTotalFaults(p) +"           "+ getTotalFaultsTime(p)+ "      ");
            System.out.printf("%-4d %-17s %-16d %-9d %s", p.getId(), p.getName() ,getTurnaroundTime(p) , getTotalFaults(p),getTotalFaultsTime(p).toString().replace("[","{").replace("]","}") );
            System.out.println("");
   
        }

    }

    // this method to for sorting the processor's completed list
    // in the order of the processor's id
    public int compare(Processor o1, Processor o2) {
        
        if (o1.getId() < o2.getId()) return -1;
        if (o1.getId() > o2.getId()) return 1;
        else return 0;

    }


   
}



