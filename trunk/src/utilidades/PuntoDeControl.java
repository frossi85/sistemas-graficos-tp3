package utilidades;

public class PuntoDeControl {
	private float x;
	private float y;
	private float z;
	
	public PuntoDeControl(float x, float y){
		this.x = x;
		this.y = y;
		this.z = 0.0f;
	}
	
	public PuntoDeControl(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	void print() {
		System.out.println("(x, y, z) = (" + x + ", " + y + ", " + z + ")");
	}
}
