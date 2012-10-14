package utilidades;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class LineaRecta implements ICurva3D {

	private PuntoDeControl Direccion;
	private PuntoDeControl Pivote;
	
	public LineaRecta(final PuntoDeControl p1, final PuntoDeControl p2) {
		
		Pivote = p1;
		Direccion = new PuntoDeControl(p2.getX() - p1.getX(), 
									p2.getY() - p1.getY(),
									p2.getZ() -p1.getZ());
		
		
	}
	
	//en los geters u no debe superar 1
	
	public float getX(float u) {
		return (Direccion.getX() * u) + Pivote.getX();
	}
	
	public float getY(float u) {
		return (Direccion.getY() * u) + Pivote.getY();
	}
	
	public float getZ(float u) {
		return (Direccion.getZ() * u) + Pivote.getZ();
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
	
}
