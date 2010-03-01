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
 
package org.semanticwb.model.comm.base;


// TODO: Auto-generated Javadoc
/**
 * The Class CommunityContainerBase.
 */
public class CommunityContainerBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Localeable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable
{
    
    /** The Constant swbcomm_MicroSiteType. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    
    /** The Constant swbcomm_hasMicroSiteType. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteType");
    
    /** The Constant swbcomm_CommunityContainer. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommunityContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityContainer");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityContainer");

    /**
     * Instantiates a new community container base.
     * 
     * @param base the base
     */
    public CommunityContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List community containers.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.CommunityContainer> listCommunityContainers(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityContainer>(it, true);
    }

    /**
     * List community containers.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.CommunityContainer> listCommunityContainers()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityContainer>(it, true);
    }

    /**
     * Gets the community container.
     * 
     * @param id the id
     * @return the community container
     */
    public static org.semanticwb.model.comm.CommunityContainer getCommunityContainer(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.model.comm.CommunityContainer ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.model.comm.CommunityContainer)obj.createGenericInstance();
           }
       }
       return ret;
    }

    /**
     * Creates the community container.
     * 
     * @param id the id
     * @param namespace the namespace
     * @return the org.semanticwb.model.comm. community container
     */
    public static org.semanticwb.model.comm.CommunityContainer createCommunityContainer(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.model.comm.CommunityContainer)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    /**
     * Removes the community container.
     * 
     * @param id the id
     */
    public static void removeCommunityContainer(String id)
    {
       org.semanticwb.model.comm.CommunityContainer obj=getCommunityContainer(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    /**
     * Checks for community container.
     * 
     * @param id the id
     * @return true, if successful
     */
    public static boolean hasCommunityContainer(String id)
    {
        return (getCommunityContainer(id)!=null);
    }

    /**
     * List micro site types.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSiteType> listMicroSiteTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSiteType>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteType));
    }

    /**
     * Checks for micro site type.
     * 
     * @param micrositetype the micrositetype
     * @return true, if successful
     */
    public boolean hasMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        if(micrositetype==null)return false;        return getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteType,micrositetype.getSemanticObject());
    }

    /**
     * Adds the micro site type.
     * 
     * @param micrositetype the micrositetype
     */
    public void addMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasMicroSiteType, micrositetype.getSemanticObject());
    }

    /**
     * Removes the all micro site type.
     */
    public void removeAllMicroSiteType()
    {
        getSemanticObject().removeProperty(swbcomm_hasMicroSiteType);
    }

    /**
     * Removes the micro site type.
     * 
     * @param micrositetype the micrositetype
     */
    public void removeMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasMicroSiteType,micrositetype.getSemanticObject());
    }

    /**
     * Gets the micro site type.
     * 
     * @return the micro site type
     */
    public org.semanticwb.model.comm.MicroSiteType getMicroSiteType()
    {
         org.semanticwb.model.comm.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.comm.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }
}
