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
package org.semanticwb.platform;

import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * objeto: generador de identificadores unicos para objetos de topicmaps.
 * 
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class IDGenerator
{
    
    /** The base id. */
    protected String baseID;
    
    /** The counter. */
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
     * 
     * @return the iD
     */
    public String getID()
    {
        return baseID + "-" + Long.toString(counter++, 16);
    }

    /**
     * Gets the counter.
     * 
     * @return the counter
     */
    public String getCounter()
    {
        return Long.toString(counter++, 16);
    }

    /**
     * Gets the iD.
     * 
     * @param titulo the titulo
     * @param mapid the mapid
     * @return the iD
     */
    public String getID(String titulo, String mapid)
    {
        return getID(titulo, mapid, true);
    }

    /**
     * Gets the iD.
     * 
     * @param titulo the titulo
     * @param mapid the mapid
     * @param contador the contador
     * @return the iD
     */
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
