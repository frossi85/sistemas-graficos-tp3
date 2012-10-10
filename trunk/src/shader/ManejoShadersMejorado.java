package shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class ManejoShadersMejorado {
	
	private static ArrayList<VertexShader> vertexShadersList = new ArrayList<VertexShader>();
	private static ArrayList<FragmentShader> fragmentShadersList = new ArrayList<FragmentShader>();
	private int currentVertexShader = -1;
	private int currentFragmentShader = -1;
	private GLAutoDrawable glDrawable;
	
	
	////////////////////////////////////////////////////////////////////
	private String archivoVertex;
	private String archivoFragment;
	private int programHandler;

	private GL2 gl_shader;

	int positionBufferHandle;
	int colorBufferHandle;
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public ManejoShadersMejorado(GLAutoDrawable glDra){
		this.glDrawable = glDra;
	}
	
	public static int addShader(VertexShader vS, FragmentShader fS) {
		vertexShadersList.add(vS);
		fragmentShadersList.add(fS);
		return vertexShadersList.size()-1;
	}
	
	public void pararAnimacion(){
		vertexShadersList.get(this.currentVertexShader).pararanimacion();
	}
	
	public void reiniciarAnimacion(){
		vertexShadersList.get(this.currentVertexShader).reiniciaranimacion();
	}
	
	public void usarPrograma(int vS, int fS){
		this.currentFragmentShader = fS;
		this.currentVertexShader = vS;
		
		if(this.currentFragmentShader >= this.fragmentShadersList.size() 
				|| this.currentVertexShader >= this.vertexShadersList.size()
				|| this.currentFragmentShader < 0
				|| this.currentVertexShader < 0){
			System.out.println("Incorrect vertexShader or fragmentShader id");
			gl_shader.glUseProgram(0);
		}
		else {
			//gl_shader.glUseProgram(0);
			this.archivoVertex = this.vertexShadersList.get(this.currentVertexShader).getFileName();
			this.archivoFragment = this.fragmentShadersList.get(this.currentFragmentShader).getFileName();
			this.bindBuffer(glDrawable);
			//this.linkeado(glDrawable);
			this.construir(glDrawable);
			gl_shader.glUseProgram(this.programHandler);
			vertexShadersList.get(this.currentVertexShader).setInfos(programHandler, gl_shader);
		}
	}
	
	
	public void displayVertexAttrib(){
		vertexShadersList.get(this.currentVertexShader).displayVertexAttrib();
	}
	
	public void displayUniform(){
		vertexShadersList.get(this.currentVertexShader).displayUniform();
	}
	
	private void bindBuffer(GLAutoDrawable gLDrawable){
		GL2 gl_aux = (GL2)gLDrawable.getGL().getGL();	// para usar glShadeModel
		GL2 gl_shader = gLDrawable.getGL().getGL2();
		gl_shader.glClearColor (0.02f, 0.02f, 0.04f, 0.0f);
	  	gl_aux.glShadeModel (GL2.GL_SMOOTH);
	  	gl_shader.glEnable(GL2.GL_DEPTH_TEST);
	}
	
	private void construir(GLAutoDrawable gLDrawable){
		
		GL2 gl_shader = gLDrawable.getGL().getGL2();
		
    	int shaderProgram = gl_shader.glCreateProgram();
		
		
		//Compilo shaders
		compilarShader(glDrawable, shaderProgram, GL2.GL_VERTEX_SHADER, this.archivoVertex);
		compilarShader(glDrawable, shaderProgram, GL2.GL_FRAGMENT_SHADER, this.archivoFragment);
		
		//Linkeo shaders
	    vertexShadersList.get(this.currentVertexShader).bind(gLDrawable,shaderProgram);
	    gl_shader.glLinkProgram(shaderProgram);
	    IntBuffer intBuffer = IntBuffer.allocate(1);
		gl_shader.glGetProgramiv(shaderProgram, GL2.GL_LINK_STATUS, intBuffer);
		if (intBuffer.get(0) != 1)
		{
			gl_shader.glGetProgramiv(shaderProgram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl_shader.glGetProgramInfoLog(shaderProgram, size, intBuffer, byteBuffer);
				for (byte b : byteBuffer.array())
				{
					System.err.print((char) b);
				}
			}
			else
			{
				System.out.println("Unknown");
			}
			System.exit(1);
		}
	   
	    gl_shader.glValidateProgram(shaderProgram);
	    this.programHandler = shaderProgram;  
	    
	    this.gl_shader = gl_shader;
	}
	
	private void compilarShader(GLAutoDrawable gLDrawable, int shaderProgram, int ShaderType, String archivoShader){
		GL2 gl_shader = gLDrawable.getGL().getGL2();
		int creador = gl_shader.glCreateShader(ShaderType);	    
	    BufferedReader reader = null;
	    String [] shaderSource = new String [1];
	    shaderSource[0] = "";
	    String line = "";
	    
		try {
			File archivo = new File (archivoShader);
			FileReader fr = new FileReader (archivo);
			reader = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("No se pudo abrir archivo de Shaders");
			//Liberar aca las cosas creadas por el Renderer, con un metodo estatico de la clase, por ejemplo shaders y eso
			System.exit(1);
		}
		
	    try {
			while ((line=reader.readLine()) != null) {
				shaderSource[0] += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problemas al leer archivo de Shaders");
			//Liberar aca las cosas creadas por el Renderer, con un metodo estatico de la clase, por ejemplo shaders y eso
			System.exit(1);
		}
	    
	    //COMPILADO SHADER
	    gl_shader.glShaderSource(creador, 1, shaderSource, (int[])null, 0);	
	    gl_shader.glCompileShader(creador);
	    IntBuffer compiladoErrorVert = IntBuffer.allocate(1);
	    gl_shader.glGetShaderiv(creador, GL2.GL_COMPILE_STATUS, compiladoErrorVert);
	    
	    if(compiladoErrorVert.get(0) == GL.GL_FALSE) {
			gl_shader.glGetProgramiv(creador, GL2.GL_INFO_LOG_LENGTH, compiladoErrorVert);
			int size = compiladoErrorVert.get(0);
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl_shader.glGetProgramInfoLog(creador, size, compiladoErrorVert, byteBuffer);
				String infoLogString = Charset.forName("US-ASCII").decode(byteBuffer).toString();
				System.err.println("Compile error:" + Charset.forName("US-ASCII").decode(byteBuffer).toString());	
			}
			else
			{
				System.err.println("Unknown");
			}
			//Borrar todo lo relacionado al renderer, sino ver si sacando el System.exit(1) para ver si la exepcion 
			//me rompe el programa y se cierra la pantalla bien
			System.exit(1);
		}
	    
	    gl_shader.glAttachShader(shaderProgram, creador);
	    
	    if( GL.GL_NO_ERROR !=  gl_shader.glGetError()) System.err.println("Error al crear programa objeto");
	}

	public int getProgramHandler(){
		return this.programHandler;		
	}

}
