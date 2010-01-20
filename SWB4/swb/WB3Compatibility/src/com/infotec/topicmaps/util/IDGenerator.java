/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * IDGenerator.java
 *
 * Created on 2 de julio de 2002, 14:41
 */

package com.infotec.topicmaps.util;

import com.infotec.appfw.util.AFUtils;

/** objeto: generador de identificadores unicos para objetos de topicmaps
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class IDGenerator
{
    protected String baseID;
    protected long counter;

    /**
     * Constructs a new IDGeneratorImpl object, with the
     * base part of the ID string set to the time of creation.
     */
    public IDGenerator()
    {
        long ts = System.currentTimeMillis();
        baseID = "x" + Long.toString(ts, 30);
    }

    /**
     * Returns a pseudo-unique identifier as a concatenation of
     * the base identifier created in the object constructor
     * followed by a dash (-) and then a simple, incrementing
     * counter value (encoded in hexadecimal).
     */
    public String getID()
    {
        return baseID + "-" + Long.toString(counter++, 16);
    }

    public String getCounter()
    {
        return Long.toString(counter++, 16);
    }

    public String getID(String titulo, String mapid)
    {
        return getID(titulo, mapid, true);
    }

    public String getID(String titulo, String mapid, boolean contador)
    {
        StringBuffer ret = new StringBuffer();
        if(mapid!=null && mapid.length()>0)
        {
//            if (mapid.length() > 4)
//                ret.append(mapid.substring(0, 4));
//            else
//                ret.append(mapid);
//            ret.append("_");
        }
        if (contador)
        {
            ret.append(getCounter());
            ret.append("_");
        }
        ret.append(AFUtils.replaceSpecialCharacters(titulo.toLowerCase(),true));
        String aux=ret.toString();
        if (aux.length() > 50)
            aux = aux.substring(0, 50);
        return aux;
    }

}
