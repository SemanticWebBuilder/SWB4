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


public class SWBRankWebPageBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_fullStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#fullStar");
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_emptyStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#emptyStar");
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_halfStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#halfStar");
    public static final org.semanticwb.platform.SemanticClass rankwebpage_SWBRankWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#SWBRankWebPage");

    public SWBRankWebPageBase()
    {
    }

    public SWBRankWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#SWBRankWebPage");

    public String getFullStar()
    {
        return getSemanticObject().getProperty(rankwebpage_fullStar);
    }

    public void setFullStar(String fullStar)
    {
        getSemanticObject().setProperty(rankwebpage_fullStar, fullStar);
    }

    public String getEmptyStar()
    {
        return getSemanticObject().getProperty(rankwebpage_emptyStar);
    }

    public void setEmptyStar(String emptyStar)
    {
        getSemanticObject().setProperty(rankwebpage_emptyStar, emptyStar);
    }

    public String getHalfStar()
    {
        return getSemanticObject().getProperty(rankwebpage_halfStar);
    }

    public void setHalfStar(String halfStar)
    {
        getSemanticObject().setProperty(rankwebpage_halfStar, halfStar);
    }
}
