public class Vector implements Cloneable {

    private final int NDIM = 3;
    private double x;
    private double y;
    private double z;
    

    //Default constructor is 0 vector
    public Vector() {
	
	x = 0.0;
	y = 0.0;
	z = 0.0;

    }

    //Constructor takes 3 doubles as x, y, z coordinates
    public Vector(double x1, double y1, double z1) {

	x = x1;
	y = y1;
	z = z1;
	
    }

    //Constructor takes 1 double as r, puts all in x direction
    public Vector(double r1) {
	
	x = r1;
	y = 0.0;
	z = 0.0;

    }

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }

    public double getZ() {
	return z;
    }
    
    public double getR() {
	return Math.sqrt(x*x + y*y + z*z);
    }

    public double getPhi() {
	if (x == 0.0) {
	    if (y > 0.0)
		return Math.PI/2;
	    else if (y < 0.0)
		return -Math.PI/2;
	    else
		return (-10.0);
	}
	else return Math.atan(y/x);
    }

    public double getTheta() {

	double r;

	if (z == 0.0) {
	    if (y == 0.0 && x == 0.0)
		return (-10.0);
	    else
		return Math.PI/2;
	}
	else {
	    r = Math.sqrt(x*x + y*y);
	    return Math.atan(z/r);
	}
    }

    public void setX(double x1) {
	x = x1;
    }
    
    public void setY(double y1) {
	y = y1;
    }
    
    public void setZ(double z1) {
	z = z1;
    }

    public void setR(double r) {

	double r0;

	r0 = Math.sqrt(x*x + y*y + z*z);
	x *= r/r0;
	y *= r/r0;
	z *= r/r0;
    }

    public void scale(double scale) {
	x *= scale;
	y *= scale;
	z *= scale;
    }

    public void setPhi(double phi) {
	double r;

	r = Math.sqrt(x*x + y*y);
	x = Math.cos(phi) * r;
	y = Math.sin(phi) * r;
    }

    public void setTheta(double theta) {

	double r;
	double r0;

	r0 = Math.sqrt(x*x + y*y);

	double cosPhi;
	double sinPhi;
	
	if (r0 == 0) {
	    cosPhi = 0.0;
	    sinPhi = 0.0;
	}
	else {
	    cosPhi = x/r0;
	    sinPhi = y/r0;
	}
	r = Math.sqrt(x*x + y*y + z*z);

	r0 = r * Math.cos(theta);

	x = r0 * cosPhi;
	y = r0 * sinPhi;
	z = r * Math.sin(theta);

    }

    public void add(Vector r) {
	x += r.getX();
	y += r.getY();
	z += r.getZ();
    }

    public void subtract(Vector r) {
	x -= r.getX();
	y -= r.getY();
	z -= r.getZ();
    }

    public double product(Vector r) {
	
	return (x * r.getX() + y * r.getY() + z * r.getZ());
    }	

    public Vector component(Vector along) {
	
	double product = product(along);
	double r = Math.sqrt(x*x + y*y + z*z);

	return (new Vector(product * x/r, product * y/r, product * z/r));
    }

    public Object clone() {
	try {
	    return (Vector) super.clone();	    
	}
	catch(CloneNotSupportedException e) {
	    System.out.println(e);
	    return null;
	}
    }
}