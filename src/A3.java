import java.util.*;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:A3
// this is the main class of A3 java program.
//This class simply will read the input data and add then excute the processor's page(intruction).

//               case 1 : If current processor's first page(instruction) is not in the main memory.
//    program behaviour : Issues a page fault ,the program will then continues to select next processor 
//                        that is in the readyQ and try to prcess the processor's  page for 3 unit quantum.

//               case 2 : If no ready processor to run next.
//    program behaviour : get the next avaliable processor and add dispatches until next avaliable processor's ready time
//                        
//               case 3 : If no ready processor to run next and no processor in the faultQ.
//    program behaviour : will display the summery of the program 



public class A3{

public static void main(String [] args) throws Exception{
    
    // craate a Ready queue for the processer
    LinkedList<Processor> input_Order = new LinkedList<>();
    // create user memory for both Fixed allocation with Local Replacement  and Variable Allocation with clobal replacement 
    Memory<Processor> fixed = new Fixed();  
    Memory<Processor> variable = new Variable();

    if(args.length<3){  // at lease one processor.txt input
        System.out.println("No processor input, program end. ");
        System.exit(0);
    }

    FileUtity read = new FileUtity();
    RR rr  = new RR();   // variable for round robin algorithm

    // read the data for fixed memory as input_Order processors
    input_Order = read.readFile(args, fixed);
    
    // FIFO - Fixed-Local Replacement :
    fixed.setMemoryType(input_Order);// set the memory frame for fixed 

    rr = read.getRR();
    rr.addProcessor(input_Order);

    rr.algorithm(fixed);    // run the round robin agorithm

    while(rr.checkAllComplete(fixed) == false){ // check if all processor has finish
        rr.algorithm(fixed);
    }

    
    rr.Sum("Fixed-Local");       //Turnaround Time //Number of Page Fault //Page fault time 
    System.out.println("");
    
     

//=======================================================

    // read the data for fixed memory as input_Order processors
    input_Order = read.readFile(args, variable);
    //FIF - Variable - Flobal Replacement :
    variable.setMemoryType(input_Order);// set the memory frame for variable 
    rr = read.getRR();

    rr.addProcessor(input_Order);

    rr.algorithm(variable);    // run the round robin agorithm

    while(rr.checkAllComplete(variable) == false){ // check if all processor has finish
        rr.algorithm(variable);
    }


    rr.Sum("Variable-Global");       //Turnaround Time //Number of Page Fault //Page fault time 
    System.out.println("");

     

}







}