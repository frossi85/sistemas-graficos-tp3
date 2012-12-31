package objetosEscena;

import utilidades.Objeto3D;
import utilidades.Vertice;
import model.iModel3DRenderer;

public class Empaquetador extends Objeto3D {

	int cantidadBotellasRecibidas;
	public static int CAPACIDAD_BOTELLAS = 4;

	public Empaquetador(LineaProduccion linea, Rampa rampa) {
		super("model/examples/models/obj/empaquetadora.obj");
		this.cantidadBotellasRecibidas = 0;
		this.posicion = new Vertice(0,0,0);
	}
	
	public void recibirBotella(Botella botella){	// cuando recibo botellas notifico de cambio a observadores
		this.cantidadBotellasRecibidas++;
		if(this.cantidadBotellasRecibidas == 4){
			this.cantidadBotellasRecibidas = 0;
		}
	}
	
	public int getCantidadBotellasRecibidas(){
		return this.cantidadBotellasRecibidas;
	}
}
