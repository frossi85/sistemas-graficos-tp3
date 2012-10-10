package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import model.examples.DisplayListRenderer;
import model.geometry.Model;

public class Etiquetador extends Observable implements Dibujable,Animable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	private Vertice posicion;
	private GLUT glut = new GLUT();
	private Model model;
	private iModel3DRenderer renderer;
	
	public Etiquetador(LineaProduccion linea, iModel3DRenderer modelRenderer, float tiempoEtiquetado){
		this.tiempoEtiquetado = tiempoEtiquetado;
		addObserver(linea);
		this.posicion = new Vertice(6f,0f,0f);
		
		renderer = modelRenderer;
		
		try
	    {
	        // Call the factory for a model from a local file
	        model = ModelFactory.createModel("model/examples/models/obj/etiquetadora.obj");
	                        
	        // When loading the model, adjust the center to the boundary center
	        //model.centerModelOnPosition(true);
	        model.centerModelOnPosition(false);

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
	
	public void etiquetar(){}
	
	//TODO: No se si es necesario
	public boolean terminoDeEtiquetar(){return terminoEtiquetado;}
	
	public Vertice getPosicion(){
		return this.posicion;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion = vert;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo etiquetador");
		final GL2 gl = gLDrawable.getGL().getGL2();
//		gl.glPushMatrix();
//			gl.glTranslatef(this.posicion.getX(), 1f, 0.0f);
//			glut.glutSolidCube(0.9f);
//		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), posicion.getY(), posicion.getZ());
			glut.glutSolidCube(0.9f);
		
	    	//renderer.render(gl, model);
		gl.glPopMatrix();
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
