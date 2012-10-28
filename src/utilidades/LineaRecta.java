package utilidades;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class LineaRecta implements ICurva3D {

	private Vertice Direccion;
	private Vertice Pivote;
	private float escalaX;
	private float escalaY;
	private float escalaZ;
	
	public LineaRecta(final Vertice p1, final Vertice p2) {
		
		Pivote = p1;
		Direccion = new Vertice(p2.getX() - p1.getX(), 
									p2.getY() - p1.getY(),
									p2.getZ() -p1.getZ());
		
		escalaX = 1.0f;
		escalaY = 1.0f;
		escalaZ = 1.0f;
	}
	
	public LineaRecta escalar(float x, float y, float z) {
		escalaX = x;
		escalaY = y;
		escalaZ = z;
		
		return this;
	}
	
	//en los geters u no debe superar 1
	
	public float getX(float u) {
		return ((Direccion.getX() * u) + Pivote.getX()) * escalaX;
	}
	
	public float getY(float u) {
		return ((Direccion.getY() * u) + Pivote.getY()) * escalaY;
	}
	
	public float getZ(float u) {
		return ((Direccion.getZ() * u) + Pivote.getZ()) * escalaZ;
	}
	
	public void test()
	{
		System.out.print("Direccion: ");
		Direccion.print();
		System.out.print("Pivote: ");
		Pivote.print();
		
		int LOD=2;
		
		// use the parametric time value 0 to 1			
		float paso = 1.0f / LOD;
		float t = 0f;
		
		for(int i=0;i<=LOD;i++) {

			// sum the control points mulitplied by their respective blending functions
			float x = getX(t);

			float y = getY(t);

			float z = getZ(t);
			
			System.out.println("u = " + t);
			System.out.println("(x, y, z) = (" + x + ", " + y + ", " + z + ")");
			
			t += paso;
		}
	}

	@Override
	public void dibujar(GL2 gl, GLU glu) {
		// TODO Auto-generated method stub
		int LOD=300;
		
		gl.glBegin(GL2.GL_LINE_STRIP);


		// use the parametric time value 0 to 1			
		float paso = 1.0f / LOD;
		float t = 0f;
		
		for(int i=0;i!=LOD;i++) {

			// sum the control points mulitplied by their respective blending functions
			float x = getX(t);

			float y = getY(t);

			float z = getZ(t);

			// specify the point
			gl.glVertex3f( x,y,z );
			
			t += paso;
		}
		
		gl.glEnd();
	}

	@Override
	public Vertice getPoint(float u) {
		return new Vertice(getX(u), getY(u), getZ(u));
	}
	
}
