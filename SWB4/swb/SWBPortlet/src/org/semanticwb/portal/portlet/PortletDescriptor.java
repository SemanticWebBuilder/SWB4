/*
 * PortletDescriptor.java
 *
 * Created on 13 de marzo de 2004, 10:39 PM
 */

package org.semanticwb.portal.portlet;

import java.util.HashMap;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class PortletDescriptor
{
    private String description;
    private String portletName;
    private String displayName;
    private String portletClass;
    private HashMap initParams;
    private long expirationCache;


    /** Creates a new instance of PortletDescriptor */
    public PortletDescriptor()
    {
    }

}
