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
package org.semanticwb.process.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.SWBPClassMgr;

/**
 *
 * @author victor.lorenzana
 */
public class SWBScriptParser
{

    private static Logger log = SWBUtils.getLogger(SWBScriptParser.class);

    public static void setValue(Instance instance, User user, String variable, Object value)
    {
        if (!variable.startsWith("{"))
        {
            variable = "{" + variable + "}";
        }
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("instance", instance);
        values.put("user", user);
        addInstanceObjects(instance, values);
        setValue(values, variable, value);
    }

    public static void setValue(Object context, LinkedList<String> keys, Object value)
    {
        if (context == null)
        {
            return;
        }
        String key = keys.removeFirst();
        if (key.isEmpty())
        {
            SemanticProperty prop = getProperty(context, key);
            if (prop == null)
            {
                Method m = setMethod(context, key);
                if (m != null)
                {
                    Object[] args = new Object[0];
                    args[0] = value;
                    try
                    {
                        m.invoke(context, args);
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }

                }
            }
            else
            {
                if (context instanceof GenericObject)
                {
                    SemanticObject semObject = ((GenericObject) context).getSemanticObject();

                    if (prop.isObjectProperty())
                    {
                        if (value instanceof SemanticObject)
                        {
                            semObject.setObjectProperty(prop, (SemanticObject) value);
                        }
                    }
                    else
                    {
                        semObject.setProperty(prop, value.toString());
                    }

                }
                else if (context instanceof SemanticObject)
                {
                    SemanticObject semObject = (SemanticObject) context;

                    if (prop.isObjectProperty())
                    {
                        if (value instanceof SemanticObject)
                        {
                            semObject.setObjectProperty(prop, (SemanticObject) value);
                        }
                    }
                    else
                    {
                        semObject.setProperty(prop, value.toString());
                    }

                }
            }
        }
        else
        {
            SemanticProperty prop = getProperty(context, key);
            if (prop == null)
            {
                Method m = setMethod(context, key);
                if (m != null)
                {
                    Object[] args = new Object[0];
                    try
                    {
                        Object newcontext = m.invoke(context, args);
                        if (newcontext != null)
                        {
                            setValue(newcontext, keys, value);
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);

                    }

                }
            }
            else
            {
                if (context instanceof GenericObject)
                {
                    SemanticObject semObject = ((GenericObject) context).getSemanticObject();

                    if (prop.isObjectProperty())
                    {
                        SemanticObject newcontext = semObject.getObjectProperty(prop);
                        if (newcontext != null)
                        {
                            setValue(newcontext, keys, value);
                        }
                    }
                    else
                    {
                        if (prop.isBoolean())
                        {
                            semObject.setBooleanProperty(prop, Boolean.parseBoolean(value.toString()));
                        }
                        else if (prop.isDate() && value instanceof Date)
                        {
                            semObject.setDateProperty(prop, (Date) value);
                        }
                        else if (prop.isDateTime() && value instanceof Timestamp)
                        {
                            semObject.setDateTimeProperty(prop, (Timestamp) value);
                        }
                        else
                        {
                            semObject.setProperty(prop, value.toString());
                        }
                    }
                }
                else if (context instanceof SemanticObject)
                {
                    SemanticObject semObject = (SemanticObject) context;

                    if (prop.isObjectProperty())
                    {
                        SemanticObject newcontext = semObject.getObjectProperty(prop);
                        if (newcontext != null)
                        {
                            setValue(newcontext, keys, value);
                        }
                    }
                    else
                    {
                        if (value == null)
                        {
                            semObject.setProperty(prop, null);
                        }
                        else
                        {
                            if (value == null)
                            {
                                semObject.setProperty(prop, null);
                            }
                            else
                            {
                                if (prop.isBoolean())
                                {
                                    semObject.setBooleanProperty(prop, Boolean.parseBoolean(value.toString()));
                                }
                                else if (prop.isDate() && value instanceof Date)
                                {
                                    semObject.setDateProperty(prop, (Date) value);
                                }
                                else if (prop.isDateTime() && value instanceof Timestamp)
                                {
                                    semObject.setDateTimeProperty(prop, (Timestamp) value);
                                }
                                else if (prop.isDateTime() && value instanceof Date)
                                {
                                    Timestamp ovalue = new Timestamp(((Date) value).getTime());
                                    semObject.setDateTimeProperty(prop, ovalue);
                                }
                                else
                                {
                                    semObject.setProperty(prop, value.toString());
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public static void setValue(Map<String, Object> values, LinkedList<String> keys, Object value)
    {
        String key = keys.removeFirst();
        Object context = values.get(key);
        if (context != null)
        {
            setValue(context, keys, value);
        }
    }

    public static void setValue(Map<String, Object> values, String variable, Object value)
    {
        String exp = "\\{\\w+(\\.\\w+)+\\}";
        Pattern p = Pattern.compile(exp);
        Matcher matcher = p.matcher(variable);
        while (matcher.find())
        {
            int start = matcher.start();
            int end = matcher.end();

            String tag = variable.substring(start, end);
            if (tag.startsWith("{"))
            {
                tag = tag.substring(1);
            }
            if (tag.endsWith("}"))
            {
                tag = tag.substring(0, tag.length() - 1);
            }
            try
            {
                String[] path = tag.split("\\.");
                LinkedList<String> keys = new LinkedList<String>();
                keys.addAll(Arrays.asList(path));
                setValue(values, keys, value);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public static String parser(Instance instance, User user, String script)
    {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("instance", instance);
        values.put("user", user);
        addInstanceObjects(instance, values);
        return parse(values, script);
    }

    private static void addInstanceObjects(Instance instance, HashMap values)
    {
        List<ItemAwareReference> list = instance.listHeraquicalItemAwareReference();
        for (ItemAwareReference item : list)
        {
            String varname = item.getItemAware().getName();
            SemanticObject object = item.getProcessObject().getSemanticObject();
            try
            {
                //System.out.println("Cargando clase "+className+" ...");
                Class clazz = SWBPClassMgr.getClassDefinition(object.getSemanticClass());
                //System.out.println("Obteniendo constructor...");
                Constructor c = clazz.getConstructor(SemanticObject.class);
                //System.out.println("Instanciando objeto...");
                Object instanceObject = c.newInstance(object);
                //System.out.println("Agregando variable "+varname+"="+instanceObject+" de tipo "+instanceObject.getClass());
                values.put(varname, instanceObject);
                //System.out.println("Variable "+ varname +" agregada");
            }
            catch (Exception cnfe)
            {
                log.error("No se agrego variable " + varname + " a script relacionada con el objeto " + object.getURI() + " en la instancia de proceso " + instance.getURI(), cnfe);
            }
        }
    }

    public static Object getValue(Instance instance, User user, String variable)
    {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("instance", instance);
        values.put("user", user);
        if (!variable.startsWith("{"))
        {
            variable = "{" + variable + "}";
        }
        return getValue(values, variable);
    }

    public static String evaluate(Map<String, Object> values, String tag)
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
            Object obj = evaluate(context, keys, "{" + tag + "}");
            if (obj != null)
            {
                return obj.toString();
            }
            else
            {
                return "";
            }
        }
        else
        {
            return "{" + tag + "}";
        }
    }

    private static Method getMethod(Object context, String name)
    {
        name = "get" + name;
        if (context instanceof SemanticObject)
        {
            context = ((SemanticObject) context).createGenericInstance();
        }

        

        for (Method m : context.getClass().getMethods())
        {
            if (m.getName().equalsIgnoreCase(name) && Modifier.isPublic(m.getModifiers()) && m.getParameterTypes().length == 0)
            {
                return m;
            }
        }


        return null;

    }

    private static Method setMethod(Object obj, String name)
    {
        name = "set" + name;
        for (Method m : obj.getClass().getMethods())
        {
            if (m.getName().equalsIgnoreCase(name) && Modifier.isPublic(m.getModifiers()) && m.getParameterTypes().length == 1)
            {
                return m;
            }
        }
        return null;

    }

    public static SemanticProperty getProperty(Object context, String name)
    {
        if (context instanceof GenericObject)
        {
            GenericObject go = (GenericObject) context;
            SemanticObject semObject = go.getSemanticObject();
            Iterator<SemanticProperty> props = semObject.getSemanticClass().listProperties();
            while (props.hasNext())
            {
                SemanticProperty prop = props.next();
                if (prop.getName().equalsIgnoreCase(name))
                {
                    return prop;
                }
            }
        }
        else if (context instanceof SemanticObject)
        {
            SemanticObject semObject = (SemanticObject) context;
            Iterator<SemanticProperty> props = semObject.getSemanticClass().listProperties();
            while (props.hasNext())
            {
                SemanticProperty prop = props.next();
                if (prop.getName().equalsIgnoreCase(name))
                {
                    return prop;
                }
            }
        }
        return null;
    }

    public static Object evaluate(Object context, LinkedList<String> keys, String tag)
    {
        if (context == null)
        {
            return "";
        }
        String key = keys.removeFirst();

        SemanticProperty prop = getProperty(context, key);
        if (prop == null)
        {
            if(context instanceof SemanticObject)
            {
                context=((SemanticObject)context).getGenericInstance();
            }
            Method m = getMethod(context, key);
            if (m != null)
            {
                Object[] args = new Object[0];
                try
                {
                    Object newcontext = m.invoke(context, args);
                    if (keys.isEmpty())
                    {
                        return newcontext;
                    }
                    else
                    {
                        return evaluate(newcontext, keys, tag);
                    }
                }
                catch (Exception e)
                {
                    return "";
                }
            }
        }
        else
        {
            if (context instanceof GenericObject)
            {
                GenericObject go = (GenericObject) context;
                SemanticObject semObject = go.getSemanticObject();

                if (prop.isObjectProperty())
                {
                    SemanticObject newcontext = semObject.getObjectProperty(prop);

                    if (keys.isEmpty())
                    {
                        return newcontext;
                    }
                    else
                    {

                        return evaluate(newcontext, keys, tag);
                    }
                }
                else
                {

                    return semObject.getProperty(prop);
                }
            }
            else if (context instanceof SemanticObject)
            {

                SemanticObject semObject = (SemanticObject) context;

                if (prop.isObjectProperty())
                {
                    SemanticObject newcontext = semObject.getObjectProperty(prop);

                    if (keys.isEmpty())
                    {
                        return newcontext;
                    }
                    else
                    {

                        return evaluate(newcontext, keys, tag);
                    }
                }
                else
                {

                    return semObject.getProperty(prop);
                }

            }
        }


        return tag;
    }

    public static String parse(Map<String, Object> values, String text)
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
        sb.append(text);
        return sb.toString();
    }

    public static Object evaluateGetValue(Object context, LinkedList<String> keys)
    {
        String key = keys.removeFirst();
        if (context instanceof GenericObject)
        {
            SemanticObject semObject = ((GenericObject) context).getSemanticObject();
            Iterator<SemanticProperty> props = semObject.getSemanticClass().listProperties();
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
                            return evaluateGetValue(newcontext, keys);
                        }
                    }
                    else
                    {
                        return semObject.getProperty(prop);
                    }
                }
            }
        }
        else if (context instanceof SemanticObject)
        {
            SemanticObject semObject = (SemanticObject) context;
            Iterator<SemanticProperty> props = semObject.getSemanticClass().listProperties();
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
                            return evaluateGetValue(newcontext, keys);
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
            if(context instanceof SemanticObject)
            {
                context=((SemanticObject)context).getGenericInstance();
            }
            Method m = getMethod(context, key);
            if (m != null)
            {
                Object[] args = new Object[0];
                try
                {
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
                        return evaluateGetValue(newcontext, keys);
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                    return null;
                }

            }
        }
        return null;
    }

    public static Object evaluateGetValue(Map<String, Object> values, String variable)
    {
        String[] path = variable.split("\\.");
        LinkedList<String> keys = new LinkedList<String>();
        keys.addAll(Arrays.asList(path));
        String key = keys.removeFirst();
        Object context = values.get(key);
        if (context != null)
        {
            return evaluateGetValue(context, keys);
        }
        else
        {
            return null;
        }


    }

    public static Object getValue(Map<String, Object> values, String variable)
    {

        String exp = "\\{\\w+(\\.\\w+)+\\}";
        Pattern p = Pattern.compile(exp);
        Matcher matcher = p.matcher(variable);
        while (matcher.find())
        {
            int start = matcher.start();
            int end = matcher.end();

            String tag = variable.substring(start, end);
            try
            {
                return evaluateGetValue(values, tag);
            }
            catch (Exception e)
            {
                log.error(e);
                return null;
            }
        }

        return null;
    }
}
