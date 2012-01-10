package aplicacion;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import utilidades.LineaProduccion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

//Implementar el Renderer como Singleton
class Renderer implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private GLU glu = new GLU();
    public static GLUT glut = new GLUT();
	private Point2D.Float posicionAnteriorMouse = new Point2D.Float(0,0);
	private float rotacionCamaraX = 0, rotacionCamaraY = 0;
	
	// Variables que controlan la ubicaci�n de la c�mara en la Escena 3D
    private float eye[] =  {0.0f, 0.0f, 4.0f};
    private float at[]  = { 0.0f,  0.0f, -1.0f};
    private float up[]  = { 0.0f,  1.0f, 0.0f};

    // Variables asociadas a �nica fuente de luz de la escena
    private float light_color[] = {0.5f, 0.5f, 0.5f, 1.0f};
    private float light_position[] = {-1f,1f,1f,0f};
	private float light_ambient[] = {0.05f, 0.05f, 0.05f, 1.0f};
    private float light_specular[] = {0f,0f,0f,0f};

    // Variables de control
    // Tama�o de la ventana
    private static float window_size[] = new float[2];
    private static float W_WIDTH = window_size[0];
    private static float W_HEIGHT = window_size[1];

    //private static final int TOP_VIEW_POSX = ((int)((float)W_WIDTH*0.60f));
    //private static final int TOP_VIEW_W = ((int)((float)W_WIDTH*0.40f));
    //private static final int TOP_VIEW_POSY = ((int)((float)W_HEIGHT*0.60f));
    //private static final int TOP_VIEW_H = ((int)((float)W_HEIGHT*0.40f));

    //Valores del buffer de color y posicion
    static int POSITION_BUFFER_SIZE = 9;
    private FloatBuffer positionData = FloatBuffer.allocate (POSITION_BUFFER_SIZE);
    private FloatBuffer colorData = FloatBuffer.allocate (POSITION_BUFFER_SIZE);
    private IntBuffer vaoHandle = IntBuffer.allocate(1);

    //public enum efectoFragment {SEMI_MATE,BRILLANTE, TEXTURA2D, REFLEJAR_ENTORNO};


    //TODO: Para que se usa, ver si se puede encapsular en una clase
	float [] positionDataOrig =
	{
	    -0.8f, -0.8f, 0.0f,
	     0.8f, -0.8f, 0.0f,
	     0.0f,  0.8f, 0.0f
	};

	//TODO: Para que se usa, ver si se puede encapsular en una clase
	int positionBufferHandle;

	//TODO: Para que se usa, ver si se puede encapsular en una clase
	float colorDataOrig[] =
    {
         1.0f,  0.0f, 0.0f,
         0.0f,  1.0f, 0.0f,
         0.0f,  0.0f, 1.0f
    };
	
	//TODO: Para que se usa, ver si se puede encapsular en una clase
	int colorBufferHandle;

	//TODO: Para que se usa, ver si se puede encapsular en una clase
	public GLCanvas canvas;
	
	private LineaProduccion linea;

    //ATRIBUTOS DE LA ANIMACION
    private boolean pause = false;
    private FPSAnimator animator;

    public Renderer(GLCanvas glCanvas)
    {
    	this.canvas = glCanvas;
    	//animator = new FPSAnimator(canvas, 60);
    	animator = new FPSAnimator(canvas, 50);
    	animator.add(canvas);
    	animator.start();
    	this.linea = new LineaProduccion();
    }

    private void update(GL2 gl)
    {
    	
    }

    
	//////////////////////////////     INICIO EVENTOS OPEN GL ////////////////////////////
    
    public void init(GLAutoDrawable gLDrawable)
    {
    	//GL4 gl2 = gLDrawable.getGL().getGL4();

    	//System.out.println("GL_VERSION: "+ gl2.glGetString(GL3.GL_VERSION)); ;
    	//System.out.println("GL_SHADING_LANGUAGE_VERSION: " + gl2.glGetString(GL4.GL_SHADING_LANGUAGE_VERSION));


    	GL2 gl = gLDrawable.getGL().getGL2();

    	gl.glClearColor(0.5f, 0.5f, 1.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//TODO: Setear la camera
		Set3DEnv(gl);

		
		//TODO:  CREACION DE LA LINEA DE PRODUCCION Y SETTEOS INICIALES /////
		//TODO: los shader se crean y actualizan solo dentro de la linea de produccion o de una clase utilidad q reciba una linea de produccion y maneje el shader
		
		
		//TODO: Settear las luces de la escena?? o mejor hacerlo en una clase que seahabitacion o algo asi y poner el setter aca
		DemoLight(gl);
		this.linea.dibujar();
    }
    
    public void display(GLAutoDrawable gLDrawable)
    {
    	final GL2 gl = gLDrawable.getGL().getGL2();

    	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

  		update(gl);

  		/////   TODO: DIBUJAR ACA   ////
  		
  		gl.glPushMatrix();
  		
  			glut.glutSolidCube(1.0f);
  		
  		gl.glPopMatrix();
    	
    	this.linea.actualizar();
  		////////////////////////////////
  		
	  	//En ves de glutSwapBuffers();.. va gl.glFlush();
	    gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged)
    {
    	//System.out.println("displayChanged called");
    }
    
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height)
    {
    	//System.out.println("reshape() called: x = "+x+", y = "+y+", width = "+width+", height = "+height);

        W_WIDTH  = (float)width;
    	W_HEIGHT = (float)height;

    	GL2 gl = gLDrawable.getGL().getGL2();
    	
    	//TODO: Setear la camera
		Set3DEnv(gl);
    }

	public void dispose(GLAutoDrawable arg0)
	{
		//System.out.println("dispose() called");
	}
	
	//////////////////////////////     FIN EVENTOS OPEN GL ////////////////////////////

    public static FloatBuffer makeFloatBuffer(float[] arr) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
    }

    private void DemoLight(GL2 gl)
    {
      gl.glEnable(GL2.GL_LIGHTING);
      gl.glEnable(GL2.GL_LIGHT0);
      gl.glEnable(GL2.GL_NORMALIZE);

      // Light model parameters:
      // -------------------------------------------

      float lmKa[] = {0.1f, 0.1f, 0.1f, 1.0f };

      gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, makeFloatBuffer(lmKa));

      gl.glLightModelf(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1.0f);
      gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, 0.0f);

      // -------------------------------------------
      // Spotlight Attenuation

      //float spot_direction[] = {1.0f, -1.0f, -1.0f }; //ORIGINAL
      float spot_direction[] = {1.0f, -1.0f, -1.0f };


      int spot_exponent = 30;
      int spot_cutoff = 180;

      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPOT_DIRECTION, makeFloatBuffer(spot_direction));
      gl.glLighti(GL2.GL_LIGHT0, GL2.GL_SPOT_EXPONENT, spot_exponent);
      gl.glLighti(GL2.GL_LIGHT0, GL2.GL_SPOT_CUTOFF, spot_cutoff);

      float Kc = 1.0f;
      float Kl = 0.0f;
      float Kq = 0.0f;

      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_CONSTANT_ATTENUATION,Kc);
      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_LINEAR_ATTENUATION, Kl);
      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_QUADRATIC_ATTENUATION, Kq);


      // -------------------------------------------
      // Lighting parameters:

      //float light_pos[] = {0.0f, 5.0f, 5.0f, 1.0f}; //ORIGINAL
      float light_pos[] = {1.0f, 3.0f, 10.0f, 1.0f}; //DIRECTIONAL LITGH O POINT??
      //float light_Ka[]  = {1.0f, 0.5f, 0.5f, 1.0f}; //ORIGINAL
      //float light_Ka[] = {0.5f, 0.5f, 0.5f, 1.0f};
      float light_Ka[] = {0.0f, 0.0f, 0.0f, 1.0f }; //Del segundo tuto
      //float light_Kd[]  = {1.0f, 0.1f, 0.1f, 1.0f}; //ORIGINAL
      float light_Kd[]  = {1.0f, 1.0f, 1.0f, 1.0f};
      float light_Ks[]  = {1.0f, 1.0f, 1.0f, 1.0f}; //Brillante


      //float light_Ks[]  = {0.0f, 0.0f, 0.0f, 0.0f}; //SemiMate

      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, makeFloatBuffer(light_pos));
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, makeFloatBuffer(light_Ka));
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, makeFloatBuffer(light_Kd));
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, makeFloatBuffer(light_Ks));

      // -------------------------------------------
      // Material parameters:

      //float material_Ka[] = {0.0f, 0.9f, 0.7f, 1.0f}; //Color Material del brillante
      float material_Ka[] = {0.3f, 0.3f, 0.3f, 1.0f }; //Del Segundo Tuto
      //float material_Kd[] = {0.4f, 0.4f, 0.4f, 1.0f};
      float material_Kd[] = {0.9f, 0.5f, 0.5f, 1.0f }; //del segundo TUTO
      //float material_Ks[] = {0.8f, 0.8f, 0.8f, 1.0f};
      float material_Ks[] = {0.6f, 0.6f, 0.6f, 1.0f }; //del segundo tuto
      float material_Ke[] = {0.1f, 0.0f, 0.0f, 0.0f};
      //float material_Se = 15.0f;
      float material_Se = 60.0f; //del segundo tuto

      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, makeFloatBuffer(material_Ka));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, makeFloatBuffer(material_Kd));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, makeFloatBuffer(material_Ks));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, makeFloatBuffer(material_Ke));
      gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, material_Se);

    }

	private void Set3DEnv(GL2 gl)
	{
	  	gl.glViewport (0, 0, (int) W_WIDTH, (int) W_HEIGHT);
	    gl.glMatrixMode (GL2.GL_PROJECTION);
	    gl.glLoadIdentity ();
	    glu.gluPerspective(60.0, W_WIDTH/W_HEIGHT, 0.10, 100.0);

	    gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(eye[0], eye[1] , eye[2],
				  at[0], at[1], at[2],
 				  up[0], up[1], up[2]);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		rotacionCamaraX += e.getX() - posicionAnteriorMouse.getX();
		rotacionCamaraY += e.getY() - posicionAnteriorMouse.getY();
		posicionAnteriorMouse.setLocation(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent m) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent key) {
		//convertir todas las letras a mayusculas o minusculas
		switch (key.getKeyChar()) {
	    	case KeyEvent.VK_ESCAPE:
	    		System.exit(0);
	    		break;
	    	case 'd':
	    		break;
	    	case 'r':
//	    		R Reiniciar la animaci�n de crecimiento
	    		System.out.println("Reiniciar animaci�n");

	    		break;
	    	case 'p':
//	    		P Pausar/reanudar animaci�n
	    		String texto = (this.pause)? "Reanudar": "Pausar";
	    		System.out.println(texto);
	    		this.pause = !this.pause;
	    		break;
	    	case 'q':
//	    		Q incrementar velocidad de crecimiento
	    		
	    		break;
	    	case 'a':
//	    		A decrementar velocidad de crecimiento
	    		break;
	    	case 'x':
	    		break;
	    	case 'z':
	    		break;
	    	default:
	    		break;
	    }

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}