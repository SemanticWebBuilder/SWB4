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
    PortletInfo portletInfo;
    String id;
    String title;
    String step;

    @Override
    public String toString()
    {
        return title.toString();
    }


}
