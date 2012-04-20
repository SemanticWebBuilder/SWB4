/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets.ftp;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JTree;

/**
 *
 * @author victor.lorenzana
 */
public class JDropTree extends JTree implements DropTargetListener
{

    ftp ftp;

    public JDropTree(ftp ftp)
    {
        this.ftp = ftp;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde)
    {
        System.out.println("Enter");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde)
    {
        System.out.println("Over");
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde)
    {
        System.out.println("dropActionChanged");
    }

    @Override
    public void dragExit(DropTargetEvent dte)
    {
        System.out.println("dragExit");
    }

    @Override
    public void drop(DropTargetDropEvent dtde)
    {
        ftp.drop(dtde);

    }
}
