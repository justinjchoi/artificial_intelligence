import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class World{
    public String filename;
    public boolean uncertainty;

    //Robot can move to O position, cannot move to X position
    //S = unique starting position
    //F = unique end position
    //Uncertainty = false --> robot's ping returns correct string at that position
    //Uncertainty = true --> robot's ping may return incorrect value
    public World(String filename, boolean uncertainty){
	this.filename = filename;
	this.uncertainty = uncertainty;
    }

    //Returns the starting position of robot (S) 
    public Point getStartPos() {
	Point start = new Point();
	File file = new File(this.filename);
	Scanner scanner = null;
	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	 
	ArrayList<String> rows = new ArrayList<String>() ;
	while(scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    String[] rowItem = line.split("\n");
	    for(int i = 0; i < rowItem.length; i++) {
		rows.add(rowItem[i]);
	    }
	}
	 
	for(int i = 0; i < rows.size(); i++) {
	    String s = rows.get(i).replaceAll("\\s+","");
	    for(int j = 0; j < s.length(); j++) {
		char c = s.charAt(j);
		if(c == 'S') {
		    start.setLocation(i, j);
		}
	    }
	}
	return start; 
    }
    //Returns end position (F)
    public Point getEndPos() {
	Point finish = new Point();
	File file = new File(this.filename);
	Scanner scanner = null;
	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	 
	ArrayList<String> rows = new ArrayList<String>() ;
	while(scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    String[] rowItem = line.split("\n");
	    for(int i = 0; i < rowItem.length; i++) {
		rows.add(rowItem[i]);
	    }
	}
	 
	for(int i = 0; i < rows.size(); i++) {
	    String s = rows.get(i).replaceAll("\\s+","");
	    for(int j = 0; j < s.length(); j++) {
		char c = s.charAt(j);
		if(c == 'F') {
		    finish.setLocation(i, j);
		}
	    }
	}
	return finish; 
    }
  
    //Column Number
    public int numCols() {
	int colCount = 0;
	File file = new File(this.filename);
	Scanner scanner = null;
	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	 
	ArrayList<String> rows = new ArrayList<String>() ;
	while(scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    String[] rowItem = line.split("\n");
	    for(int i = 0; i < rowItem.length; i++) {
		rows.add(rowItem[i]);
	    }
	}
	 
	for(int i = 0; i < rows.size(); i++) {
	    String s = rows.get(i).replaceAll("\\s+","");
	    colCount = s.length();
	}
	return colCount;
    }
  
    //Row Number
    public int numRows() {
	int rowCount = 0;
	File file = new File(this.filename);
	Scanner scanner = null;
	try {
	    scanner = new Scanner(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	 
	ArrayList<String> rows = new ArrayList<String>() ;
	while(scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    String[] rowItem = line.split("\n");
	    for(int i = 0; i < rowItem.length; i++) {
		rows.add(rowItem[i]);
	    }
	}
	 
	for(int i = 0; i < rows.size(); i++) {
	    rowCount++;
	}
	return rowCount;
    }
  
    //Main Testing Driver - todo: delete
    //  public static void main(String[] args) throws IOException {
    //  World w = new World("TestCases/myInputFile4.txt", true);
    //  System.out.println(w.getStartPos());
    //  System.out.println(w.getEndPos());
    //  System.out.println(w.numCols());
    //  System.out.println(w.numRows());
    //  }
  
  
}