/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.ui.icons;

import java.net.URL;
import java.util.Hashtable;
import javax.swing.ImageIcon;

/**
 *
 * @author victor.lorenzana
 */
public class ImageLoader
{

    public static Hashtable<String, ImageIcon> images = new Hashtable<String, ImageIcon>();


    static
    {
        try
        {
            URL url = ImageLoader.class.getResource("close.png");
            ImageIcon image = new ImageIcon(url);
            images.put("close", image);

            url = ImageLoader.class.getResource("open.png");
            image = new ImageIcon(url);
            images.put("open", image);
            url = ImageLoader.class.getResource("splash.jpg");
            image = new ImageIcon(url);
            images.put("splash", image);

            url = ImageLoader.class.getResource("see.png");
            image = new ImageIcon(url);
            images.put("see", image);

            url = ImageLoader.class.getResource("edit.png");
            image = new ImageIcon(url);
            images.put("edit", image);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }

    }
}
