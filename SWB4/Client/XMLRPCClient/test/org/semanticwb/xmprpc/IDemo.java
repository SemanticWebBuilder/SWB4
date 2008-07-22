/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmprpc;

import java.util.Date;
import org.semanticwb.xmlrpc.XmlProxy;
import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface IDemo extends XmlProxy
{
    @XmlRpcMethod(methodName="Demo.add")
    public String add(int a, double b, String c, Date d, boolean e);
}
