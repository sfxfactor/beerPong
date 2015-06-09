import java.util.Scanner;
import java.io.*;

//Class for simulating and printing a single toss of a pong ball
public class Toss {
    public static void main(String[] args) {

	//sets three dimensions, 0 = X, 1 = Y, 2 = Z
	final int NDIM = 3;

	//sets upper bound on time limit and time per steps
	final int MAXT = 10000000;
	final double DT = .00001;

	//sets boundaries of table
	final double MAX_X = 2.80; final double MIN_X = 0.0;
	final double MAX_Y = 0.76; final double MIN_Y = -0.76;

	//sets up boolean for if there has been a hit and one for if the 
	//ball is in
	boolean hit = false;
	boolean in = false;
	
	//constructs objects
	Scanner console = new Scanner(System.in);
	Ball pongBall = new Ball();
	Rack game = new Rack();

	//takes input
	System.out.print("Initial position in y z plane: ");
	pongBall.setPos(
			new Vector(0.0, 
				   console.nextDouble(), 
				   console.nextDouble())
			);

	System.out.print("Initial velocity x y z components: ");
	pongBall.setVel(
			new Vector(console.nextDouble(), 
				   console.nextDouble(), 
				   console.nextDouble())
			);
	
	//sets up output
	try{
	    PrintWriter output = new PrintWriter(new FileWriter("out.dat"));

	    //prints output comment
	    output.println("#timestep x y z");

	    //sets up conditions to continue simulation
	    int t = 0;
	    while (pongBall.getX() <= MAX_X && pongBall.getX()
		   >= MIN_X &&
		   pongBall.getY() <= MAX_Y && pongBall.getY() 
		   >= MIN_Y &&
		   t < MAXT &&
		   !in) 
		{

		    //prints current position and time every 10 steps
		    if (t % 10 == 0)
			output.println(((double) t) * DT + 
				       "\t" + pongBall.getX() + 
				       "\t" + pongBall.getY() +
				       "\t" + pongBall.getZ());

		    //integrates forward in time
		    pongBall.update(DT);

		    //checks for hits if there hasn't been a hit
		    hit = game.hit(pongBall);
		    
		    //checks to see if the ball should bounce up from the 
		    //ground
		    if (!hit)
			pongBall.checkGround();

		    in = game.isIn(pongBall);

		    t++;
		}

	    if (in)
		System.out.println("The ball is in!");

	    //closes output
	    output.close();    
	}

	//catches exceptions and ignores them
	catch(IOException e) {}
    }
}