package aplicacion;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import shader.FragmentGeneral;
import shader.ManejoShaders2;
import shader.ManejoShadersMejorado;
import shader.Refraccion;
import shader.SinDeformacionVert;
import objetosEscena.Botella;
import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Piso;
import utilidades.BSplineCuadratica;
import utilidades.BSplineGenerica;
import utilidades.GLProvider;
import utilidades.ICurva3D;
import utilidades.LineaProduccion;
import utilidades.LineaRecta;
import utilidades.Vertice;
import utilidades.SuperficieDeBarrido;
import utilidades.Utilidades;
import utilidades.Vertice;

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
import java.util.ArrayList;

import Primitivas.cubo;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

//Implementar el Renderer como Singleton
public class Renderer implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private GLU glu = new GLU();
    public static GLUT glut = new GLUT();
	private Point2D.Float posicionAnteriorMouse = new Point2D.Float(0,0);
	private Camara camara;
	private float timer;
	private static float actualizacionEscena = 0.6f;	// cada cuanto se actualiza la escena a dibujar

    // Variables de control
    // Tama�o de la ventana
    private static float window_size[] = new float[2];
    private static float W_WIDTH = window_size[0];
    private static float W_HEIGHT = window_size[1];
 
    
    ManejoShadersMejorado mS;
    int SIN_DEFORMACION_VERT = ManejoShaders2.addVertexShader(new SinDeformacionVert());
    int REFRACCION_VERT = ManejoShaders2.addVertexShader(new Refraccion());
    int GENERIC_FRAG;
    FragmentGeneral fragment;
    efectoFragment currentFrag;
    int currentVert;
    public enum efectoFragment {SEMI_MATE,BRILLANTE, TEXTURA2D, REFLEJAR_ENTORNO};
    
    cubo unCubo = new cubo(0.35f, 50);
    LuzSpot spot1;

	//TODO: Para que se usa, ver si se puede encapsular en una clase
	public GLCanvas canvas;
	
	private LineaProduccion linea;
	private Piso piso;

    //ATRIBUTOS DE LA ANIMACION
    private boolean pause = false;
    private FPSAnimator animator;
    Botella botella;
    Dispenser dispenser;
    ICurva3D spline;
    ICurva3D spline2;
    ICurva3D lineaRecta;
    SuperficieDeBarrido SuperficieBarrido;
    
    float u = 0.0f;

    public Renderer(GLCanvas glCanvas)
    {
    	this.canvas = glCanvas;
    	//animator = new FPSAnimator(canvas, 60);
    	animator = new FPSAnimator(canvas, 50);
    	animator.add(canvas);
    	animator.start();
    	this.timer = 0.0f;
    	this.piso = new Piso();
    }

    private void update(GL2 gl)
    {
    	u += 0.01;
    	
    	if(u >= 1.0f)
    		u = 0.0f;
    		
    }
