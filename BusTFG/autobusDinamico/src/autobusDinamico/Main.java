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
        Algoritmo algorithm = new Algoritmo();
        try {
			algorithm.main(args);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
}