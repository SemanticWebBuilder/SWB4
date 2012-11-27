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

// TODO: Auto-generated Javadoc
/**
 * The Class CaptchaUtil.
 * 
 * @author serch
 */
public class CaptchaUtil
{

    /** The Constant width. */
    private static final int width = 150;
    
    /** The Constant height. */
    private static final int height = 50;
    
    /** The Constant _gen. */
    private static final Random _gen = new SecureRandom();

    /**
     * Write captcha.
     * 
     * @param cadena the cadena
     * @param out the out
     * @throws IOException Signals that an I/O exception has occurred.
     */
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

    /**
     * Gimp.
     * 
     * @param bi the bi
     */
    static private void gimp(BufferedImage bi)
    {
        Graphics2D g = bi.createGraphics();
        //shearX(g, bi.getWidth(), bi.getHeight());
        shearY(g, bi.getWidth(), bi.getHeight());
        g.dispose();
    }

    /**
     * Shear x.
     * 
     * @param g the g
     * @param w1 the w1
     * @param h1 the h1
     */
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

    /**
     * Shear y.
     * 
     * @param g the g
     * @param w1 the w1
     * @param h1 the h1
     */
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
