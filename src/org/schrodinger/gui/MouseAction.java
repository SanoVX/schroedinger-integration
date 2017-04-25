package org.schrodinger.gui;

public class MouseAction {
	public static boolean inKs(int xpos, int ypos, int xsize, int ysize, int x, int y){
		if(x < xpos + xsize && x > xpos && y > ypos && y < ypos + ysize){
			return true;
		}
		
		return false;
	}
}
