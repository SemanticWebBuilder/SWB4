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
package org.semanticwb.portal.resources.sem.base;


// TODO: Auto-generated Javadoc
/**
 * The Class HTMLContentBase.
 */
public class HTMLContentBase extends org.semanticwb.portal.resources.sem.Content implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    
    /** The Constant swb_VersionInfo. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    
    /** The Constant swb_actualVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    
    /** The Constant swb_lastVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    
    /** The Constant swbres_HTMLContent. */
    public static final org.semanticwb.platform.SemanticClass swbres_HTMLContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    /**
     * Instantiates a new hTML content base.
     */
    public HTMLContentBase()
    {
    }

    /**
     * Instantiates a new hTML content base.
     * 
     * @param base the base
     */
    public HTMLContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#setActualVersion(org.semanticwb.model.VersionInfo)
     */
    /**
     * Sets the actual version.
     * 
     * @param value the new actual version
     */
    public void setActualVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#removeActualVersion()
     */
    /**
     * Removes the actual version.
     */
    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

   /**
    * List html content by actual version.
    * 
    * @param actualversion the actualversion
    * @param model the model
    * @return the java.util. iterator
    */
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByActualVersion(org.semanticwb.model.VersionInfo actualversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_actualVersion, actualversion.getSemanticObject()));
       return it;
   }

   /**
    * List html content by actual version.
    * 
    * @param actualversion the actualversion
    * @return the java.util. iterator
    */
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByActualVersion(org.semanticwb.model.VersionInfo actualversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(actualversion.getSemanticObject().getModel().listSubjects(swb_actualVersion,actualversion.getSemanticObject()));
       return it;
   }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#getActualVersion()
     */
    /**
     * Gets the actual version.
     * 
     * @return the actual version
     */
    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#setLastVersion(org.semanticwb.model.VersionInfo)
     */
    /**
     * Sets the last version.
     * 
     * @param value the new last version
     */
    public void setLastVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#removeLastVersion()
     */
    /**
     * Removes the last version.
     */
    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

   /**
    * List html content by last version.
    * 
    * @param lastversion the lastversion
    * @param model the model
    * @return the java.util. iterator
    */
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByLastVersion(org.semanticwb.model.VersionInfo lastversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_lastVersion, lastversion.getSemanticObject()));
       return it;
   }

   /**
    * List html content by last version.
    * 
    * @param lastversion the lastversion
    * @return the java.util. iterator
    */
   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByLastVersion(org.semanticwb.model.VersionInfo lastversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(lastversion.getSemanticObject().getModel().listSubjects(swb_lastVersion,lastversion.getSemanticObject()));
       return it;
   }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.VersionableBase#getLastVersion()
     */
    /**
     * Gets the last version.
     * 
     * @return the last version
     */
    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }
}
