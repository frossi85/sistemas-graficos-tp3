package objetosEscena;
import java.util.ArrayList;
import java.util.Observable;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;



import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Animable;
import utilidades.AreaDeIncidencia;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.PuntoDeControl;
import utilidades.SuperficieDeRevolucion;
import utilidades.Vertice;
import shader.ManejoShaders2;
import shader.TexturaCubeMap;
import javax.media.opengl.GLAutoDrawable;




public class Botella  implements Dibujable,Animable {
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	public static float altura;
	private SuperficieDeRevolucion sup;
	private Vertice posicion;	// posicion relativa a la cinta transportadora
	private GLUT glut;
	private TexturaCubeMap texturaCubica;
	private GL2 gl;
	private GLU glu;
	private ManejoShaders2 shader;
	private AreaDeIncidencia area;
	
	
	
	
	public Botella(ManejoShaders2 shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
		
		this.gl = gLDrawable.getGL().getGL2();
		this.glu = glu;
		this.glut = glut;
		//texturaCubica = new TexturaCubeMap(gl, glu, 512);

		//texturaCubica.cargarXPositivo("lib/textura_pared.jpg");
		//texturaCubica.cargarYPositivo("lib/textura_pared.jpg");
	  	//texturaCubica.cargarZPositivo("lib/textura_pared.jpg");
	  	//texturaCubica.cargarXNegativo("lib/textura_pared.jpg");
	  	//texturaCubica.cargarYNegativo("lib/textura_pared.jpg");
	  	//texturaCubica.cargarZNegativo("lib/textura_piso.jpg");
		
		this.shader = shader;
	  	this.lleno = false;
		this.etiquetado = false;
		this.posicion = new Vertice(0f,0f,0f);
		ArrayList<PuntoDeControl>list = new ArrayList<PuntoDeControl>();
		//opcion1	los primeros puntos de la lista tienen las coord Y mas altas (esto lo usa el calculo de altura)
		
		/*	ORIGINALES
		 * PuntoDeControl punto1 = new PuntoDeControl(1.21f-1f,0.64f);
		PuntoDeControl punto2 = new PuntoDeControl(1.14f-1f ,-0.82f);
		PuntoDeControl punto3 = new PuntoDeControl(3.71f-1f,-4.28f);
		PuntoDeControl punto4 = new PuntoDeControl(2.81f-1f,-7.32f);
		
		PuntoDeControl punto5 = new PuntoDeControl(2.81f-1f,-7.32f);
		PuntoDeControl punto6 = new PuntoDeControl(2.54f-1f,-8.22f);
		PuntoDeControl punto7 = new PuntoDeControl(4.97f-1f,-12.45f);
		PuntoDeControl punto8 = new PuntoDeControl(3.39f-1.5f,-13.15f);
		*/
		
		/*BOTELLA AL REVES
		PuntoDeControl punto1 = new PuntoDeControl(0.3f,0f);
		PuntoDeControl punto2 = new PuntoDeControl(0.15f ,1.46f);
		PuntoDeControl punto3 = new PuntoDeControl(2.71f,4.92f);
		PuntoDeControl punto4 = new PuntoDeControl(1.81f,7.96f);
		
		PuntoDeControl punto5 = new PuntoDeControl(1.81f,7.96f);
		PuntoDeControl punto6 = new PuntoDeControl(1.54f,8.86f);
		PuntoDeControl punto7 = new PuntoDeControl(3.4f,13.09f);
		PuntoDeControl punto8 = new PuntoDeControl(1.89f,13.79f);	
		*/
		
		PuntoDeControl punto1 = new PuntoDeControl(0.3f,13.79f);
		PuntoDeControl punto2 = new PuntoDeControl(0.15f ,13.09f);
		PuntoDeControl punto3 = new PuntoDeControl(2.51f,10.86f);
		PuntoDeControl punto4 = new PuntoDeControl(1.81f,7.96f);
		
		PuntoDeControl punto5 = new PuntoDeControl(1.81f,7.96f);
		PuntoDeControl punto6 = new PuntoDeControl(1.54f,4.92f);
		PuntoDeControl punto7 = new PuntoDeControl(3.1f,1.46f);
		PuntoDeControl punto8 = new PuntoDeControl(1.89f,0f);	
				
		list.add(punto1);	// agregadas al reves para q se vea bien la normal en efecto de iluminacion verde (?)
		list.add(punto2);
		list.add(punto3);
		list.add(punto4);
		
		list.add(punto5);
		list.add(punto6);
		list.add(punto7);
		list.add(punto8);
		
		
		// armo el area de incidencia de la botella tomando 1)el punto de la base, 2) el punto de la base con -x, 3)  intercambio y por x, 4) -x de el punto anterior
		Vertice posTemp1 = new Vertice(list.get(list.size() - 1).getX(), list.get(list.size() - 1).getY(), list.get(list.size() - 1).getX());
		Vertice posTemp2 = new Vertice(- posTemp1.getX(), posTemp1.getY(), posTemp1.getX());
		Vertice posTemp3 = new Vertice(posTemp1.getX(), posTemp1.getY(), - posTemp1.getX());
		Vertice posTemp4 = new Vertice(- posTemp1.getX(), posTemp1.getY(), -posTemp1.getX());
		area = new AreaDeIncidencia(posTemp1, posTemp2, posTemp3, posTemp4); 
		
		this.sup = new SuperficieDeRevolucion(list);
		altura = 0;
		//float alt = 0;
		for (int h = 0; h < sup.getNumCurvas(); h++){
			altura += sup.getListaCurvas().get(h).getY(0);
			//alt += sup.getListaCurvas().get(h).getY(0);
		}
		
	}

