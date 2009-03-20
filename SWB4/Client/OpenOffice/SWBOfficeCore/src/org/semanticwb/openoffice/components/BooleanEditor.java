/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import javax.swing.JCheckBox;

/**
 *
 * @author victor.lorenzana
 */
class BooleanEditor extends JCheckBox
{

    int row, col;

    public BooleanEditor(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}
