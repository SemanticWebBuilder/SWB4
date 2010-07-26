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

    private static final int width = 150;
    private static final int height = 50;
    private static final Random _gen = new SecureRandom();

    public static void writeCaptcha(String cadena, OutputStream out) throws IOException
    {
        Font font = new Font("SansSerif", Font.BOLD, 30); //|Font.ITALIC


        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffer.createGraphics();

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
        Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(15, 37));

        g.setColor(Color.BLACK);

        g.draw(shape);

        g.setStroke(new BasicStroke(0.75f));

        for (int i = _gen.nextInt(10); i <= width; i += _gen.nextInt(5)+25)
        {
            g.drawLine(i, 0, i, height);
        }
        for (int i = _gen.nextInt(5); i <= height; i += _gen.nextInt(5)+15)
        {
            g.drawLine(0, i, width - 1, i);
        }

        gimp(buffer);

        javax.imageio.ImageIO.write(buffer, "png", out);

    }

    static private void gimp(BufferedImage bi)
    {
        Graphics2D g = bi.createGraphics();
        //shearX(g, bi.getWidth(), bi.getHeight());
        shearY(g, bi.getWidth(), bi.getHeight());
        g.dispose();
    }

    private static void shearX(Graphics2D g, int w1, int h1)
    {
        int period = _gen.nextInt(10) + 5;
        boolean borderGap = true;
        int frames = 15;
        int phase = _gen.nextInt(5) + 2;
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
    

}
