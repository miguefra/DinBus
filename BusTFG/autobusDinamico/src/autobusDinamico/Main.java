package autobusDinamico;

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
    private static Autobus bustemp;

	 
    public static void main(String[] args) {  // Visualización Swing del mapa.
        
        Algoritmo algorithm = new Algoritmo(); // Llamada al algoritmo que nos dice que autobus es el que modifica su ruta
		bustemp = algorithm.algorithm();
        System.out.println(bustemp);
    	
    	final Browser browser = new Browser();  // Creamos un "navegador" de la librería jxbrowser para poder visualizar el mapa
        
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
     
        JButton setMarkerButton = new JButton("Marcador 1");  // Marcador que situa la ubicación actual del autobus elegido
        setMarkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.executeJavaScript("var myLatlng = new google.maps.LatLng(" + bustemp.getUbicacionActual().getX() +" ," + bustemp.getUbicacionActual().getY() +");\n" +
                        "var marker = new google.maps.Marker({\n" +
                        "    position: myLatlng,\n" +
                        "    map: map,\n" +
                        "    title: 'Hello World!'\n" +
                        "});");
            }
        });
       
//        JButton setMarkerButton2 = new JButton("Marcador 2");  // Marcador que situa el destino final del autobus elegido (si el destino está mas adelantado que el destino final hay que actualizar el destino del bus)
//        setMarkerButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                browser.executeJavaScript("var myLatlng2 = new google.maps.LatLng(" + bustemp.getDestinoActual().getX() +" ," + bustemp.getDestinoActual().getY() +");\n" +
//                        "var marker2 = new google.maps.Marker({\n" +
//                        "    position: myLatlng2,\n" +
//                        "    map: map,\n" +
//                        "    title: 'Hello World!'\n" +
//                        "});");
//            }
//        });
//        
        JButton setMarkerButton2 = new JButton("Ruta autobus elegido");  // Debería mostrar ruta de autobus solución
        setMarkerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
		        browser.executeJavaScript( "   origin: " + bustemp.getUbicacionActual().getX() +" ," + bustemp.getUbicacionActual().getY() +	 
		        		"  destination: " + bustemp.getDestinoActual().getX() +" ," + bustemp.getDestinoActual().getY() + 
		        	    " travelMode: google.maps.TravelMode.DRIVING " +
		        	  "}, function(response, status) { " +
		        	   " if (status === google.maps.DirectionsStatus.OK) {" +
		        	   "   directionsDisplay.setDirections(response);" + 
		        	    "} else { " +
		        	      "window.alert('Directions request failed due to ' + status); " +
		        	   " } " +
		        	"  });");
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
        
        //browser.loadURL("http://maps.google.es");
 
        browser.loadURL("file:///C:/Users/Miguel/Documents/DinBus/BusTFG/autobusDinamico/resources/maps.html");
		
    }
    
}