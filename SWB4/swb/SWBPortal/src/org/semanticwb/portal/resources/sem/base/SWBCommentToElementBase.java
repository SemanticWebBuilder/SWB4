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
package org.semanticwb.portal.resources.sem.base;


public abstract class SWBCommentToElementBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_CommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#CommentToElement");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#hasComment");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_SWBCommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");

    public SWBCommentToElementBase()
    {
    }

   /**
   * Constructs a SWBCommentToElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBCommentToElement
   */
    public SWBCommentToElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.CommentToElement
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.CommentToElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(getSemanticObject().listObjectProperties(swb_res_cmts_hasComment));
    }

   /**
   * Gets true if has a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to verify
   * @return true if the org.semanticwb.portal.resources.sem.CommentToElement exists, false otherwise
   */
    public boolean hasComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_res_cmts_hasComment,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to add
   */

    public void addComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasComment, value.getSemanticObject());
    }
   /**
   * Removes all the Comment
   */

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasComment);
    }
   /**
   * Removes a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to remove
   */

    public void removeComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasComment,value.getSemanticObject());
    }

   /**
   * Gets the Comment
   * @return a org.semanticwb.portal.resources.sem.CommentToElement
   */
    public org.semanticwb.portal.resources.sem.CommentToElement getComment()
    {
         org.semanticwb.portal.resources.sem.CommentToElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CommentToElement)obj.createGenericInstance();
         }
         return ret;
    }
}
