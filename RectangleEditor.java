//package EIEV3;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Canvas;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RectangleEditor extends Applet implements Runnable{
    Thread thread = null;
    Color col;
    //runメソッドない出できたRectangleのリストをとりあえずここに格納
    List<Rectangle> rectangle = new ArrayList<Rectangle>();
    private boolean running = true;
    InputPanel input;
    DisplayCanvas display;
    
    //GUIの初期化
    public void init(){
	//背景を白にする
	setBackground(Color.white);
	//InputPanelクラスに自分自身を渡す
	input = new InputPanel(this);
	//DisplayCanvasクラスに自分自身を渡す
	display = new DisplayCanvas(this);

	//ボードにInputpanelを追加する
	add(input, BorderLayout.NORTH);
	//ボードにDisplayCanvasを追加する
	add(display, BorderLayout.SOUTH);
	
	//マルチスレッド開始
	thread = new Thread(this);
	thread.start();
    }

    //GUIの描画処理
    public void paint(Graphics g){
	this.display.display(g);
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






class InputPanel extends Panel implements ActionListener {
    //テキストフィールド作成
    TextField[] valueField = new TextField[4];
    //width,height,x,yを格納するための変数
    double[] value = new double[4];
    //表示させるためのボタン
    Button dispButton;
    //親はActionExampleだよって言うことを示す(今回の場合はRectangleEditor？)
    RectangleEditor parent;
    //実際の処理
    InputPanel(RectangleEditor app) {
	//親自身を受け取る
	parent = app;
	//ラベル作成
	Label[] label = new Label[4];
	//ラベル情報入力
	label[0] = new Label("width=");
	label[1] = new Label("height= ");
	label[2] = new Label("x =");
	label[3] = new Label("y =");
	//ラベルとテキストフィールドをボード上に作成
	for (int i = 0; i < 4; i++) {
	    this.add(label[i]);
	    this.valueField[i] = new TextField( );
	    this.add(this.valueField[i]);
	}
	//表示させるためのボタンを作成
	this.dispButton = new Button("Disp");
	this.add(this.dispButton);
	this.dispButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt){
	Button button = (Button)evt.getSource();
	if(button == this.dispButton){
	    for(int i = 0; i < 4; i++){
		if(this.valueField[i].getText().equals(""))
		    this.value[i] = 0;
		else
		    //数字が入力されていれば、メンバに格納
		    this.value[i] = new Double(this.valueField[i].getText()).doubleValue();
	    }
	    this.parent.repaint();
	}
    }
}






class DisplayCanvas extends Canvas{
    //RectangleEditorを親に持つことを示す
    RectangleEditor parent;

    DisplayCanvas(RectangleEditor app){
	//親を受け取る
	this.parent = app;
    }

    public void display(Graphics g){
	//多分フォントの形式を決めてる
	Font font = new Font("TimesRoman", Font.BOLD,12);
	//フォントを指定する
	g.setFont(font);
	//色を指定する
	g.setColor(Color.red);
	//フォントのサイズを確認
	FontMetrics fm = g.getFontMetrics(font);
	//フォントのサイズを受け取る
	int h = fm.getHeight();
	//受け取ったサイズhを生かして入力された情報を表示
	g.drawString("幅は" + this.parent.input.value[0], 10, 70);
	g.drawString("高さは" + this.parent.input.value[1], 10, 70+h);
	g.drawString("x座標は" + this.parent.input.value[2], 10, 70+h*2);
	g.drawString("y座標は" + this.parent.input.value[3], 10, 70+h*3);
    }
}