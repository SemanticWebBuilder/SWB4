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


public class SWBCommentsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#Comment");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#hasComment");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_SWBComments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#SWBComments");

    public SWBCommentsBase()
    {
    }

    public SWBCommentsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#SWBComments");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment>(getSemanticObject().listObjectProperties(swb_res_cmts_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        if(comment==null)return false;        return getSemanticObject().hasObjectProperty(swb_res_cmts_hasComment,comment.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasComment, comment.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasComment);
    }

    public void removeComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasComment,comment.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.Comment getComment()
    {
         org.semanticwb.portal.resources.sem.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.Comment)obj.createGenericInstance();
         }
         return ret;
    }
}
