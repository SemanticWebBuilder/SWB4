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
package org.semanticwb.portal.admin.resources.workflow.proxy;

// TODO: Auto-generated Javadoc
/**
 * Clase utileria utilizada en algunos metodos de la clase Proxy workflow, para
 * indicar id de flujo y versi�n generada dentro del motor de workflow.
 * 
 * @author Victor Lorenzana
 */
public class WorkflowResponse {
    
    /** The id. */
    String id;
    
    /** The version. */
    int version;
    
    /**
     * Contructor de la clase WorlflowResponse, debe incluir el Id del flujo de
     * publicaci�n y la versi�n del mismo.
     * 
     * @param id the id
     * @param version the version
     */    
    public WorkflowResponse(String id,int version) {
        this.id=id;
        this.version=version;
    }
    
    /**
     * Regresa el id del flujo de publicaci�n creado.
     * 
     * @return the iD
     */    
    public String getID()
    {
        return id;
    }
    
    /**
     * Regresa la versi�n del flujo de publicaci�n creado.
     * 
     * @return the version
     */    
    public int getVersion()
    {
        return version;
    }
    
}
