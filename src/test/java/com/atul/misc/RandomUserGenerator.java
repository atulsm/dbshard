package com.atul.misc;

import java.util.Random;

public class RandomUserGenerator {

	static final String[] names = {"Hana","Carlena","Lasandra","Patrina","Florine","Karisa","Rosalinda","Katheryn","Kristy","Cleveland","Christina","Wm","Lady","Elida","Tyesha","Mimi","Maryrose","Bella","Gricelda","Tim","Maisie","Mohammad","Dane","Pauletta","Asia","Rosia","Robt","Tish","Elwanda","Reyna","Glynis","Virgilio","Xochitl","Alia","Suzette","Angelyn","Tracie","Harold","Jerri","Felicita","Marilee","Ella","Florentina","Wilfredo","Mellissa","Kaila","Nieves","Spencer","Ayanna","Coleman"};
	static final String[] pins = {"1000","2000","3000","4000","5000","6000","7000","8000","9000"}; 
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			String fname = getName();
			String lname = getName();
			System.out.println(userId(fname, lname) + " " + fname + " " + getPin() + " " + getPhone());
		}
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min)) + min;

	    return randomNum;
	}
	
	public static String getName(){	
		return names[randInt(0, names.length)];
	}

	public static String getPin(){	
		return pins[randInt(0, pins.length)];
	}
	
	public static String getPhone(){	
		return randInt(100, 999)+"-"+randInt(100, 999)+"-"+randInt(100, 999);
	}
	
	public static String userId(String fname, String lname){
		return (fname + lname.charAt(0)).toLowerCase();
	}
	
	
}
