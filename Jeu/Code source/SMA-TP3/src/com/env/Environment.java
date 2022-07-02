package com.env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Environment {
	
	//Default DATA 
	public static int level = 100;
	public static boolean gamestatus = true;
	public static boolean gameend = false;
	private static int length, width;
	private static int [][] mat_env;
	private static int deathLimit =3;
	private static int birthLimit =4;
	private static int numberOfSteps =10;
	private static int nbCave = 0;
	public static long timeout=15L;
	public static long scoreforprice=4;
	
	private static List<Integer>  cavesSurface = new ArrayList<Integer>();
	private static float chanceToStartAlive=0.37f;
	public static int distmin=20;
	
	
	
	public Environment(int length, int width){
		Environment.length=length;
		Environment.width=width;
		Environment.mat_env=initializeMap(length,width);
		firstRandomMap();
		for(int i=0;i<numberOfSteps ;i++)
			Environment.mat_env=doSimulation();
		epurate();
		
	}
	
	
	int[][] initializeMap(int length,int width) {
		int[][] map= new int[length][width];
		for (int[] row: map)
		    Arrays.fill(row,0);
		return map;
	}
	
	int[][] generateRandomMap(int length,int width) {
		int[][] map= new int[length][width];
		for (int[] row: map)
		    Arrays.fill(row,0);
		return map;
	}
	
	void firstRandomMap(){
		for(int i=0;i<Environment.length;i++)
			for(int j=0;j<Environment.width;j++)
				if(Math.random()<chanceToStartAlive) 
					Environment.mat_env[i][j]=1;
				
	}
	int[][] doSimulation() {
		int[][] newMap = Environment.mat_env;
		
		for(int i=0;i<length;i++ ) {
			for(int j=0;j<width;j++ ) {
				int nbNeighbours = countAliveNeighbours(i, j);
				
				if(Environment.mat_env[i][j]==1) {
					if (nbNeighbours < deathLimit) {
						newMap[i][j]=0;
					}
					
				}else {
					if (nbNeighbours > birthLimit) {
						newMap[i][j]=1;
					}
				}
						
			}
		}
		return newMap;
		
	}

	public int countAliveNeighbours(int x , int y) {
		int count = 0;
		for (int i=-1 ;i <2 ;i++) { 
			for(int j=-1 ;j <2 ;j++)  {
				 int neighbour_x; int neighbour_y;
				neighbour_x = x + i;
				neighbour_y = y + j;
				
				if (i == 0 && j == 0) {
					continue;
				}
				else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= length || neighbour_y >= width ){
						count = count + 1;
				}
				else if( Environment.mat_env[neighbour_x][neighbour_y] == 1 ) { 
					count = count + 1;
				}
			}
		}
		return count;
	}
	public void epurate() {
		int k=0;
		List<Integer> emptyCell = findEmptyCell();
		while (emptyCell != null) {
			nbCave += 1;
			cavesSurface.add(0);
			k++;
			floodfill(emptyCell.get(0), emptyCell.get(1), 0, nbCave + 1);
			emptyCell = findEmptyCell();
			
		}	
		int biggestCave = 0;
		for (int x=0 ;x<nbCave;x++) {
			if (cavesSurface.get(x) > cavesSurface.get(biggestCave) ) {
				biggestCave = x;
			}			
		}
		
		for (int x=0 ;x<length;x++) 
			for (int y=0 ;y<width;y++) 
				if (Environment.mat_env[x][y] != biggestCave + 2 ) 
					Environment.mat_env[x][y] = 1;
				else
					Environment.mat_env[x][y] = 0;
				
	}
	public static List<Integer> findRandomEmptyCell(){
		int x;
		int y;
		do {
			 x =  (int) (Math.random() * length -1 );
			 y =  (int) (Math.random() * width -1 );
		}while(Environment.mat_env[x][y]==1);

		return Arrays.asList(x,y);
	}

	public List<Integer> findEmptyCell() {
		for(int i=0;i<length;i++ ) 
			for(int j=0;j<width;j++ ) 
				if (Environment.mat_env[i][j] == 0) 
					return Arrays.asList(i,j);
		return null;
	}
	
	public static List<Integer> getRealMove(int x,int y,List<Integer> movedir){
		List<Integer> move=new ArrayList();
		move.add(movedir.get(0));
		move.add(movedir.get(1));

		if(x + move.get(0)== length){
			move.set(0, -length+1);
		}
		else if(x + move.get(0)== -1){
			move.set(0, length-1);
		}
		else if(y + move.get(1)== width){
			move.set(1, -width+1);
		}
		else if(y + move.get(1)== -1){
			move.set(1, width-1);
		}
		return move;
	}
		
	public void floodfill( int x,int y, int oldNb, int newNb) {
		if (Environment.mat_env[x][y] != oldNb ) {
			return;
		}
		Environment.mat_env[x][y]=newNb;
		cavesSurface.set(nbCave-1, cavesSurface.get(nbCave-1) + 1);
		if (x < length -1) {
			floodfill(x + 1, y, oldNb, newNb); // right
		}
		if (x == 0){
			floodfill(length-1, y, oldNb, newNb);
		}
		if (y == 0){
			floodfill(x, width-1, oldNb, newNb);
		}
		if (x > 0) {
			floodfill(x - 1, y, oldNb, newNb); // left
		}
		if (y < width - 1) {
			floodfill(x, y + 1, oldNb, newNb);// down
		}
		if (y > 0) {
			floodfill(x, y - 1, oldNb, newNb); // up
		}	
	}

	public static int getLength() {
		return length;
	}


	public static void setLength(int length) {
		Environment.length = length;
	}


	public static int getWidth() {
		return width;
	}


	public static void setWidth(int width) {
		Environment.width = width;
	}


	public static int[][] getMat_env() {
		return mat_env;
	}


	public static void setMat_env(int[][] mat_env) {
		Environment.mat_env = mat_env;
	}
	
	

}
