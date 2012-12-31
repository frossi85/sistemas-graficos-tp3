package utilidades;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import model.ModelFactory;
import model.ModelLoadException;
import model.geometry.Model;

public abstract class Objeto3DConAnimacion extends Objeto3D {
	protected Model articulationModel;
	protected float posicionArticulacion;
	protected float posicionArticulacionInicial;
	protected float posicionArticulacionFinal;
	protected float avanceArticulacion;
	protected boolean estaBajando;
	
	public Objeto3DConAnimacion(String modelUrl, String articulationModelUrl) {
		super(modelUrl);	
		
		estaBajando = false;
			
		try
	    {
	        // Call the factory for a model from a local file
			articulationModel = ModelFactory.createModel(articulationModelUrl);
	                        
	        // When loading the model, adjust the center to the boundary center
			articulationModel.centerModelOnPosition(true);

			articulationModel.setUseTexture(true);

	        // Render the bounding box of the entire model
			articulationModel.setRenderModelBounds(false);

	        // Render the bounding boxes for all of the objects of the model
			articulationModel.setRenderObjectBounds(false);

	        // Make the model unit size
			articulationModel.setUnitizeSize(true);

	        // Get the radius of the model to use for lighting and view presetting
	        //radius = articulationModel.getBounds().getRadius();
	        
	    }
	    catch (ModelLoadException ex)
	    {
	        ex.printStackTrace();
	    }
	}
	
	public Vertice getPosicion(){
		return posicion;
	}
	
	public Objeto3DConAnimacion setPosicion(Vertice v) {
		posicion = v;
		return this;
	}
	
	public Objeto3DConAnimacion rotar(float angulo) {
		anguloRotacion = angulo;
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
	    		gl.glPushMatrix();
	    			transformacionesArticulacion(gl);
	    			renderer.render(gl, articulationModel);
	    		gl.glPopMatrix();
	    	gl.glPopMatrix();
    	gl.glPopMatrix();
	}
	
	protected abstract void transformacionesArticulacion(GL2 gl);
}
