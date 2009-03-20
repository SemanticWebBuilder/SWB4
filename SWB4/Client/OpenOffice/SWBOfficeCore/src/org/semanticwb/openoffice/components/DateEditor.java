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
public class DateEditor extends JSpinner
{

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    /*private static final SimpleDateFormat DATE_SIMPLEFORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat TIME_SIMPLEFORMAT = new SimpleDateFormat(TIME_FORMAT);*/

    int row, col;

    public DateEditor(int row, int col, DateTypeFormat format)
    {
        this.row = row;
        this.col = col;
        switch (format)
        {
            case TIME:
                setEditor(new JSpinner.DateEditor(this, TIME_FORMAT));
                break;
            case DATE_TIME:
                setEditor(new JSpinner.DateEditor(this, DATE_TIME_FORMAT));
                break;
            default:
                setEditor(new JSpinner.DateEditor(this, DATE_FORMAT));
                break;

        }

    }
}
