//package EIEV3;

import java.util.Scanner;
import java.awt.Color;

public class Rectangle{

    Scanner scan = new Scanner(System.in);
    
    Board board = new Board();

    private int width = 0;
    private int height = 0;
    private int x = 0;
    private int y = 0;
    private Color color;

    //セッター
    void setElement(int w, int h, int x, int y){
	this.width = w;
	this.height = h;
	this.x = x;
	this.y = y;
	
    }
    void setColor(String c){
	if(c.equals("red")){
	    this.color = Color.red;
	}else if(c.equals("blue")){
	    this.color = Color.blue;
	}else if(c.equals("yellow")){
	    this.color = Color.yellow;
	}else if(c.equals("gray")){
	    this.color = Color.gray;
	}
    }
    void setColor(Color c){
	this.color = c;
    }

    //ゲッター
    int getW(){
	return this.width;
    }
    int getH(){
	return this.height;
    }
    int getX(){
	return this.x;
    }
    int getY(){
	return this.y;
    }
    Color getColor(){
	return this.color;
    }
    
    boolean move(int x0, int y0){
	this.x += x0;
	this.y += y0;
	return board.rectangleSizeCheck(this.width,this.height,this.x,this.y);
    }
    boolean scale(float mx, float my){
	this.width = Math.round(this.width*mx);
	this.height = Math.round(this.height*my);
	return board.rectangleSizeCheck(this.width,this.height,this.x,this.y);
    }
}
