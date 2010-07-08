/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import javax.servlet.ServletException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeSmartTagServlet extends XMLRPCServlet{

    private static Logger log = SWBUtils.getLogger(OfficeSmartTagServlet.class);
    @Override
    public void init() throws ServletException
    {
        log.event("Adding mappingType OfficeSmartTag...");
        addMappingType("OfficeSmartTag", OfficeSmartTag.class);
    }

}
