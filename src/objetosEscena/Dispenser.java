package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import model.geometry.Model;

import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShaders2;
import shader.ManejoShadersMejorado;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;
import aplicacion.Renderer;

public class Dispenser extends Observable implements Dibujable {
	
	private Vertice posicion;
	private GLUT glut;
	private float alto = 2f;
	private float ancho = 1f;
	private float largo = 1.5f;
	private float anchoAbertura = ancho/4f;
	private float altoAbertura = alto/4f;
	private float profundidadAbertura = largo/8f;
	private float separacionAberturaX = ancho/6;	// alejamiento de la abertura con respecto al borde del dispenser
	private float separacionAberturaY = largo/4;	// alejamiento de la abertura con respecto a la base del dispenser
	
	private Model model;
	private iModel3DRenderer renderer;
	
	private ManejoShadersMejorado shader;
	GLU glu;
	GLAutoDrawable gLDrawable;
	
	
	public Dispenser(LineaProduccion linea, iModel3DRenderer modelRenderer, ManejoShadersMejorado shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
		addObserver(linea);
		this.glu = glu;
		this.glut = glut;
		this.gLDrawable = gLDrawable;
		this.posicion = new Vertice(-0.25f,0f,0f);
		this.shader = shader;
		
		renderer = modelRenderer;
		
		try
	    {
	        // Call the factory for a model from a local file
	        model = ModelFactory.createModel("model/examples/models/obj/expendedora.obj");
	                        
	        // When loading the model, adjust the center to the boundary center
	        model.centerModelOnPosition(true);

	        model.setUseTexture(true);

	        // Render the bounding box of the entire model
	        model.setRenderModelBounds(false);

	        // Render the bounding boxes for all of the objects of the model
	        model.setRenderObjectBounds(false);

	        // Make the model unit size
	        model.setUnitizeSize(true);

	        // Get the radius of the model to use for lighting and view presetting
	        //radius = model.getBounds().getRadius();
	        
	    }
	    catch (ModelLoadException ex)
	    {
	        ex.printStackTrace();
	    }
	}
	
	public Botella entregarBotella(){
		return new Botella(this.shader, glut, glu, gLDrawable);
	}
	
	public Vertice getPosicon(){
		return this.posicion;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo dispenser");
		final GL2 gl = gLDrawable.getGL().getGL2();
//		gl.glPushMatrix();
//			//gl.glScalef(2, 2, 2);
//			gl.glRotatef(90, 0, 0, 1);
//			gl.glTranslatef(this.posicion.getX(), 0.0f, 0.0f);
//			//glut.glutSolidCube(0.5f);
//			Vertice vert1 = new Vertice(0,0,0);
//			Vertice vert2 = new Vertice(0,0,0);
//			Vertice vert3 = new Vertice(0,0,0);
//			Vertice vert4 = new Vertice(0,0,0);
//			
//			gl.glPushMatrix();	// dibujo abertura
//				gl.glTranslated(separacionAberturaX, separacionAberturaY,0);
//				
//				// PARTE IZQUIERDA
//				gl.glBegin(GL.GL_LINE_STRIP);
//				
//				vert1.set(0, 0, 0);
//				vert2.set(0, 0, +profundidadAbertura);
//				vert3.set(0, altoAbertura,+profundidadAbertura);
//				vert4.set(0, altoAbertura,0);
//				Renderer.dibujarQuads(gLDrawable, vert1, vert2, vert3, vert4);
//				/*
//				gl.glVertex3f(0, 0,0);
//				gl.glVertex3f(0, 0, -profundidadAbertura);
//				gl.glVertex3f(0, altoAbertura,-profundidadAbertura);
//				gl.glVertex3f(0, altoAbertura,0);
//				gl.glVertex3f(0, 0,0);
//				*/
//				gl.glEnd();
//				
//				//PARTE SUPERIOR
//				gl.glBegin(GL.GL_LINE_STRIP);
//				gl.glVertex3f(0, altoAbertura,+profundidadAbertura);
//				gl.glVertex3f(0, altoAbertura,0);
//				gl.glVertex3f(anchoAbertura, altoAbertura,0);
//				gl.glVertex3f(anchoAbertura, altoAbertura,+profundidadAbertura);
//				gl.glVertex3f(0, altoAbertura,+profundidadAbertura);
//				gl.glEnd();
//				
//				//PARTE DERECHA
//				gl.glBegin(GL.GL_LINE_STRIP);
//				gl.glVertex3f(anchoAbertura, 0,0);
//				gl.glVertex3f(anchoAbertura, 0, +profundidadAbertura);
//				gl.glVertex3f(anchoAbertura, altoAbertura,profundidadAbertura);
//				gl.glVertex3f(anchoAbertura, altoAbertura,0);
//				gl.glVertex3f(anchoAbertura, 0,0);
//				gl.glEnd();
//				
//				//PARTE INFERIOR
//				gl.glBegin(GL.GL_LINE_STRIP);
//				gl.glVertex3f(0, 0,profundidadAbertura);
//				gl.glVertex3f(0, 0,0);
//				gl.glVertex3f(anchoAbertura, 0,0);
//				gl.glVertex3f(anchoAbertura, 0,profundidadAbertura);
//				gl.glVertex3f(0, 0,profundidadAbertura);
//				gl.glEnd();
//			gl.glPopMatrix();
//			
//			gl.glBegin(GL.GL_TRIANGLES);
//			 vert1 = new Vertice(0,0,profundidadAbertura);
//			 vert2 = new Vertice(largo,0,profundidadAbertura);
//			 vert3 = new Vertice(largo,alto,profundidadAbertura);
//			 vert4 = new Vertice(0,alto,profundidadAbertura);
//			Renderer.dibujarQuads(gLDrawable, vert1, vert2, vert3, vert4);
//			gl.glEnd();
//		gl.glPopMatrix();	
		
		gl.glPushMatrix();
//		gl.glTranslatef(this.posicion.getX(), posicion.getY(), posicion.getZ());
			glut.glutSolidCube(0.5f);
			
    		//renderer.render(gl, model);
    	gl.glPopMatrix();

	}

}
