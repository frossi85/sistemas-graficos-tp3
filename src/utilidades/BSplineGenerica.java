package utilidades;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class BSplineGenerica implements ICurva3D {
	
	private ArrayList<BSplineCuadratica> listaDeCurvas;	//lista de curvas de bezier
	private int numCurvas;

	public BSplineGenerica(ArrayList<PuntoDeControl> puntosDeControl) throws Exception {
				
		listaDeCurvas = new ArrayList<BSplineCuadratica>();
		
		for(int i = 0; i < puntosDeControl.size() - 2; i ++){
			ArrayList<PuntoDeControl> auxL = new ArrayList<PuntoDeControl>();
			auxL.add(puntosDeControl.get(i));
			auxL.add(puntosDeControl.get(i+1));
			auxL.add(puntosDeControl.get(i+2));
			BSplineCuadratica auxB = new BSplineCuadratica(auxL);
			listaDeCurvas.add(auxB);
		}	
		
		numCurvas = listaDeCurvas.size();
		System.err.print(numCurvas);
	}
	
	private int getIndiceCurvaSeleccionada(float u)
	{
		return (int) (u * (float) numCurvas); //Ver si el casteo esta bien o hay que hacer algun metodo
		//de redondeo
	}
	
	private BSplineCuadratica getCurvaAEvaluar(float u) {
		return listaDeCurvas.get(getIndiceCurvaSeleccionada(u));
	}
	
	private float getPasoNormalizado(float u) {
		int indice = getIndiceCurvaSeleccionada(u);
		float ancho = 1.0f/ ((float) numCurvas);
		float c = u - ((float) indice /  (float) numCurvas);
		
		return c / ancho;
	}
	
	public float getX(float u) {
		return getCurvaAEvaluar(u).getX(getPasoNormalizado(u));
	}
	
	public float getY(float u) {
		return getCurvaAEvaluar(u).getY(getPasoNormalizado(u));
	}
	
	public float getZ(float u) {
		return getCurvaAEvaluar(u).getZ(getPasoNormalizado(u));
	}
	
	public void test() {
		System.out.println("Cantidad de Curvas =" + numCurvas);
		
		float u = 0;
		float varianza = 0.0000001f;
		
		for(int i = 0; i < numCurvas; i++) {
			u = (float) i / numCurvas;
			System.out.println("U =" + u + " => IndiceCurva =" + getIndiceCurvaSeleccionada(u));
			System.out.println("Unormalizado =" + getPasoNormalizado(u));
			System.out.println("U' =" + (u - varianza) + " => IndiceCurva =" + getIndiceCurvaSeleccionada((u - varianza)));
			System.out.println("Unormalizado' =" + getPasoNormalizado(u - varianza));
		}
	} 
	
	public void dibujar(GL2 gl, GLU glu) {
		/// the level of detail of the curve
				int LOD=300;
				
				
				gl.glBegin(GL2.GL_LINE_STRIP);


				// use the parametric time value 0 to 1			
				float paso = 1.0f / LOD;
				float t = 0f;
				
				for(int i=0;i<=LOD;i++) {

					// sum the control points mulitplied by their respective blending functions
					float x = getX(t);

					float y = getY(t);

					float z = getZ(t);

					// specify the point
					gl.glVertex3f( x,y,z );
					
					t += paso;
				}
				
				gl.glEnd();
				
				
	}
	
	public void dibujarTodoDeAUnaCurva(GL2 gl, GLU glu)
	{
		for(int i = 0; i < listaDeCurvas.size(); i ++){
			((BSplineCuadratica) listaDeCurvas.get(i)).dibujar2(gl, glu);			
		}
	}
}
