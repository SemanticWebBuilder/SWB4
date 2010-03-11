/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Random;

/**
 *
 * @author serch
 */
public class CaptchaUtil
{

    private static final double YOFFSET = 0.25;
    private static final double XOFFSET = 0.1;
    private static final float DEFAULT_STROKE_WIDTH = 0.75f;
    private static final int width = 150;
    private static final int height = 50;

    public static void writeCaptcha(String cadena, OutputStream out) throws IOException
    {
        Font font = new Font("SansSerif", Font.BOLD, 26); //|Font.ITALIC


        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffer.createGraphics();
        int cadSize = g.getFontMetrics(font).stringWidth(cadena);

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, width, height);
        g.setColor(new Color(0, 0, 0));



        AttributedString as = new AttributedString(cadena);
        as.addAttribute(TextAttribute.FONT, font);

        FontRenderContext frc = g.getFontRenderContext();
        AttributedCharacterIterator aci = as.getIterator();

        TextLayout tl = new TextLayout(aci, frc);
        int xBaseline = (int) Math.round(buffer.getWidth() * XOFFSET);
        int yBaseline = buffer.getHeight() - (int) Math.round(buffer.getHeight() * YOFFSET);
        System.out.println("offset:" + xBaseline + "|" + yBaseline);
        Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(xBaseline, yBaseline));

        g.setColor(Color.BLUE);


        g.draw(shape);

        g.setStroke(new BasicStroke(DEFAULT_STROKE_WIDTH));

//            Font f = new Font("Serif", Font.BOLD|Font.ITALIC, 25);
//
//            g.setFont(f);
//            g.drawString(cadena, 15, 30);

        for (int i = 0; i <= width; i += 20)
        {
            g.drawLine(i, 0, i, height);
        }
        for (int i = 0; i <= height; i += 10)
        {
            g.drawLine(0, i, width - 1, i);
        }

        gimp(buffer);



        javax.imageio.ImageIO.write(buffer, "png", out);

    }

    static private void gimp(BufferedImage bi)
    {
        Graphics2D g = bi.createGraphics();
        shearX(g, bi.getWidth(), bi.getHeight());
        shearY(g, bi.getWidth(), bi.getHeight());
        g.dispose();
    }
    private static final Random _gen = new SecureRandom();

    private static void shearX(Graphics2D g, int w1, int h1)
    {
        int period = _gen.nextInt(10) + 5;
        System.out.println("xPer:" + period);
        boolean borderGap = true;
        int frames = 15;
        int phase = _gen.nextInt(5) + 2;
        System.out.println("xPhs:" + phase);

        for (int i = 0; i < h1; i++)
        {
            double d = (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap)
            {
                g.setColor(Color.WHITE);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private static void shearY(Graphics2D g, int w1, int h1)
    {
        int period = _gen.nextInt(25) + 7; // 50;
        System.out.println("yPer:" + period);

        boolean borderGap = true;
        int frames = 15;
        int phase = 7;
        for (int i = 0; i < w1; i++)
        {
            double d = (period >> 1)
                    * Math.sin((float) i / period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap)
            {
                g.setColor(Color.WHITE);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
//        private static void gimp(BufferedImage image) {
//            int height = image.getHeight();
//            int width = image.getWidth();
//
//            int hstripes = height / 12;
//            int vstripes = width / 12;
//
//            // Calculate space between lines
//            int hspace = height / (hstripes + 1);
//            int vspace = width / (vstripes + 1);
//
//            Graphics2D graph = (Graphics2D) image.getGraphics();
////            float dstrk =  DEFAULT_STROKE_WIDTH * (2.0f);
////            graph.setStroke(new BasicStroke(dstrk));
////            // Draw the horizontal stripes
////            for (int i = hspace; i < height; i = i + hspace) {
////                graph.setColor(Color.BLUE);
////                graph.drawLine(0, i, width, i);
////            }
////
////            // Draw the vertical stripes
////            for (int i = vspace; i < width; i = i + vspace) {
////                graph.setColor(Color.BLUE);
////                graph.drawLine(i, 0, i, height);
////            }
//
//            // Create a pixel array of the original image.
//            // we need this later to do the operations on..
//            int pix[] = new int[height * width];
//            int j = 0;
//
//            for (int j1 = 0; j1 < width; j1++) {
//                for (int k1 = 0; k1 < height; k1++) {
//                    pix[j] = image.getRGB(j1, k1);
//                    j++;
//                }
//            }
//
//            double distance = ranInt(width / 4, width / 3);
//
//            // put the distortion in the (dead) middle
//            int wMid = image.getWidth() / 2;
//            int hMid = image.getHeight() / 2;
//
//            // again iterate over all pixels..
//            for (int x = 0; x < image.getWidth(); x++) {
//                for (int y = 0; y < image.getHeight(); y++) {
//
//                    int relX = x - wMid;
//                    int relY = y - hMid;
//
//                    double d1 = Math.sqrt(relX * relX + relY * relY);
//                    if (d1 < distance) {
//
//                        int j2 = wMid
//                                + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (x - wMid));
//                        int k2 = hMid
//                                + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (y - hMid));
//                        image.setRGB(x, y, pix[j2 * height + k2]);
//                    }
//                }
//            }
//
//            graph.dispose();
//        }
//
//        private static final int ranInt(int i, int j) {
//            double d = Math.random();
//            return (int) (i + ((j - i) + 1) * d);
//        }
//
//        private static final double fishEyeFormula(double s) {
//            // implementation of:
//            // g(s) = - (3/4)s3 + (3/2)s2 + (1/4)s, with s from 0 to 1.
//            if (s < 0.0D) {
//               return 0.0D;
//           }
//           if (s > 1.0D) {
//               return s;
//           }
//
//           return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
//       }
}
