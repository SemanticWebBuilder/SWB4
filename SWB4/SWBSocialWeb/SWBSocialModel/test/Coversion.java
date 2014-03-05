
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jorge.jimenez
 */
public class Coversion {

    BufferedImage toBufferedImage(Image image) {
        /**
         * miramos uqe la imagen no sea ya una instancia de BufferedImage
         */
        if (image instanceof BufferedImage) {
            /**
             * genial, no hay que hacer nada
             */
            return ((BufferedImage) image);
        } else {
            /**
             * nos aseguramos que la imagen está totalmente cargada
             */
            image = new ImageIcon(image).getImage();
            /**
             * creamos la nueva imagen
             */
            BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            /*
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();*/
            return (bufferedImage);
        }
    } // fin del metodo 
}
