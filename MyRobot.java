import world.Robot;
import world.World;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyRobot extends Robot {
    boolean isUncertain;
    public String filename;
    public static int numPings = 0;
    public static int numMoves = 0;
    public static Stack<Point> moveList = new Stack<Point>();
    
    public MyRobot(String filename) {
	this.filename = filename;
    }

    //Travel to Destination
    @Override
	public void travelToDestination() {
	World world = null;
	try {
	    world = new World(this.filename, true);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Point finish = world.getEndPos();
	//initializations
	Point curr = new Point(super.getPosition());
	int currX = (int) super.getPosition().getX();
	int currY = (int) super.getPosition().getY();
	Point N = new Point(currX-1, currY);
	Point NW = new Point(currX-1,currY-1);
	Point NE = new Point(currX-1, currY+1);
	Point S = new Point(currX+1, currY);
	Point SW = new Point(currX+1,currY-1);
	Point SE = new Point(currX+1, currY+1);
	Point E = new Point(currX, currY+1);
	Point W = new Point(currX, currY-1);
	
	ArrayList<Point> pointList = new ArrayList<Point>();
	ArrayList<Double> distanceList = new ArrayList<Double>();
	double distance;
	pointList.add(N); pointList.add(S); pointList.add(E); pointList.add(W);
	pointList.add(NE);pointList.add(NW);pointList.add(SE);pointList.add(SW);
	pointList.removeIf(Objects::isNull);
	
	HashMap<Point,Double> pointDist = new HashMap<Point,Double>();  
	ArrayList<Point> nextMoves = new ArrayList<Point>();
	Point next = new Point();
	
	//if any direction has a O, pick the one that has the shortest distance from F 
	for(int i = 0; i < pointList.size(); i++) {
	    Point item = new Point();
	    item = pointList.get(i);
	    if(super.pingMap(item).equals("O")) {
		distance = calcDistance(item, finish);
		distanceList.add(distance);
		pointDist.put(item, distance);
		double min = Collections.min(pointDist.values());
		for(Map.Entry m : pointDist.entrySet()){
		    if(m.getValue().equals(min)) {
			next = (Point) m.getKey();
			nextMoves.add(next);
			super.move(next);
		    }
		}
	    }
	}
	
	if(super.getPosition().equals(finish)) {
	    System.out.println("Made it to destination!");
	    System.out.println("Num Pings: " + numPings);
	    System.out.println("Num Moves: " + numMoves);
	    //shut down simulator
	    System.exit(0);
	}
	else {
	    System.out.println("Stuck.");
	}
    }
  
    
    //Distance calculator 
    public double calcDistance(Point a, Point b) {
	double ax = a.getX();
	double bx = b.getX();
	double ay = a.getY();
	double by = b.getY();
	
	double distance = Math.hypot(ax-bx, ay-by);
	return distance;
    }
    
    //addToWorld
    @Override
	public void addToWorld(World world) {
        isUncertain = world.getUncertain();
        super.addToWorld(world);
    }
    
    //Move
    public Point move(Point position) {
	Point curr = this.getPosition();
	int moveX = (int) position.getX();
	int moveY = (int) position.getY();
	
	//legal case
	if((moveX == curr.getX()-1 && moveY == curr.getY()) || (moveX == curr.getX() + 1 && moveY == curr.getY()) 
	   || (moveX == curr.getX() && moveY == curr.getY()-1) || (moveX == curr.getX() && moveY == curr.getY()+1)
	   || (moveX == curr.getX()-1 && moveY == curr.getY()-1) || (moveX == curr.getX()-1 && moveY == curr.getY()+1)
	   || (moveX == curr.getX()+1 && moveY == curr.getY()-1) || (moveX == curr.getX()+1 && moveY == curr.getY()+1)) {
	    
	    if(this.pingMap(position).equals("O")) {
		numMoves++;
		curr.setLocation(moveX, moveY);
		moveList.push(curr);
	    }
	    else {
		System.out.println("Blocked by x!");
	    }
	    
	}
	else {
	    System.out.println("Not a legal move");
	}
	
	return curr; 
    }
  
  
    //Ping Map
    public String pingMap(Point position) {
	int x = (int) position.getX();
	int y = (int) position.getY(); 
	String result = null;
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
	String[] list = {"O", "X"};
	Random r = new Random();
	for(int i = 0; i < rows.size(); i++) {
	    String s = rows.get(x).replaceAll("\\s+","");
	    for(int j = 0; j < s.length(); j++) {
		char c = s.charAt(y);
		if(isUncertain) {
		    result = String.valueOf(c); 
		}
		else {
		    result = list[r.nextInt(list.length)];
		}
		 
	    }
	}
	numPings++;
	return result;
    }
    
    //getPosition
    //returns robot's current position as a point
    public Point getPosition() {
	Point result = new Point();
	if(!moveList.empty()) {
	    result.setLocation(moveList.peek());
	}
	else {
	    result.setLocation(super.getPosition());
	}
	
	return result;
    }
    
    //getX
    public int getX() {
	return (int) this.getPosition().getX();
    }
    
    //getY
    public int getY() {
	return (int) this.getPosition().getY();
    }
    
    //getNumMoves
    public int getNumMoves() {
	return numMoves;
    }
    
    //getNumPings 
    public int getNumPings() {
	return numPings;
	
    }

    //tester
    public static void main(String[] args) {
        try {
	    World myWorld = new World("TestCases/myInputFile1.txt", false);
            MyRobot robot = new MyRobot("TestCases/myInputFile1.txt");
            robot.addToWorld(myWorld);
            
	    myWorld.createGUI(400, 400, 200); // uncomment this and create a GUI; the last parameter is delay in msecs
	    robot.travelToDestination();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}