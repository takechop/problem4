package EIEV3;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class RectangleEditor extends Applet implements Runnable{
    Thread thread = null;
    Color col;
    //runメソッドない出できたRectangleのリストをとりあえずここに格納
    List<Rectangle> rectangle = new ArrayList<Rectangle>();
    private boolean running = true;
    
    //GUIの初期化
    public void init(){
	//背景を白にする
	setBackground(Color.white);
	
	//マルチスレッド開始
	thread = new Thread(this);
	thread.start();
    }

    //GUIの描画処理
    public void paint(Graphics g){
	for(Rectangle rect : this.rectangle){
      
	    g.setColor(rect.getColor());
	    g.fillRect(rect.getX(),rect.getY(),rect.getW(),rect.getH());
	}
    }

    public void run(){
	try{
	    Scanner scan = new Scanner(System.in);
	    Command command = new Command();
	    Board board = new Board();
	
	    while(true){
		try{
		    //選択画面
		    System.out.println("1:create");
		    System.out.println("2:move");
		    System.out.println("3:expand");
		    System.out.println("4:shrink");
		    System.out.println("5:delete");
		    System.out.println("6:deleteAll");
		    System.out.println("7:intersect");
		    System.out.println("8:displayBoard");
		    System.out.println("9:exit");
		
		    int choices = scan.nextInt();
		
		    //Commandクラスに選択した操作とリストを送る
		    command.selected(choices,board);
		
		    this.setRectList(board.rectangles);
		
		    repaint();
		}catch(InputMismatchException e){
		    System.out.println("操作番号を入力してください");
		    scan.next();
		}
	    }
	    //握りつぶしちゃいけない気がする
	}catch(java.security.AccessControlException e){
	    System.out.println("アプレットを閉じてください");
	
	}
    }
    
    
    //このクラスのRectangleリストに入れる
    public void setRectList(List<Rectangle> rect){
	this.rectangle = rect;
    }
}
