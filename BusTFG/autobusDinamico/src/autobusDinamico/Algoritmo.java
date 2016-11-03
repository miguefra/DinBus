package autobusDinamico;

import java.util.concurrent.CountDownLatch;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.gson.*;

public class Algoritmo {
	
	int tiempoGoogle = 0;
	private static final String API_KEY = "AIzaSyDqoXN6gR5nSYywRJ720kmte-505TUf9FY";  // Id API Google
	GeoApiContext context; 
	
	public Algoritmo() {  // El constructor de la clase algoritmo establece los parámetros de la conexión con la API de Google
		context = new GeoApiContext();
	    context.setApiKey(API_KEY);
	}

	public int googleTime(Coordenadas destino1, Coordenadas ubicacion1) throws InterruptedException {

	    DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context,  // Nueva variable para la request a google, con un destino y un origen
	    new String[]{destino1.getX() + ", " + destino1.getY()},
	    new String[]{ubicacion1.getX() + ", " + ubicacion1.getY()});

	    final CountDownLatch latch = new CountDownLatch(1);
	    req.setCallback(new PendingResult.Callback<DistanceMatrix>() {
	      @Override
	      public void onResult(DistanceMatrix result) {  // Cuando recibimos la respuesta en JSON buscamos el tiempo ("inSeconds") que tarda de un punto a otro 
	  	    boolean found = false;
		    String[] json;
		    int tiempo = 0;
		    int i = 0;
		    String test = "\"inSeconds\":";
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String userjson = gson.toJson(result);
        	json = userjson.split(" ");

	        while(!found){
	        	if (json[i].equals(test)) {
	        		found = true;
	        		tiempo = Integer.parseInt(json[i+1].split(",")[0]);
	        	}
	        	i++;
	        }
    		tiempoGoogle = tiempo;
	      }

	      @Override
	      public void onFailure(Throwable e) {
	        System.out.println("Exception thrown: "+e);
	        latch.countDown();
	      }
	    });

	    //latch.await();
	    return tiempoGoogle;
	}
	
    public Autobus algorithm() {  // Base para probar algoritmos que construyamos
    	int numBuses = 2;
    	Autobus bus1, bus2, bustemp = null;  // Creamos dos autobuses con diferentes orígenes y destinos, y un usuario que va de un punto a otro
    	Autobus autobuses[] = new Autobus[numBuses];    	
    	bus1 = new Autobus(autobuses, new Coordenadas(40.453212, -3.733868), new Coordenadas(40.853212, -3.713868), 1);
    	bus2 = new Autobus(autobuses, new Coordenadas(41.453212, -3.733868), new Coordenadas(38.453212, -1.733868), 2);
    	Usuario usu = new Usuario(new Coordenadas(39.453212, -2.733868),new Coordenadas(41.453212, -3.733868));
    	
    	for (int j = 0; j < autobuses.length; j++) {
    		bustemp = decidirAutobus(bus1, bus2, usu, j); // La parada no es exactamente donde est· el usuario, podemos hacerle andar.
    		bustemp.addParada(j, usu.getOrigen());
    	}
    	
    	return bustemp;
    }
    
    Autobus decidirAutobus(Autobus bus1, Autobus bus2, Usuario usu, int i) {
	   	double diferencia1= 0, diferencia2 = 0, distanciaOriginalBus1 = 0, distanciaOriginalBus2 = 0;
		double distanciaBus1Usuario = 0, distanciaBus2Usuario = 0, distanciaNuevaBus1 = 0, distanciaNuevaBus2 = 0;

		try {
			distanciaOriginalBus1 = googleTime(bus1.getDestinoActual(), bus1.getUbicacionActual());
		   	distanciaOriginalBus2 = googleTime(bus2.getDestinoActual(), bus2.getUbicacionActual());
		    distanciaBus1Usuario = googleTime(usu.getOrigen(), bus1.getUbicacionActual());  // Cogemos el origen del usuario, pero lo tenemos que establecer antes
		   	distanciaBus2Usuario = googleTime(usu.getOrigen(), bus2.getUbicacionActual());
		   	distanciaNuevaBus1 = googleTime(usu.getDestino(), bus1.getUbicacionActual());
		   	distanciaNuevaBus2 = googleTime(usu.getDestino(), bus2.getUbicacionActual());
		} catch (InterruptedException e) {
			// En caso de que la conexión con Google falle.
			System.err.println("Error al realizar petición a Google");
		}
	
	   	diferencia1 = distanciaOriginalBus1 - (distanciaBus1Usuario + distanciaNuevaBus1);
	   	diferencia2 = distanciaOriginalBus2 - (distanciaBus2Usuario + distanciaNuevaBus2);
	   
	   	if (Math.abs(diferencia1) < Math.abs(diferencia2)) {
	   		return bus1; // Falta comprobar si est· mas alejado del destino actual
	   	}
	   	else {
	   		return bus2;
	   	}
   }

}
