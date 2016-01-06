/* ShortestPath.java
   
   Implementation of Dijkstra's Algorithm to find a single source shortest path.

   This program returns the minimum weight path from vertex 0 to vertex 1,
   but can be easily modified to determine shortest paths between other vertices.

   Sam Taylor - 09/10/2015

*/

import java.util.Arrays;
import java.util.ArrayList;

public class ShortestPath{

	/* ShortestPath(G)
		Given an adjacency matrix for graph G, return the total weight
		of a minimum weight path from vertex 0 to vertex 1.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int ShortestPath(int[][] G){
		int numVerts = G.length;
		
		int[] distance = new int[numVerts];
		Vertex[] vertices = new Vertex[numVerts];
		Arrays.fill(distance, 999999999); //initialize array to some large number (to imitate a value of infinity)
		distance[0] = 0;
		
		int heapSize = numVerts; 
		Vertex[] vertArr = new Vertex[numVerts+1]; //1 based indexing

		// create Vertex objects and set their neighbors
		for(int i = 0; i < numVerts; i++){
			vertArr[i+1] = new Vertex(i);
			for(int k = 0; k < i; k++){
                if(G[i][k] > 0){
					vertArr[i+1].neighbors.add(vertArr[k+1]);
					vertArr[k+1].neighbors.add(vertArr[i+1]);                                
                }
			}
		}

		vertArr[1].distance = 0;       
                

		while(heapSize > 0){
			Vertex v = removeMin(vertArr, heapSize);
                                                          
			heapSize--;
			for(Vertex n : v.neighbors){
                if(distance[v.index] + G[v.index][n.index] < distance[n.index]){
                    distance[n.index] = distance[v.index] + G[v.index][n.index];
                    n.distance = distance[n.index];
                }
			}
    		heapify(vertArr, heapSize);
		}
		return distance[1];		
	}


	/* heapify(vertArr, heapSize)
		Make the input vertArr into a proper heap
		Heapify runs in O(n) time
	*/
	public static void heapify(Vertex[] vertArr, int heapSize){
		
		for(int i = vertArr.length; i > 0; i--){
			bubbleDown(i, vertArr, heapSize);
		}
	}


	public static void bubbleDown(int i, Vertex[] vertArr, int heapSize){
		while(2*i <= heapSize){

			int smallChild;
			if(2*i +1 > heapSize){ //only has a leftchild
				smallChild = 2*i; //smallChild is left child
			}
			else{
				smallChild = getSmallChild(i, vertArr);
			}

			if(vertArr[i].distance > vertArr[smallChild].distance){
				//swap vertices
				Vertex temp = vertArr[i];
				vertArr[i] = vertArr[smallChild];
				vertArr[smallChild] = temp;

				i = smallChild;
			}
			else{
				break;
			}			
		}		
	}


	public static int getSmallChild(int i, Vertex[] vertArr){
		if(vertArr[2*i].distance < vertArr[2*i + 1].distance){
			return 2*i;
		}
		return 2*i + 1;
	}


	public static Vertex removeMin(Vertex[] vertArr, int heapSize){
		Vertex min = vertArr[1];
		vertArr[1] = vertArr[heapSize];
		return min;
	}


	public static class Vertex{
		int index;
		int distance;
		ArrayList<Vertex> neighbors;

		public Vertex(int index){
			this.index = index;
			neighbors = new ArrayList<Vertex>();
			distance = 999999999;
		}
	}
}