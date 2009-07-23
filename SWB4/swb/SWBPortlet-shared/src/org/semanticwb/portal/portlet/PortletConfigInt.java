/*
 * PortletConfigInt.java
 *
 * Created on 24 de agosto de 2005, 11:54 PM
 */
package org.semanticwb.portal.portlet;

import javax.portlet.*;


/**
 *
 * @author Javier Solis Gonzalez
 */
public interface PortletConfigInt extends PortletConfig
{
    public WBPortletDefinition getPortletDefinition();
}
