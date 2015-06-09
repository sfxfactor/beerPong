//class for making new 3d ball objects
public class Ball {
    
    //Sets 3 dimensionality
    private final int NDIM = 3;

    //sets gravitational acceleration
    private final Vector G_A = new Vector(0.0, 0.0, -9.8);

    //radius, mass, position, velocity, and drag coefficients
    private double r;
    private double m;
    private double beta;
    private double gamma;
    private double bounceCoeff;

    private Vector position;
    private Vector velocity;
    
    //default constructor creates Ping Pong ball in SI at 0 with v = 0
    //numbers based on measured values
    //assume dimensions are x, y, z
    public Ball() {
	r = .020;
	m = .0027;
	beta = .00016;
	gamma = .25;
	//add in experimentally based bounce coeff
	bounceCoeff = 0.50;

	position = new Vector();
	velocity = new Vector();

    }

    //constructor takes initial position and velocity
    public Ball(Vector x, Vector v) {
	r = .020;
	m = .0027;
	beta = .00016;
	gamma = .25;
	bounceCoeff = 0.50;

	position = (Vector) x.clone();
	velocity = (Vector) v.clone();
    }

    //accessor for radius
    public double getRadius() {
	return r;
    }

    //accessor methods send up vectors
    public Vector getPos() {
	return position;
    }

    public double getX() {
	return position.getX();
    }
    
    public double getY() {
	return position.getY();
    }
    
    public double getZ() {
	return position.getZ();
    }

    public Vector getVel() {
	return velocity;
    }

    public double getVX() {
	return velocity.getX();
    }
    
    public double getVY() {
	return velocity.getY();
    }
    
    public double getVZ() {
	return velocity.getZ();
    }

    //similarly mutator methods allow access to each dimension
    public void setPos(Vector x) {
	position = (Vector) x.clone();
	
    }

    public void setVel(Vector v) {
	velocity = (Vector) v.clone();
    }

    //update uses runge-kutta integration with the gravitational and drag 
    //forces to update position
    public void update(double dt) {
	
	//Creates drag acceleration
	Vector acceleration = (Vector) velocity.clone();
	acceleration.scale( - 1.0 / m * 
			    (
			     beta * 2 * r + 
			     gamma * (2 * r) * (2 * r) * velocity.getR()
			     )
			    );
	
	//Adds gravitational acceleration
	acceleration.add(G_A);

	//Creates velocity change vector
	Vector deltaV = (Vector) acceleration.clone();
	deltaV.scale(dt);

	//Creates position change vector
	Vector deltaX = (Vector) deltaV.clone();
	deltaX.scale(0.5);

	deltaX.add(velocity);
	deltaX.scale(dt);

	//Updates
	position.add(deltaX);
	velocity.add(deltaV);
	
    }

    //Reflects around unit vector that defines plane of reflection
    public void reflect(Vector norm) {
	
	Vector change = norm.component(velocity);
	change.scale(- 1.0 - bounceCoeff);

	velocity.add(change);
    }

    public void checkGround() {
	
	if (position.getZ() < r) {
	    if (velocity.getZ() < 0.0)
		velocity.setZ(-0.70 * velocity.getZ());
	    position.setZ(r);
	}
    }
}