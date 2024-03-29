package curva;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import utilidades.Vertice;

public interface ICurva3D {

	float getX(float u);
	
	float getY(float u);
	
	float getZ(float u);
	
	Vertice getPoint(float u);
	
	void dibujar(GL2 gl, GLU glu);
}
