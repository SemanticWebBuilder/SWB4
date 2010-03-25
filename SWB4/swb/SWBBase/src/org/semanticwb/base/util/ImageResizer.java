/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
     * Resize.
     *
     * @param origFile the orig file
     * @param topsize the topsize
     * @param centered the centered
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resize(File origFile, int maxWidth, int maxHeight, boolean centered, File destfile, String type) throws IOException
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
        ImageIO.write(createResizedCopy(bi, maxWidth, maxHeight, centered, calcWidth, calcHeight), type, destfile);
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
    private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topWidth, int topHeight)
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
        g.setColor(Color.WHITE);
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

    /**
     * Resize crop.
     * 
     * @param origFile the orig file
     * @param topsize the topsize
     * @param destfile the destfile
     * @param type the type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void resizeCrop(File origFile, int maxWidth, int maxHeight, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        System.out.println("ResizeCrop");
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
