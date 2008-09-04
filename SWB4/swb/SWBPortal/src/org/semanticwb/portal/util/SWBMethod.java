/*
 * WBMethod.java
 *
 * Created on 21 de junio de 2002, 17:57
 */

package org.semanticwb.portal.util;

import java.util.*;

import java.lang.reflect.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.SWBResourceMgr;

/** objeto: almacena la instancia de un metodo dentro de un template.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBMethod
{
    private static Logger log = SWBUtils.getLogger(SWBMethod.class);
    private Method method;
    private Object obj;
    private Object[] arguments;
    private Template tpl;
    private boolean objArgs;

    /**
     * @param method
     * @param obj
     * @param arguments
     * @param tpl  */
    public SWBMethod(Method method, Object obj, Object[] arguments, Template tpl)
    {
        this.method = method;
        this.obj = obj;
        this.arguments = arguments;
        this.tpl = tpl;
    }

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
     * @return  */
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
            log.error("Error invoking methos:"+method.getName(), e);
        }
        if (o == null) return "";
        return o;
    }

    /**
     * @param obj
     * @return  */
    public Object invoke(Object obj, WebPage topic, User user, HttpServletRequest request, HttpServletResponse response)
    {
        Object[] args = evalObjArgs(topic, user, request,response);
        Object o = null;
        try
        {
            o = method.invoke(obj, args);
        } catch (Exception e)
        {
            log.error("Error invoking methos:"+method.getName(), e);
        }
        if (o == null) return "";
        return o;
    }

    /**
     * @param mgr
     * @param user
     * @param topic
     * @return  */
    public Object invoke(SWBResourceMgr mgr, User user, WebPage topic)
    {
        Object o = null;
        if ("content".equals(arguments[0]))
        {
            try
            {
                o = mgr.getContents(user, topic, (HashMap) arguments[1], tpl);
            } catch (Exception e)
            {
                log.error("Error invoking methos:"+arguments[0], e);
            }
        } else
        {
            try
            {
                o = mgr.getResources((String)arguments[0], user, topic, (HashMap) arguments[1], tpl);
            } catch (Exception e)
            {
                log.error("Error invoking methos:"+arguments[0], e);
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

    /** Indexed getter for property arguments.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
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
