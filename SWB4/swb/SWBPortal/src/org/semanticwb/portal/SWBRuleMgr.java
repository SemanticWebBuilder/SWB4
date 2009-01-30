/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * DBUser.java
 *
 * Created on 3 de junio de 2002, 11:20
 */
package org.semanticwb.portal;

import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticProperty;
import org.w3c.dom.*;


/** Objeto: Manejador de las Reglas en memoria.
 *
 * Object: Manager of the rules in memory.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBRuleMgr
{
    private static Logger log = SWBUtils.getLogger(SWBRuleMgr.class);

    public static final String TAG_INT_RULE ="SWBRule";
    public static final String TAG_INT_ROLE ="SWBRole";
    public static final String TAG_INT_USERGROUP ="SWBUserGroup";
    public static final String TAG_INT_ISREGISTERED ="SWBIsRegistered";
    public static final String TAG_INT_ISSIGNED ="SWBIsSigned";
    public static final String TAG_INT_DEVICE ="SWBDevice";
    public static final String TAG_REQUEST_PARAM="SWBReqParam";
    public static final String TAG_SESSION_ATT="SWBSessAtt";
    
    private HashMap<String,Document> doms;
    
//    //eval Inner Rules in occurrences
//    private HashMap occDoms;
//    private HashMap occUpds;

    /** Creates new DBUser */
    public SWBRuleMgr()
    {
        log.event("SWBRuleMgr Initialized...");
        doms = new HashMap();
        
////        //eval Inner Rules in occurrences
//        occDoms=new HashMap();
//        occUpds=new HashMap();
    }

    
    public void init()
    {
        Iterator<Rule> it = Rule.listRules();
        while (it.hasNext())
        {
            Rule rule = it.next();
            try
            {
                String xml=rule.getXml();
                if (xml != null)
                {
                    Document dom = SWBUtils.XML.xmlToDom(xml);
                    if (dom != null) doms.put(rule.getURI(), dom);
                }
            } catch (Exception e)
            {
                log.error("Rule:"+rule.getURI(), e);
            }
        }
    }
    
