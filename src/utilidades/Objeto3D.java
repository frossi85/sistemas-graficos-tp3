package utilidades;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import model.examples.DisplayListRenderer;
import model.geometry.Model;

public abstract class Objeto3D {
	protected Vertice posicion;
	protected float anguloRotacion;
	protected float factorEscala;
	protected iModel3DRenderer renderer;
	protected Model model;
	
	public Objeto3D(String modelUrl) {
		posicion = new Vertice(0f,0f,0f);
		factorEscala = 1f;
		renderer = DisplayListRenderer.getInstance();
        // Turn on debugging
		renderer.debug(true);
			
		try
	    {
	        // Call the factory for a model from a local file
	        model = ModelFactory.createModel(modelUrl);
	                        
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
	
	public Vertice getPosicion(){
		return posicion;
	}
	
	public Objeto3D setPosicion(Vertice v) {
		posicion = v;
		return this;
	}
	
	public Objeto3D rotar(float angulo) {
		anguloRotacion = angulo;
		return this;		
	}
	
	public Objeto3D escalar(float escala) {
		factorEscala = escala;
		return this;
	}
	
	public void dibujar() {
		final GL2 gl = GLProvider.getGL2();
		
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), posicion.getY(), posicion.getZ());	
			gl.glPushMatrix();
				gl.glRotatef(anguloRotacion, 0, 1, 0);
				gl.glScalef(factorEscala, factorEscala, factorEscala);
	    		renderer.render(gl, model);
	    	gl.glPopMatrix();
    	gl.glPopMatrix();
	}
}
