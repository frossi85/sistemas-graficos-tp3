package objetosEscena;

import utilidades.Animable;
import utilidades.LineaProduccion;
import utilidades.Objeto3D;
import utilidades.Vertice;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import model.geometry.Model;

public class Rellenador extends Objeto3D implements Animable {
	public void liberarLiquido(){}
	
	public Rellenador(LineaProduccion linea){
		super("model/examples/models/obj/llenadora.obj");
		
		this.posicion = new Vertice(0f,0f,0f);
		addObserver(linea);
	}
		

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
