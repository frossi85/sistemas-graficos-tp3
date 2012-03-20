package shader;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Refraccion extends VertexShader {

	private String filename = "Refraccion.vert";
	private boolean parado = false;
	
	public void bind(GLAutoDrawable gLDrawable,int shaderprogram){
		
		GL2 gl_shader = gLDrawable.getGL().getGL2();
	}

	@Override
	public void displayVertexAttrib() {
	}

	@Override
	public void displayUniform() {

	}

	@Override
	public String getFileName() {
		return this.filename;
	}

	@Override
	public void pararanimacion() {
		parado = true;		
	}
	
	public void reiniciaranimacion(){
		parado = false;
	}
		
	
}
