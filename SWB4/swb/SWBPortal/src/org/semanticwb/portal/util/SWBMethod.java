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
package org.semanticwb.portal.util;

import java.util.*;

import java.lang.reflect.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.SWBResourceMgr;

// TODO: Auto-generated Javadoc
/** objeto: almacena la instancia de un metodo dentro de un template.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBMethod
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBMethod.class);
    
    /** The method. */
    private Method method;
    
    /** The obj. */
    private Object obj;
    
    /** The arguments. */
    private Object[] arguments;
    
    /** The tpl. */
    private Template tpl;
    
    /** The obj args. */
    private boolean objArgs;

    /**
     * Instantiates a new sWB method.
     * 
     * @param method the method
     * @param obj the obj
     * @param arguments the arguments
     * @param tpl the tpl
     */
    public SWBMethod(Method method, Object obj, Object[] arguments, Template tpl)
    {
        this.method = method;
        this.obj = obj;
        this.arguments = arguments;
        this.tpl = tpl;
    }

    /**
     * Eval obj args.
     * 
     * @param topic the topic
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the object[]
     */
    private Object[] evalObjArgs(WebPage topic, User user, HttpServletRequest request, HttpServletResponse response)
    {
        Object[] args = arguments;
        if (objArgs)
        {
            args = new Object[1];
            HashMap old = (HashMap) arguments[0];
            HashMap params = new HashMap();
            args[0] = params;
            Iterator it = old.keySet().iterator();
            while (it.hasNext())
            {
                String key = (String) it.next();
                Object val = old.get(key);
                Object val2 = val;

                if (val instanceof WebPage)
                {
                    val2 = topic;
                } else if (val instanceof User)
                {
                    val2 = user;
                } else if (val == HttpServletRequest.class)
                {
                    val2 = request;
                } else if (val == HttpServletResponse.class)
                {
                    val2 = response;
                } else if (val instanceof SWBMethod)
                {
                    SWBMethod wbm = (SWBMethod) val;
                    if (wbm.getObj() instanceof User)
                        val2 = wbm.invoke(user, topic, user, request,response);
                    else if (wbm.getObj() instanceof WebPage)
                        val2 = wbm.invoke(topic, topic, user, request,response);
                    else if (wbm.getObj() == HttpServletRequest.class)
                        val2 = wbm.invoke(request, topic, user, request,response);
                    else if (wbm.getObj() == HttpServletResponse.class)
                        val2 = wbm.invoke(response, topic, user, request,response);
                    else
                        val2 = wbm.invoke(topic, user, request,response);
                }
                //System.out.println("key="+key+" val="+val+" val2="+val2);
                params.put(key, val2);
            }
        }
        return args;
    }

    /**
     * Invoke.
     * 
     * @param topic the topic
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the object
     * @return
     */
    public Object invoke(WebPage topic, User user, HttpServletRequest request, HttpServletResponse response )
    {
        Object[] args = evalObjArgs(topic, user, request, response);
        Object o = null;
        try
        {
            //System.out.println(method+":"+args);
            o = method.invoke(obj, args);
            //System.out.println("{"+o+"}");
        } catch (Exception e)
        {
            log.error("Error invoking method:"+method.getName(), e);
        }
        if (o == null) return "";
        return o;
    }

    /**
     * Invoke.
     * 
     * @param obj the obj
     * @param topic the topic
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the object
     * @return
     */
    public Object invoke(Object obj, WebPage topic, User user, HttpServletRequest request, HttpServletResponse response)
    {
        Object[] args = evalObjArgs(topic, user, request,response);
        Object o = null;
        try
        {
            o = method.invoke(obj, args);
        } catch (Exception e)
        {
            log.error("Error invoking method:"+method.getName(), e);
        }
        if (o == null) return "";
        return o;
    }

    /**
     * Invoke.
     * 
     * @param mgr the mgr
     * @param user the user
     * @param topic the topic
     * @return the object
     * @return
     */
    public Object invoke(SWBResourceMgr mgr, User user, WebPage topic)
    {
        Object o = null;
        if ("content".equals(arguments[0]))
        {
            ResourceType type=(ResourceType)arguments[2];
            try
            {
                if(type!=null)
                {
                    o = mgr.getResources(type, null, user, topic, (HashMap) arguments[1], tpl);
                }else
                {
                    o = mgr.getContents(user, topic, (HashMap) arguments[1], tpl);
                }
            } catch (Exception e)
            {
                log.error("Error invoking method:"+arguments[0], e);
            }
        } else
        {
            try
            {
                o = mgr.getResources((ResourceType)arguments[0], (ResourceSubType)arguments[2], user, topic, (HashMap) arguments[1], tpl);
            } catch (Exception e)
            {
                log.error("Error invoking method:"+arguments[0], e);
            }
        }
        return o;
    }

    /** Getter for property obj.
     * @return Value of property obj.
     */
    public java.lang.Object getObj()
    {
        return obj;
    }

    /** Setter for property obj.
     * @param obj New value of property obj.
     */
    public void setObj(java.lang.Object obj)
    {
        this.obj = obj;
    }

    /** Getter for property method.
     * @return Value of property method.
     */
    public java.lang.reflect.Method getMethod()
    {
        return method;
    }

    /** Setter for property method.
     * @param method New value of property method.
     */
    public void setMethod(java.lang.reflect.Method method)
    {
        this.method = method;
    }

    /**
     * Indexed getter for property arguments.
     * 
     * @param index Index of the property.
     * @return Value of the property at .
     */
    public java.lang.Object getArguments(int index)
    {
        return arguments[index];
    }

    /** Getter for property arguments.
     * @return Value of property arguments.
     */
    public java.lang.Object[] getArguments()
    {
        return arguments;
    }

    /** Indexed setter for property arguments.
     * @param index Index of the property.
     * @param arguments New value of the property at <CODE>index</CODE>.
     */
    public void setArguments(int index, java.lang.Object arguments)
    {
        this.arguments[index] = arguments;
    }

    /** Setter for property arguments.
     * @param arguments New value of property arguments.
     */
    public void setArguments(java.lang.Object[] arguments)
    {
        this.arguments = arguments;
    }

    /** Getter for property objArgs.
     * @return Value of property objArgs.
     */
    public boolean isObjArgs()
    {
        return objArgs;
    }

    /** Setter for property objArgs.
     * @param objArgs New value of property objArgs.
     */
    public void setObjArgs(boolean objArgs)
    {
        this.objArgs = objArgs;
    }

}
