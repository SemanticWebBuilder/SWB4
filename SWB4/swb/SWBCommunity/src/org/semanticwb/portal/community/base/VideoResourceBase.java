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
package org.semanticwb.portal.community.base;


public abstract class VideoResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasVideo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasVideo");
    public static final org.semanticwb.platform.SemanticClass swbcomm_VideoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoResource");

    public VideoResourceBase()
    {
    }

    public VideoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> listVideos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement>(getSemanticObject().listObjectProperties(swbcomm_hasVideo));
    }

    public boolean hasVideo(org.semanticwb.portal.community.VideoElement videoelement)
    {
        boolean ret=false;
        if(videoelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasVideo,videoelement.getSemanticObject());
        }
        return ret;
    }

    public void addVideo(org.semanticwb.portal.community.VideoElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasVideo, value.getSemanticObject());
    }

    public void removeAllVideo()
    {
        getSemanticObject().removeProperty(swbcomm_hasVideo);
    }

    public void removeVideo(org.semanticwb.portal.community.VideoElement videoelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasVideo,videoelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.VideoElement getVideo()
    {
         org.semanticwb.portal.community.VideoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasVideo);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.VideoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
