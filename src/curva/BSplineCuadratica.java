package curva;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import utilidades.Vertice;

public class BSplineCuadratica extends Curva implements ICurva3D {	
	
	public BSplineCuadratica(ArrayList<Vertice> arrayList) throws Exception {
		super(arrayList);
		
		if(arrayList.size() != 3)
			throw new Exception("BSplineCuadratica: La cantidad de puntos de control debe ser 3");
	}
	
	// calculate blending function B0
	private float getB0(float t)
	{
		return ( t*t - 2.0f*t + 1.0f )/2.0f;
	}
	
	// calculate blending function B1
	private float getB1(float t)
	{
		return (-2.0f*t*t + 2*t + 1 )/2.0f;
	}
	
	// calculate blending function B2
	private float getB2(float t)
	{
		return ( t*t )/2.0f;
	}
	
	public float getX(float u) {
		// use the parametric time value 0 to 1
		float t = u; //(float)i/(LOD-1);

		// sum the control points mulitplied by their respective blending functions
		float x = getB0(t)*this.puntosDeControl.get(0).getX() +
				  getB1(t)*this.puntosDeControl.get(1).getX() + 
				  getB2(t)*this.puntosDeControl.get(2).getX() ;

		return x;
	}
	
	public float getY(float u) {
		// use the parametric time value 0 to 1
		float t = u; //(float)i/(LOD-1);

		// sum the control points mulitplied by their respective blending functions
		float y = getB0(t)*this.puntosDeControl.get(0).getY() + 
				  getB1(t)*this.puntosDeControl.get(1).getY() + 
				  getB2(t)*this.puntosDeControl.get(2).getY() ;
		
		return y;
	}
	
	public float getZ(float u) {
		// use the parametric time value 0 to 1
		float t = u; //(float)i/(LOD-1);

		// sum the control points mulitplied by their respective blending functions
		float z = getB0(t)*this.puntosDeControl.get(0).getZ() + 
				  getB1(t)*this.puntosDeControl.get(1).getZ() + 
				  getB2(t)*this.puntosDeControl.get(2).getZ() ;
		
		return z;
	}
	
	
	public void dibujar(GL2 gl, GLU glu)
	{
		/// the level of detail of the curve
		int LOD=20;
		
		
		gl.glBegin(GL2.GL_LINE_STRIP);


		// use the parametric time value 0 to 1
		for(int i=0;i!=LOD;++i) {

			float t = (float)i/(LOD-1);

			// sum the control points mulitplied by their respective blending functions
			float x = getX(t);

			float y = getY(t);

			float z = getZ(t);

			// specify the point
			gl.glVertex3f( x,y,z );
		}
		gl.glEnd();

		// draw the control points
		gl.glColor3f(0,1,0);
		gl.glPointSize(3);
		gl.glBegin(GL2.GL_POINTS);
		for(int i=0;i!=3;++i) {
			gl.glVertex3f( this.puntosDeControl.get(i).getX(), this.puntosDeControl.get(i).getY(), this.puntosDeControl.get(i).getZ() );
		}
		gl.glEnd();

		// draw the hull of the curve
		gl.glColor3f(0,1,1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		for(int i=0;i!=3;++i) {
			gl.glVertex3f( this.puntosDeControl.get(i).getX(), this.puntosDeControl.get(i).getY(), this.puntosDeControl.get(i).getZ() );
		}
		gl.glEnd();		
	}
	

	@Override
	public Vertice getPoint(float u) {
		return new Vertice(getX(u), getY(u), getZ(u));
	}
}