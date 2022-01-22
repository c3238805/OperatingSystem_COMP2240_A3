import java.util.*;
import java.io.*;
import java.util.Scanner;

/*  
    /============================\
    |  COMP2240 Assignment 3     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:A3
// this class is to read the file into the program

public class FileUtity {
     // craate a Ready queue for the processer
    private LinkedList<Processor> input_Order = new LinkedList<>();
    private RR rr ;   // variable for round robin algorithm

    public FileUtity(){

    }
    // this method is to read the file and create given input memory type
    public LinkedList<Processor> readFile(String[] args , Memory<Processor> type ) throws IOException{
        int pCounter = 0;   // the processor's id start from id
        rr = new RR();

        // first read all the input txt
        for (int i = 0 ; i<args.length ; i++){
            if(i == 0){
                type.setMemory(Integer.parseInt(args[i]));// set the memory frame     
                    
            }
            else if(i == 1 ){
                rr.setquantum(Integer.parseInt(args[i]));
                // input the quantum for Round Robin 
            }
            else {
                // load all input processor data into input_Order queue.
                File file = new File(args[i]);      // open the txt file
                Scanner scan = new Scanner(file);   // create scanner to scan the file
                String stream = "";     // variable to store the tempory inputStream 
                
                try {
                    
                    while(scan.hasNextLine()){
                        stream = scan.nextLine();
                        if(stream.matches("begin")){
                            // start reading 
                            stream = scan.nextLine();
                            input_Order.add(new Processor(pCounter+1 , args[i]));    // create a new processor and add to the ready queue
                            // 
                            while(!stream.matches("end") ){
                                // input page into new crated processer       
                                input_Order.get(pCounter).setPage(Integer.parseInt(stream));                  
                                stream = scan.nextLine();     
                                if(stream.matches("end")){
                                    pCounter++;     // increase processor counter 
                                }
                                
                            }
                 
                        }
                        
                    }
                }catch (Exception e){
                    System.out.println("error occured: "+e.getMessage());
                }

                for(Processor p : input_Order){
                    // IF current processor input page exceed 50 pages, throw IOException
                    if (p.getPagesSize() > 50) {
                        System.out.println("Process: " + p.getId() + " - request count exceeds the allowed 50! Please check input files and try again.") ;
                        System.exit(0);
                    }
                }

                scan.close();   // close the scanner 

            }
        }
        return input_Order;     // return list of inout processor 
    }

    public RR getRR(){
        return rr;      // return round robin algorithm variable 
    }
}
