package superficie;

import java.util.ArrayList;
import java.lang.Math;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import objetosEscena.Botella;
import utilidades.Dibujable;
import utilidades.GLProvider;
import utilidades.Utilidades;
import utilidades.Vertice;

import com.jogamp.opengl.util.gl2.GLUT;

import curva.BezierCubica;

public class SuperficieDeRevolucion implements Dibujable {
	
	private ArrayList<BezierCubica>listaDeCurvas;	//lista de curvas de bezier
	
	
	private static final float PASOS_DISCRETIZACION_ANGULO = 7;		//7
	private float anguloRevolucion = 360f/PASOS_DISCRETIZACION_ANGULO;	//cuanto angulo roto para dibujar una nueva curva
	private static final float PASOS_DISCRETIZACION_CURVA = 5;	// 5 cuantos puntos de cada curva de bezier tomo para dibujar la botella
	private float intervaloCurva = 1f/PASOS_DISCRETIZACION_CURVA;	//cada cuanto dibujo un vertice de la curva de bezier
	private int numCurvas;
	private float factorEscalado;
	private ArrayList<ArrayList<Vertice>> grilla;
	
	public SuperficieDeRevolucion(ArrayList<Vertice>list){	
		factorEscalado = 0.05f;
		listaDeCurvas = new ArrayList<BezierCubica>();
		
		for(int i = 0; i < list.size(); i += 4){
			ArrayList<Vertice> auxL = new ArrayList<Vertice>();
			auxL.add(list.get(i));
			auxL.add(list.get(i+1));
			auxL.add(list.get(i+2));
			auxL.add(list.get(i+3));
			BezierCubica auxB = new BezierCubica(auxL);
			listaDeCurvas.add(auxB);
		}
		
		numCurvas = listaDeCurvas.size();
		
		generarGrillaPuntos();
	}
	
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
	
		
	public float getIntCurvas(){
		return this.intervaloCurva;
	}
	
	public int getNumCurvas(){
		return this.numCurvas;
	}
	
	public ArrayList<BezierCubica> getListaCurvas(){
		return this.listaDeCurvas;
	}
	
	public double rotacionX(float x, float z,float angulo){
		return (x*Math.cos(angulo) + z*Math.sin(angulo));
		
	}
	
	public double rotacionZ(float x, float z, float angulo){
		return  (-x*Math.sin(angulo) + z*Math.cos(angulo));
	}
	
	public double rotacionXEjeZ(float x, float y, float angulo){
		return  (x*Math.cos(angulo) - y*Math.sin(angulo));
	}
	
	public double rotacionYEjeZ(float x, float y, float angulo){
		return  (x*Math.sin(angulo) + y*Math.cos(angulo));
	}
	
	public int CantPuntosControl(){
		return numCurvas*4;
	}
	
	public float getFactorEscalado(){
		return factorEscalado;
	}
	
	public Vertice getPerpendicular(Vertice vert1, Vertice vert2){
		Vertice vert3 = new Vertice(vert1.getY()*vert2.getZ() - vert2.getY()*vert1.getZ(),-vert1.getX()*vert2.getZ()+ vert1.getZ()*vert2.getX() ,vert1.getX()*vert2.getY() - vert2.getX()*vert1.getY() );
		return(vert3);
	}
	
	private Vertice rotarEnXY(Vertice v, float angulo) {
		return null;
	}
	
