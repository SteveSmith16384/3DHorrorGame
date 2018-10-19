package com.scs.horrorgame.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.scs.horrorgame.HorrorGame;

import ssmith.lang.Functions;

public class CSVMap implements IMapInterface {

	private ArrayList<String> al;// = new ArrayList<>();

	public CSVMap(String filename) throws IOException {
		HorrorGame.p("Trying to load " + filename + "...");
		al = new ArrayList<>();
		String all = Functions.readAllFileFromJar(filename);
		String[] lines = all.split("\\n");
		/*BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			br =  new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
		}
		try {*/
		for (int i=0 ; i<lines.length ; i++) {
			String line = lines[i];// br.readLine();
			if (line == null) {
				break;
			}
			al.add(line.trim());
		}
		HorrorGame.p("Loaded map");
		/*} finally {
			br.close();
		}*/
	}


	@Override
	public int getWidth() {
		return al.get(0).split("\t").length;
	}


	@Override
	public int getDepth() {
		return al.size();
	}


	@Override
	public int getCodeForSquare(int x, int z) {
		String line = al.get(z);
		String parts[] = line.split("\t");
		return Integer.parseInt(parts[x]);
	}

}
