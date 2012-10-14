package utilidades;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class SuperficieDeBarridoMejorada implements Dibujable {
	
	private ICurva3D recorrido;	//long path
	private ICurva3D barrido;	//width path
	private int divisionesRecorrido;
	private int divisionesBarrido;
	
	public SuperficieDeBarridoMejorada(ICurva3D recorrido, ICurva3D barrido, int divisionesRecorrido, int divisionesBarrido) throws Exception{	

		this.barrido = barrido;
		this.recorrido = recorrido;
		this.divisionesBarrido = divisionesBarrido;
		this.divisionesRecorrido = divisionesRecorrido;
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
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
		//gl.glScalef(0.04f, 0.04f, 0.04f);
		
		float posicionBarrido = 0.0f;
		
//		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT|GL2.GL_COLOR_BUFFER_BIT);
//
//		// clear the previous transform
//		gl.glLoadIdentity();
//
//		// set the camera position
//		GLU glu = new GLU();
//		glu.gluLookAt(	1,10,30,	//	eye pos
//					0,0,0,	//	aim point
//					0,1,0);	//	up direction
		
		float pasoRecorrido = 1.0f / (float) divisionesRecorrido;
		float pasoBarrido = 1.0f / (float) divisionesBarrido;
		float u = 0.0f; //Para el recorrido
		float u2 = 0.0f; //Para el barrido
		
		//FALTAAAAAAAAAAAAAAAAAAAAAAAAAAAA NORMALESSSSSSSSSSSS
		//Falta el calculo de NORMALES

		for(int i = 0; i < divisionesBarrido; i++, u2 += pasoBarrido, u = 0)
		{
			
			float u2Siguiente = u2 + pasoBarrido;
			
			
			for(int j = 0; j < divisionesRecorrido; j++, u += pasoRecorrido)
			{					
					float uSiguiente = u + pasoRecorrido;
					
					float xU = recorrido.getX(u);
					float yU = recorrido.getY(u);
					float zU = recorrido.getZ(u);
					
					float xUSiguiente = recorrido.getX(uSiguiente);
					float yUSiguiente = recorrido.getY(uSiguiente);
					float zUSiguiente = recorrido.getZ(uSiguiente);
					
					float xU2 = barrido.getX(u2);
					float yU2 = barrido.getY(u2);
					float zU2 = barrido.getZ(u2);
					
					float xU2Siguiente = barrido.getX(u2Siguiente);
					float yU2Siguiente = barrido.getY(u2Siguiente);
					float zU2Siguiente = barrido.getZ(u2Siguiente);
					
					gl.glBegin(GL2.GL_TRIANGLE_STRIP);
						//1-Primer punto del cuadrado
						gl.glVertex3f(xU + getDistanciaNormalizada(xU, xU2), 
									  yU + getDistanciaNormalizada(yU, yU2),
									  zU + getDistanciaNormalizada(zU, zU2));
						
						//2-Segundo punto del cuadrado
						gl.glVertex3f(xUSiguiente + getDistanciaNormalizada(xUSiguiente, xU2),
									  yUSiguiente + getDistanciaNormalizada(yUSiguiente, yU2),
									  zUSiguiente + getDistanciaNormalizada(zUSiguiente, zU2));
								
						
						//3-Tercer punto del cuadrado
						gl.glVertex3f(xU + getDistanciaNormalizada(xU, xU2Siguiente), 
								      yU + getDistanciaNormalizada(yU, yU2Siguiente),
								      zU + getDistanciaNormalizada(zU, zU2Siguiente));

						//4-Cuarto punto del cuadrado
						gl.glVertex3f(xUSiguiente + getDistanciaNormalizada(xUSiguiente, xU2Siguiente),
								  	  yUSiguiente + getDistanciaNormalizada(yUSiguiente, yU2Siguiente),
							  	  	  zUSiguiente + getDistanciaNormalizada(zUSiguiente, zU2Siguiente));
					gl.glEnd();
					
			}
		}
	}
	
	private float getDistanciaNormalizada(float x1, float x2)
	{
		return ((Math.abs(x2) > 0) ? (x2 - x1): 0);
	}
}
