/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.base.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageResizer.
 * 
 * @author serch
 */
public class ImageResizer
{

    /**
     * Resize.
     * 
     * @param origFile the orig file
     * @param topsize the topsize
     * @param centered the centered
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resize(File origFile, int topsize, boolean centered, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        int calcHeight = 0;
        int calcWeight = 0;
        if (bi.getWidth() > bi.getHeight())
        {
            calcHeight = (topsize * bi.getHeight() / bi.getWidth());
            calcWeight = topsize;
        } else
        {
            calcWeight = (topsize * bi.getWidth() / bi.getHeight());
            calcHeight = topsize;
        }

        ImageIO.write(createResizedCopy(bi, calcWeight, calcHeight, centered, topsize), type, destfile);
    }

    /**
     * Shrink to.
     * 
     * @param origFile the orig file
     * @param thresholdWidth the threshold width
     * @param thresholdHeight the threshold height
     * @param destfile the destfile
     * @param type the type
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static boolean shrinkTo(File origFile, int thresholdWidth, int thresholdHeight, File destfile, String type) throws IOException{
        int newWidth=0;
        int newHeight=0;
        boolean ret = false;
        BufferedImage bi = ImageIO.read(origFile);
        int x = bi.getWidth();
        int y = bi.getHeight();
//        System.out.println("X:"+x+",Y:"+y+",tw:"+thresholdWidth+",th:"+thresholdHeight+",ret:"+ret);
        if (x>thresholdWidth){
            newWidth=thresholdWidth;
            float cw=(float)x/newWidth;
            newHeight=(int)(y/cw);
//            System.out.println("nw:"+newWidth+",nH:"+newHeight+",cw:"+cw);
            if (newHeight<=thresholdHeight) {
                ret=true;
            }
        }
//        System.out.println("X:"+x+",Y:"+y+",tw:"+thresholdWidth+",th:"+thresholdHeight+",ret:"+ret);
        if (!ret && y > thresholdHeight){
            newHeight=thresholdHeight;
            float cw=(float)y/newHeight;
            newWidth=(int)(x/cw);
//            System.out.println("nw:"+newWidth+",nH:"+newHeight+",cw:"+cw);
            if (newWidth<=thresholdWidth) {
                ret=true;
            }
        }
//        System.out.println("X:"+x+",Y:"+y+",tw:"+thresholdWidth+",th:"+thresholdHeight+",ret:"+ret);
        if (ret){
            ImageIO.write(createResizedCopy(bi, newWidth, newHeight, false, newWidth, newHeight), type, destfile);
        }
        return ret;
    }
    
    /**
     * Resize.
     * 
     * @param origFile the orig file
     * @param maxWidth the max width
     * @param maxHeight the max height
     * @param centered the centered
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resize(File origFile, int maxWidth, int maxHeight, boolean centered, File destfile, String type) throws IOException
    {
        resize(origFile, maxWidth, maxHeight, centered, destfile, type, Color.WHITE);
    }    

    /**
     * Resize.
     * 
     * @param origFile the orig file
     * @param maxWidth the max width
     * @param maxHeight the max height
     * @param centered the centered
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resize(File origFile, int maxWidth, int maxHeight, boolean centered, File destfile, String type, Color color) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        int calcHeight = -1;
        float ch = bi.getHeight() / (float) maxHeight;
        int calcWidth = -1;
        float cw = bi.getWidth() / (float) maxWidth;
        if (cw > ch)
        {
            calcHeight = (int) (bi.getHeight() / cw);
            calcWidth = (int) (bi.getWidth() / cw);
        } else if (ch > cw)
        {
            calcWidth = (int) (bi.getWidth() / ch);
            calcHeight = (int) (bi.getHeight() / ch);
            ;
        } else
        {
            calcHeight = maxHeight;
            calcWidth = maxWidth;
        }
        ImageIO.write(createResizedCopy(bi, maxWidth, maxHeight, centered, calcWidth, calcHeight, color), type, destfile);
    }
    
    
    /**
     * Creates the resized copy.
     * 
     * @param originalImage the original image
     * @param scaledWidth the scaled width
     * @param scaledHeight the scaled height
     * @param centered the centered
     * @param topWidth the top width
     * @param topHeight the top height
     * @return the buffered image
     */
    private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topWidth, int topHeight)
    {
        return createResizedCopy(originalImage, scaledWidth, scaledHeight, centered, topWidth, topHeight, Color.WHITE);
    }    

    /**
     * Creates the resized copy.
     * 
     * @param originalImage the original image
     * @param scaledWidth the scaled width
     * @param scaledHeight the scaled height
     * @param centered the centered
     * @param topWidth the top width
     * @param topHeight the top height
     * @return the buffered image
     */
    private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topWidth, int topHeight, Color color)
    {

        BufferedImage scaledBI = null;
        scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);

        int offH = 0;
        if (centered)
        {
            offH = (scaledHeight - topHeight) / 2;
        }
        int offW = 0;
        if (centered)
        {
            offW = (scaledWidth - topWidth) / 2;
        }
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(color);
        g.fillRect(0, 0, scaledWidth, scaledHeight);
        g.drawImage(originalImage, 0 + offW, 0 + offH, topWidth, topHeight, null);
        g.dispose();
        return scaledBI;
    }

    /**
     * Creates the resized copy.
     * 
     * @param originalImage the original image
     * @param scaledWidth the scaled width
     * @param scaledHeight the scaled height
     * @param centered the centered
     * @param topsize the topsize
     * @return the buffered image
     */
    private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topsize)
    {

        BufferedImage scaledBI = null;
        if (centered)
        {
            scaledBI = new BufferedImage(topsize, topsize, BufferedImage.TYPE_INT_RGB);
        } else
        {
            scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        }

        int offH = 0;
        if (centered)
        {
            offH = (topsize - scaledHeight) / 2;
        }
        int offW = 0;
        if (centered)
        {
            offW = (topsize - scaledWidth) / 2;
        }
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, topsize, topsize);
        g.drawImage(originalImage, 0 + offW, 0 + offH, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    
    public static void crop(File origFile, int maxWidth, int maxHeight, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        int originalHeight=bi.getHeight();
        int originalWidth=bi.getWidth();
        if(maxHeight>originalHeight)
        {
            maxHeight=originalHeight;
        }
        if(maxWidth>originalWidth)
        {
            maxWidth=originalWidth;
        }
        ImageIO.write(createCropCopy(bi, maxWidth, maxHeight), type, destfile);
    }
    /**
     * Resize crop.
     * 
     * @param origFile the orig file
     * @param maxWidth the max width
     * @param maxHeight the max height
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resizeCrop(File origFile, int maxWidth, int maxHeight, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);

        int calcHeight = -1;
        float ch = bi.getHeight() / (float) maxHeight;
        int calcWidth = -1;
        float cw = bi.getWidth() / (float) maxWidth;
        float factor=0.0f;
        if (cw < ch)
        {
            calcHeight = (int) (bi.getHeight() / cw);
            calcWidth = (int) (bi.getWidth() / cw);
            factor = cw;
        } else if (ch < cw)
        {
            calcWidth = (int) (bi.getWidth() / ch);
            calcHeight = (int) (bi.getHeight() / ch);
            factor = ch;
        } else
        {
            calcHeight = maxHeight;
            calcWidth = maxWidth;
            factor = cw;
        }
//        int calcHeight = 0;
//        int calcWeight = 0;
//        if (bi.getWidth() > bi.getHeight())
//        {
//            calcWeight = (bi.getWidth() - bi.getHeight()) / 2;
//        } else
//        {
//            calcHeight = (bi.getHeight() - bi.getWidth()) / 2;
//        }
//        System.out.println("-->" + calcWidth + ":" + calcHeight + "|" + maxWidth + ":" + maxHeight);
        ImageIO.write(createResizedCropCopy(bi, calcWidth, calcHeight, maxWidth, maxHeight, factor), type, destfile);

    }

    /**
     * Resize crop.
     *
     * @param origFile the orig file
     * @param topsize the topsize
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resizeCrop(File origFile, int topsize, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        int calcHeight = 0;
        int calcWeight = 0;
        if (bi.getWidth() > bi.getHeight())
        {
            calcWeight = (bi.getWidth() - bi.getHeight()) / 2;
        } else
        {
            calcHeight = (bi.getHeight() - bi.getWidth()) / 2;
        }

        ImageIO.write(createResizedCropCopy(bi, calcWeight, calcHeight, true, topsize), type, destfile);
    }

    /**
     * Creates the resized crop copy.
     * 
     * @param originalImage the original image
     * @param scaledWidth the scaled width
     * @param scaledHeight the scaled height
     * @param centered the centered
     * @param topsize the topsize
     * @return the buffered image
     */
    private static BufferedImage createResizedCropCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topsize)
    {

        BufferedImage scaledBI = null;
        if (centered)
        {
            scaledBI = new BufferedImage(topsize, topsize, BufferedImage.TYPE_INT_RGB);
        } else
        {
            scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        }



        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, topsize, topsize);
        g.drawImage(originalImage,
                0, 0, topsize, topsize, scaledWidth,
                scaledHeight, originalImage.getWidth(null) - scaledWidth, originalImage.getHeight(null) - scaledHeight,
                Color.WHITE, null);
        g.dispose();
        return scaledBI;
    }

    private static BufferedImage createCropCopy(BufferedImage originalImage, int topWidth, int topHeight)
    {        
        return originalImage.getSubimage(0,0, topWidth, topHeight);
    }
    /**
     * Creates the resized crop copy.
     * 
     * @param originalImage the original image
     * @param scaledWidth the scaled width
     * @param scaledHeight the scaled height
     * @param topWidth the top width
     * @param topHeight the top height
     * @param factor the factor
     * @return the buffered image
     */
    private static BufferedImage createResizedCropCopy(Image originalImage, int scaledWidth, int scaledHeight, int topWidth, int topHeight, float factor)
    {

        BufferedImage scaledBI = null;

        scaledBI = new BufferedImage(topWidth, topHeight, BufferedImage.TYPE_INT_RGB);

        int x1 = (int)((scaledWidth - topWidth) / 2 * factor);
        int y1 = (int)((scaledHeight - topHeight) / 2 * factor);
        int x2 = (int)(scaledWidth * factor) - x1;
        int y2 = (int)(scaledHeight * factor) - y1;
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, topWidth, topHeight);
//        System.out.println("Solicited:0,0,"+topWidth+","+topHeight+"|"+x1+","+y1+","+x2+","+y2);
        g.drawImage(originalImage,
                0, 0, topWidth, topHeight, x1,
                y1, x2, y2,
                Color.WHITE, null);
        g.dispose();
        return scaledBI;
    }
}
