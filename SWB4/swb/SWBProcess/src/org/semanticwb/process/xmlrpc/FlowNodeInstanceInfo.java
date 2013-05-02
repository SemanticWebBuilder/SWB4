/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import java.util.Date;

/**
 *
 * @author hasdai
 */
public class FlowNodeInstanceInfo extends InstanceInfo {
    public String subject;
    public String assignedTo;
    public Date assigned;
    public InstanceInfo processInstance;
}