	public void setPorcentajelLlenado(float porcentaje){
		this.porcentajeLlenado = porcentaje;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion.setX(vert.getX());
		this.posicion.setY(vert.getY());
		this.posicion.setZ(vert.getZ());
	}
	
	public Vertice getPosicion(){
		return this.posicion;
	}
	
	public boolean estaLleno() {
		return lleno;
	}

	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}

	public boolean estaEtiquetado() {
		return etiquetado;
	}

	public void setEtiquetado(boolean etiquetado) {
		this.etiquetado = etiquetado;
	}

	public float getPorcentajeLlenado() {
		return porcentajeLlenado;
	}

	public void setPorcentajeLlenado(float porcentajeLlenado) {
		this.porcentajeLlenado = porcentajeLlenado;
	}

	public float getAltura() {
		return altura;
	}

		
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//texturaCubica.habilitar();
		int uniloc = -1;
		 //SETEO LA TEXTURA DEL UNIFORM cubeMap A LA UNIDAD DE TEXTURA 0 
        //uniloc = gl.glGetUniformLocation(shader.getProgramHandler(), "cubeMap"); 
        //if( uniloc >= 0 ) 
          //  gl.glUniform1i(uniloc, texturaCubica.cubemap);   
        final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glScalef(sup.getFactorEscalado(),sup.getFactorEscalado(), sup.getFactorEscalado());
		//gl.glTranslatef(this.posicion.getX(),0, 0.0f);
        gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0f);
        
		this.sup.dibujar(gLDrawable);
//		gl.glBegin(gl.GL_TRIANGLES);
//		// dibujo el area de incidencia
//		gl.glVertex3f(this.area.getArea()[0].getX(),this.area.getArea()[0].getY() ,this.area.getArea()[0].getZ());
//		gl.glVertex3f(this.area.getArea()[1].getX(),this.area.getArea()[1].getY() ,this.area.getArea()[1].getZ());
//		gl.glVertex3f(this.area.getArea()[2].getX(),this.area.getArea()[2].getY() ,this.area.getArea()[2].getZ());
//		gl.glVertex3f(this.area.getArea()[3].getX(),this.area.getArea()[3].getY() ,this.area.getArea()[3].getZ());
//		gl.glVertex3f(this.area.getArea()[1].getX(),this.area.getArea()[1].getY() ,this.area.getArea()[1].getZ());
//		gl.glVertex3f(this.area.getArea()[2].getX(),this.area.getArea()[2].getY() ,this.area.getArea()[2].getZ());
//		gl.glEnd();
		gl.glPopMatrix();
//		ejes de coord		
//		gl.glBegin(gl.GL_LINES);
//		gl.glVertex3f(5f,0f,0f);
//		gl.glVertex3f(0f,0f,0f);
//		gl.glVertex3f(0f,3f,0f);
//		gl.glVertex3f(0f,0f,0f);
//		gl.glVertex3f(0f,0f,2f);
//		gl.glVertex3f(0f,0f,0f);
//		gl.glEnd();
		
		gl.glPopMatrix();	
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
