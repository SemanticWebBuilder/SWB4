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


   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social. 
   */
public class SocialNetworkUser extends org.semanticwb.social.base.SocialNetworkUserBase 
{
    //USER GENDER
    public static final int USER_GENDER_MALE=1;
    public static final int USER_GENDER_FEMALE=2;
    public static final int USER_GENDER_UNDEFINED=3;
    
    //USER RELATIONSHIP STATUS
    public static final int USER_RELATION_SINGLE=1;
    public static final int USER_RELATION_MARRIED=2;
    public static final int USER_RELATION_DIVORCED=3;
    public static final int USER_RELATION_WIDOWED=4;
    public static final int USER_RELATION_UNDEFINED=5;
    
    //USER EDUCATION
    public static final int USER_EDUCATION_HIGHSCHOOL=1;
    public static final int USER_EDUCATION_COLLEGE=2;
    public static final int USER_EDUCATION_GRADUATE=3;
    public static final int USER_EDUCATION_UNDEFINED=4;
    
    public SocialNetworkUser(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static SocialNetworkUser getSocialNetworkUserbyIDAndSocialNet(String userId, SocialNetwork socialNetwork, SWBModel model)
    {
        //System.out.println("entra getSocialNetworkUserbyIDAndSocialNet-1:"+socialNetwork);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_snu_id, userId); //No encuentra
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            SocialNetworkUser socialNetUser=(SocialNetworkUser)obj.createGenericInstance();
            //System.out.println("GEOOOOORGGGEEE:socialNetUser.getSnu_SocialNetwork().getId():"+socialNetUser.getSnu_SocialNetworkObj().getId());
            //System.out.println("GEOOOOORGGGEEE:socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId():"+socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId());
            if(socialNetUser.getSnu_SocialNetworkObj()!=null && socialNetUser.getSnu_SocialNetworkObj().getId().equals(socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId()));
            {
                  return socialNetUser;
            }
        }
        return null;
    }
}
