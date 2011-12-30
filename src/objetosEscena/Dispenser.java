package objetosEscena;

import utilidades.Dibujable;

public class Dispenser implements Dibujable {

	
	public Botella entregarBotella(){
		return new Botella(null);
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}
