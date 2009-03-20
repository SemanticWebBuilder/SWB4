/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author victor.lorenzana
 */
class IntegerEditor extends JSpinner
{

    int row, col;

    public IntegerEditor(int row, int col)
    {
        this.row = row;
        this.col = col;
        SpinnerModel model = new SpinnerNumberModel(0, 0, 9999, 1);
        this.setModel(model);
        this.setEditor(new JSpinner.NumberEditor(this, "####"));
    }
}
