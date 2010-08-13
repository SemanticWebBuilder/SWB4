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
 


/*
 * DBUser.java
 *
 * Created on 3 de junio de 2002, 11:20
 */
package org.semanticwb.model;

import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.w3c.dom.*;


// TODO: Auto-generated Javadoc
/** Objeto: Manejador de las Reglas en memoria.
 *
 * Object: Manager of the rules in memory.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBRuleMgr
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBRuleMgr.class);

    /** The Constant TAG_INT_RULE. */
    public static final String TAG_INT_RULE ="SWBRule";
    
    /** The Constant TAG_INT_ROLE. */
    public static final String TAG_INT_ROLE ="SWBRole";
    
    /** The Constant TAG_INT_USERGROUP. */
    public static final String TAG_INT_USERGROUP ="SWBUserGroup";
    
    /** The Constant TAG_INT_ISREGISTERED. */
    public static final String TAG_INT_ISREGISTERED ="SWBIsRegistered";
    
    /** The Constant TAG_INT_ISSIGNED. */
    public static final String TAG_INT_ISSIGNED ="SWBIsSigned";
    
    /** The Constant TAG_INT_DEVICE. */
    public static final String TAG_INT_DEVICE ="SWBDevice";
    
    /** The Constant TAG_REQUEST_PARAM. */
    public static final String TAG_REQUEST_PARAM="SWBReqParam";
    
    /** The Constant TAG_SESSION_ATT. */
    public static final String TAG_SESSION_ATT="SWBSessAtt";
    
    /** The doms. */
    private HashMap<String,Document> doms;
    
//    //eval Inner Rules in occurrences
//    private HashMap occDoms;
//    private HashMap occUpds;

    /**
 * Creates new DBUser.
 */
    public SWBRuleMgr()
    {
        log.event("SWBRuleMgr Initialized...");
        doms = new HashMap();
        
////        //eval Inner Rules in occurrences
//        occDoms=new HashMap();
//        occUpds=new HashMap();
    }

    
    /**
     * Inits the.
     */
    public void init()
    {
//        Iterator<Rule> it = Rule.ClassMgr.listRules();
//        while (it.hasNext())
//        {
//            Rule rule = it.next();
//            try
//            {
//                String xml=rule.getXml();
//                if (xml != null)
//                {
//                    Document dom = SWBUtils.XML.xmlToDom(xml);
//                    if (dom != null)
//                    {
//                        doms.put(rule.getURI(), dom);
//                    }
//                }
//            } catch (Exception e)
//            {
//                log.error("Rule:"+rule.getURI(), e);
//            }
//        }
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
 * Eval.
 * 
 * @param user the user
 * @param rule_uri the rule_uri
 * @return true, if successful
 * @return
 */
    public boolean eval(User user, String rule_uri)
    {
        //System.out.println("rule:"+rule+" site:"+topicmap);
        boolean ret=false;
        Document rul = doms.get(rule_uri);
        if(rul==null)
        {
            SemanticObject obj=SemanticObject.createSemanticObject(rule_uri);
            if(obj!=null)
            {
                reloadRule((Rule)obj.createGenericInstance());
                rul = doms.get(rule_uri);
            }
        }
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
     * And.
     * 
     * @param node the node
     * @param user the user
     * @return true, if successful
     * @return
     */
    public boolean and(Node node, User user)
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
                    ret = and(nl.item(x), user);
                }
                else if ("or".equals(nl.item(x).getNodeName()))
                {
                    ret = or(nl.item(x), user);
                }else
                {
                    ret = exp(nl.item(x), user);
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
    public boolean or(Node node, User user)
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
                    ret = and(nl.item(x), user);
                }
                else if ("or".equals(nl.item(x).getNodeName()))
                {
                    ret = or(nl.item(x), user);
                }else
                {
                    ret = exp(nl.item(x), user);
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
    public boolean exp(Node node, User user)
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
                Device dev=(Device)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(value);
                return user.hasDevice(dev);
            }else //se busca en el xml del usuario
            {
                SemanticProperty prop=user.getSemanticObject().getSemanticClass().getProperty(name);
                if(prop!=null && prop.isDataTypeProperty())
                {
                    String usrval = user.getSemanticObject().getProperty(prop);
                    //System.out.println(usrval+cond+value);
                    if(usrval==null && value==null)
                    {
                        ret=true;
                    }else if(usrval==null)
                    {
                        ret=false;
                    }else if (cond.equals("="))
                    {
                        if (usrval.equals(value))
                        {
                            ret = true;
                        }
                    } else if (cond.equals("!="))
                    {
                        if (!usrval.equals(value))
                        {
                            ret = true;
                        }
                    } else if (cond.equals(">"))
                    {
                        try
                        {
                            float i = Float.parseFloat(usrval);
                            float j = Float.parseFloat(value);
                            if (i > j)
                            {
                                ret = true;
                            }
                        } catch (NumberFormatException e)
                        {
                            if (usrval.compareTo(value) > 0)
                            {
                                ret = true;
                            }
                        }
                    } else if (cond.equals("<"))
                    {
                        try
                        {
                            float i = Float.parseFloat(usrval);
                            float j = Float.parseFloat(value);
                            if (i < j)
                            {
                                ret = true;
                            }
                        } catch (NumberFormatException e)
                        {
                            if (usrval.compareTo(value) < 0)
                            {
                                ret = true;
                            }
                        }
                    }
                    //System.out.println(ret);
                    //if (ret) return true;
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        //System.out.println(ret);
        return ret;
    }

    /**
     * Destroy.
     */
    public void destroy()
    {
        log.event("SWBRuleMgr Destroyed...");
    }

    /**
     * Reload rule.
     * 
     * @param rule the rule
     */
    public void reloadRule(Rule rule)
    {
        try
        {
            String xml=rule.getXml();
            if (xml != null)
            {
                Document dom = SWBUtils.XML.xmlToDom(xml);
                if (dom != null)
                {
                    doms.put(rule.getURI(), dom);
                }
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