//
//    public static void dibujarQuads(GLAutoDrawable gLDrawable, Vertice vert1, Vertice vert2, Vertice vert3, Vertice vert4){
//    	final GL2 gl = gLDrawable.getGL().getGL2();
//    	
//    	gl.glBegin(GL.GL_TRIANGLES);
//    	gl.glVertex3f(vert1.getX(), vert1.getY(), vert1.getZ());
//    	gl.glVertex3f(vert2.getX(), vert2.getY(), vert2.getZ());
//    	gl.glVertex3f(vert3.getX(), vert3.getY(), vert3.getZ());
//    	gl.glEnd();
//    	
//    	gl.glBegin(GL.GL_TRIANGLES);
//    	gl.glVertex3f(vert3.getX(), vert3.getY(), vert3.getZ());
//    	gl.glVertex3f(vert4.getX(), vert4.getY(), vert4.getZ());
//    	gl.glVertex3f(vert1.getX(), vert1.getY(), vert1.getZ());
//    	gl.glEnd();
//    }
    
	//////////////////////////////     INICIO EVENTOS OPEN GL ////////////////////////////
    
    public void init(GLAutoDrawable gLDrawable)
    {
    	//GL4 gl2 = gLDrawable.getGL().getGL4();

    	//System.out.println("GL_VERSION: "+ gl2.glGetString(GL3.GL_VERSION)); ;
    	//System.out.println("GL_SHADING_LANGUAGE_VERSION: " + gl2.glGetString(GL4.GL_SHADING_LANGUAGE_VERSION));
    	

    	GL2 gl = gLDrawable.getGL().getGL2();

    	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//TODO: Setear la camera
		Set3DEnv(gl);
		
		
		//SETEO DE SHADERS
		mS = new ManejoShadersMejorado(gLDrawable);

		fragment = new FragmentGeneral(gl, glu, mS);
		GENERIC_FRAG = SIN_DEFORMACION_VERT = ManejoShadersMejorado.addShader(new SinDeformacionVert(), fragment);

		currentVert = SIN_DEFORMACION_VERT;
		//currentVert = REFRACCION_VERT;
		currentFrag = efectoFragment.BRILLANTE;
		
		linea = new LineaProduccion(mS, glut, glu, gLDrawable);
		botella = new Botella(mS,glut, glu, gLDrawable );
		
		ArrayList<Vertice> puntos = new ArrayList<Vertice>();
		
		puntos.add(new Vertice(0f, 0f, 0f));
		puntos.add(new Vertice(0.5f, 0.5f, 0f));
		puntos.add(new Vertice(1.0f, 0.0f, 0f));
		puntos.add(new Vertice(1.5f, 0.5f, 0f));
		puntos.add(new Vertice(2.0f, 0.0f, 0f));
		puntos.add(new Vertice(2.5f, 0.5f, 0f));
		
		ArrayList<Vertice> puntos2 = new ArrayList<Vertice>();
		
		puntos2.add(new Vertice(0f, 0f, 0f));
		puntos2.add(new Vertice(0f, 0.5f, 0.5f));
		puntos2.add(new Vertice(0f, 1.0f, 0.0f));
		puntos2.add(new Vertice(0f, 1.5f, 0.5f));
		puntos2.add(new Vertice(0f, 2.0f, 0.0f));
		puntos2.add(new Vertice(0f, 2.5f, 0.5f));
		
		try {
			spline = new BSplineGenerica(puntos);
			spline2 = new BSplineGenerica(puntos2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			lineaRecta = new LineaRecta(new Vertice(0f, 0f, 0f), new Vertice(0.5f, 0.5f, 0.0f));
			//SuperficieBarrido = new SuperficieDeBarridoMejorada(spline, lineaRecta, 50, 50);
			SuperficieBarrido = new SuperficieDeBarrido(spline, spline2, 50, 50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//((BSplineGenerica)spline).test();
		//((LineaRecta)lineaRecta).test();
			
		//TODO:  CREACION DE LA LINEA DE PRODUCCION Y SETTEOS INICIALES /////
		//TODO: los shader se crean y actualizan solo dentro de la linea de produccion o de una clase utilidad q reciba una linea de produccion y maneje el shader
		
		
		//TODO: Settear las luces de la escena?? o mejor hacerlo en una clase que seahabitacion o algo asi y poner el setter aca
		DemoLight(gl);
		
		//this.linea.dibujar(gLDrawable);
		
			//glu.gluLookAt(0.0f, 0.0f, -1f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		//glu.gluLookAt(0,0,2, 0,0,0, 0,1,0);
		
    }
    
    
    void dibujarEjes(GL2 gl)
    {
    	gl.glBegin(GL2.GL_LINE);
    		gl.glColor3f(1.0f, 0.0f, 0.0f);
    		gl.glVertex3f(0.0f, 0.0f, -20.0f); 
    		gl.glVertex3f(0.0f, 0.0f, 20.0f);
    	gl.glEnd();
    	
    	gl.glBegin(GL2.GL_LINE);
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex3f(-20.0f, 0.0f, 0.0f); 
			gl.glVertex3f(20.0f, 0.0f, 0.0f);
		gl.glEnd();
		
	   	gl.glBegin(GL2.GL_LINE);
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex3f(0.0f, -20.0f, 0.0f); 
			gl.glVertex3f(0.0f, 20.0f, 0.0f);
		gl.glEnd();
 	
    }
    
    
    public void display(GLAutoDrawable gLDrawable)
    {	
    	if(this.timer >= actualizacionEscena)
    		this.timer = 0.0f;
    	
    	
    	final GL2 gl = gLDrawable.getGL().getGL2();
    	
    	GLProvider.SetUp(gLDrawable);

    	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

  		update(gl);
  		
  		boolean esCodigoGustavo = false;

  		if(esCodigoGustavo)
  		{
	  		gl.glPushMatrix();
	  		/////   TODO: DIBUJAR ACA   ////
	  		//fragment.changeFileName("fragmentGenerico2.frag");
	  		camara.render();
	  		
	  		gl.glPushMatrix();
	  		
  			gl.glColor3d(1.0f, 1.0f, 0.0f);
	  			//glut.glutSolidCube(10.0f);
	  			//glut.glutSolidCylinder(1.0f, 2.0f, 20, 20);
	  			
	  			gl.glColor4f(0.7f, 0.0f, 0.0f, 0.5f);
	  			gl.glPushMatrix();
	  				gl.glTranslatef(0.0f, 1.0f, 0.5f);
	  				//glut.glutSolidCube(0.5f);
	  				//glut.glutSolidCylinder(1.0f, 2.0f, 20, 20);
	  				//this.linea.dibujar(gLDrawable);
  				//this.piso.dibujar(gLDrawable);
	  				//this.linea = new LineaProduccion(mS, glut, glu, gLDrawable);
	  				//this.linea.dibujar(gLDrawable);
	  				//this.dispenser = new Dispenser(linea, mS, glut, glu, gLDrawable);
	  				//this.dispenser = new Dispenser(linea, mS);
	  				//botella = new Botella(mS,glut, glu, gLDrawable );
	  				//botella.dibujar(gLDrawable);
	  				botella.dibujar();
	  				//this.dispenser.dibujar(gLDrawable);
//	  				mS.usarPrograma(currentVert, GENERIC_FRAG);
//	  				if(this.timer == 0.0f){
//	  					
//	  					this.linea.actualizar();
//	  				}
	  			gl.glPopMatrix();
	  			
	  		
	  		gl.glPopMatrix();	
	  		
	  		gl.glPopMatrix();
  		}
  		else
  		{
			//camara.beginRender();
  			gl.glPushMatrix();
  				camara.render();
  				//spline.dibujar(gl, glu);
  			
  				gl.glScalef(0.5f, 0.5f, 0.5f);
  				
  				//SuperficieBarrido.dibujar(gLDrawable);
  			
  				
  				linea.dibujar(gLDrawable);
  				
  				//lineaRecta.dibujar(gl, glu);
  				
  				CintaTransportadora cinta = linea.getCinta();
  			
  				gl.glPushMatrix();
  					gl.glRotatef(90, -1, 0, 0);	
  					gl.glTranslatef(cinta.getReccorido().getX(u), cinta.getReccorido().getY(u), cinta.getReccorido().getZ(u));
  					gl.glPushMatrix();
  					gl.glScalef(0.5f, 0.5f, 0.5f);
  					glut.glutSolidCube(1.0f);
  				
  					gl.glPopMatrix();
  				gl.glPopMatrix();
  				
  				
  				//dibujarEjes(gl);
  				//mS.usarPrograma(currentVert, GENERIC_FRAG);

//  		    	mS.reiniciarAnimacion();
// 		
//  		    	fragment.setEfectoSemiMate();
//  		
//  		    	mS.displayUniform();
//  		    	mS.displayVertexAttrib();
  			gl.glPopMatrix();
//  	  		
//	  	  	mS.usarPrograma(currentVert, GENERIC_FRAG);
//	    	mS.reiniciarAnimacion();
//	
//	    	fragment.setEfectoSemiMate();
//	
//	    	mS.displayUniform();
//	    	mS.displayVertexAttrib();
  			
  			//camara.endRender();
  		}
  		////////////////////////////////
  		
	  	//En ves de glutSwapBuffers();.. va gl.glFlush();
	    gl.glFlush();
	    this.timer += 0.1f;
	    
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


    private void DemoLight(GL2 gl)
    {
      gl.glEnable(GL2.GL_LIGHTING);
      //gl.glEnable(GL2.GL_LIGHT0);
      
      
      gl.glEnable(GL2.GL_NORMALIZE);

      // Light model parameters:
      // -------------------------------------------

      float lmKa[] = {0.1f, 0.1f, 0.1f, 1.0f };

      gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, Utilidades.makeFloatBuffer(lmKa));

      gl.glLightModelf(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1.0f);
      gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, 0.0f);

    
//      float Kc = 1.0f;
//      float Kl = 0.0f;
//      float Kq = 0.0f;
//      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_CONSTANT_ATTENUATION,Kc);
//      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_LINEAR_ATTENUATION, Kl);
//      gl.glLightf(GL2.GL_LIGHT0, GL2.GL_QUADRATIC_ATTENUATION, Kq);
    
      // -------------------------------------------
      // Lighting parameters: 
      float light_pos[] = {-0.3f, 0.0f, -1.0f, 1.0f};   ///Curta coordenada en 0:Direccional y 1:Posicional o puntual
      float light_Ka[] = {0.3f, 0.3f, 0.3f, 1.0f }; //Del segundo tuto
      float light_Kd[]  = {1.0f, 1.0f, 1.0f, 1.0f};
      float light_Ks[]  = {1.0f, 1.0f, 1.0f, 1.0f}; //Brillante
      // -------------------------------------------
      // Spotlight Attenuation
      float spot_direction[] = {0.0f, 0.0f, 1.0f };
      
      
      int spot_exponent = 9;
      int spot_cutoff = 45;
      
      spot1 = LuzSpot.getLuzSpot(gl, light_Ka, light_Kd, light_Ks, spot_cutoff, spot_exponent, light_pos, spot_direction);
      
      float light_pos2[] = {0.3f, 0.0f, -1.0f, 1.0f};
      
      float light_Ka2[] = {1.0f, 0.0f, 0.0f, 1.0f }; //Del segundo tuto
      float light_Kd2[]  = {0.0f, 1.0f, 0.0f, 1.0f};
      float light_Ks2[]  = {0.8f, 0.8f, 0.8f, 1.0f}; //Brillante
      
      //LuzSpot.getLuzSpot(gl, light_Ka2, light_Kd2, light_Ks2, spot_cutoff, spot_exponent, light_pos2, spot_direction);

      // -------------------------------------------
      // Material parameters:

      float material_Ka[] = {0.0f, 0.9f, 0.7f, 1.0f}; //Color Material del brillante
      float material_Kd[] = {0.4f, 0.4f, 0.4f, 1.0f};
      float material_Ks[] = {0.6f, 0.6f, 0.6f, 1.0f }; //del segundo tuto
      float material_Ke[] = {0.1f, 0.1f, 0.1f, 0.0f};
      float material_Se = 60.0f; //del segundo tuto

      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, Utilidades.makeFloatBuffer(material_Ka));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, Utilidades.makeFloatBuffer(material_Kd));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, Utilidades.makeFloatBuffer(material_Ks));
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, Utilidades.makeFloatBuffer(material_Ke));
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
	
		//Seteos de la camara
        camara = new Camara(gl, glu, 0.0f, 0.0f, -1f, 0.0, 0.0);
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
		//CALCULO la diferencia entre posiciones actual y anterior
		double dx = e.getX() - posicionAnteriorMouse.getX();
		double dy = e.getY() - posicionAnteriorMouse.getY(); 
		
		
		double mouseSensitivity = 0.5f;
		double mouseSensitivityX = mouseSensitivity/this.W_WIDTH;
		double mouseSensitivityY = mouseSensitivity/this.W_HEIGHT;
		
	
        //controll camera yaw from x movement fromt the mouse
        camara.yawDerecha(dx * mouseSensitivity);
        //controll camera pitch from y movement fromt the mouse
        camara.pitchArriba(dy * mouseSensitivity);
		
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
		
		float velRotacion = 1000.0f;
		
		//rotacionCamaraX += (e.getX() - posicionAnteriorMouse.getX()) / velRotacion;
		//rotacionCamaraY += (e.getY() - posicionAnteriorMouse.getY()) / velRotacion;

		
		//posicionAnteriorMouse.setLocation(e.getX(), e.getY());
		
		//convertir todas las letras a mayusculas o minusculas
		switch (key.getKeyChar()) {
	    	case KeyEvent.VK_ESCAPE:
	    		System.exit(0);
	    		break;
			case 'w':                       // Move forwards
	            camara.avanzar(0.1);
	            break;
			case 's':                         // Move backwards
	            camara.avanzar(-0.1);
	            break;  
			case 'j':                      // Pitch up
	            camara.pitchArriba(0.7);
	            break;
			case 'k':                         // Pitch down
	            camara.pitchAbajo(0.7);
	            break;
			case 'q':                        // Turn left
	            camara.yawIzquierda(0.7);
	            break;
	        case 'e':                       // Turn right
	            camara.yawDerecha(0.7);
	            //camara.look(10);
	            break;
	        case 'a':                        // Strafe left
	            camara.desplazarIzquierda(0.1);
	            break;
	        case 'd':                       // Strafe right
	            camara.desplazarDerecha(0.1);
	            break;	
//	    	case 'r':
////	    		R Reiniciar la animaci�n de crecimiento
//	    		System.out.println("Reiniciar animaci�n");
//
//	    		break;
//	    	case 'p':
////	    		P Pausar/reanudar animaci�n
//	    		String texto = (this.pause)? "Reanudar": "Pausar";
//	    		System.out.println(texto);
//	    		this.pause = !this.pause;
//	    		break;
//	    	case 'q':
////	    		Q incrementar velocidad de crecimiento
//	    		
//	    		break;
//	    	case 'a':
////	    		A decrementar velocidad de crecimiento
//	    		break;
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