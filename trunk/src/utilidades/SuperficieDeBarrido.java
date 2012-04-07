package utilidades;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class SuperficieDeBarrido implements Dibujable {
	
	private ArrayList<BSplineCuadratica>listaDeCurvas;	//lista de curvas de bezier
	
	
	private static final float PASOS_DISCRETIZACION_ANGULO = 7;		
	private float anguloRevolucion = 360f/PASOS_DISCRETIZACION_ANGULO;	//cuanto angulo roto para dibujar una nueva curva
	private static final float PASOS_DISCRETIZACION_CURVA = 5;	//cuantos puntos de cada curva de bezier tomo para dibujar la botella
	private float intervaloCurva = 1f/PASOS_DISCRETIZACION_CURVA;	//cada cuanto dibujo un vertice de la curva de bezier
	private int numCurvas;
	
	public SuperficieDeBarrido(ArrayList<PuntoDeControl> puntosDeControl){	

		listaDeCurvas = new ArrayList<BSplineCuadratica>();
		
		for(int i = 0; i < puntosDeControl.size() - 2; i ++){
			ArrayList<PuntoDeControl> auxL = new ArrayList<PuntoDeControl>();
			auxL.add(puntosDeControl.get(i));
			auxL.add(puntosDeControl.get(i+1));
			auxL.add(puntosDeControl.get(i+2));
			BSplineCuadratica auxB = new BSplineCuadratica(auxL, 20);
			listaDeCurvas.add(auxB);
		}	
		
		numCurvas = listaDeCurvas.size();
	}
	
	public void dibujar(GL2 gl, GLU glu) 
	{
		ArrayList<utilidades.PuntoDeControl> puntosDeControl = new ArrayList<utilidades.PuntoDeControl>();
		
		puntosDeControl.add(new PuntoDeControl(10f ,10f, 0f));
		puntosDeControl.add(new PuntoDeControl(5f, 10f, 2f));
		puntosDeControl.add(new PuntoDeControl(-5f, 0f, 0f));
		puntosDeControl.add(new PuntoDeControl(-10f, 5f, -2f));
		puntosDeControl.add(new PuntoDeControl(-15f, 2f, -2));	
		
		listaDeCurvas = new ArrayList<BSplineCuadratica>();
		
		for(int i = 0; i < puntosDeControl.size() - 2; i ++){
			ArrayList<PuntoDeControl> auxL = new ArrayList<PuntoDeControl>();
			auxL.add(puntosDeControl.get(i));
			auxL.add(puntosDeControl.get(i+1));
			auxL.add(puntosDeControl.get(i+2));
			BSplineCuadratica auxB = new BSplineCuadratica(auxL, 20);
			listaDeCurvas.add(auxB);
		}	

		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);

		// clear the previous transform
		gl.glLoadIdentity();

		// set the camera position
		glu.gluLookAt(	1,10,30,	//	eye pos
					0,0,0,	//	aim point
					0,1,0);	//	up direction

		
		for(int i = 0; i < listaDeCurvas.size(); i ++){
			listaDeCurvas.get(i).dibujar2(gl, glu);			
		}
		
		
	}
	
	public void dibujarBspline(GL2 gl, GLU glu)
	{
		ArrayList<utilidades.PuntoDeControl> puntosDeControl = new ArrayList<utilidades.PuntoDeControl>();
		
		puntosDeControl.add(new PuntoDeControl(10f ,10f, 0f));
		puntosDeControl.add(new PuntoDeControl(5f, 10f, 2f));
		puntosDeControl.add(new PuntoDeControl(-5f, 0f, 0f));
		puntosDeControl.add(new PuntoDeControl(-10f, 5f, -2f));
		puntosDeControl.add(new PuntoDeControl(-15f, 2f, -2));	
		
		listaDeCurvas = new ArrayList<BSplineCuadratica>();
		
		for(int i = 0; i < puntosDeControl.size() - 2; i ++){
			ArrayList<PuntoDeControl> auxL = new ArrayList<PuntoDeControl>();
			auxL.add(puntosDeControl.get(i));
			auxL.add(puntosDeControl.get(i+1));
			auxL.add(puntosDeControl.get(i+2));
			BSplineCuadratica auxB = new BSplineCuadratica(auxL, 20);
			listaDeCurvas.add(auxB);
		}	

		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);

		// clear the previous transform
		gl.glLoadIdentity();

		// set the camera position
		glu.gluLookAt(	1,10,30,	//	eye pos
					0,0,0,	//	aim point
					0,1,0);	//	up direction

		
		for(int i = 0; i < listaDeCurvas.size(); i ++){
			listaDeCurvas.get(i).dibujar2(gl, glu);			
		}
	}

//	@Override
//	public void dibujar() {
//		// TODO Auto-generated method stub
//		
//	}
	
	public float getX(float u){
		float valor = u;
		double piso = Math.floor(valor);
		return(listaDeCurvas.get((int) piso).getX((float) (valor - piso)));
	}
	
	public float getY(float u){
		float valor = u;
		double piso = Math.floor(valor);
		return(listaDeCurvas.get((int) piso).getY((float) (valor - piso)));
	}
		
	public double rotacionX(float x, float z,float angulo){
		return (x*Math.cos(angulo) + z*Math.sin(angulo));
		
	}
	
	public double rotacionZ(float x, float z, float angulo){
		return  (-x*Math.sin(angulo) + z*Math.cos(angulo));
	}
	
	public int CantPuntosControl(){
		return numCurvas*4;
	}
	
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glScalef(0.04f, 0.04f, 0.04f);
		
		
		int divisionesCurva = 20;
		int divisionesBarrido = 1;
		float anchoBarrido = 1.0f;
		
		float posicionBarrido = 0.0f;
		float pasoDelBarrido = anchoBarrido / (float) divisionesBarrido;
		
		
		//Vamos a hacer el barrido a lo largo de y
		//1 - Por cada division a lo largo de y del barrido
		for(int i = 0; i < divisionesBarrido - 1; i++)
		{
			posicionBarrido += pasoDelBarrido;
			
			gl.glBegin(GL2.GL_LINE_STRIP);
			
			//2 - Por cada curva, hacer:
			for(int j = 0; j < listaDeCurvas.size(); j++)
			{
				//3 - Por cada division de la curva hacer:
				for(int k = 0; k < divisionesCurva; k++)
				{	
					float u = (float)k/(divisionesCurva - 1);
					
					/*gl.glVertex3f(listaDeCurvas.get(j).getX(u), 
							  listaDeCurvas.get(j).getY(u) + pasoDelBarrido,
							  listaDeCurvas.get(j).getZ(u));*/
					
					gl.glVertex3f(listaDeCurvas.get(j).getX(u), 
								  listaDeCurvas.get(j).getY(u),
								  listaDeCurvas.get(j).getZ(u));
				}
			}
			gl.glEnd();
		}
	}
}
