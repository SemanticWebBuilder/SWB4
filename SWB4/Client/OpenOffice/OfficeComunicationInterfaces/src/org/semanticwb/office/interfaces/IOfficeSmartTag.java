/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface IOfficeSmartTag {

    @XmlRpcMethod(methodName = "OfficeSmartTag.search")
    public ObjecInfo[] search(String text) throws Exception;


    @XmlRpcMethod(methodName = "OfficeSmartTag.isSmartTag")
    public boolean isSmartTag(String text) throws Exception;

    @XmlRpcMethod(methodName = "OfficeSmartTag.getTokens")
    public String[] getTokens(String text) throws Exception;

}
