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
package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface GenericObject.
 * 
 * @author Jei
 */
public interface GenericObject 
{
    
    /**
     * Regresa uri del objeto.
     * 
     * @return the uRI
     * @return
     */
    public String getURI();

    public String getShortURI();

    /**
     * Regresa Identificador del objeto.
     * 
     * @return the id
     * @return
     */
    public String getId();

    /**
     * Segresa el objeto semantico que contiene la informacion delobjeto.
     * 
     * @return the semantic object
     * @return
     */
    public SemanticObject getSemanticObject();    
    
    /**
     * Asigna la propiedad con el valor especificado.
     * 
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value);

    /**
     * Elimina una propiedad del objeto.
     * 
     * @param prop the prop
     * @return the generic object
     * @return
     */
    public GenericObject removeProperty(String prop);

    /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     * /dominio/Objeto/id.
     * 
     * @return String con la ruta relativa al directorio work
     */    
    public String getWorkPath();

    /**
     * Se invoca cuando la instancia del objeto es eliminado de cache.
     */
    public void dispose();

}
