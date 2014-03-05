
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jorge.jimenez
 */
public class ReadImage {

    public static void main(String[] args) {
        int R = 0;
        int G = 0;
        int B = 0;


        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("c://SocialTmp/mapaMexico.gif"));
        } catch (IOException e) {
        }
        System.out.println("img:"+img);
        
        // Leemos algunos pixeles ....
        System.out.println("Pix 1 = " + img.getRGB(10,10));
        System.out.println("Pix 2 = " + img.getRGB(10,11));
        System.out.println("Pix 3 = " + img.getRGB(10,21));

        int rgb = 0xFF00FF00; // Asignamos colo a la Variable
        
        //PUNTO 1 (32.65325, -117.14722)
        img.setRGB(0, 0, rgb); // Escribimos Pixel 
        img.setRGB(0, 1, rgb); // Escribimos Pixel 
        img.setRGB(0, 2, rgb); // Escribimos Pixel 
        img.setRGB(50, 3, rgb); // Escribimos Pixel 
        img.setRGB(0, 4, rgb); // Escribimos Pixel 
        img.setRGB(0, 5, rgb); // Escribimos Pixel 
        img.setRGB(0, 6, rgb); // Escribimos Pixel 
        img.setRGB(0, 7, rgb); // Escribimos Pixel 
        img.setRGB(0, 8, rgb); // Escribimos Pixel 
        img.setRGB(0, 9, rgb); // Escribimos Pixel 
        img.setRGB(0, 10, rgb); // Escribimos Pixel 
        img.setRGB(0, 11, rgb); // Escribimos Pixel 
        img.setRGB(0, 12, rgb); // Escribimos Pixel 
        img.setRGB(0, 13, rgb); // Escribimos Pixel 
        img.setRGB(0, 14, rgb); // Escribimos Pixel 
        img.setRGB(0, 15, rgb); // Escribimos Pixel 
        img.setRGB(0, 16, rgb); // Escribimos Pixel 
        img.setRGB(0, 17, rgb); // Escribimos Pixel 
        img.setRGB(0, 18, rgb); // Escribimos Pixel 
        img.setRGB(0, 19, rgb); // Escribimos Pixel 
        img.setRGB(0, 20, rgb); // Escribimos Pixel 
        img.setRGB(0, 21, rgb); // Escribimos Pixel 
        img.setRGB(0, 22, rgb); // Escribimos Pixel 
        img.setRGB(0, 23, rgb); // Escribimos Pixel 
        img.setRGB(0, 24, rgb); // Escribimos Pixel 
        img.setRGB(0, 25, rgb); // Escribimos Pixel 
        img.setRGB(0, 26, rgb); // Escribimos Pixel 
        img.setRGB(0, 27, rgb); // Escribimos Pixel 
        img.setRGB(0, 28, rgb); // Escribimos Pixel 
        img.setRGB(0, 29, rgb); // Escribimos Pixel 
        img.setRGB(0, 30, rgb); // Escribimos Pixel 
        
        
        //PUNTO 2 (13.85408, -86.71509)
        img.setRGB(3899, 2751, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2750, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2749, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2748, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2747, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2746, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2745, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2744, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2743, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2742, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2741, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2740, rgb); // Escribimos Pixel  
        img.setRGB(3899, 2739, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2738, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2737, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2736, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2735, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2734, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2733, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2732, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2731, rgb); // Escribimos Pixel 
        img.setRGB(3899, 2730, rgb); // Escribimos Pixel 
        
        //PUNTO 3 -->Aguascalientes (21.95005, -102.33833)
        rgb = 0xFF0000FF; // Asignamos colo a la Variable
        img.setRGB(1835, 1720, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1721, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1722, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1723, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1724, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1725, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1726, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1727, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1728, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1729, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1730, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1731, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1732, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1733, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1734, rgb); // Escribimos Pixel 
        img.setRGB(1835, 1735, rgb); // Escribimos Pixel 
        
        //PUNTO 4-->DF
        rgb = 0xFF00FF00;
        img.setRGB(876, 1161, rgb); // Escribimos Pixel 
        img.setRGB(876, 1162, rgb); // Escribimos Pixel 
        img.setRGB(876, 1163, rgb); // Escribimos Pixel 
        img.setRGB(876, 1164, rgb); // Escribimos Pixel 
        img.setRGB(876, 1165, rgb); // Escribimos Pixel 
        img.setRGB(876, 1166, rgb); // Escribimos Pixel 
        /*
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1735, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1074, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1075, rgb); // Escribimos Pixel 
        img.setRGB(2308, 1076, rgb); // Escribimos Pixel 
        */
        
        
        try{
            ImageIO.write(img, "gif", new File("c://SocialTmp/MapaMexico_1.gif"));
        }catch(IOException io){
            io.printStackTrace();
        }
        
        ////////////////////////////
        //CONVERTING LAT-LNG TO X,Y
       /////////////////////////////
       //Aguascalientes
       //double lat=21.95005; 
       //double lng=-102.33833; 
       //DF
       double lat=19.43244; 
       double lng=-99.13323; 
       
       
       // Map image size (in points)
       Double mapWidth = 3900.0;
       Double mapHeight = 2752.0;
       
        
       //Intento 1
       /* 
       int MAP_WIDTH=3899;
       int MAP_HEIGHT=2751;
       double y = Math.round(((-1 * lat) + 90) * (MAP_HEIGHT / 180));
       double x = Math.round((lng + 180) * (MAP_WIDTH / 360));
       System.out.println("X:"+x+",Y:"+y);
       * */
      
        //Intento 2
       
       
        double minLat = 13.85408;
        double minLong = -117.14722;
        double maxLat = 32.65325;
        double maxLong = -86.71509;
       
       /*
        double minLat = -85.05112878;
        double minLong = -180;
        double maxLat = 85.05112878;
        double maxLong = 180;
       */
        /*
        // Determine the map scale (points per degree)
        double xScale = mapWidth/ (maxLong - minLong);
        double yScale = mapHeight / (maxLat - minLat);

        // position of map image for point
        double x = (lng - minLong) * xScale;
        double  y = - (lat + minLat) * yScale;

        System.out.println("final coords: " + x + " " + y); 
        */
        
        //Intento 3
        /*
        double mapLonLeft = -180;
        double mapLonRight = 180;
        double mapLonDelta = mapLonRight - mapLonLeft;

        double mapLatBottom = -85.05112878;
        double mapLatBottomDegree = mapLatBottom * Math.PI / 180;
        double worldMapWidth = ((mapWidth / mapLonDelta) * 360) / (2 * Math.PI);
        double mapOffsetY = (worldMapWidth / 2 * Math.log((1 + Math.sin(mapLatBottomDegree)) / (1 - Math.sin(mapLatBottomDegree))));

        double x = (lng - mapLonLeft) * (mapWidth / mapLonDelta);
        double y = 0.1;
        if (lat < 0) {
            lat = lat * Math.PI / 180;
            y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(lat)) / (1 - Math.sin(lat)))) - mapOffsetY);
        } else if (lat > 0) {
            lat = lat * Math.PI / 180;
            lat = lat * -1;
            y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(lat)) / (1 - Math.sin(lat)))) - mapOffsetY);
            System.out.println("y before minus: " + y);
            y = mapHeight - y;
        } else {
            y = mapHeight / 2;
        }
        System.out.println("X:"+x);
        System.out.println("Y:"+y);
        * */
        
        
        //Intento 4
        // get x value
        double x = (lng+180)*(mapWidth/360);

        // convert from degrees to radians
        double latRad = lat*Math.PI/180;

        // get y value
        double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
        double y = (mapHeight/2)-(mapWidth*mercN/(2*Math.PI));
       
        System.out.println("x:"+x+",Y:"+y);
        
        img.setRGB(876, 1161, rgb); // Escribimos Pixel 
    }
}