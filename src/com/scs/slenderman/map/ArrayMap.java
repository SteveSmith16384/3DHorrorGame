package com.scs.slenderman.map;

public class ArrayMap implements IMapInterface {

	int[][] data = new int[][]{
		{4,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,4,0,4,0,4,0,4,0,4,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,5,0,1,0,1,0,1,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{1,0,0,0,0,0,5,0,1,0,2,0,1,0,5,0,0,0,0,1}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0}
	};
	

	public ArrayMap() {

	}

	
	@Override
	public int getWidth() {
		return data[0].length;
	}

	
	@Override
	public int getDepth() {
		return data.length;
	}

	
	@Override
	public int getCodeForSquare(int x, int z) {
		return data[x][z];
	}


}
