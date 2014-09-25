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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialRule;
import org.semanticwb.social.Stream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialRuleMgr {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBSocialRuleMgr.class);
    
    private static final String SENTIMENT_TYPE=PostIn.social_postSentimentalType.getName();
    private static final String KLOUT_VALUE=Stream.social_stream_KloutValue.getName();
    private static final String MESSAGE_TEXT=MessageIn.social_msg_Text.getName();
    
    
    /**
 * Eval.
 * 
 * @param user the user
 * @param rule_uri the rule_uri
 * @return true, if successful
 * @return
 */
    public boolean eval(PostIn postIn, SocialRule socialRule)
    {
        //System.out.println("En SocialRuleMgr/eval_:"+((MessageIn)postIn).getMsg_Text()+",socialRule:"+socialRule.getId());
        boolean ret=false;
        if(postIn != null && socialRule != null && socialRule.getXml()!=null)
        {
            Document rul = SWBUtils.XML.xmlToDom(socialRule.getXml()); 

            Node node = rul.getChildNodes().item(0);
            //System.out.println("Start:"+node.getNodeName());
            if (node != null && node.getNodeName().equals("rule"))
            {
                ret=and(node, postIn);
            }
        }
        //System.out.println("user:"+user.getId()+" rule:"+rule+" site:"+topicmap+" ret:"+ret);
        //System.out.println(" ret:"+ret);
        return ret;
    }

    /**
     * And.
     * 
     * @param node the node
     * @param user the user
     * @return true, if successful
     * @return
     */
    public boolean and(Node node, PostIn postIn)
    {
        boolean ret=true;
        //System.out.println("and:"+node.getNodeName());
        NodeList nl = node.getChildNodes();
        for (int x = 0; x < nl.getLength(); x++)
        {
            if(nl.item(x)!=null)
            {
                if ("and".equals(nl.item(x).getNodeName()))
                {
                    ret = and(nl.item(x), postIn);
                }
                else if ("or".equals(nl.item(x).getNodeName()))
                {
                    ret = or(nl.item(x), postIn);
                }else
                {
                    ret = exp(nl.item(x), postIn);
                }
                if (!ret) 
                {
                    //System.out.println(" AND false");
                    ret=false;
                    break;
                }
            }
        }
        //System.out.println(" AND true");
        return ret;
    }

    /**
     * Or.
     * 
     * @param node the node
     * @param user the user
     * @return true, if successful
     * @return
     */
    public boolean or(Node node, PostIn postIn)
    {
        boolean ret=false;
        //System.out.println("or:"+node.getNodeName());
        NodeList nl = node.getChildNodes();
        for (int x = 0; x < nl.getLength(); x++)
        {
            if(nl.item(x)!=null)
            {
                if ("and".equals(nl.item(x).getNodeName()))
                {
                    ret = and(nl.item(x), postIn);
                }
                else if ("or".equals(nl.item(x).getNodeName()))
                {
                    ret = or(nl.item(x), postIn);
                }else
                {
                    ret = exp(nl.item(x), postIn);
                }
                if (ret)
                {
                    ret=true;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Exp.
     * 
     * @param node the node
     * @param user the user
     * @return true, if successful
     * @return
     */
    public boolean exp(Node node, PostIn postIn)
    {
        boolean ret = false;
        //System.out.println("exp:"+node.getNodeName());
        try
        {
            Node aux = node.getChildNodes().item(0);
            if (aux == null)
            {
                return false;
            }
            String name = node.getNodeName();
            String cond = "=";
            Node att = node.getAttributes().getNamedItem("cond");
            if (att != null)
            {
                cond = att.getNodeValue();
            }
            String value = aux.getNodeValue();
            
            //System.out.println("name:"+name+" cond:"+cond +" value:"+value);
            //new Exception().printStackTrace();
            
            if(name.equals(SENTIMENT_TYPE)) //validacion de tipo de sentimiento
            {
                try
                {
                    //System.out.println("En SocialRuleMgr/exp_:"+SENTIMENT_TYPE+",cond:"+cond);
                    if(cond.equals("="))
                    {
                        boolean x=postIn.getPostSentimentalType()==Integer.parseInt(value);
                        //System.out.println("REGRESA-1:"+x); 
                        return x;
                    }else 
                    {
                        return postIn.getPostSentimentalType()!=Integer.parseInt(value); 
                    }
                }catch (NumberFormatException e)
                {
                        log.error(e);
                        ret = false;
                }
            }else if(name.equals(KLOUT_VALUE)) //validacion de si el usuario del mensaje tiene un klout mayor o igual al valor del nodo de la regla
            {
                //System.out.println("En SocialRuleMgr/exp_:"+KLOUT_VALUE+",cond:"+cond);
                try
                {
                    if(cond.equals(">") && value!=null && postIn!=null && postIn.getPostInSocialNetworkUser()!=null)
                    {
                        
                        //System.out.println("Klout de user:"+postIn.getPostInSocialNetworkUser().getSnu_id()+",klout:"+postIn.getPostInSocialNetworkUser().getSnu_klout());
                        //boolean x=postIn.getPostInSocialNetworkUser().getSnu_klout()>=Float.parseFloat(value);
                        
                        //System.out.println("REGRESA-2:"+x);
                        return postIn.getPostInSocialNetworkUser().getSnu_klout()>=Float.parseFloat(value);
                    }
                }catch (NumberFormatException e)
                {
                        log.error(e);
                        ret = false;
                }
            }else if(name.equals(MESSAGE_TEXT)) //validacion de si el mensaje contiene en su texto el texto del valor del nodo de la regla
            {
                //System.out.println("En SocialRuleMgr/exp_:"+MESSAGE_TEXT+",cond:"+cond);
                try
                {
                    if(cond.equals("="))
                    {
                        //boolean x=((MessageIn) postIn).getMsg_Text().toLowerCase().contains(value.toLowerCase());
                        //System.out.println("REGRESA-Jorge09Sep..");
                        //return ((PostIn) postIn).getMsg_Text().toLowerCase().contains(value.toLowerCase());
                        return postIn.getMsg_Text().toLowerCase().contains(value.toLowerCase());
                    }
                }catch(Exception e)
                {
                    log.error(e);
                    ret = false;
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        //System.out.println(ret);
        return ret;
    }

    
}
