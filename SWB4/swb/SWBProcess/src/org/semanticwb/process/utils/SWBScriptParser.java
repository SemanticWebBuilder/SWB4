/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.*;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.model.Instance;

/**
 *
 * @author victor.lorenzana
 */
public class SWBScriptParser
{

    public static String parser(Instance instance, User user, String text) throws Exception
    {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("instance", instance);
        values.put("user", user);
        return parse(values, text);
    }

    public static String evaluate(Map<String, Object> values, String tag) throws Exception
    {
        if (tag.startsWith("{"))
        {
            tag = tag.substring(1);
        }
        if (tag.endsWith("}"))
        {
            tag = tag.substring(0, tag.length() - 1);
        }
        String[] path = tag.split("\\.");
        LinkedList<String> keys = new LinkedList<String>();
        keys.addAll(Arrays.asList(path));
        String key = keys.removeFirst();
        Object context = values.get(key);
        if (context != null)
        {
            Object obj = evaluate(context, keys);
            if (obj != null)
            {
                return obj.toString();
            }
            else
            {
                throw new Exception("The path " + tag + " was not evaluated");
            }
        }
        else
        {
            throw new Exception("The object " + key + " was not found");
        }
    }

    private static Method getMethod(Object obj, String name)
    {
        name = "get" + name;
        for (Method m : obj.getClass().getMethods())
        {
            if (m.getName().equalsIgnoreCase(name) && Modifier.isPublic(m.getModifiers()) && m.getParameterTypes().length == 0)
            {
                return m;
            }
        }
        return null;

    }

    public static Object evaluate(Object context, LinkedList<String> keys) throws Exception
    {
        String key = keys.removeFirst();
        if (context instanceof SemanticObject)
        {
            SemanticObject semObject = (SemanticObject) context;
            Iterator<SemanticProperty> props = semObject.listProperties();
            while (props.hasNext())
            {
                SemanticProperty prop = props.next();
                if (prop.getName().equalsIgnoreCase(key))
                {
                    if (prop.isObjectProperty())
                    {
                        SemanticObject newcontext = semObject.getObjectProperty(prop);
                        if (newcontext == null)
                        {
                            return newcontext;
                        }
                        if (keys.isEmpty())
                        {
                            return newcontext;
                        }
                        else
                        {
                            return evaluate(newcontext, keys);
                        }
                    }
                    else
                    {
                        return semObject.getProperty(prop);
                    }
                }
            }
        }
        else
        {
            Method m = getMethod(context, key);
            if (m != null)
            {
                Object[] args = new Object[0];
                Object newcontext = m.invoke(context, args);
                if (newcontext == null)
                {
                    return null;
                }
                if (keys.isEmpty())
                {
                    return newcontext;
                }
                else
                {
                    return evaluate(newcontext, keys);
                }
            }
        }
        return null;
    }

    public static String parse(Map<String, Object> values, String text) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        String exp = "\\{\\w+(\\.\\w+)+\\}";
        Pattern p = Pattern.compile(exp);
        Matcher matcher = p.matcher(text);
        while (matcher.find())
        {
            int start = matcher.start();
            int end = matcher.end();
            sb.append(text.substring(0, start));
            String tag = text.substring(start, end);
            try
            {
                tag = evaluate(values, tag);
                sb.append(tag);
                if (end < text.length())
                {
                    text = text.substring(end);
                    matcher = p.matcher(text);

                }
                else
                {
                    text = "";
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        sb.append(text);
        return sb.toString();
    }
}
