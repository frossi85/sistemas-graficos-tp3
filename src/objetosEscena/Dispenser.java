package objetosEscena;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShadersMejorado;
import utilidades.LineaProduccion;
import utilidades.Objeto3D;
import utilidades.Vertice;

public class Dispenser extends Objeto3D {
	
	private ManejoShadersMejorado shader;
	GLU glu;
	GLAutoDrawable gLDrawable;
	
	
	public Dispenser(LineaProduccion linea, ManejoShadersMejorado shader, GLU glu){
		super("model/examples/models/obj/expendedora.obj");
		
		this.glu = glu;
		this.shader = shader;
	}
	
	public Botella entregarBotella(){
		return new Botella(this.shader, glu);
	}
}
