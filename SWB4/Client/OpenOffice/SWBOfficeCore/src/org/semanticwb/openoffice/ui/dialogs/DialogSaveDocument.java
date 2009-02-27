/*
 * DialogSaveDocument.java
 *
 * Created on 5 de junio de 2008, 05:11 PM
 */

package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Frame;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogSaveDocument extends javax.swing.JDialog {
    
    private boolean isCanceled=true;
    /** Creates new form DialogSaveDocument */
    public DialogSaveDocument() {
        super((Frame)null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        
        
    }
    public void setFileFilter(FileFilter filter)
    {
        this.jFileChooser1.setFileFilter(filter);
    }
    public File getSelectedFile()
    {
        return this.jFileChooser1.getSelectedFile();
    }
    public boolean isCanceled()
    {
        return isCanceled;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Guardar Documento");
        setModal(true);
        setResizable(false);

        jFileChooser1.setDialogTitle("Guardar documento");
        jFileChooser1.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });
        getContentPane().add(jFileChooser1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if(evt.getActionCommand().toLowerCase().startsWith("app"))
        {
            isCanceled=false;
        }
        else
        {
            isCanceled=true;
        }
        this.setVisible(false);
        //this.dispose();
    }//GEN-LAST:event_jFileChooser1ActionPerformed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
    
}
