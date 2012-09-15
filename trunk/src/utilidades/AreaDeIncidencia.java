package utilidades;


public class AreaDeIncidencia {
	
	private Vertice vertice1;
	private Vertice vertice2;
	private Vertice vertice3;
	private Vertice vertice4;
	private float [] maximos;	// x,y,z maximos respectivamente
	private float [] minimos; 	// x,y,z minimos respectivamente
	
	public AreaDeIncidencia(Vertice vert1, Vertice vert2, Vertice vert3, Vertice vert4){	// consideramos el caso en q el area es un plano. (se genera con 3 puntos)
		
		vertice1 = vert1;
		vertice2 = vert2;
		vertice3 = vert3;
		vertice4 = vert4;
		
		maximos = new float[3];
		minimos = new float[3];
		
		maximos = getMaximos(vert1, vert2, vert3, vert4);
		minimos = getMinimos(vert1, vert2, vert3, vert4);
		
				
	}
	
	public boolean estaAdentro(Vertice p1, Vertice p2, Vertice p3, Vertice p4){
		float maxTemp[] = new float [3];	// representan los max y min del area pasada por parametro
		float minTemp[] = new float [3];
		
		maxTemp = getMaximos(p1, p2, p3, p4);
		minTemp = getMinimos(p1, p2, p3, p4);
		
		return(estaAdentroX(maxTemp[0], minTemp[0]) && (estaAdentroY(maxTemp[1], minTemp[1])) && (estaAdentroZ(maxTemp[2], minTemp[2])));
	}
	
	float[] getMaximos(Vertice p1, Vertice p2, Vertice p3, Vertice p4){
		float max[] = new float[3];
		max[0] = getMaximo(p1.getX(), p2.getX(), p3.getX(),  p4.getX());
		max[1] = getMaximo(p1.getY(), p2.getY(), p3.getY(), p4.getY());
		max[2] = getMaximo(p1.getZ(), p2.getZ(), p3.getZ(), p4.getZ());
		return max;		
	}
	
	float getMaximo(float a, float b, float c, float d){
		if(a >= b){
			if(a >= c){
				if(a >= d) return a; return d;
			}
			else{
				if(c >= d) return c; return d;
			}
		}
		else{
			if(b >= c) {
				if(b >= d) return b; return d; 
			}
			else{
				if(c >= d) return c; return d;
			}
		}
	}
	
	float[] getMinimos(Vertice p1, Vertice p2, Vertice p3, Vertice p4){
		float min[] = new float[3];
		min[0] = getMinimo(p1.getX(), p2.getX(), p3.getX(), p4.getX());
		min[1] = getMinimo(p1.getY(), p2.getY(), p3.getY(), p4.getY());
		min[2] = getMinimo(p1.getZ(), p2.getZ(), p3.getZ(), p4.getZ());
		return min;		
	}
	
	float getMinimo(float a, float b, float c, float d){
		if(a <= b){
			if(a <= c){
				if(a <= d) return a; return d;
			}
			else{
				if(c <= d) return c; return d;
			}
		}
		else{
			if(b <= c) {
				if(b <= d) return b; return d; 
			}
			else{
				if(c <= d) return c; return d;
			}
		}
	}
	
	boolean estaAdentroX(float xMax, float xMin){
		return ((xMax <= maximos[0]) && (xMin >= minimos[0]));
	}
	
	boolean estaAdentroY(float yMax, float yMin){
		return ((yMax <= maximos[1]) && (yMin >= minimos[1]));
	}
	
	boolean estaAdentroZ(float zMax, float zMin){
		return ((zMax <= maximos[2]) && (zMin >= minimos[2]));
	}
	
	public Vertice[] getArea(){
		Vertice resultado[] = new Vertice [4];
		resultado[0] = vertice1;
		resultado[2] = vertice2;
		resultado[3] = vertice4;
		return resultado;
	}

}
