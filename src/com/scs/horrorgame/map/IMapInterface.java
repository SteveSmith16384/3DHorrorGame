package com.scs.horrorgame.map;

public interface IMapInterface {

	int getWidth();
	
	int getDepth();
	
	int getCodeForSquare(int x, int z);
	
	//int getNumCollectables();
}
