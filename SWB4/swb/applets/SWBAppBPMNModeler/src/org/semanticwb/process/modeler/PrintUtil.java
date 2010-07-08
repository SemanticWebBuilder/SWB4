/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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