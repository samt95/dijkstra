/* ShortestPathTest.java  
   
   This Class includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPathTest
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPathTest file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;


public class ShortestPathTest{
   /* main()
      Contains code to test the ShortestPath function. You may modify the
      testing code if needed, but nothing in this function will be considered
      during marking, and the testing process used for marking will not
      execute any of the code below.
   */
   public static void main(String[] args){
      Scanner s;
      if (args.length > 0){
         try{
            s = new Scanner(new File(args[0]));
         } catch(java.io.FileNotFoundException e){
            System.out.printf("Unable to open %s\n",args[0]);
            return;
         }
         System.out.printf("Reading input values from %s.\n",args[0]);
      }else{
         s = new Scanner(System.in);
         System.out.printf("Reading input values from stdin.\n");
      }
      
      int graphNum = 0;
      double totalTimeSeconds = 0;
      
      //Read graphs until EOF is encountered (or an error occurs)
      while(true){
         graphNum++;
         if(graphNum != 1 && !s.hasNextInt())
            break;
         System.out.printf("Reading graph %d\n",graphNum);
         int n = s.nextInt();
         int[][] G = new int[n][n];
         int valuesRead = 0;
         for (int i = 0; i < n && s.hasNextInt(); i++){
            for (int j = 0; j < n && s.hasNextInt(); j++){
               G[i][j] = s.nextInt();
               valuesRead++;
            }
         }
         if (valuesRead < n*n){
            System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
            break;
         }
         long startTime = System.currentTimeMillis();
         
         int totalWeight = ShortestPath.ShortestPath(G);
         long endTime = System.currentTimeMillis();
         totalTimeSeconds += (endTime-startTime)/1000.0;
         
         System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
      }
      graphNum--;
      System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
   }

}