public class Cup {
        
    //Sets 3 dimensionality
    //needs rework if cup bottom not at z = 0
    private final int NDIM = 3;

    private Vector base;
    private double rBottom;
    private double rTop;
    private double height;
    private double beerRatio;
    private double slope;

    //wasHit is a boolean created to make sure balls leave the vicinity of
    //the cup before checking if they should bounce off the cup again
    private boolean wasHit;

    //Default constructor makes a standard solo cup at 0,0,0
    public Cup() {
	
	base = new Vector();
	rBottom = .03175;
	rTop = .0476;
	height = .120;

	beerRatio = 0.25;

	wasHit = false;
	slope = height / (rTop - rBottom);

    }

    //Constructor makes solo cup at position
    public Cup(Vector position) {
	
	base = (Vector) position.clone();
	rBottom = .03175;
	rTop = .0476;
	height = .120;

	beerRatio = 0.25;

	wasHit = false;
	slope = height / (rTop - rBottom);
    }

    public double getX() {
	return base.getX();
    }

    public double getY() {
	return base.getY();
    }

    public double getHeight() {
	return height;
    }

    public double getR(double z) {
	return rBottom + z / slope;
    }

    //isIn takes a ball and returns if it is in the cup
    public boolean isIn(Ball aBall) {
	
	double z = aBall.getZ();
	double dr = 0.0;

	if (z < height * beerRatio) {
	    dr += (aBall.getX() - base.getX()) * 
		(aBall.getX() - base.getX());
	    dr += (aBall.getY() - base.getY()) * 
		(aBall.getY() - base.getY());
	    dr = Math.sqrt(dr);
	    if (dr < (rBottom + z / slope)) {
		return true;
	    }
	}
	return false;
    }
		    
    /*hit checks if a ball is close enough to bounce off of the cup
     *if the ball is close and no recent hit happened then it bounces off
     *the method returns whether the ball is within its radius of the cup
     */
    public boolean hit(Ball aBall) {
	
	Vector ballPos = new Vector();
	Vector norm = new Vector();
	Vector edge = new Vector();
	double z = 0.0;
	double r = 0.0;
	double nMag;
	double dr;

	ballPos = (Vector) aBall.getPos().clone();

	if (ballPos.getZ() - aBall.getRadius() < height) {
	    
	    //Calculates position of nearest part of cup to ball
	    
	    //edge is vector to the ball's position
	    edge = (Vector) ballPos.clone();
	    
	    //edge is now the xy vector from center of cup to ball
	    edge.subtract(base);
	    edge.setZ(0.0);
	    
	    //calculates z and r of nearest point
	    //case where ball is above cup easy
	    if (ballPos.getZ() > height) {
		z = height;
		r = rTop;
	    }
	    //otherwise calculates cup radius at balls height first
	    else {
		z = ballPos.getZ();
		r = rBottom + z/slope;
		//then checks if outside of cup
		if (edge.getR() > r) {
		    z += aBall.getRadius() / slope;
		    r = rBottom + z/slope;
		}
		//or inside cup
		else {
		    z -= aBall.getRadius() / slope;
		    r = rBottom + z/slope;
		}
	    }
	    
	    //edge is vector from center of cup to nearest point on cup's 
	    //edge
	    edge.setR(r);
	    edge.setZ(z);
	   
	    //edge is position of nearest point on cup's edge
	    edge.add(base);

	     //norm is vector from cup to ball
	    norm = (Vector) ballPos.clone();
	    norm.subtract(edge);
	    
	    //dr is distance from ball to cup
	    dr = norm.getR();

	    //then checks if the ball hits
	    if (dr < aBall.getRadius()) {
		
		//if the ball has not recently bounced it performs a bounce
		if (!wasHit) {
		    
		    //turns edge to unit vector
		    norm.scale(1.0/dr);
		    
		    //then uses vector to reflect the ball
		    aBall.reflect(norm);

		    wasHit = true;

		}
	    }
	    
	    //if the ball is not within r of the cup it resets the hit flag
	    else {
		wasHit = false;
	    }
	}

	//if the ball is not within r of the cup it resets the hit flag
	else
	    wasHit = false;

	return wasHit;
    }
}

