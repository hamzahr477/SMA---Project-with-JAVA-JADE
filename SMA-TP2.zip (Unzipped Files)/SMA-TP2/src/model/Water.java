package model;

import java.util.Arrays;
import java.util.List;

public class Water {
	
	public static int[][] water = new int[50][50];
	public static int count_fish=1;
	
	public static void setup() {
		for (int[] row: water)
		    Arrays.fill(row,0);
	}
	
	public static void updateWithBreed(List<Integer> currentPos,List<Integer> NPos, int agnum) {
		System.out.println("fishi number "+agnum);
		water[currentPos.get(0)][currentPos.get(1)] = 1; 
		water[NPos.get(0)][NPos.get(1)] = agnum; 
	}
	
	public static void update(List<Integer> newPos,List<Integer> currentPos, int agnum) {
		water[currentPos.get(0)][currentPos.get(1)] = 0;
		water[newPos.get(0)][newPos.get(1)] = agnum; 
	}
	
	public static int getumbOfFish() {
		int count_fish=0;
		for (int[] row: water)
			for (int b: row)
				if(b > 0)
					count_fish+=1;
		return count_fish;
	}

	public static void updateWithBreed_Shark(List<Integer> newPos) {
		// TODO Auto-generated method stub
		water[newPos.get(0)][newPos.get(1)] = -1; 
	}

	public static void update_Shark(List<Integer> newPos, List<Integer> current) {
		// TODO Auto-generated method stub
		water[current.get(0)][current.get(1)] = 0;
		water[newPos.get(0)][newPos.get(1)] = -1; 
		
	}

	public static int getumbOfShark() {
		int count_shark=0;
		for (int[] row: water)
			for (int b: row)
				if(b < 0)
					count_shark+=1;
		return count_shark;
	}
	
	

}
