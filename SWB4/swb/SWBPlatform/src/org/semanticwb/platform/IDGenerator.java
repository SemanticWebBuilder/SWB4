/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * IDGenerator.java
 *
 * Created on 2 de julio de 2002, 14:41
 */

package org.semanticwb.platform;

import org.semanticwb.SWBUtils;

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
        ret.append(SWBUtils.TEXT.replaceSpecialCharacters(titulo.toLowerCase(),true));
        String aux=ret.toString();
        if (aux.length() > 50)
            aux = aux.substring(0, 50);
        return aux;
    }

}
