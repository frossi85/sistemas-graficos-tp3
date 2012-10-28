package utilidades;

import java.util.ArrayList;

public class Vertice {

	private float x;
	private float y;
	private float z;
	
	public Vertice(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vertice(float x, float y){
		this.x = x;
		this.y = y;
		this.z = 0.0f;
	}

	public void set(float x, float y, float z){
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
	
	public static Vertice productoVectorial(Vertice v1, Vertice v2)
	{
		return new Vertice(v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
								  v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
								  v1.getX() * v2.getY() - v1.getY() * v2.getX());
	}
	
	public static Vertice restar(Vertice v1, Vertice v2)
	{
		return new Vertice(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
	}
	
	public Vertice escalar(float x, float y, float z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		
		return this;
	}
	
	public Vertice productoEscalar(float k)
	{
		return new Vertice(x*k, y*k, z*k);
	}
	
	public Vertice normalizar()
	{
		float length = (float) Math.sqrt((double) (x*x + y*y + z*z));
		
		x = x/length;
		y = y/length;
		z = z/length;
		
		return this;
	}
	
	public static Vertice sumar(ArrayList<Vertice> vertices)
	{
		float x = 0;
		float y = 0;
		float z = 0;
		
		for(int i = 0; i < vertices.size(); i++) {
			x += vertices.get(i).getX();
			y += vertices.get(i).getY();
			z += vertices.get(i).getZ();
		}
	
		return new Vertice(x, y, z);
	}
	
}
