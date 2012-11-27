/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.portlet;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import javax.portlet.*;
import javax.servlet.http.HttpSession;


/**
 * The <CODE>PortletSession</CODE> interface provides a way to identify a user
 * across more than one request and to store transient information about that user.
 * <p>
 * A <code>PortletSession</code> is created per user client per portlet application.
 * <p>
 * A portlet can bind an object attribute into a <code>PortletSession</code> by name.
 * The <code>PortletSession</code> interface defines two scopes for storing objects:
 * <ul>
 * <li><code>APPLICATION_SCOPE</code>
 * <li><code>PORTLET_SCOPE</code>
 * </ul>
 * All objects stored in the session using the <code>APPLICATION_SCOPE</code>
 * must be available to all the portlets, servlets and
 * JSPs that belongs to the same portlet application and that handles a
 * request identified as being a part of the same session.
 * Objects stored in the session using the <code>PORTLET_SCOPE</code> must be
 * available to the portlet during requests for the same portlet window
 * that the objects where stored from. Attributes stored in the
 * <code>PORTLET_SCOPE</code> are not protected from other web components
 * of the portlet application. They are just conveniently namespaced.
 * <P>
 * The portlet session is based on the <code>HttpSession</code>. Therefore all
 * <code>HttpSession</code> listeners do apply to the portlet session and
 * attributes set in the portlet session are visible in the <code>HttpSession</code>
 * and vice versa.
 */
public class PortletSessionImp implements PortletSession
{
    HttpSession session;
    PortletContext porletContex;
    private String resId;
    
    public PortletSessionImp(HttpSession session, PortletContext porletContex, String resId)
    {
        this.session=session;
        this.porletContex=porletContex;
        this.resId=resId;
    }
    
    
    /**
     * This constant defines an application wide scope for the session attribute.
     * <code>APPLICATION_SCOPE</code> session attributes enable Portlets
     * within one portlet application to share data.
     * <p>
     * Portlets may need to prefix attributes set in this scope with some
     * ID, to avoid overwriting each other's attributes in the
     * case where two portlets of the same portlet definition
     * are created.
     * <p>
     * Value: <code>0x01</code>
     */
    //public static final int APPLICATION_SCOPE = 0x01;
    
    /**
     * This constant defines the scope of the session attribute to be
     * private to the portlet and its included resources.
     * <p>
     * Value: <code>0x02</code>
     */
    //public static final int PORTLET_SCOPE = 0x02;
    
    
    
    /**
     * Returns the object bound with the specified name in this session
     * under the <code>PORTLET_SCOPE</code>, or <code>null</code> if no
     * object is bound under the name in that scope.
     *
     * @param name		a string specifying the name of the object
     *
     * @return			the object with the specified name for
     *                            the <code>PORTLET_SCOPE</code>.
     *
     * @exception java.lang.IllegalStateException	if this method is called on an
     *					invalidated session.
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public java.lang.Object getAttribute(java.lang.String name)
    {
        return getAttribute(name,PORTLET_SCOPE);
    }
    
    
    /**
     * Returns the object bound with the specified name in this session,
     * or <code>null</code> if no object is bound under the name in the given scope.
     *
     * @param name		a string specifying the name of the object
     * @param scope               session scope of this attribute
     *
     * @return			the object with the specified name
     *
     * @exception java.lang.IllegalStateException	if this method is called on an
     *					invalidated session
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public java.lang.Object getAttribute(java.lang.String name,int scope)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("PorletSession.getAttribute(name==null)");
        }
        if (scope==PortletSession.APPLICATION_SCOPE)
        {
            //System.out.println("getAttribute("+name+"):"+session+"->"+session.getAttribute(name));
            return session.getAttribute(name);
        }
        else
        {
            Object attribute = session.getAttribute("javax.portlet.p."+resId+"?"+name);
            if (attribute == null)
            {
                // not sure, if this should be done for all attributes or only javax.servlet.
                attribute = session.getAttribute(name);
            }
            //System.out.println("getAttribute("+name+"):"+session+"->"+attribute);
            return attribute;
        }
    }
    
    
    /**
     * Returns an <code>Enumeration</code> of String objects containing the names of
     * all the objects bound to this session under the <code>PORTLET_SCOPE</code>, or an
     * empty <code>Enumeration</code> if no attributes are available.
     *
     * @return			an <code>Enumeration</code> of
     *				<code>String</code> objects specifying the
     *				names of all the objects bound to
     *				this session, or an empty <code>Enumeration</code>
     *                if no attributes are available.
     *
     * @exception java.lang.IllegalStateException	if this method is called on an
     *					invalidated session
     */
    
