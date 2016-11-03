package autobusDinamico;

import java.util.concurrent.CountDownLatch;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.gson.*;

public class Algoritmo {
	
	private static final String API_KEY = "AIzaSyDqoXN6gR5nSYywRJ720kmte-505TUf9FY";

	public Algoritmo() {
		super();
	}

	public static void main(String[] args) throws InterruptedException {
		GeoApiContext context = new GeoApiContext();
	    context.setApiKey(API_KEY);
	    DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context,
	    new String[]{"ciudad universitaria"},
	    new String[]{"calle alcala 328 madrid"});

	    final CountDownLatch latch = new CountDownLatch(1);
	    req.setCallback(new PendingResult.Callback<DistanceMatrix>() {
	      @Override
	      public void onResult(DistanceMatrix result) {
	  	    boolean found = false;
		    String[] json;
		    int tiempo;
		    int i = 0;
		    String test = "\"inSeconds\":";
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String userjson = gson.toJson(result);
	        System.out.println(userjson);
        	json = userjson.split(" ");

	        while(!found){
	        	if (json[i].equals(test)) {
	        		found = true;
	        		tiempo = Integer.parseInt(json[i+1].split(",")[0]);
	        		System.out.println(tiempo);
	        	}
	        	i++;
	        }
	      }

	      @Override
	      public void onFailure(Throwable e) {
	        System.out.println("Exception thrown: "+e);
	        latch.countDown();
	      }
	    });

	    // We have to hold the main thread open until callback is called by OkHTTP.
	    latch.await();
	}
	
    public void algorithm() {
    	int numBuses = 2;
    	Autobus bus1, bus2;
    	Autobus autobuses[] = new Autobus[numBuses];    	
    	bus1 = new Autobus(autobuses, new Coordenadas(5,5), new Coordenadas(5,5));
    	bus2 = new Autobus(autobuses, new Coordenadas(5,5), new Coordenadas(5,5));
    	Usuario usu = new Usuario(new Coordenadas(0,0),new Coordenadas(10,10));
    	
    	for (int j = 0; j < autobuses.length; j++) {

    		decidirAutobus(bus1, bus2, usu, j).addParada(j, usu.getOrigen()); // La parada no es exactamente donde est· el usuario, podemos hacerle andar.

    	}

    }
    
    double calcularDistancias(Coordenadas destino1, Coordenadas ubicacion1) {
	   	int x = 0; // LLamar api google
	   	//GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDqoXN6gR5nSYywRJ720kmte-505TUf9FY");
	   	String[] origen = new String[1];
	   	origen[0] = "40.453212, -3.733868";
	   	String[] destino = new String[1];
	   	destino[0] = "40.753212, -3.733868";
	   	//DistanceMatrixApiRequest a = DistanceMatrixApi.getDistanceMatrix(context, origen,destino);

	   	return x;
   }

    Autobus decidirAutobus(Autobus bus1, Autobus bus2, Usuario usu, int i) {
	   	double diferencia1= 0, diferencia2 = 0;
	  	double distanciaOriginalBus1 = calcularDistancias(bus1.getDestinoActual(), bus1.getUbicacionActual());
	   	//double distanciaOriginalBus2 = calcularDistancias(bus2.getDestinoActual(), bus2.getUbicacionActual());
	   	//double distanciaBus1Usuario = calcularDistancias(usu.getOrigen(), bus1.getUbicacionActual());  // Cogemos el origen del usuario, pero lo tenemos que establecer antes
	   	//double distanciaBus2Usuario = calcularDistancias(usu.getOrigen(), bus2.getUbicacionActual());
	   	
	   	//double distanciaNuevaBus1 = calcularDistancias(usu.getDestino(), bus1.getUbicacionActual());
	   	//double distanciaNuevaBus2 = calcularDistancias(usu.getDestino(), bus2.getUbicacionActual());
	
	   	//diferencia1 = distanciaOriginalBus1 - (distanciaBus1Usuario + distanciaNuevaBus1);
	   	//diferencia2 = distanciaOriginalBus2 - (distanciaBus2Usuario + distanciaNuevaBus2);
	   	

	   	if (Math.abs(diferencia1) < Math.abs(diferencia2)) {
	   		System.out.println("BUS 1");
	   		return bus1; // Falta comprobar si est· mas alejado del destino actual
	   	}
	   	else {
	   		System.out.println(distanciaOriginalBus1);
	   		return bus2;
	   	}
   }

}
