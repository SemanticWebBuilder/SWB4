/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class PostOut extends org.semanticwb.social.base.PostOutBase 
{
    public PostOut(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static PostOut getPostOutbySocialMsgId(SWBModel model, String socialMsgId)
    {
        //System.out.println("PostIn/getPostInbySocialMsgId-LLega:"+socialMsgId);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(PostOutNet.social_po_socialNetMsgID, socialMsgId);
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            //System.out.println("objJorge:"+obj);
            //System.out.println("objJorge GenInst:"+obj.createGenericInstance());
            if(obj.createGenericInstance() instanceof PostOutNet)
            {
                PostOutNet postOutNet=(PostOutNet)obj.createGenericInstance();
                //System.out.println("postOutNetGeoge:"+postOutNet.getPo_socialNetMsgID());
                //System.out.println("postOutGeoge:"+postOutNet.getSocialPost());
                //return (PostIn)obj.createGenericInstance();
                return postOutNet.getSocialPost();
            }
        }
        return null;
    }    
}
