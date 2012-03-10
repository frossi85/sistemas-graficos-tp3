package utilidades;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

public class SuperficieDeRevolucion implements Dibujable {
	
	private ArrayList<BezierCubica>listaDeCurvas;	//lista de curvas de bezier
	
	
	private static final float PASOS_DISCRETIZACION_ANGULO = 4;		//cuanto angulo roto por paso
	private Vertice ejeRotacion = new Vertice(0f,0f,1f);
	private float anguloRevolucion = 360f/PASOS_DISCRETIZACION_ANGULO;
	private static final float PASOS_DISCRETIZACION_CURVA = 6;
	private float intervaloCurva = 1f/PASOS_DISCRETIZACION_CURVA;	//cada cuanto dibujo un vertice de la curva
	private GLUT glut = new GLUT();
	private int numCurvas;
	
	public SuperficieDeRevolucion(ArrayList<PuntoDeControl>list){	
		/*this.curva1 = new BezierCubica(list);
		this.curva2 = new BezierCubica(list);
		curva2.setPuntoDeControl(0, list.get(1));
		curva2.setPuntoDeControl(1, list.get(2));
		curva2.setPuntoDeControl(2, list.get(3));
		curva2.setPuntoDeControl(3, list.get(0));
		this.curva3 = new BezierCubica(list);
		curva3.setPuntoDeControl(0, list.get(2));
		curva3.setPuntoDeControl(1, list.get(3));
		curva3.setPuntoDeControl(2, list.get(0));
		curva3.setPuntoDeControl(3, list.get(1));
		this.curva4 = new BezierCubica(list);
		curva4.setPuntoDeControl(0, list.get(3));
		curva4.setPuntoDeControl(1, list.get(0));
		curva4.setPuntoDeControl(2, list.get(1));
		curva4.setPuntoDeControl(3, list.get(2));
		*/
		listaDeCurvas = new ArrayList<BezierCubica>();
		
		for(int i = 0; i < list.size(); i += 4){
			ArrayList<PuntoDeControl> auxL = new ArrayList<PuntoDeControl>();
			auxL.add(list.get(i));
			auxL.add(list.get(i+1));
			auxL.add(list.get(i+2));
			auxL.add(list.get(i+3));
			BezierCubica auxB = new BezierCubica(auxL);
			listaDeCurvas.add(auxB);
		}
		
		numCurvas = listaDeCurvas.size();
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
		
	public float rotacionX(float x, float y,float angulo){
		return (float) (x*Math.cos(angulo) - y*Math.sin(angulo));
		
	}
	
	public float rotacionY(float x, float y, float angulo){
		return (float) (x*Math.sin(angulo) + y*Math.cos(angulo));
	}
	
	public int CantPuntosControl(){
		return numCurvas*4;
	}
	
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
			//gl.glBegin(GL.GL_TRIANGLES);
  			/*for(float i = 0; i <= 360f - anguloRevolucion; i+= anguloRevolucion){
  				for(float j = 0; j <= 1 - intervaloCurva; j += intervaloCurva){
  					Vertice aux1 = new Vertice(rotacionX(curva.getX(j),curva.getY(j),i),rotacionY(curva.getX(j),curva.getY(j),i),0);
  					Vertice aux2 = new Vertice(rotacionX(aux1.getX(),aux1.getY(),i + anguloRevolucion),rotacionY(aux1.getX(),aux1.getY(), i+anguloRevolucion ),0);
  					Vertice aux3 = new Vertice(rotacionX(curva.getX(j + intervaloCurva),curva.getY(j + intervaloCurva),i),rotacionY(curva.getX(j + intervaloCurva),curva.getY(j + intervaloCurva),i),0);
  					Vertice aux4 = new Vertice(rotacionX(aux3.getX(),aux3.getY(),i + anguloRevolucion), rotacionY(aux3.getX(),aux3.getY(),i + anguloRevolucion), 0);
  					
  					gl.glVertex3f(aux3.getX(),aux3.getY(),aux3.getZ());
  					gl.glVertex3f(aux2.getX(),aux2.getY(),aux2.getZ());
  					gl.glVertex3f(aux1.getX(),aux1.getY(),aux1.getZ());
  					
  					gl.glVertex3f(aux3.getX(),aux3.getY(),aux3.getZ());
  					gl.glVertex3f(aux4.getX(),aux4.getY(),aux4.getZ());
  					gl.glVertex3f(aux2.getX(),aux2.getY(),aux2.getZ());
  					
  				}
  			}
  			*/
		gl.glScalef(0.6f, 0.6f, 0.6f);
		gl.glBegin(GL.GL_LINE_STRIP);
				
				gl.glVertex3f(listaDeCurvas.get(0).getX(0f),listaDeCurvas.get(0).getY(0f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.1f),listaDeCurvas.get(0).getY(0.1f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.3f),listaDeCurvas.get(0).getY(0.3f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.4f),listaDeCurvas.get(0).getY(0.4f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.5f),listaDeCurvas.get(0).getY(0.5f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.75f),listaDeCurvas.get(0).getY(0.75f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(0.80f),listaDeCurvas.get(0).getY(0.80f),0);
				gl.glVertex3f(listaDeCurvas.get(0).getX(1f),listaDeCurvas.get(0).getY(1f),0);
				/*
				gl.glVertex3f(listaDeCurvas.get(1).getX(0f),listaDeCurvas.get(1).getY(0f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.1f),listaDeCurvas.get(1).getY(0.1f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.3f),listaDeCurvas.get(1).getY(0.3f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.4f),listaDeCurvas.get(1).getY(0.4f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.5f),listaDeCurvas.get(1).getY(0.5f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.75f),listaDeCurvas.get(1).getY(0.75f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(0.80f),listaDeCurvas.get(1).getY(0.80f),0);
				gl.glVertex3f(listaDeCurvas.get(1).getX(1f),listaDeCurvas.get(1).getY(1f),0);
				
				gl.glVertex3f(listaDeCurvas.get(2).getX(0f),listaDeCurvas.get(2).getY(0f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.1f),listaDeCurvas.get(2).getY(0.1f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.3f),listaDeCurvas.get(2).getY(0.3f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.4f),listaDeCurvas.get(2).getY(0.4f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.5f),listaDeCurvas.get(2).getY(0.5f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.75f),listaDeCurvas.get(2).getY(0.75f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(0.80f),listaDeCurvas.get(2).getY(0.80f),0);
				gl.glVertex3f(listaDeCurvas.get(2).getX(1f),listaDeCurvas.get(2).getY(1f),0);
				
				gl.glVertex3f(listaDeCurvas.get(3).getX(0f),listaDeCurvas.get(3).getY(0f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.1f),listaDeCurvas.get(3).getY(0.1f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.3f),listaDeCurvas.get(3).getY(0.3f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.4f),listaDeCurvas.get(3).getY(0.4f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.5f),listaDeCurvas.get(3).getY(0.5f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.75f),listaDeCurvas.get(3).getY(0.75f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(0.80f),listaDeCurvas.get(3).getY(0.80f),0);
				gl.glVertex3f(listaDeCurvas.get(3).getX(1f),listaDeCurvas.get(3).getY(1f),0);
				
				*/
  			gl.glEnd();
  		gl.glPopMatrix();	
		
	}
	
	
	
}
