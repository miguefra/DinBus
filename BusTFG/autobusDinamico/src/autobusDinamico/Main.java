package autobusDinamico;

import com.google.maps.DistanceMatrixApi;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;
	private static int zoomValue = 15;
    
    static double calcularDistancias(Coordenadas destino1, Coordenadas ubicacion1) {
    	int x = 0; // LLamar api google
    	//DistanceMatrixApi.getDistanceMatrix(context, origins, destinations);
    	//https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR&key=YOUR_API_KEY

    	
    	return x;
    }

    static Autobus decidirAutobus(Autobus bus1, Autobus bus2, Usuario usu, int i) {
    	double diferencia1, diferencia2 = 0;
    	double distanciaOriginalBus1 = calcularDistancias(bus1.getDestinoActual(), bus1.getUbicacionActual());
    	double distanciaOriginalBus2 = calcularDistancias(bus2.getDestinoActual(), bus2.getUbicacionActual());
    	double distanciaBus1Usuario = calcularDistancias(usu.getOrigen(), bus1.getUbicacionActual());  // Cogemos el origen del usuario, pero lo tenemos que establecer antes
    	double distanciaBus2Usuario = calcularDistancias(usu.getOrigen(), bus2.getUbicacionActual());
    	
    	double distanciaNuevaBus1 = calcularDistancias(usu.getDestino(), bus1.getUbicacionActual());
    	double distanciaNuevaBus2 = calcularDistancias(usu.getDestino(), bus2.getUbicacionActual());

    	diferencia1 = distanciaOriginalBus1 - (distanciaBus1Usuario + distanciaNuevaBus1);
    	diferencia2 = distanciaOriginalBus2 - (distanciaBus2Usuario + distanciaNuevaBus2);
    	
    	if (Math.abs(diferencia1) < Math.abs(diferencia2)) {
    		System.out.println("BUS 1");
    		return bus1; // Falta comprobar si est· mas alejado del destino actual
    	}
    	else {
    		System.out.println("BUS 2");
    		return bus2;
    	}
    }
	
    public static void algorithm() {
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
	 
    public static void main(String[] args) {  // Visualización Swing del mapa.
        final Browser browser = new Browser();
        
        JButton zoomInButton = new JButton("Acercar");
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });
 
        JButton zoomOutButton = new JButton("Alejar");
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });
     
        JButton setMarkerButton = new JButton("Marcador 1");
        setMarkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript("var myLatlng = new google.maps.LatLng(40.453212,-3.733868);\n" +
                        "var marker = new google.maps.Marker({\n" +
                        "    position: myLatlng,\n" +
                        "    map: map,\n" +
                        "    title: 'Hello World!'\n" +
                        "});");
            }
        });
       
        JButton setMarkerButton2 = new JButton("Marcador 2");
        setMarkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript("var myLatlng2 = new google.maps.LatLng(41.453212,-3.733868);\n" +
                        "var marker2 = new google.maps.Marker({\n" +
                        "    position: myLatlng2,\n" +
                        "    map: map,\n" +
                        "    title: 'Hello World!'\n" +
                        "});");
            }
        });
        
        BrowserView view = new BrowserView(browser);
 
        JPanel toolBar = new JPanel();
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(setMarkerButton);
        toolBar.add(setMarkerButton2);
 
        JFrame frame = new JFrame("Autobuses Dinámicos");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
 
        browser.loadURL("file:///Users/naxo_guerra/Documents/workspace/autobusDinamico/src/autobusDinamico/maps.html");
        algorithm();
    }
    
}