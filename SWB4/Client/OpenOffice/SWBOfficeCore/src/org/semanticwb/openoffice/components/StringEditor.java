/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import javax.swing.JTextField;

/**
 *
 * @author victor.lorenzana
 */
class StringEditor extends JTextField
{

    int row, col;

    public StringEditor(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}
