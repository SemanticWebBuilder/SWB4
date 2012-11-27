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
package org.semanticwb.process.modeler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


/**
 *
 * @author javier.solis
 */
public class PrintUtil
{
    /**
     *
     * @param bufferedImage
     */
    public void print(BufferedImage bufferedImage[])
    {
        // Get the representation of the current printer and
        //the current print job.
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pf=printerJob.defaultPage();
        printerJob.pageDialog(pf);
        printerJob.setPrintable(new DocToPrint(bufferedImage));

        //  Show the print dialog to the user. This is an optional step
        // and need not be done if the application wants to perform
        // 'quiet' printing. If the user cancels the print dialog then false
        // is returned. If true is returned we go ahead and print.
        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            try {
                printerJob.print();
            } catch (PrinterException exception) {
                System.err.println("Printing error: " + exception);
            }
        }
    }

    // Code to construct the Printable Document
    class DocToPrint implements Printable
    {
        Image img[];

        public DocToPrint(Image img[])
        {
            this.img=img;
        }


        public int print(Graphics g, PageFormat pf, int page)
        {
//            // code to generate what you want to print
//            g.drawImage(img, 0,0, null);
//            //g.drawLine(0,0,100,100);
//            return 0;

            if (page > img.length-1) { /* We have only one page, and 'page' is zero-based */
                 return NO_SUCH_PAGE;
            }
            System.out.println("page:"+page);

            /* User (0,0) is typically outside the imageable area, so we must
             * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D)g;
            System.out.println("translate:"+pf.getImageableX()+" "+pf.getImageableY());
            System.out.println("page:"+pf.getImageableWidth()+" "+pf.getImageableHeight());
            System.out.println("img:"+img[page].getWidth(null)+" "+img[page].getHeight(null));

            double sw=pf.getImageableWidth()/img[page].getWidth(null);
            double sh=pf.getImageableHeight()/img[page].getHeight(null);
            double scale=1.0;
            if(sw<1 || sh<1)
            {
                if(sw<sh)scale=sw;
                else scale=sh;
            }
            System.out.println(sw+" "+sh+" "+scale);

            g2d.translate(pf.getImageableX(), pf.getImageableY());
            g2d.scale(scale, scale);
            /* Now we perform our rendering */
            AffineTransform af=new AffineTransform();
            g2d.drawImage(img[page], af, null);

            /* tell the caller that this page is part of the printed document */
            return PAGE_EXISTS;
        }
    }

}