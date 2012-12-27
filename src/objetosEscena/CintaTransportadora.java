package objetosEscena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Queue;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.text.html.HTMLDocument.Iterator;

import curva.BSplineGenerica;
import curva.ICurva3D;
import curva.LineaRecta;

import superficie.SuperficieDeBarrido;
import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.GLProvider;
import utilidades.LineaProduccion;
import utilidades.Vertice;
import utilidades.Vertice;

public class CintaTransportadora extends Observable implements Dibujable,Animable {

	private int capacidadBotellas;	
	Queue<Botella> botellas;
	private float velocidadCinta;
	private float avanceBotellas; // representa el delta u de la curva cuando avance la botella
	private boolean avanzando = true;
	private float alto = 750f;
	private float ancho = 800f;
	private SuperficieDeBarrido caraSuperior;
	private SuperficieDeBarrido caraLaterales;
	private ICurva3D pathDeBotellas;
	private float u = 0;	
	private HashMap<Botella, Float> hash;	// Relaciona cada botella en la cinta con su propio u del path.
	private Vertice posicion;
	
	
	private SuperficieDeBarrido borde;
	private float scala = 0.001f;
	
	
	public CintaTransportadora(int capacidadBotellas, LineaProduccion linea,float velCinta, float avanceBotellas){
		this.capacidadBotellas = capacidadBotellas;  //cant max de botellas que puede tener la cinta transportadora
		this.velocidadCinta = velCinta;
		this.avanceBotellas = avanceBotellas;
		this.botellas = new LinkedList<Botella>();
		posicion = new Vertice(0, 0, 0);
		hash = new HashMap<Botella, Float>();		
		addObserver(linea);
			
		//Lo necesario para dibujarla
		ArrayList<Vertice> puntos = new ArrayList<Vertice>();
		Vertice puntoInicial = new Vertice(0f, 0f, 0f);
		
		//Los puntos de control se obtuvieron con Inkscape, un programa de dibujo vecorial pero se necesita escalarlos
		puntos.add(puntoInicial);
		puntos.add(new Vertice(30.926f, -0.055f, 0));
		puntos.add(new Vertice(91.916f, 10.143f, 0));
		puntos.add(new Vertice(134.774f, 53.001f, 0));
		puntos.add(new Vertice(180.488f, 121.572f, 0));
		puntos.add(new Vertice(267.021f, 162.879f, 0));
		puntos.add(new Vertice(328.208f, 164.435f, 0));
		puntos.add(new Vertice(439.611f, 160.796f, 0));
		puntos.add(new Vertice(500.352f, 126.245f, 0));
		puntos.add(new Vertice(523.209f, -10.898f, 0));
		puntos.add(new Vertice(588.011f, -82.337f, 0));
		puntos.add(new Vertice(647.883f, -88.572f, 0));
		puntos.add(new Vertice(691.781f, -86.224f, 0));
		
		
		
		ArrayList<Vertice> puntos2 = new ArrayList<Vertice>();
		
		puntos2.add(puntoInicial);
		puntos2.add(new Vertice(0, -60.374f, 63.497f));
		puntos2.add(new Vertice(0, -59.333f, 67.661f));
		puntos2.add(new Vertice(0, -40.596f, 106.175f));
		puntos2.add(new Vertice(0, 34.351f, 110.339f));
		puntos2.add(new Vertice(0, 57.252f, 74.947f));
		puntos2.add(new Vertice(0, 35.392f, 16.655f));
		puntos2.add(new Vertice(0, -42.815f, 19.671f));
		puntos2.add(new Vertice(0, -57.251f, 42.679f));
		puntos2.add(new Vertice(0, -60.374f, 63.497f));
		puntos2.add(new Vertice(0, -59.333f, 67.661f));

			
		
		try {
			ICurva3D spline = new BSplineGenerica(puntos).escalar(5*scala, scala, scala);
			pathDeBotellas = spline;
			ICurva3D spline2 = new BSplineGenerica(puntos2).escalar(5*scala, scala, scala);
			ICurva3D lineaRecta = new LineaRecta(puntoInicial, new Vertice(0f, 0f, alto)).escalar(5*scala, scala, scala);
			ICurva3D lineaRecta2 = new LineaRecta(puntoInicial, new Vertice(0f, ancho, 0f)).escalar(5*scala, scala, scala);
			caraSuperior = new SuperficieDeBarrido(spline, lineaRecta2, 50, 50);
			caraLaterales = new SuperficieDeBarrido(spline, lineaRecta, 100, 50);
			borde = new SuperficieDeBarrido(spline, spline2, 100, 50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void recibirBotella(Botella botella){ 
		if(!this.estaLlenaDeBotellas()){
			Vertice vert = new Vertice(this.getReccorido().getX(0f), this.getReccorido().getY(0f), this.getReccorido().getZ(0f));
			botella.setPosicion(vert);  			
			botellas.add(botella);
			hash.put(botella, new Float(0));
		}	
	}
	
	public float getAvanceBotellas(){
		return this.avanceBotellas;
	}
	
	public Botella entregarBotella(){
		return botellas.poll();
	}
	
	public void avanzarCinta(){
		java.util.Iterator<Botella> it =  this.botellas.iterator();		
		while(it.hasNext()){
			Botella bot = it.next();
			if( this.avanceBotellas + hash.get(bot) >= 1f){ 
				entregarBotella(); 
				it = botellas.iterator();
				continue;
			}
			hash.put(bot, this.avanceBotellas + hash.get(bot));			 
			avanzarBotella(bot);			
		}		
		//if(this.botellas.peek() != null){
		//System.out.println("pos de primer botella: " + this.botellas.peek().getPosicion().getX());
		
		}
	
	private void avanzarBotella(Botella botella){	
		Vertice vert = new Vertice(getReccorido().getX(hash.get(botella)), getReccorido().getY(hash.get(botella)), getReccorido().getZ(hash.get(botella)));     	
		botella.setPosicion(vert);		
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
		return false;
	}
	
	public int getNumeroDeBotellas(){
		return this.botellas.size();
	}
	
	public ICurva3D getReccorido() {
		return pathDeBotellas;
	}
	
	public CintaTransportadora setPosicion(Vertice p) {
		posicion = p;	
		return this;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		//Para optimizar el dibujado puedo crear una display list y luego llamarla (Se puede y luego usar shader??))
		
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), this.posicion.getY(), this.posicion.getZ());
			gl.glPushMatrix();
				gl.glRotatef(90, -1, 0, 0);	
			
				gl.glPushMatrix();
					//CARAS SUPERIOR
					gl.glPushMatrix();
						caraSuperior.dibujar(false);
					gl.glPopMatrix();
				
				
					//CARAS LATERALES
					gl.glPushMatrix();
						caraLaterales.dibujar(true);
					gl.glPopMatrix();
				
					gl.glPushMatrix();			
						gl.glTranslatef(0, -0.78f, 0); //ancho 400 -> translado 0.39 mas o menos 0.4
						caraLaterales.dibujar(false);
					gl.glPopMatrix();
				
				gl.glPopMatrix();
				
				///BORDES
				gl.glPushMatrix();			
					gl.glPushMatrix();
						gl.glTranslatef(0, -0.78f, 0.06f); //ancho 400 -> translado 0.39 mas o menos 0.4
						borde.dibujar(true);
					gl.glPopMatrix();
					
					gl.glPushMatrix();
						gl.glTranslatef(0, 0, 0.06f); // Pase 60 a 0.06 hice la misma promorcion con el ancho, ver de donde salio y hacer q sea calculada
						borde.dibujar(true);
					gl.glPopMatrix();
				gl.glPopMatrix();
			gl.glPopMatrix();
	  		
			java.util.Iterator<Botella> it =  this.botellas.iterator();
			gl.glPushMatrix();
				//gl.glRotatef(90f, -1f, 0f, 0f);			
				while(it.hasNext()){						
					it.next().dibujar();
				}	
			gl.glPopMatrix();
		gl.glPopMatrix();	
  		gl.glFlush();
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
