package com.shortpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.env.Environment;

public class Djikstra {
	
	
	
	 
	
	
	
	public  int [][] computeDijkstra(List<Integer> pos){
		int [][] djikstra_mat = new int [Environment.getLength()][Environment.getWidth()];
	 	for (int[] row:djikstra_mat)
	 		Arrays.fill(row,0);
		int number ;
		List<List<Integer>> openSquares = new ArrayList() ;
		openSquares.add(pos);
		do {
			List<Integer> cell =openSquares.get(0);
			if(cell==null)
				break;
			number = djikstra_mat[cell.get(0)][cell.get(1)];
			List<List<Integer>> coordinates = neighbors(cell.get(0),cell.get(1));
			for(List<Integer> coordinate : coordinates) {
	            if(( djikstra_mat[coordinate.get(0)][coordinate.get(1)] == 0) && (Environment.getMat_env()[coordinate.get(0)][coordinate.get(1)] == 0 || Environment.getMat_env()[coordinate.get(0)][coordinate.get(1)] == 2)) {
	            	djikstra_mat[coordinate.get(0)][coordinate.get(1)] = number + 1;
	                openSquares.add(coordinate);
	            }
	        }
			openSquares.remove(0);
		}while(!openSquares.isEmpty());
		return djikstra_mat;	
	}
	
	
	
	 
	 public List<List<Integer>> neighbors(int x,int y) {
		 List<List<Integer>> possibilities = new ArrayList();

		 for(int i=-1;i<2;i++) {
			 for(int j=-1;j<2;j++) {
				 if(i==0 | j==0) {
					 int newx=x+i;
					 int newy=y+j;
					 if(newx<0)
						 newx=Environment.getLength()-1;
					 else if(newx==Environment.getLength())
						 newx=0;
					 if(newy<0)
						 newy=Environment.getLength()-1;
					 else if(newy==Environment.getLength())
						 newy=0;
					 possibilities.add(Arrays.asList(newx,newy));
				 }
			 }
		 }
		 
		 return possibilities;
	 }

}
