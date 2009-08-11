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
 
package org.semanticwb.portal.community.base;


public class VideoBase extends org.semanticwb.portal.community.CommunityElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoType");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoDuration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoDuration");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoCode");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoPreview=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoPreview");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoSize");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Video");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Video");

    public VideoBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Video> listVideos(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Video>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Video> listVideos()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Video>(it, true);
    }

    public static org.semanticwb.portal.community.Video getVideo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Video)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Video createVideo(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Video)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeVideo(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasVideo(String id, org.semanticwb.model.SWBModel model)
    {
        return (getVideo(id, model)!=null);
    }

    public String getType()
    {
        return getSemanticObject().getProperty(swbcomm_videoType);
    }

    public void setType(String videoType)
    {
        getSemanticObject().setProperty(swbcomm_videoType, videoType);
    }

    public void setWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swbcomm_videoWebPage, webpage.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_videoWebPage);
    }

    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_videoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getDuration()
    {
        return getSemanticObject().getIntProperty(swbcomm_videoDuration);
    }

    public void setDuration(int videoDuration)
    {
        getSemanticObject().setIntProperty(swbcomm_videoDuration, videoDuration);
    }

    public String getCode()
    {
        return getSemanticObject().getProperty(swbcomm_videoCode);
    }

    public void setCode(String videoCode)
    {
        getSemanticObject().setProperty(swbcomm_videoCode, videoCode);
    }

    public String getPreview()
    {
        return getSemanticObject().getProperty(swbcomm_videoPreview);
    }

    public void setPreview(String videoPreview)
    {
        getSemanticObject().setProperty(swbcomm_videoPreview, videoPreview);
    }

    public int getSize()
    {
        return getSemanticObject().getIntProperty(swbcomm_videoSize);
    }

    public void setSize(int videoSize)
    {
        getSemanticObject().setIntProperty(swbcomm_videoSize, videoSize);
    }
}