//    /**
//     * @param User user
//     * @param Occurrence occ
//     * @return boolean
//     */
//    public boolean eval(User user, Occurrence occ)
//    {
//        //System.out.println("EvalOcc:"+occ);
//        boolean ret=false;
//        String topicmap=occ.getDbdata().getIdtm();
//        String occid=occ.getId();
//        HashMap map=(HashMap)occDoms.get(topicmap);
//        HashMap mapUpd=(HashMap)occUpds.get(topicmap);
//        if(map==null)
//        {
//            map=new HashMap();
//            occDoms.put(topicmap,map);
//            mapUpd=new HashMap();
//            occUpds.put(topicmap,mapUpd);
//        }
//
//        Document dom=(Document)map.get(occid);
//        Timestamp update=(Timestamp)mapUpd.get(occid);
//
//        if(dom==null || update==null || !occ.getDbdata().getLastupdate().equals(update))
//        {
//            //System.out.println("AddOcc:"+occ);
//            String raux=occ.getResourceData();
////            raux=AFUtils.replaceAll(raux, "{|{","<");
////            raux=AFUtils.replaceAll(raux, "}|}",">");
//            //dom=AFUtils.getInstance().XmltoDom(AFUtils.replaceXMLTags(raux));
//            dom=AFUtils.getInstance().XmltoDom(raux);
//            update=occ.getDbdata().getLastupdate();
//            map.put(occid, dom);
//            mapUpd.put(occid,update);
//        }
//
//        if (user.getDom() != null && dom != null)
//        {
//            NodeList nl = dom.getElementsByTagName("rule");
//            if(nl.getLength()>0)
//            {
//                Node node = nl.item(0);
//                //System.out.println("Start:"+node.getNodeName());
//                if (node != null)
//                {
//                    ret=and(node, user, topicmap);
//                }
//            }
//        }
//        return ret;
//    }

    /**
     * @param user
     * @param rule
     * @return  */
    public boolean eval(User user, String rule_uri)
    {
        //System.out.println("rule:"+rule+" site:"+topicmap);
        boolean ret=false;
        Document rul = doms.get(rule_uri);
        if (user != null && rul != null)
        {
            Node node = rul.getChildNodes().item(0);
            //System.out.println("Start:"+node.getNodeName());
            if (node != null && node.getNodeName().equals("rule"))
            {
                ret=and(node, user);
            }
        }
        //System.out.println("user:"+user.getId()+" rule:"+rule+" site:"+topicmap+" ret:"+ret);
        //System.out.println(" ret:"+ret);
        return ret;
    }

    /**
     * @param node
     * @param usr
     * @return  */
    public boolean and(Node node, User user)
    {
        boolean ret;
        //System.out.println("and:"+node.getNodeName());
        NodeList nl = node.getChildNodes();
        for (int x = 0; x < nl.getLength(); x++)
        {
            if(nl.item(x)!=null)
            {
                if ("and".equals(nl.item(x).getNodeName()))
                    ret = and(nl.item(x), user);
                else if ("or".equals(nl.item(x).getNodeName()))
                    ret = or(nl.item(x), user);
                else
                    ret = exp(nl.item(x), user);
                if (!ret) 
                {
                    //System.out.println(" AND false");
                    return false;
                }
            }
        }
        //System.out.println(" AND true");
        return true;
    }

    /**
     * @param node
     * @param usr
     * @return  */
    public boolean or(Node node, User user)
    {
        boolean ret;
        //System.out.println("or:"+node.getNodeName());
        NodeList nl = node.getChildNodes();
        for (int x = 0; x < nl.getLength(); x++)
        {
            if(nl.item(x)!=null)
            {
                if ("and".equals(nl.item(x).getNodeName()))
                    ret = and(nl.item(x), user);
                else if ("or".equals(nl.item(x).getNodeName()))
                    ret = or(nl.item(x), user);
                else
                    ret = exp(nl.item(x), user);
                if (ret) return true;
            }
        }
        return false;
    }

    /**
     * @return
     * @param node
     * @param usr  */
    public boolean exp(Node node, User user)
    {
        boolean ret = false;
        //System.out.println("exp:"+node.getNodeName());
        try
        {
            Node aux = node.getChildNodes().item(0);
            if (aux == null) return false;
            String name = node.getNodeName();
            String cond = "=";
            Node att = node.getAttributes().getNamedItem("cond");
            if (att != null) cond = att.getNodeValue();
            String value = aux.getNodeValue();
            
            //System.out.println("cond:"+cond +" value:"+value);
            
            //validacion de Reglas
            if(name.equals(TAG_INT_RULE))
            {
                if(cond.equals("="))
                {
                    return eval(user, value);
                }else 
                {
                    return !eval(user, value);
                }
            }else if(name.equals(TAG_INT_ROLE)) //validacion de roles
            {
                Role role=(Role)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(value);
                if(cond.equals("="))
                {
                    return user.hasRole(role);
                }else 
                {
                    return !user.hasRole(role);
                }
            }else if(name.equals(TAG_INT_USERGROUP)) //validacion de roles
            {
                UserGroup group=(UserGroup)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(value);
                if(cond.equals("="))
                {
                    return group.hasUser(user);
                }else
                {
                    return !group.hasUser(user);
                }
            }else if(name.equals(TAG_INT_ISREGISTERED)) //validacion de roles
            {
                return user.isRegistered()==Boolean.parseBoolean(value);
            }else if(name.equals(TAG_INT_ISSIGNED)) //validacion de roles
            {
                return user.isSigned()==Boolean.parseBoolean(value);
            }else if(name.equals(TAG_INT_DEVICE)) //validacion de roles
            {
                return user.getDevice().equals(value);
            }else //se busca en el xml del usuario
            {
                Iterator<SemanticProperty> it=user.getSemanticObject().getSemanticClass().listProperties();
                while(it.hasNext())
                {
                    SemanticProperty prop=it.next();
                    if(prop.isDataTypeProperty())
                    {
                        String usrval = user.getSemanticObject().getProperty(prop);
                        System.out.println(usrval+cond+value);
                        if (cond.equals("="))
                        {
                            if (usrval.equals(value)) ret = true;
                        } else if (cond.equals("!="))
                        {
                            if (!usrval.equals(value)) ret = true;
                        } else if (cond.equals(">"))
                        {
                            try
                            {
                                float i = Float.parseFloat(usrval);
                                float j = Float.parseFloat(value);
                                if (i > j) ret = true;
                            } catch (NumberFormatException e)
                            {
                                if (usrval.compareTo(value) > 0) ret = true;
                            }
                        } else if (cond.equals("<"))
                        {
                            try
                            {
                                float i = Float.parseFloat(usrval);
                                float j = Float.parseFloat(value);
                                if (i < j) ret = true;
                            } catch (NumberFormatException e)
                            {
                                if (usrval.compareTo(value) < 0) ret = true;
                            }
                        }
                        //System.out.println(ret);
                        if (ret) return true;
                    }
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        System.out.println(ret);
        return ret;
    }

    public void destroy()
    {
        log.event("SWBRuleMgr Destroyed...");
    }

    public void reloadRule(Rule rule)
    {
        try
        {
            String xml=rule.getXml();
            if (xml != null)
            {
                Document dom = SWBUtils.XML.xmlToDom(xml);
                if (dom != null) doms.put(rule.getURI(), dom);
            }
        } catch (Exception e)
        {
            log.error("Rule:"+rule.getURI(), e);
        }
    }

//    /** Avisa al observador de que se ha producido un cambio.
//     * @param s
//     * @param obj  */
//    public void sendDBNotify(String s, Object obj)
//    {
//        RecRule rule=(RecRule)obj;
//        if(rule==null)return;
//        HashMap map=(HashMap)doms.get(rule.getTopicMapId());
//        if(map==null)
//        {
//            map=new HashMap();
//            doms.put(rule.getTopicMapId(),map);
//        }
//
//        if (s.equals("remove"))
//        {
//            map.remove(new Integer(rule.getId()));
//        } else if (s.equals("create"))
//        {
//            try
//            {
//                if (rule.getXml() != null)
//                {
//                    Document dom = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(rule.getXml());
//                    if (dom != null) map.put(new Integer(rule.getId()), dom);
//                }
//            } catch (Exception e)
//            {
//                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RuleMgr_sendDBNotify_trasnXML") + rule.getId(), true);
//            }
//        } else if (s.equals("update") || s.equals("load"))
//        {
//            try
//            {
//                if (rule.getXml() != null)
//                {
//                    Document dom = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(rule.getXml());
//                    if (dom != null)
//                    {
//                        map.remove(new Integer(rule.getId()));
//                        map.put(new Integer(rule.getId()), dom);
//                    }
//                }
//            } catch (Exception e)
//            {
//                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RuleMgr_sendDBNotify_trasnXML") + rule.getId(), true);
//            }
//        }
//    }

}
