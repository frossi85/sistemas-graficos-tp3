package shader;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class SinDeformacionVert extends VertexShader{
	private String filename = "SinDeformacion2.vert";
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
	
	public void pararanimacion() {
		parado = true;		
	}
	
	public void reiniciaranimacion(){
		parado = false;
	}
	
	
	
	
	

}
