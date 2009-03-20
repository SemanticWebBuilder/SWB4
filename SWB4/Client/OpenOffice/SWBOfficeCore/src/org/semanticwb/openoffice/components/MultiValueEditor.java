/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.components;

import javax.swing.JComboBox;

/**
 *
 * @author victor.lorenzana
 */
public class MultiValueEditor extends JComboBox{

    int row, col;

    public MultiValueEditor(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.setEditable(false);
    }
}
