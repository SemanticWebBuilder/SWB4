/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class FlowContentInformation {
    public ResourceInfo resourceInfo;
    public String id;
    public String title;
    public String step;
    public int status;

    @Override
    public String toString()
    {
        return title.toString();
    }


}