	private void generarGrillaPuntos() {
		grilla = new ArrayList<ArrayList<Vertice>>();

		for(int h = 0, nivel = 0; h < numCurvas; h ++){
			
  			for(float j = 0; j <= 1 ; j += intervaloCurva, nivel++){
  				grilla.add(new ArrayList<Vertice>());	
  				
  				for(float i = 0; i <= 360 ;  i+= anguloRevolucion){	
  					Vertice aux1 = new Vertice(listaDeCurvas.get(h).getX(j),listaDeCurvas.get(h).getY(j) ,0);
  					Vertice v = new Vertice((float)rotacionX(aux1.getX(),aux1.getZ(),(float)Math.PI*(i)/180), aux1.getY(), (float) (rotacionZ(aux1.getX(),aux1.getZ(), (float)Math.PI*(i)/180)));
  					
  					grilla.get(nivel).add(v);
  				}
  					
  			}  		
		}
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {	
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
		//gl.glScalef(factorEscalado, factorEscalado, factorEscalado);
		//gl.glRotatef(180, 1f, 0f, 0f);
		//gl.glTranslatef(0, Botella.altura, 0);
		for(int h = 0; h < numCurvas; h ++){
  			for(float j = 0; j <= 1 - intervaloCurva; j += intervaloCurva){
  				Vertice aux1 = new Vertice(listaDeCurvas.get(h).getX(j),listaDeCurvas.get(h).getY(j) ,0);	//10
  				Vertice aux2 = new Vertice(listaDeCurvas.get(h).getX(j + intervaloCurva),listaDeCurvas.get(h).getY(j + intervaloCurva) ,0);
  				Vertice gen1 = new Vertice(aux2.getX() - aux1.getX(), aux2.getY() - aux1.getY(), aux2.getZ() - aux1.getZ());
  				Vertice mitad = new Vertice(gen1.getX()/2, gen1.getY()/2, gen1.getZ()/2);
  				Vertice norm;
  				if(aux2.getX() < aux1.getX()){
  					 norm = new Vertice((float)rotacionX(mitad.getX(), mitad.getZ(),(float)Math.PI*(-90)/180), mitad.getY(), (float)rotacionZ(mitad.getX(), mitad.getZ(),(float)Math.PI*(-90)/180));
  				}
  				else{
  				 norm = new Vertice((float)rotacionX(mitad.getX(), mitad.getZ(),(float)Math.PI*(90)/180), mitad.getY(), (float)rotacionZ(mitad.getX(), mitad.getZ(),(float)Math.PI*(90)/180));
  				}
  				mitad.setX(mitad.getX() + aux2.getX());
  				mitad.setY(mitad.getY() + aux2.getY());
  				mitad.setZ(mitad.getZ() + aux2.getZ());
  				norm.setX(norm.getX() + mitad.getX());
  	  			norm.setY(norm.getY() + mitad.getY());
  	  			norm.setZ(norm.getZ() + mitad.getZ());
  				
  				gl.glPushMatrix();
  				gl.glBegin(GL.GL_TRIANGLES);
  				//gl.glNormal3f((float)rotacionXEjeZ(gen1.getX(), gen1.getY(),(float)Math.PI*(-90)/180), (float)rotacionYEjeZ(gen1.getX(), gen1.getY(),(float)Math.PI*(-90)/180), gen1.getZ());
  				gl.glNormal3f(norm.getX(),norm.getY(),norm.getZ());
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
  					
  					
//  					gl.glPushMatrix();
//  	  				gl.glBegin(GL.GL_LINES);
//  		  				gl.glVertex3d(0,0,0);
//  		  				//gl.glVertex3d(10,0,0);
//  		  				
//  		  				gl.glVertex3d(norm.getX()*2,norm.getY()*2,norm.getZ()*2);
//  		  				gl.glEnd();
//  					gl.glPopMatrix();
  					
  					
  					gl.glBegin(GL.GL_TRIANGLES);
  					gl.glNormal3f(norm.getX(), norm.getY(), norm.getZ());
  					// viejo_por las dudas lo dejo gl.glNormal3f((float) rotacionX(norm.getX(),norm.getZ(),(float)Math.PI*((0+i)/180)),norm.getY(),(float) rotacionZ(norm.getX(), norm.getZ(), (float)Math.PI*((0+i)/180)));
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
	/*
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glScalef(0.09f, 0.09f, 0.09f);
		for(int h = 0; h < numCurvas; h ++){
  			for(float j = 0; j <= 1 - intervaloCurva; j += intervaloCurva){
  				Vertice aux1 = new Vertice(listaDeCurvas.get(h).getX(j),listaDeCurvas.get(h).getY(j),0);
  				Vertice aux2 = new Vertice(listaDeCurvas.get(h).getX(j+intervaloCurva),listaDeCurvas.get(h).getY(j+intervaloCurva),0);
  				gl.glBegin(GL.GL_LINES);
				//gl.glVertex3d(1f,1f,1f);
				//gl.glVertex3d(2f,2f,2f);
  				gl.glVertex3d(aux1.getX(),aux1.getY(),aux1.getZ());
  				gl.glVertex3d(aux2.getX(),aux2.getY(),aux2.getZ());
				gl.glEnd();
  			}
  			gl.glBegin(GL.GL_LINES);
  			gl.glVertex3d(0f,0f,0f);
  			gl.glVertex3d(7f,1f,1f);
  			
  			gl.glVertex3d(0f,0f,0f);
  			gl.glVertex3d(0f,4f,0f);
			gl.glEnd();
		}
		
		gl.glPopMatrix();
	}*/
	
	
	public void dibujar(boolean invetirNormales) {
		final GL2 gl = GLProvider.getGL2();
		
		int cantidadPuntoCurvaRecorrido = grilla.get(0).size();
		
		//El calculo de las normales se puede hacer antes y tener una grilla de normales, consume mas meoria pero ahorro calculo

		for(int i = 0; i < grilla.size() - 1; i++)
		{
			ArrayList<Vertice> curva1 = grilla.get(i);
			ArrayList<Vertice> curva2 = grilla.get(i+1);
			
			for(int j = 0; j < cantidadPuntoCurvaRecorrido - 1; j++)
			{		
				
				ArrayList<Vertice> adyacentes;
				
				//Cambiar de normales por face a nomales por vetex
				
				Vertice v1 = curva1.get(j);
				Vertice v2 = curva1.get(j+1);
				Vertice v3 = curva2.get(j);
				Vertice v4 = curva2.get(j+1);
			
				
				Vertice normal1 = Utilidades.getNormalVerticeIJ(grilla, i, j);
				Vertice normal2 = Utilidades.getNormalVerticeIJ(grilla, i, j+1);
				Vertice normal3 = Utilidades.getNormalVerticeIJ(grilla, i+1, j);
				Vertice normal4 = Utilidades.getNormalVerticeIJ(grilla, i+1, j+1);
				
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
