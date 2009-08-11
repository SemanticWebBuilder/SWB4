/**  
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
**/ 
 
package org.semanticwb.repository.base;


public class VersionBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");

    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.repository.Version> listVersions(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.Version> listVersions()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
    }

    public static org.semanticwb.repository.Version getVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.repository.Version createVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeVersion(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (getVersion(id, model)!=null);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(jcr_created, created);
    }

    public void setSuccessors(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_successors, version.getSemanticObject());
    }

    public void removeSuccessors()
    {
        getSemanticObject().removeProperty(jcr_successors);
    }

    public org.semanticwb.repository.Version getSuccessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_successors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public void setPredecessors(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_predecessors, version.getSemanticObject());
    }

    public void removePredecessors()
    {
        getSemanticObject().removeProperty(jcr_predecessors);
    }

    public org.semanticwb.repository.Version getPredecessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_predecessors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }
}
