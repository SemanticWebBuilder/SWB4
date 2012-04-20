/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets.ftp;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author victor.lorenzana
 */
public class ScroollPanel extends JScrollPane implements DropTargetListener
{

    ftpPanel ftp;

    public ScroollPanel(ftpPanel ftp)
    {
        this.ftp = ftp;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dragExit(DropTargetEvent dte)
    {
    }

    @Override
    public void drop(DropTargetDropEvent dtde)
    {
        ftp.drop(dtde);

    }
}
