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


public class PostIn extends org.semanticwb.social.base.PostInBase 
{
    
    public PostIn(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    
    public static PostIn getPostInbySocialMsgId(SWBModel model, String socialMsgId)
    {
        //System.out.println("PostIn/getPostInbySocialMsgId-LLega:"+socialMsgId);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(PostIn.social_socialNetMsgId, socialMsgId);
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            //System.out.println("objJorge:"+obj);
            //System.out.println("objJorge GenInst:"+obj.getGenericInstance());
            if(obj.createGenericInstance() instanceof PostIn)
            {
                PostIn postIn=(PostIn)obj.createGenericInstance();
                //System.out.println("postInGeoge:"+postIn.getSocialNetMsgId());
                //return (PostIn)obj.createGenericInstance();
                return postIn;
            }
        }
        return null;
    }
    
    
    static {
        //Observador del objeto PostIn, este observador en realidad no se utiliza, ya que cada objeto
        //MessageIn, PhotoIn y VideoIn tienen su propio observador, de hecho probé y si hace algo con ellos
        //aunque desciendan de PostIn, no se ejecuta el código de este (PostIn), solo se ejecuta cuando se interactua
        //con ese objeto (PostIn) de manera directa.
        PostIn.social_PostIn.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null)
                {
                    PostIn postIn = (PostIn) obj.createGenericInstance();
                    
                    //Cada que un PostIn se elimine, se ejecutara este código, revisa si el usuario(SocialNetworkUser) asociado al PostIn,
                    //tiene mas PostIn asociados, de no ser así, elimina dicho usuario (SocialNetworkUser).
                    
                    //System.out.println("*********************postIn/Observer/action:"+action+", postIn:"+postIn);
                    
                    //TODO:Probar este código.
                    if(action.equals("REMOVE") && prop==null) //Si la acción es eliminar el SocialTopic
                    {
                        SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
                        if(socialNetworkUser!=null)
                        {
                            int i=0;
                            Iterator itPostInUserNumber=PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser);
                            while(itPostInUserNumber.hasNext())
                            {
                                i++;
                                //System.out.println("IJ1:"+i);
                                if(i>1) 
                                {
                                    //System.out.println("IJ2:"+i);
                                    break;
                                }
                                //System.out.println("IJ3:"+i);
                                itPostInUserNumber.next();
                            }
                           
                            //System.out.println("I:"+i);
                            
                            if(i<=1)
                            {
                                //System.out.println("MessageIn Observador-J3:"+socialNetworkUser+", FUE ELIMINADO...");
                                socialNetworkUser.remove();
                                System.out.println("PostIn Observador-J3.1:"+socialNetworkUser+", FUE ELIMINADO...");
                            }else{
                                System.out.println("Por PostIn, no encontro ni maiz k");
                            }
                    }
                    
                }
            }
            }
        });
    }
    
    
}