    public java.util.Enumeration getAttributeNames()
    {
        return getAttributeNames(PORTLET_SCOPE);
    }
    
    
    /**
     * Returns an <code>Enumeration</code> of String objects containing the names of
     * all the objects bound to this session in the given scope, or an
     * empty <code>Enumeration</code> if no attributes are available in the
     * given scope.
     *
     * @param scope               session scope of the attribute names
     *
     * @return			an <code>Enumeration</code> of
     *				<code>String</code> objects specifying the
     *				names of all the objects bound to
     *				this session, or an empty <code>Enumeration</code>
     *                            if no attributes are available in the given scope.
     *
     * @exception java.lang.IllegalStateException	if this method is called on an
     *					invalidated session
     */
    
    public java.util.Enumeration getAttributeNames(int scope)
    {
        if (scope==PortletSession.APPLICATION_SCOPE)
        {
            return session.getAttributeNames();
        }
        else
        {
            Enumeration attributes = session.getAttributeNames();
            
            Vector portletAttributes = new Vector();
            
            /* Fix that ONLY attributes of PORTLET_SCOPE are returned. */
            int prefix_length = "javax.portlet.p.".length();
            String portletWindowId = ""+resId;
            
            while (attributes.hasMoreElements())
            {
                String attribute = (String)attributes.nextElement();
                
                int attributeScope = PortletSessionUtil.decodeScope(attribute);
                
                if (attributeScope == PortletSession.PORTLET_SCOPE && attribute.startsWith(portletWindowId, prefix_length))
                {
                    String portletAttribute = PortletSessionUtil.decodeAttributeName(attribute);
                    
                    if (portletAttribute!=null)
                    {
                        // it is in the portlet's namespace
                        portletAttributes.add(portletAttribute);
                    }
                }
            }
            
            return portletAttributes.elements();
        }
    }
    
    /**
     * Returns the time when this session was created, measured in
     * milliseconds since midnight January 1, 1970 GMT.
     *
     * @return				a <code>long</code> specifying
     * 					when this session was created,
     *					expressed in
     *					milliseconds since 1/1/1970 GMT
     *
     * @exception java.lang.IllegalStateException	if this method is called on an
     *					invalidated session
     */
    
    public long getCreationTime()
    {
        return session.getCreationTime();
    }
    
    
    /**
     * Returns a string containing the unique identifier assigned to this session.
     *
     * @return				a string specifying the identifier
     *					assigned to this session
     */
    
    public java.lang.String getId()
    {
        return session.getId();
    }
    
    
    /**
     * Returns the last time the client sent a request associated with this session,
     * as the number of milliseconds since midnight January 1, 1970 GMT.
     *
     * <p>Actions that your portlet takes, such as getting or setting
     * a value associated with the session, do not affect the access
     * time.
     *
     * @return				a <code>long</code>
     *					representing the last time
     *					the client sent a request associated
     *					with this session, expressed in
     *					milliseconds since 1/1/1970 GMT
     */
    
    public long getLastAccessedTime()
    {
        return session.getLastAccessedTime();
    }
    
    
    /**
     * Returns the maximum time interval, in seconds, for which the portlet container
     * keeps this session open between client accesses. After this interval,
     * the portlet container invalidates the session.  The maximum time
     * interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     * A negative time indicates the session should never timeout.
     *
     * @return		an integer specifying the number of
     *			seconds this session remains open
     *			between client requests
     *
     * @see		#setMaxInactiveInterval
     */
    
    public int getMaxInactiveInterval()
    {
        return session.getMaxInactiveInterval();
    }
    
    
    /**
     * Invalidates this session (all scopes) and unbinds any objects bound to it.
     * <p>
     * Invalidating the portlet session will result in invalidating the underlying
     * <code>HttpSession</code>
     *
     * @exception java.lang.IllegalStateException	if this method is called on a
     *					session which has already been invalidated
     */
    
