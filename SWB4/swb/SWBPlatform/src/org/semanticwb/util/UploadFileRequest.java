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
package org.semanticwb.util;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class UploadFileRequest.
 * 
 * @author serch
 */
public class UploadFileRequest
{

    /** The filtros. */
    private final HashMap<String, String> filtros;
    
    /** The multiples. */
    private final boolean multiples;
    
    /** The size. */
    private final long size;

    /**
     * Instantiates a new upload file request.
     * 
     * @param filtros the filtros
     * @param multiples the multiples
     * @param size the size
     */
    public UploadFileRequest(HashMap<String, String> filtros, boolean multiples, long size)
    {
        this.filtros = filtros;
        this.multiples = multiples;
        this.size = size;

    }

    /**
     * Gets the filtros.
     * 
     * @return the filtros
     */
    public HashMap<String, String> getFiltros()
    {
        return filtros;
    }

    /**
     * Checks if is multiples.
     * 
     * @return true, if is multiples
     */
    public boolean isMultiples()
    {
        return multiples;
    }

    /**
     * Size.
     * 
     * @return the long
     */
    public long size()
    {
        return size;
    }
}
