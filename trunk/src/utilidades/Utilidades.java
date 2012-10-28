package utilidades;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL2;

public class Utilidades {
	
    public static FloatBuffer makeFloatBuffer(float[] arr) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
    }
    
    public static void glVertex(Vertice p)
    {
    	GLProvider.getGL2().glVertex3f(p.getX(), p.getY(), p.getZ());
    }
    
    public static void glNormal(Vertice p)
    {
    	GLProvider.getGL2().glNormal3f(p.getX(), p.getY(), p.getZ());
    }
    
    public static Vertice getFaceNormal(Vertice v1, Vertice v2, Vertice v3)
    {		
		Vertice v21 = Vertice.restar(v2, v1);
		Vertice v22 = Vertice.restar(v3, v1);
				
		return Vertice.productoVectorial(v21, v22).normalizar();		
    }
    
    public static Vertice getVertexNormal(Vertice vCentral, ArrayList<Vertice> adyacentes)
    {		
		ArrayList<Vertice> normales = new ArrayList<Vertice>();
		int cantidadAdyacentes = adyacentes.size();
		
		for(int i = 0; i < cantidadAdyacentes - 1 ; i++) {
			normales.add(getFaceNormal(vCentral, adyacentes.get(i), adyacentes.get(i+1)));
		}
		normales.add(getFaceNormal(vCentral, adyacentes.get(cantidadAdyacentes-1), adyacentes.get(0)));
		
		return Vertice.sumar(normales);	
    }
    
    public static Vertice getDistancia(Vertice v1, Vertice v2) {
		float x = getDistancia(v1.getX(), v2.getX());
		float y = getDistancia(v1.getY(), v2.getY());
		float z = getDistancia(v1.getZ(), v2.getZ());
		
		return new Vertice(x, y, z);
	}
	
	public static float getDistancia(float x1, float x2)
	{
		return (x2 - x1);
	}
	
	public static Vertice getNormalVerticeIJ(ArrayList<ArrayList<Vertice>> grilla, int i, int j) {
		ArrayList<Vertice> adyacentes = new ArrayList<Vertice>();
		int iMax = grilla.size();
		int jMax = grilla.get(0).size();
		float iPrevio = i - 1;
		float iSiguiente = i + 1;
		float jPrevio = j - 1;
		float jSiguiente = j + 1;
		
		if(!(iPrevio < 0))
			adyacentes.add(grilla.get(i-1).get(j));
		
		if(!(jPrevio < 0))
			adyacentes.add(grilla.get(i).get(j-1));
		
		if(iSiguiente < iMax && !(jPrevio < 0))
			adyacentes.add(grilla.get(i+1).get(j-1));
		
		if(iSiguiente < iMax)
			adyacentes.add(grilla.get(i+1).get(j));
		
		if(jSiguiente < jMax)
			adyacentes.add(grilla.get(i).get(j+1));
		
		if(!(iPrevio < 0) && jSiguiente < jMax)
			adyacentes.add(grilla.get(i-1).get(j+1));
		
		return Utilidades.getVertexNormal(grilla.get(i).get(j), adyacentes);
	}
}