    public void invalidate()
    {
        session.invalidate();
    }
    
    
    
    /**
     * Returns true if the client does not yet know about the session or
     * if the client chooses not to join the session.
     *
     * @return 				<code>true</code> if the
     *					server has created a session,
     *					but the client has not joined yet.
     *
     * @exception java.lang.IllegalStateException	if this method is called on a
     *					session which has already been invalidated
     *
     */
    
    public boolean isNew()
    {
        return session.isNew();
    }
    
    
    /**
     * Removes the object bound with the specified name under
     * the <code>PORTLET_SCOPE</code> from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param name   the name of the object to be
     *               removed from this session in the
     *               <code> PORTLET_SCOPE</code>.
     *
     * @exception java.lang.IllegalStateException
     *                   if this method is called on a
     *                   session which has been invalidated
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public void removeAttribute(String name)
    {
        removeAttribute(name,PORTLET_SCOPE);
    }
    
    
    /**
     * Removes the object bound with the specified name and the given scope from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param name   the name of the object to be
     *               removed from this session
     * @param scope  session scope of this attribute
     *
     * @exception java.lang.IllegalStateException
     *                   if this method is called on a
     *                   session which has been invalidated
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public void removeAttribute(String name, int scope)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("PorletSession.removeAttribute(name==null)");
        }
        if (scope == PortletSession.APPLICATION_SCOPE)
        {
            session.removeAttribute(name);
        }
        else
        {
            session.removeAttribute("javax.portlet.p."+resId+"?"+name);
        }
    }
    
    
    /**
     * Binds an object to this session under the <code>PORTLET_SCOPE</code>, using the name specified.
     * If an object of the same name in this scope is already bound to the session,
     * that object is replaced.
     *
     * <p>After this method has been executed, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListeners</code> in the web
     * application.
     * <p>If an object was already bound to this session
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     *
     * <p>If the value is <code>null</code>, this has the same effect as calling
     * <code>removeAttribute()</code>.
     *
     *
     * @param name		the name to which the object is bound under
     *                            the <code>PORTLET_SCOPE</code>;
     *				this cannot be <code>null</code>.
     * @param value		the object to be bound
     *
     * @exception java.lang.IllegalStateException	if this method is called on a
     *					session which has been invalidated
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public void setAttribute(java.lang.String name, java.lang.Object value)
    {
        setAttribute(name,value, PORTLET_SCOPE);
    }
    
    
    /**
     * Binds an object to this session in the given scope, using the name specified.
     * If an object of the same name in this scope is already bound to the session,
     * that object is replaced.
     *
     * <p>After this method has been executed, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListeners</code> in the web
     * application.
     * <p>If an object was already bound to this session
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     *
     * <p>If the value is <code>null</code>, this has the same effect as calling
     * <code>removeAttribute()</code>.
     *
     *
     * @param name		the name to which the object is bound;
     *				this cannot be <code>null</code>.
     * @param value		the object to be bound
     * @param scope               session scope of this attribute
     *
     * @exception java.lang.IllegalStateException	if this method is called on a
     *					session which has been invalidated
     * @exception  java.lang.IllegalArgumentException
     *                            if name is <code>null</code>.
     */
    
    public void setAttribute(java.lang.String name, java.lang.Object value, int scope)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("PorletSession.removeAttribute(name==null)");
        }
        //System.out.println("setAttribute("+name+"):"+session+"->"+value);
        if (scope==PortletSession.APPLICATION_SCOPE)
        {
            session.setAttribute(name,value);
        }
        else
        {
            session.setAttribute("javax.portlet.p."+resId+"?"+name, value);
        }
        
    }
    
    
    /**
     * Specifies the time, in seconds, between client requests, before the
     * portlet container invalidates this session. A negative time
     * indicates the session should never timeout.
     *
     * @param interval		An integer specifying the number
     * 				of seconds
     */
    
    public void setMaxInactiveInterval(int interval)
    {
        session.setMaxInactiveInterval(interval);
    }
    
    
    /**
     * Returns the portlet application context associated with this session.
     *
     * @return   the portlet application context
     */
    
    public PortletContext getPortletContext()
    {
        return porletContex;
    }

    public Map<String, Object> getAttributeMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, Object> getAttributeMap(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}


