package superficie;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import utilidades.Dibujable;
import utilidades.GLProvider;
import utilidades.Utilidades;
import utilidades.Vertice;

import com.jogamp.opengl.util.gl2.GLUT;

import curva.ICurva3D;

public class SuperficieDeBarrido implements Dibujable {
	
	private ICurva3D recorrido;	//long path
	private ICurva3D barrido;	//width path
	private ArrayList<ArrayList<Vertice>> curvas;
	
	public SuperficieDeBarrido(ICurva3D recorrido, ICurva3D barrido, int divisionesRecorrido, int divisionesBarrido) throws Exception{	
		this.curvas = new ArrayList<ArrayList<Vertice>>();
		generarGrillaPuntos(recorrido, barrido, divisionesRecorrido, divisionesBarrido);
	}
	
	public void dibujarGuias(GL2 gl, GLU glu) throws Exception 
	{
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);

		// clear the previous transform
		gl.glLoadIdentity();

		// set the camera position
		glu.gluLookAt(	1,10,30,	//	eye pos
					0,0,0,	//	aim point
					0,1,0);	//	up direction

		
		barrido.dibujar(gl, glu);
		recorrido.dibujar(gl, glu);		
	}
	
	private void generarGrillaPuntos(ICurva3D recorrido, ICurva3D barrido, float divisionesRecorrido, float divisionesBarrido) {
		float pasoRecorrido = 1.0f / (float) divisionesRecorrido;
		float pasoBarrido = 1.0f / (float) divisionesBarrido;
		float u = 0.0f; //Para el recorrido
		float u2 = 0.0f; //Para el barrido
	
		for(int i = 0; i < divisionesBarrido; i++, u2 += pasoBarrido)
		{
			u = 0;
			
			curvas.add(new ArrayList<Vertice>());
			
			for(int j = 0; j < divisionesRecorrido; j++, u += pasoRecorrido)
			{	
				Vertice v1= recorrido.getPoint(u);
				Vertice desfasaje = Utilidades.getDistancia(v1, barrido.getPoint(u2));
				curvas.get(i).add(Vertice.restar(v1, desfasaje));
			}
		}
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		dibujar(false);
	}
	
	public void dibujar(boolean invetirNormales) {
		final GL2 gl = GLProvider.getGL2();
		
		int cantidadPuntoCurvaRecorrido = curvas.get(0).size();
		
		//El calculo de las normales se puede hacer antes y tener una grilla de normales, consume mas meoria pero ahorro calculo

		for(int i = 0; i < curvas.size() - 1; i++)
		{
			ArrayList<Vertice> curva1 = curvas.get(i);
			ArrayList<Vertice> curva2 = curvas.get(i+1);
			
			for(int j = 0; j < cantidadPuntoCurvaRecorrido - 1; j++)
			{		
				
				ArrayList<Vertice> adyacentes;
				
				//Cambiar de normales por face a nomales por vetex
				
				Vertice v1 = curva1.get(j);
				Vertice v2 = curva1.get(j+1);
				Vertice v3 = curva2.get(j);
				Vertice v4 = curva2.get(j+1);
			
				
				Vertice normal1 = Utilidades.getNormalVerticeIJ(curvas, i, j);
				Vertice normal2 = Utilidades.getNormalVerticeIJ(curvas, i, j+1);
				Vertice normal3 = Utilidades.getNormalVerticeIJ(curvas, i+1, j);
				Vertice normal4 = Utilidades.getNormalVerticeIJ(curvas, i+1, j+1);
				
				if(invetirNormales) {
					normal1 = normal1.productoEscalar(-1);
					normal2 = normal2.productoEscalar(-1);
					normal3 = normal3.productoEscalar(-1);
					normal4 = normal4.productoEscalar(-1);
				}
					

			
				gl.glBegin(GL2.GL_TRIANGLE_STRIP);
					//1-Primer punto del cuadrado
					Utilidades.glNormal(normal1);
					Utilidades.glVertex(v1);
					
					//2-Segundo punto del cuadrado
					Utilidades.glNormal(normal2);
					Utilidades.glVertex(v2);
							
					//3-Tercer punto del cuadrado
					Utilidades.glNormal(normal3);
					Utilidades.glVertex(v3);
					
				gl.glEnd();
				
				gl.glBegin(GL2.GL_TRIANGLE_STRIP);
				
					//3-Tercer punto del cuadrado
					Utilidades.glNormal(normal3);
					Utilidades.glVertex(v3);
					
					//2-Segundo punto del cuadrado
					Utilidades.glNormal(normal2);
					Utilidades.glVertex(v2);

					//4-Cuarto punto del cuadrado
					Utilidades.glNormal(normal4);
					Utilidades.glVertex(v4);
				gl.glEnd();
					
			}
		}
	}
}
