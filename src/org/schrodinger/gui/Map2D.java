package org.schrodinger.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Map2D extends JPanel {
	private double[][] data;
	private double max_value = Double.MIN_VALUE;
	private double min_value = Double.MAX_VALUE;
	private JLabel lblmin, lblmax;
	
	public Map2D(){
		lblmin = new JLabel();
		lblmin.setBounds(getHeight()-25, 0, 50, 25);
		lblmax = new JLabel();
		lblmax.setBounds(getHeight()-25, getWidth()-50, 50, 25);
		add(lblmax);
		add(lblmin);
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				lblmin.setBounds(0, getHeight()-25, 50, 25);
				lblmax.setBounds(getWidth()-50, getHeight()-25, 50, 25);
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
		});
	}
	
	@Override
	public void paintComponent(Graphics g){
		int rows = data.length;
		int columns = data[0].length; 
		if(rows == 0 || columns ==0){
			return;
		}

		float column_width = (getWidth()/(float)columns);
		float row_width = ((getHeight()-50)/(float)rows);
		
		column_width = Math.min(column_width, row_width);
		row_width = column_width;
		
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<columns; j++){//1. Eintrag von 0 bis 360°
				g.setColor(Color.getHSBColor(0.7f*(float) ((data[i][j]*data[i][j]-min_value)/(max_value-min_value)), 1.0f, 1.0f));
				g.fillRect((int)column_width*j, (int)row_width*i, (int)column_width, (int)row_width);
			}
		}
		
		//Farblegende
		int width = getWidth();
		int height = getHeight();
		for(int i = 0; i<width; i++){
			g.setColor(Color.getHSBColor(0.7f*i/(float)width, 1.0f, 1.0f));
			g.drawLine(i,height-45 , i, height-25);
		}
	}
	
	public void setData(double[][] data){
		this.data = data;
		int rows = data.length;
		int columns = data[0].length; 
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<columns; j++){
				if(data[i][j]*data[i][j]>max_value){
					max_value = data[i][j]*data[i][j];
				}
				if(data[i][j]*data[i][j]<min_value){
					min_value = data[i][j]*data[i][j];
				}
			}
		}
		lblmin.setText(new DecimalFormat("##.##").format(min_value));
		lblmax.setText(new DecimalFormat("##.##").format(max_value));
		this.repaint();
	}

}
