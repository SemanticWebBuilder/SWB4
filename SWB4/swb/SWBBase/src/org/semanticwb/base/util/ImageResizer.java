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

/**
 *
 * @author serch
 */
public class ImageResizer
{

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

    private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topsize)
    {

        BufferedImage scaledBI = null;
        if (centered) scaledBI = new BufferedImage(topsize, topsize, BufferedImage.TYPE_INT_RGB);
        else scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);

        int offH = 0; if (centered) offH = (topsize - scaledHeight) /2;
        int offW = 0; if (centered) offW = (topsize - scaledWidth) /2;
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, topsize, topsize);
        g.drawImage(originalImage, 0+offW, 0+offH, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static void resizeCrop(File origFile, int topsize, File destfile, String type) throws IOException
    {
        BufferedImage bi = ImageIO.read(origFile);
        int calcHeight = 0;
        int calcWeight = 0;
        if (bi.getWidth() > bi.getHeight())
        {
            calcWeight = (bi.getWidth() - bi.getHeight())/2;
        } else
        {
            calcHeight = (bi.getHeight() - bi.getWidth())/2;
        }

        ImageIO.write(createResizedCropCopy(bi, calcWeight, calcHeight, true, topsize), type, destfile);
    }

    private static BufferedImage createResizedCropCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean centered, int topsize)
    {

        BufferedImage scaledBI = null;
        if (centered) scaledBI = new BufferedImage(topsize, topsize, BufferedImage.TYPE_INT_RGB);
        else scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);



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
}
