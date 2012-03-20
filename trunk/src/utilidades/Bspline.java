package utilidades;
import java.awt.Point;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class Bspline extends Curva {
	
	public ArrayList<Float> nodos = new ArrayList<Float>();
	public int d = 3;
	public int n = 3;
	public int cantidadMaximaNodos;
	public float nodoMinimo;
	public float nodoMaximo;
	
	
	public Bspline(ArrayList<PuntoDeControl> arrayList){
		super(arrayList);
		
		//n = list.size() + 1;
		
		this.puntosDeControl = new ArrayList<PuntoDeControl>();
		

		this.puntosDeControl.add(new PuntoDeControl(0.25f, 0.25f));
		this.puntosDeControl.add(new PuntoDeControl(0.5f, 0.25f));
		this.puntosDeControl.add(new PuntoDeControl(1.0f, 0.0f));
		
		this.cantidadMaximaNodos = d + n + 1;
		
		for(int i = 0; i < this.cantidadMaximaNodos; i++)
		{
			nodos.add((float) i);
		}
		
		nodoMinimo = 0.0f;
		nodoMaximo = (float) (this.cantidadMaximaNodos - 1);
	}
	
	
	public void dibujar2(GL2 gl, GLU glu)
	{		
		/// the control points for the curve
		float Points[][] = {
			{ 10,10,0 },
			{  5,10,2 },
			{ -5,0,0 },
			{-10,5,-2}
		};

		/// the level of detail of the curve
		int LOD=20;
		
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);

		// clear the previous transform
		gl.glLoadIdentity();

		// set the camera position
		glu.gluLookAt(	1,10,30,	//	eye pos
					0,0,0,	//	aim point
					0,1,0);	//	up direction

		
		gl.glBegin(GL2.GL_LINE_STRIP);

		// use the parametric time value 0 to 1
		for(int i=0;i!=LOD;++i) {

			float t = (float)i/(LOD-1);

			// the t value inverted
			float it = 1.0f-t;

			// calculate blending functions
			float b0 = it*it*it/6.0f;
			float b1 = (3*t*t*t - 6*t*t +4)/6.0f;
			float b2 = (-3*t*t*t +3*t*t + 3*t + 1)/6.0f;
			float b3 =  t*t*t/6.0f;

			// sum the control points mulitplied by their respective blending functions
			float x = b0*Points[0][0] +
					  b1*Points[1][0] + 
					  b2*Points[2][0] + 
					  b3*Points[3][0] ;

			float y = b0*Points[0][1] + 
					  b1*Points[1][1] + 
					  b2*Points[2][1] + 
					  b3*Points[3][1] ;

			float z = b0*Points[0][2] + 
					  b1*Points[1][2] + 
					  b2*Points[2][2] + 
					  b3*Points[3][2] ;

			// specify the point
			gl.glVertex3f( x,y,z );
		}
		gl.glEnd();

		// draw the control points
		gl.glColor3f(0,1,0);
		gl.glPointSize(3);
		gl.glBegin(GL2.GL_POINTS);
		for(int i=0;i!=4;++i) {
			gl.glVertex3fv( Utilidades.makeFloatBuffer(Points[i]) );
		}
		gl.glEnd();

		// draw the hull of the curve
		gl.glColor3f(0,1,1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		for(int i=0;i!=4;++i) {
			gl.glVertex3fv( Utilidades.makeFloatBuffer(Points[i]) );
		}
		gl.glEnd();
	}
	
	
	public void dibujar(GL2 gl, GLU glu)
	{		
		/// the control points for the curve
		float Points[][] = {
			{ 10,10,0 },
			{  5,10,2 },
			{ -5,0,0 }
		};

		/// the level of detail of the curve
		int LOD=20;
		
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);

		// clear the previous transform
		gl.glLoadIdentity();

		// set the camera position
		glu.gluLookAt(	1,10,30,	//	eye pos
					0,0,0,	//	aim point
					0,1,0);	//	up direction

		
		gl.glBegin(GL2.GL_LINE_STRIP);

		// use the parametric time value 0 to 1
		for(int i=0;i!=LOD;++i) {

			float t = (float)i/(LOD-1);

			// the t value inverted
			//float it = 1.0f-t;
			
			float it = t;

			// calculate blending functions
			/*float b0 = it*it*it/6.0f;
			float b1 = (3*t*t*t - 6*t*t +4)/6.0f;
			float b2 = (-3*t*t*t +3*t*t + 3*t + 1)/6.0f;
			float b3 =  t*t*t/6.0f;*/
			
			float b0 = ( it*it - 2.0f*it + 1.0f )/2.0f;
			float b1 = (-2.0f*t*t + 2*t + 1 )/2.0f;
			float b2 = ( t*t )/2.0f;

			// sum the control points mulitplied by their respective blending functions
			float x = b0*Points[0][0] +
					  b1*Points[1][0] + 
					  b2*Points[2][0] ;

			float y = b0*Points[0][1] + 
					  b1*Points[1][1] + 
					  b2*Points[2][1] ;

			float z = b0*Points[0][2] + 
					  b1*Points[1][2] + 
					  b2*Points[2][2] ;

			// specify the point
			gl.glVertex3f( x,y,z );
		}
		gl.glEnd();

		// draw the control points
		gl.glColor3f(0,1,0);
		gl.glPointSize(3);
		gl.glBegin(GL2.GL_POINTS);
		for(int i=0;i!=3;++i) {
			gl.glVertex3fv( Utilidades.makeFloatBuffer(Points[i]) );
		}
		gl.glEnd();

		
		/*
		// draw the hull of the curve
		gl.glColor3f(0,1,1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		for(int i=0;i!=3;++i) {
			gl.glVertex3fv( Utilidades.makeFloatBuffer(Points[i]) );
		}
		gl.glEnd();*/
	}
	
}