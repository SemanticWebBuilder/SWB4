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
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


   /**
   * Clase en donde se almacenan todos los post de tipo Photo y que entran por el Listener 
   */
public class PhotoIn extends org.semanticwb.social.base.PhotoInBase 
{
    public PhotoIn(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    static {
        //Observador del objeto PostIn
        PhotoIn.social_PhotoIn.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(PhotoIn.social_PhotoIn))
                {
                    //System.out.println("PhotoIn--Observer:"+obj+",prop:"+prop+",action:"+action);
                    //Cada que un PostIn se elimine, se ejecutara este código, revisa si el usuario(SocialNetworkUser) asociado al PostIn,
                    //tiene mas PostIn asociados, de no ser así, elimina dicho usuario (SocialNetworkUser).
                    if(action.equals("REMOVE") && prop==null) //Si la acción es eliminar el PhotoIn
                    {
                        PostIn postIn = (PostIn) obj.createGenericInstance();
                        //System.out.println("PhotoIn/Observer/action:"+action+", postIn:"+postIn);
                        //System.out.println("PhotoIn Observador-1");
                        SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
                        if(socialNetworkUser!=null)
                        {
                            //System.out.println("PhotoIn Observador-2:"+socialNetworkUser+", trae:"+PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser).hasNext());
                            
                            //Si dicho usuario no tiene mas PostIns, entonces que lo elimine de la clase SocialNetworkUser
                            //De lo contrarío se quedaría ahi como basura, pudiendo crecer demaciado la info en esa Clase.
                            
                            //****Si el usuario no tiene mas de un mensaje, que elimine a su SocialNewWorkUser asociado, porque un mensaje
                            //Porque javier mansa llamar este código antes de que se elimine el mensaje por el cual llegó aqui, de lo 
                            //contrario vería si no tiene ningún mensaje (0 mensajes) ejecutaría este código, pero como no es así, por 
                            //eso lo hago cuando solo tenga un mensaje, ya que aún cuenta por el que llega a este código.
                            
                            int i=0;
                            Iterator itPostInUserNumber=PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetworkUser);
                            while(itPostInUserNumber.hasNext())
                            {
                                i++;
                                if(i>1) break;
                                itPostInUserNumber.next();
                            }
                           
                            if(i<=1)    
                            {
                                socialNetworkUser.remove();
                                System.out.println("Elimino socialNetUser en PhotoIn:"+socialNetworkUser);
                            }
                        }
                        if(postIn.getPostInStream()!=null)
                        {
                            //Si el postIn que llega es el ultimo mensaje en su Stream, que elimine el registro(instancia) de ese Stream en la
                            //clase SocialNetStreamSearch
                            Stream stream=postIn.getPostInStream();
                            int i=0;
                            Iterator<PostIn> itPostInStreamNumber=stream.listPostInStreamInvs();
                            while(itPostInStreamNumber.hasNext())
                            {
                                i++;
                                if(i>1) break;
                                itPostInStreamNumber.next();
                            }

                            if(i<=1)    
                            {
                                Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
                                while(itSocialNetStreamSearch.hasNext())
                                {
                                    SocialNetStreamSearch socialNetStreamSearch=itSocialNetStreamSearch.next();
                                    //System.out.println("Remueve en Stream:"+socialNetStreamSearch);
                                    socialNetStreamSearch.remove();
                                }
                            }
                        }
                    }
                }
            }
        });
     }
}
