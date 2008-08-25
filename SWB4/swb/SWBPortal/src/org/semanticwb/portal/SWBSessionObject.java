/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.io.Serializable;
import javax.security.auth.Subject;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 *
 * @author Jei
 */
public class SWBSessionObject implements HttpSessionBindingListener, Serializable
{
    private Subject subject;
    
    public SWBSessionObject()
    {
        subject=new Subject();
    }

    public Subject getSubject() {
        return subject;
    }

    public void valueBound(HttpSessionBindingEvent arg0) 
    {
        
    }

    public void valueUnbound(HttpSessionBindingEvent arg0) 
    {
        
    }
    

}
