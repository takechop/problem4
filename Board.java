//package EIEV3;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

class Board{
    Scanner scan = new Scanner(System.in);
    
    //ボードの大きさは一定なのでstatic変数にしちゃった
    final static int WIDTH = 500;
    final static int HEIGHT = 400;
    //ボードの色付け
    private String color = "white";
    //リスト情報をここに持たせる
    List<Rectangle> rectangles = new ArrayList<Rectangle>();

    //これ汚い
    int index = 0;

    //長方形の作成
    void create(int w, int h, int x, int y, String c){
	Rectangle rect_tmp = new Rectangle();
	try{
	    //長方形がボードに収まり
	    if(this.rectangleSizeCheck(w,h,x,y) == true){
		//長方形に被りがない
		if(this.rectangleMatchCheck(w,h,x,y) == true){
		    //色がちゃんと指定されている
		    if(this.rectangleColorCheck(c) == true){
			if(this.rectangles.size() != 10){
			    rect_tmp.setElement(w,h,x,y);
			    rect_tmp.setColor(c);
			    this.rectangles.add(rect_tmp);
			}else{
			    System.out.println("長方形をこれ以上作成できません");
			}
		    }
		}
	    }
	}catch(InputMismatchException e){
	    System.out.println("数値を入力してください");
	    scan.next();
	}
    }

    
    //長方形がボードに収まるか確認
    boolean rectangleSizeCheck(int w, int h, int x, int y){
	if(w > 0 && h > 0 && x >= 0 && y >= 0 &&
	   w+x > 0 && h+y > 0 && w+x <= Board.WIDTH && h+y <= Board.HEIGHT){
	    return true;
	}else{
	    System.out.println("長方形がボードに収まっていません");
	    return false;
	}
    }
    //長方形に被りが存在するか確認
    boolean rectangleMatchCheck(int w, int h, int x, int y){
	for(Rectangle rect : this.rectangles){
	    if(rect.getW() == w && rect.getH() == h &&
	       rect.getX() == x && rect.getY() == y){
		System.out.println("その長方形は存在しています");
		return false;
	    }else{
		;
	    }
	}
	return true;
    }

    //長方形の色がちゃんと指定されているか確認
    boolean rectangleColorCheck(String c){
	if(c.equals("red") || c.equals("blue") ||
	   c.equals("yellow") || c.equals("gray")){
	    return true;
	}else{
	    System.out.println("決められた色情報の中から選んでください");
	    return false;
	}
    }
    
    //リスト内にある長方形を表示
    void displayBoard(){
	int i = 1;
	for(Rectangle rect : this.rectangles){
	    System.out.println(i + "個目の長方形");
	    System.out.println("color : " + rect.getColor());
	    System.out.print("width : " + rect.getW() + ", ");
	    System.out.print("height : " + rect.getH() + ", ");
	    System.out.print("x : " + rect.getX() + ", ");
	    System.out.println("y : " + rect.getY());
	    i++;
	}

    }
    //選択した長方形を削除
    void delete(){
	if(this.rectangles.isEmpty() == true){
	    System.out.println("長方形は作成されていません");
	}else{
	    try{
		System.out.println("削除したい長方形は何個目ですか？");
		this.displayBoard();
		System.out.print("削除したい長方形 : ");
		int index = scan.nextInt();
		this.rectangles.remove(index-1);
	    }catch(IndexOutOfBoundsException e){
		System.out.println("存在していません");
	    }catch(InputMismatchException e){
		System.out.println("数値を入力してください");
		scan.next();
	    }
	}
    }
    
    //すべての長方形を削除
    void deleteAll(){
	if(this.rectangles.isEmpty() == true){
	    System.out.println("長方形は作成されていません");
	}else{
	    System.out.println("長方形を全て削除します");
	    this.rectangles.clear();
	}
    }

    int selectRectangle(double mx, double my){
	System.out.println("ここ入ってる？");
	System.out.println("mx : " + mx + "my : " + my);
	for(int i = this.rectangles.size()-1 ; i >= 0; i--){
	    if(this.rectangles.get(i).getX() <= mx &&
	       mx <= this.rectangles.get(i).getXW() &&
	       this.rectangles.get(i).getY() <= my &&
	       my <= this.rectangles.get(i).getYH()){
		System.out.println("長方形番号 : " + i);
		return i;
	    }
	}
	return -1;
    }
	
    //処理する長方形を選び返す
    Rectangle selectRectangle(){
	Rectangle rect_before = new Rectangle();
	System.out.println("操作したい長方形を選んでください");
	this.displayBoard();
	System.out.print("操作したい長方形 : ");
	index = scan.nextInt();
	rect_before.setElement(this.rectangles.get(index-1).getW(), this.rectangles.get(index-1).getH(),this.rectangles.get(index-1).getX(), this.rectangles.get(index-1).getY());
	rect_before.setColor(this.rectangles.get(index-1).getColor());
	return rect_before;
    }

    //処理後の長方形の置き換え
    void replaceRectangle(Rectangle rect_after){
	//移動後の長方形がかぶってないかチェック
	if(this.rectangleMatchCheck(rect_after.getW(), rect_after.getH(),rect_after.getX(), rect_after.getY()) == true){
	    this.rectangles.set(index-1, rect_after);
	}
    }

    List<Rectangle> getList(){
	return rectangles;
    }
}

