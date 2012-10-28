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
import utilidades.GLProvider;
import utilidades.LineaProduccion;
import utilidades.Vertice;
import utilidades.SuperficieDeRevolucion;
import utilidades.Vertice;
import shader.ManejoShaders2;
import shader.ManejoShadersMejorado;
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
	private ManejoShadersMejorado shader;
	private AreaDeIncidencia area;
	
	
	
	
	public Botella(ManejoShadersMejorado shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
		
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
		ArrayList<Vertice>list = new ArrayList<Vertice>();
		//opcion1	los primeros puntos de la lista tienen las coord Y mas altas (esto lo usa el calculo de altura)
		
		/*	ORIGINALES
		 * Vertice punto1 = new Vertice(1.21f-1f,0.64f);
		Vertice punto2 = new Vertice(1.14f-1f ,-0.82f);
		Vertice punto3 = new Vertice(3.71f-1f,-4.28f);
		Vertice punto4 = new Vertice(2.81f-1f,-7.32f);
		
		Vertice punto5 = new Vertice(2.81f-1f,-7.32f);
		Vertice punto6 = new Vertice(2.54f-1f,-8.22f);
		Vertice punto7 = new Vertice(4.97f-1f,-12.45f);
		Vertice punto8 = new Vertice(3.39f-1.5f,-13.15f);
		*/
		
		/*BOTELLA AL REVES
		Vertice punto1 = new Vertice(0.3f,0f);
		Vertice punto2 = new Vertice(0.15f ,1.46f);
		Vertice punto3 = new Vertice(2.71f,4.92f);
		Vertice punto4 = new Vertice(1.81f,7.96f);
		
		Vertice punto5 = new Vertice(1.81f,7.96f);
		Vertice punto6 = new Vertice(1.54f,8.86f);
		Vertice punto7 = new Vertice(3.4f,13.09f);
		Vertice punto8 = new Vertice(1.89f,13.79f);	
		*/
		
		Vertice punto1 = new Vertice(0.3f,13.79f);
		Vertice punto2 = new Vertice(0.15f ,13.09f);
		Vertice punto3 = new Vertice(2.51f,10.86f);
		Vertice punto4 = new Vertice(1.81f,7.96f);
		
		Vertice punto5 = new Vertice(1.81f,7.96f);
		Vertice punto6 = new Vertice(1.54f,4.92f);
		Vertice punto7 = new Vertice(3.1f,1.46f);
		Vertice punto8 = new Vertice(1.89f,0f);	
				
//		list.add(punto1);	// agregadas al reves para q se vea bien la normal en efecto de iluminacion verde (?)
//		list.add(punto2);
//		list.add(punto3);
//		list.add(punto4);
//		
//		list.add(punto5);
//		list.add(punto6);
//		list.add(punto7);
//		list.add(punto8);
		
		list.add(punto8);	// agregadas al reves para q se vea bien la normal en efecto de iluminacion verde (?)
		list.add(punto7);
		list.add(punto6);
		list.add(punto5);
		
		list.add(punto4);
		list.add(punto3);
		list.add(punto2);
		list.add(punto1);
		
		
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
	
	public void dibujar() {
		GL2 gl = GLProvider.getGL2();
		
		//texturaCubica.habilitar();
				int uniloc = -1;
				 //SETEO LA TEXTURA DEL UNIFORM cubeMap A LA UNIDAD DE TEXTURA 0 
		        //uniloc = gl.glGetUniformLocation(shader.getProgramHandler(), "cubeMap"); 
		        //if( uniloc >= 0 ) 
		          //  gl.glUniform1i(uniloc, texturaCubica.cubemap);   
				gl.glPushMatrix();
				gl.glScalef(sup.getFactorEscalado(),sup.getFactorEscalado(), sup.getFactorEscalado());
		        gl.glPushMatrix();
				gl.glRotatef(90, 1, 0, 0f);
		        
				this.sup.dibujar(true);
				gl.glPopMatrix();
				
				gl.glPopMatrix();	
		
	}
	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
