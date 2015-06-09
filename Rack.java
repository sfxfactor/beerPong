public class Rack {

    private final int NDIM = 3;
    private int nCups;
    private Cup[] cups;
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    private double minZ;
    private double maxZ;

    //Default constructor is a standard 10 cup pong game
    public Rack() {
	
	nCups = 10;
	cups = new Cup[nCups];
	double x;
	double y;
	int n = 0;
	int nRows = 4;
	
	for (int row = 0; row < nRows; row++) {
	    x = 2.74 - .03175 - 
		Math.sqrt(3.0) * ((double) row) * .0476;
	    for (int i = 0; i < (nRows - row); i++) {
		y = (-0.5 *((double) (nRows - (row + 1))) + ((double) i));
		y *= 2.0 * .0476;
		cups[n] = new Cup(new Vector(x, y, 0.0));
		n++;
	    }
	}

	setRange();
    }

    //sets up the 3-2-1 rack
    public void set321() {

	nCups = 6;
	cups = new Cup[nCups];
	double x;
	double y;
	int n = 0;
	int nRows = 3;
	
	for (int row = 0; row < nRows; row++) {
	    x = 2.74 - .03175 - 
		Math.sqrt(3.0) * ((double) row) * .0476;
	    for (int i = 0; i < (nRows - row); i++) {
		y = (-0.5 *((double) (nRows - (row + 1))) + ((double) i));
		y *= 2.0 * .0476;
		cups[n] = new Cup(new Vector(x, y, 0.0));
		n++;
	    }
	}


	setRange();
    }
    //sets up the triangle rack
    public void setTriangle() {

	nCups = 3;
	cups = new Cup[nCups];
	double x;
	double y;
	int n = 0;
	int nRows = 2;
	
	for (int row = 0; row < nRows; row++) {
	    x = 2.74 - .03175 - 
		Math.sqrt(3.0) * ((double) row) * .0476;
	    for (int i = 0; i < (nRows - row); i++) {
		y = (-0.5 *((double) (nRows - (row + 1))) + ((double) i));
		y *= 2.0 * .0476;
		cups[n] = new Cup(new Vector(x, y, 0.0));
		n++;
	    }
	}

	setRange();
    }

    //prints out a string for the XY positions
    public String printXY() {
	String accum = "";
	    
	    for (int i = 0; i < nCups; i++) {
		accum = accum 
		    + cups[i].getX() + "\t" 
		    + cups[i].getY() + "\n";
	    }
	    
	    return accum;
    }

    //prints out a string for the XZ positions
    public String printXZ()  {
	String accum = "";
	
	double x;
	double z;
	
	for (int i = 0; i < nCups; i++) {
	    
	    z = 0.0;
	    x = cups[i].getX() + cups[i].getR(z);
	    
	    accum = accum + x + "\t" + z + "\n";

	    z = .120;
	    x = cups[i].getX() + cups[i].getR(z);

	    accum = accum + x + "\t" + z + "\n";

	    z = .120;
	    x = cups[i].getX() - cups[i].getR(z);
	    
	    accum = accum + x + "\t" + z + "\n";
	    z = 0.0;
	    x = cups[i].getX() - cups[i].getR(z);
	    
	    accum = accum + x + "\t" + z + "\n";

	}
	
	return accum;
    }

    //test hit attempts to hit a ball against all cups in order
    //returns whether any of the balls actually hit
    public boolean hit(Ball aBall) {
	
	boolean hit;
	
	double x = aBall.getX();
	double y = aBall.getY();
	double z = aBall.getZ();

	hit = false;
	if (x >= minX && x <= maxX &&
	    y >= minY && y <= maxY &&
	    z >= minZ && z <= maxZ)
	    for (int i = 0; i < nCups; i++)
		if (!hit) {
		    hit = cups[i].hit(aBall);
		}
    
	return hit;
    }

    //isIn takes a ball and returns whether the ball is in the rack
    public boolean isIn(Ball aBall) {
	
	boolean in = false;
	double x = aBall.getX();
	double y = aBall.getY();
	double z = aBall.getZ();
	int i = 0;

	if (x >= minX && x <= maxX &&
	    y >= minY && y <= maxY &&
	    z >= minZ && z <= maxZ)
	    while (i < nCups && !in) {
		in = cups[i].isIn(aBall);
		i++;
	    }
	
	return in;
    }
	    
    //Sets appropriate range for box around cups
    //Requires z = 0.0, R = .02, ncups > 0
    private void setRange() {
	
	//Assumes all cups at 0 base height and ball R = .02
	final double R = .02;

	minZ = 0.0;
	maxZ = cups[0].getHeight();

	double r = cups[0].getR(maxZ);

	//Scans cups for appropriate min and max X and Y
	minX = cups[0].getX();
	maxX = cups[0].getX();
	minY = cups[0].getY();
	maxY = cups[0].getY();
	
	for (int i = 1; i < nCups; i++) {
	    if (cups[i].getX() > maxX)
		maxX = cups[i].getX();
	    else if (cups[i].getX() < minX)
		minX = cups[i].getX();

	    if (cups[i].getY() > maxY)
		maxY = cups[i].getY();
	    else if (cups[i].getY() < minY)
		minY = cups[i].getY();
	}

	//adjusts for ball radius
	minX -= R + r;
	minY -= R + r;
	minZ -= R + r;
	maxX += R + r;
	maxY += R + r;
	maxZ += R + r;
    }

}