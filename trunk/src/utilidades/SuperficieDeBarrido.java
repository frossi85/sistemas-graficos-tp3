package utilidades;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

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
				Vertice desfasaje = getDistanciaNormalizada(v1, barrido.getPoint(u2));
				curvas.get(i).add(Vertice.restar(v1, desfasaje));
			}
		}
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		int cantidadPuntoCurvaRecorrido = curvas.get(0).size();
		
		//FALTAAAAAAAAAAAAAAAAAAAAAAAAAAAA NORMALESSSSSSSSSSSS
		//Falta el calculo de NORMALES

		for(int i = 0; i < curvas.size() - 1; i++)
		{
			ArrayList<Vertice> curva1 = curvas.get(i);
			ArrayList<Vertice> curva2 = curvas.get(i+1);
			
			for(int j = 0; j < cantidadPuntoCurvaRecorrido - 1; j++)
			{					
				
					
					//Cambiar de normales por face a nomales por vetex
				
					Vertice p1 = curva1.get(j);
					
					Vertice p2 = curva1.get(j+1);
					
					Vertice p3 = curva2.get(j);
					
					Vertice p4 = curva2.get(j+1);
					
					Vertice v1 = Vertice.restar(p2, p1);
					Vertice v2 = Vertice.restar(p3, p1);
					Vertice v3 = Vertice.restar(p2, p4);
					Vertice v4 = Vertice.restar(p3, p4);
							
					Vertice normalFace1 = Vertice.productoVectorial(v1, v2);
					Vertice normalFace2 = Vertice.productoVectorial(v3, v4);
						
					gl.glBegin(GL2.GL_TRIANGLE_STRIP);
						//1-Primer punto del cuadrado
						Utilidades.glNormal(normalFace1);
						Utilidades.glVertex(p1);
						
						//2-Segundo punto del cuadrado
						Utilidades.glNormal(normalFace1);
						Utilidades.glVertex(p2);
								
						
						//3-Tercer punto del cuadrado
						Utilidades.glNormal(normalFace1);
						Utilidades.glVertex(p3);
						
					gl.glEnd();
					
					gl.glBegin(GL2.GL_TRIANGLE_STRIP);
					
						//3-Tercer punto del cuadrado
						Utilidades.glNormal(normalFace1);
						Utilidades.glVertex(p3);
						
						//2-Segundo punto del cuadrado
						Utilidades.glNormal(normalFace1);
						Utilidades.glVertex(p2);

						//4-Cuarto punto del cuadrado
						Utilidades.glNormal(normalFace2);
						Utilidades.glVertex(p4);
					gl.glEnd();
					
			}
		}
	}
	
	private Vertice getDistanciaNormalizada(Vertice v1, Vertice v2) {
		float x = getDistanciaNormalizada(v1.getX(), v2.getX());
		float y = getDistanciaNormalizada(v1.getY(), v2.getY());
		float z = getDistanciaNormalizada(v1.getZ(), v2.getZ());
		
		return new Vertice(x, y, z);
	}
	
	private float getDistanciaNormalizada(float x1, float x2)
	{
		return (x2 - x1);
	}
}
