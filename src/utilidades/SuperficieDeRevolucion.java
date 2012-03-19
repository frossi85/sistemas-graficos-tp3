package utilidades;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

public class SuperficieDeRevolucion implements Dibujable {
	
	private ArrayList<BezierCubica>listaDeCurvas;	//lista de curvas de bezier
	
	
	private static final float PASOS_DISCRETIZACION_ANGULO = 7;		
	private float anguloRevolucion = 360f/PASOS_DISCRETIZACION_ANGULO;	//cuanto angulo roto para dibujar una nueva curva
	private static final float PASOS_DISCRETIZACION_CURVA = 5;	//cuantos puntos de cada curva de bezier tomo para dibujar la botella
	private float intervaloCurva = 1f/PASOS_DISCRETIZACION_CURVA;	//cada cuanto dibujo un vertice de la curva de bezier
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
		for(int h = 0; h < numCurvas; h ++){
  			for(float j = 0; j <= 1 - intervaloCurva; j += intervaloCurva){
  				Vertice aux1 = new Vertice(listaDeCurvas.get(h).getX(j),listaDeCurvas.get(h).getY(j),0);	
  				Vertice aux2 = new Vertice(listaDeCurvas.get(h).getX(j + intervaloCurva),listaDeCurvas.get(h).getY(j + intervaloCurva),0);
  			
  				gl.glPushMatrix();
  				gl.glBegin(GL.GL_TRIANGLES);
  			
  				gl.glVertex3d(aux1.getX(),aux1.getY(),aux1.getZ());
  				gl.glVertex3d(aux2.getX(),aux2.getY(),aux2.getZ());
  				gl.glVertex3d(rotacionX(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180),aux1.getY(), rotacionZ(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180));
  			
  				gl.glVertex3d(aux2.getX(),aux2.getY(),aux2.getZ());
  				gl.glVertex3d(rotacionX(aux2.getX(),aux2.getZ(),(float)Math.PI*((0+anguloRevolucion)/180)),aux2.getY(), rotacionZ(aux2.getX(),aux2.getZ(),(float)Math.PI*((0+anguloRevolucion)/180)));
  				gl.glVertex3d(rotacionX(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180),aux1.getY(), rotacionZ(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180));
  					
  				gl.glEnd();
  				gl.glPopMatrix();
  					
  				for(float i = anguloRevolucion; i <= 360 ;  i+= anguloRevolucion){	
  					gl.glPushMatrix();
  					gl.glRotatef(i, 0, 1, 0);
  					gl.glBegin(GL.GL_TRIANGLES);
  			
  					gl.glVertex3d(aux1.getX(),aux1.getY(),aux1.getZ());
  					gl.glVertex3d(aux2.getX(),aux2.getY(),aux2.getZ());
  					gl.glVertex3d(rotacionX(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180),aux1.getY(), rotacionZ(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180));
  					
  					gl.glVertex3d(aux2.getX(),aux2.getY(),aux2.getZ());
  					gl.glVertex3d(rotacionX(aux2.getX(),aux2.getZ(),(float)Math.PI*((0+anguloRevolucion)/180)),aux2.getY(), rotacionZ(aux2.getX(),aux2.getZ(),(float)Math.PI*((0+anguloRevolucion)/180)));
  					gl.glVertex3d(rotacionX(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180),aux1.getY(), rotacionZ(aux1.getX(),aux1.getZ(),(float)Math.PI*(0+anguloRevolucion)/180));
  				
  					gl.glEnd();
  					gl.glPopMatrix();
  				}
  					
  			}
  		}
  			
  		gl.glPopMatrix();
	}
}
