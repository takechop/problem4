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
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class RectangleEditor extends Applet{
    Thread thread = null;
    Color col;
    //runメソッドない出できたRectangleのリストをとりあえずここに格納
    List<Rectangle> rectangle = new ArrayList<Rectangle>();
    private boolean running = true;
    InputPanel input;
    DisplayCanvas display;
    Board board = new Board();
    Command command = new Command(board);
    

    //GUIの初期化
    public void init(){
	//背景を白にする
	setBackground(Color.white);
	//レイアウトわからない
	//	setLayout(new FlowLayout());
	//InputPanelクラスに自分自身を渡す
	this.input = new InputPanel(this);
	this.input.setLayout(new FlowLayout());
	//DisplayCanvasクラスに自分自身を渡す
	display = new DisplayCanvas(this);

	//ボードにInputpanelを追加する
	add(input, BorderLayout.NORTH);
	//ボードにDisplayCanvasを追加する
	add(display, BorderLayout.SOUTH);


    }

    //GUIの描画処理
    public void paint(Graphics g){
	this.display.display(g);
	for(Rectangle rect : this.rectangle){
      
	    g.setColor(rect.getColor());
	    g.fillRect(rect.getX(),rect.getY(),rect.getW(),rect.getH());
	}
	start();
    }

    public void start(){
	try{
	    Scanner scan = new Scanner(System.in);
	
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
		
		    //  int choices = scan.nextInt();
		
		    //Commandクラスに選択した操作とリストを送る
		    //   command.selected(choices);
		    this.setRectList(board.rectangles);
		
		    //	    repaint();
		    
		}catch(InputMismatchException e){
		    System.out.println("操作番号を入力してください");
		    scan.next();
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






class InputPanel extends Panel implements ActionListener{
 
    //this.setLayout(new FlowLayout(FlowLayout.CENTER));

    Panel[] panel = new Panel[5];

    //テキストフィールド作成
    TextField[] valueField = new TextField[5];
    TextField[] moveField = new TextField[2];
    TextField[] expandField = new TextField[2];
    TextField[] shrinkField = new TextField[2];
    //width,height,x,yを格納するための変数
    double[] value = new double[4];
    String value_c;
    double[] moveValue = new double[2];
    double[] expandValue = new double[2];
    double[] shrinkValue = new double[2];
    //表示させるためのボタン
    Button dispButton;
    Button moveButton;
    Button expandButton;
    Button shrinkButton;
    //消去するボタン
    Button deleteButton;
    Button deleteAllButton;
    //親はActionExampleだよって言うことを示す(今回の場合はRectangleEditor？)
    RectangleEditor parent;
    //ラベル作成
    Label[] label = new Label[5];
    Label[] moveLabel = new Label[2];
    Label[] expandLabel = new Label[2];
    Label[] shrinkLabel = new Label[2];
    //実際の処理
    InputPanel(RectangleEditor app) {
	//親自身を受け取る
	parent = app;
	/*
	//ラベル作成
	Label[] label = new Label[4];
	*/
	for(int i = 0; i < 5; i++){
	    panel[i] = new Panel();
	}
	//ラベル情報入力
	label[0] = new Label("w= ");
	label[1] = new Label("h= ");
	label[2] = new Label("x= ");
	label[3] = new Label("y= ");
	label[4] = new Label("c= ");
	//ラベルとテキストフィールドをボード上に作成
	for (int i = 0; i < 5; i++) {
	    this.panel[0].add(label[i]);
	    this.valueField[i] = new TextField( );
	    this.panel[0].add(this.valueField[i]);
	}
	//表示させるためのボタンを作成
	this.dispButton = new Button("Disp");
	this.panel[0].add(this.dispButton);
	this.dispButton.addActionListener(this);

	Label[] moveLabel = new Label[2];
	moveLabel[0] = new Label("x+= ");
	moveLabel[1] = new Label("y+= ");
	for(int i = 0; i < 2; i++){
	    this.panel[1].add(moveLabel[i]);
	    this.moveField[i] = new TextField("", 5);
	    this.panel[1].add(moveField[i]);
	}
	this.moveButton = new Button("move");
	this.panel[1].add(this.moveButton);
	this.moveButton.addActionListener(this);

	Label[] expandLabel = new Label[2];
	expandLabel[0] = new Label("mx= ");
	expandLabel[1] = new Label("my= ");
	for(int i = 0; i < 2; i++){
	    this.panel[2].add(expandLabel[i]);
	    this.expandField[i] = new TextField("", 5);
	    this.panel[2].add(expandField[i]);
	}
	this.expandButton = new Button("expand");
	this.panel[2].add(this.expandButton);
	this.expandButton.addActionListener(this);

	Label[] shrinkLabel = new Label[2];
	shrinkLabel[0] = new Label("mx= ");
	shrinkLabel[1] = new Label("my= ");
	for(int i = 0; i < 2; i++){
	    this.panel[3].add(shrinkLabel[i]);
	    this.shrinkField[i] = new TextField("", 5);
	    this.panel[3].add(shrinkField[i]);
	}
	this.shrinkButton = new Button("shrink");
	this.panel[3].add(this.shrinkButton);
	this.shrinkButton.addActionListener(this);

	this.deleteButton = new Button("delete");
	this.panel[4].add(this.deleteButton);
	this.deleteButton.addActionListener(this);

	this.deleteAllButton = new Button("deleteAll");
	this.panel[4].add(this.deleteAllButton);
	this.deleteAllButton.addActionListener(this);
	
	for(int i = 0; i < 5; i++){
	    this.add(this.panel[i]);
	}
	
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
	    this.value_c = this.valueField[4].getText();
	    this.parent.command.create((int)this.value[0], (int)this.value[1], (int)this.value[2], (int)this.value[3], this.value_c);
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