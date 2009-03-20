/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import javax.swing.JSpinner;

/**
 *
 * @author victor.lorenzana
 */
public class FloatEditor extends JSpinner
{

    int row, col;

    public FloatEditor(int row, int col)
    {
        this.row = row;
        this.col = col;
        setEditor(new JSpinner.NumberEditor(this, "####.##"));
    }
}
