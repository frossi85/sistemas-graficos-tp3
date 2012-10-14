package objetosEscena;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.text.html.HTMLDocument.Iterator;

import utilidades.Animable;
import utilidades.BSplineGenerica;
import utilidades.Dibujable;
import utilidades.ICurva3D;
import utilidades.LineaProduccion;
import utilidades.LineaRecta;
import utilidades.PuntoDeControl;
import utilidades.SuperficieDeBarrido;
import utilidades.Vertice;

public class CintaTransportadora extends Observable implements Dibujable,Animable {

	private int capacidadBotellas;
	Queue<Botella> botellas;
	private float velocidadCinta;
	private float avanceBotellas;
	private boolean avanzando = true;
	private float alto = 0.75f;
	private float ancho = 0.5f;
	private SuperficieDeBarrido caraSuperiorInferior;
	private SuperficieDeBarrido caraLaterales;
	
	private SuperficieDeBarrido caraPrueba;
	
	
	public CintaTransportadora(int capacidadBotellas, LineaProduccion linea,float velCinta, float avanceBotellas){
		this.capacidadBotellas = capacidadBotellas;  //cant max de botellas que puede tener la cinta transportadora
		this.velocidadCinta = velCinta;
		this.avanceBotellas = avanceBotellas;
		this.botellas = new LinkedList<Botella>();
		addObserver(linea);
		
		//Lo necesario para dibujarla
		ArrayList<PuntoDeControl> puntos = new ArrayList<PuntoDeControl>();
		PuntoDeControl puntoInicial = new PuntoDeControl(0f, 0f, 0f);
		
		puntos.add(puntoInicial);
		puntos.add(new PuntoDeControl(0.25f, 0f, 0f));
		puntos.add(new PuntoDeControl(0.75f, 1f, 0f));
		puntos.add(new PuntoDeControl(2.75f, 1f, 0f));
		puntos.add(new PuntoDeControl(3.25f, 0f, 0f));
		puntos.add(new PuntoDeControl(4f, 0f, 0f));
		
		ArrayList<PuntoDeControl> puntos2 = new ArrayList<PuntoDeControl>();
		
		puntos2.add(puntoInicial);
		puntos2.add(new PuntoDeControl(0f, 0f, 0.25f));
		puntos2.add(new PuntoDeControl(0f, 0.25f, 0.5f));
		puntos2.add(new PuntoDeControl(0f, 0.5f, 0.5f));
		puntos2.add(new PuntoDeControl(0f, 0.75f, 0.25f));
		puntos2.add(new PuntoDeControl(0f, 1f, 0f));
		
		
		
		
		try {
			ICurva3D spline = new BSplineGenerica(puntos);
			ICurva3D spline2 = new BSplineGenerica(puntos2);
			caraSuperiorInferior = new SuperficieDeBarrido(spline, new LineaRecta(puntoInicial, new PuntoDeControl(0f, ancho, 0f)) , 50, 50);
			caraLaterales = new SuperficieDeBarrido(spline, new LineaRecta(puntoInicial, new PuntoDeControl(0f, 0f, alto)) , 50, 50);
			caraPrueba = new SuperficieDeBarrido(spline, spline2, 50, 50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void recibirBotella(Botella botella){ 
		if(!this.estaLlenaDeBotellas()){
			Vertice vert = new Vertice(avanceBotellas,0f,0f);
			botella.setPosicion(vert);
			botellas.add(botella);
		}	
	}
	
	public Botella entregarBotella(){
		return botellas.poll();
	}
	
	public void avanzarCinta(){
		java.util.Iterator<Botella> it =  this.botellas.iterator();
		while(it.hasNext()){
			Botella bot = it.next();
			avanzarBotella(bot);
		}
		//System.out.println("se movio cinta ");	
		if(this.botellas.peek() != null){
		//System.out.println("pos de primer botella: " + this.botellas.peek().getPosicion().getX());
		}
		}
	
	public void avanzarBotella(Botella botella){	// por ahora solo avanza en coord x
		Vertice ver = botella.getPosicion();
		ver.setX(ver.getX() + this.avanceBotellas);
		botella.setPosicion(ver);
	}
	
	public void setVelocidad(float vel){}
	public void detenerCinta(){this.avanzando = false;} // detiene cinta
	public void activarCinta(){this.avanzando = true;}
	public boolean estaAvanzando(){return this.avanzando;}
	public float getVelocidad(){return 0.0f;}
	
	public boolean estaLlenaDeBotellas(){
		if(this.botellas.size() == this.capacidadBotellas)
			return true;
		else
			return false;
		
	}
	
	public boolean buscarPosicion(float posicion ){	// busca si existe una botella en una posicion dada de la cinta
		//for(int i = 0; i < this.botellas.size(); i++){
			java.util.Iterator<Botella> it =  this.botellas.iterator();
			while(it.hasNext()){
				Botella bot = it.next();
				float diff =  bot.getPosicion().getX() - posicion;
				//System.out.println("diff es: " + diff );
				if((diff <= avanceBotellas) && (diff >= 0f)){
					//System.out.println("diff es: " + diff );
					return true;
				}	
			}
		//}
		return false;
	}
	
	public int getNumeroDeBotellas(){
		return this.botellas.size();
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo cinta transportadora");
		final GL2 gl = gLDrawable.getGL().getGL2();
//		gl.glPushMatrix();
//  			gl.glColor3d(1.0f, 0.0f, 0.0f);
//  			gl.glBegin(GL.GL_LINE_STRIP);
//  			gl.glVertex3f(0f, ancho/2f, 0f);
//  			gl.glVertex3f(largo, ancho/2f, 0f);
//  			gl.glVertex3f(largo, ancho/2f, 0f);
//  			gl.glVertex3f(largo, -ancho/2f, 0f);
//  			gl.glVertex3f(largo, -ancho/2f, 0f);
//  			gl.glVertex3f(0f, -ancho/2f, 0f);
//  			gl.glVertex3f(0f, -ancho/2f, 0f);
//  			gl.glVertex3f(0f, ancho/2f, 0f);
//  			
//  			gl.glEnd();
//  		gl.glPopMatrix();
		
		//Para optimizar el dibujado puedo crear una display list y luego llamarla
		
//		gl.glPushMatrix();
//			caraSuperiorInferior.dibujar(gLDrawable);
//		gl.glPopMatrix();
//		
//		gl.glPushMatrix();
//			gl.glTranslatef(0f, 0f, alto);
//			caraSuperiorInferior.dibujar(gLDrawable);
//			gl.glPopMatrix();
//		
//		gl.glPushMatrix();
//			caraLaterales.dibujar(gLDrawable);
//		gl.glPopMatrix();
//		
//		gl.glPushMatrix();
//			gl.glTranslatef(0f, ancho, 0f);
//			caraLaterales.dibujar(gLDrawable);
//		gl.glPopMatrix();
		
		
		gl.glPushMatrix();
			caraPrueba.dibujar(gLDrawable);
		gl.glPopMatrix();
		  		
  		
		java.util.Iterator<Botella> it =  this.botellas.iterator();
		while(it.hasNext()){
				it.next().dibujar(gLDrawable);
			}
		
  		gl.glFlush();
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
