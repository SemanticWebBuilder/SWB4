/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.SocialFlow;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.PFlowRef;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialPFlowInstance;
import org.semanticwb.social.SocialPFlowRef;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.util.SWBSocialUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 * 
 */

// TODO: Auto-generated Javadoc
/**
 * The Class SocialPFlowMgr.
 * 
 * @author jorge.jimenez
 * Si status es -1= enviado al autor del contenido, es como si no fuera enviado
 * Si status es 0 = no enviado ninguna vez
 * Si status es 1= aprovado, pero no necesariamnete autorizado
 * Si status es 2= autorizado // fin de ejecución del
 * Si status es 3 = rechazado   // fin de ejecución, rechazado, no hay que volver a envíar
 */

public class SocialPFlowMgr {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SocialPFlowMgr.class);

    /**
     * Instantiates a new p flow manager.
     */
    public SocialPFlowMgr()
    {
    }

    /**
     * Inits the.
     */
    public void init()
    {
        log.event("Initializing SocialPFlowMgr...");
    }

    /**
     * Gets the flows to send content.
     * 
     * @param resource the resource
     * @return the flows to send content
     */
    public static SocialPFlow[] getFlowsToSendContent(PostOut resource)
    {
        HashSet<SocialPFlow> flows = new HashSet<SocialPFlow>();
        //WebSite site = WebSite.ClassMgr.getWebSite(resource.getSemanticObject().getModel().getName());
        SocialTopic socialTopic=resource.getSocialTopic();
        
        for (SocialPFlow flow : getFlows(socialTopic))
        {
            String _xml = flow.getXml();
            Document docflow = SWBUtils.XML.xmlToDom(_xml);
            Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
            NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
            for (int ires = 0; ires < resourceTypes.getLength(); ires++)
            {
                Element eres = (Element) resourceTypes.item(ires);
                String iresw = eres.getAttribute("id");
                //ResourceType type = site.getResourceType(iresw);
                //if (resource.getResourceType().equals(type))
                if (resource.getSemanticObject().getSemanticClass().getClassId().equals(iresw))
                {
                    flows.add(flow); 
                }
            }
        }
            
        return flows.toArray(new SocialPFlow[flows.size()]);
    }

    /**
     * Gets the flows.
     * 
     * @param page the page
     * @return the flows
     */
    public static SocialPFlow[] getFlows(SocialTopic page)
    {
        HashSet<SocialPFlow> flows = new HashSet<SocialPFlow>();
        GenericIterator<SocialPFlowRef> refs = page.listInheritPFlowRefs();
        while (refs.hasNext())
        {
            SocialPFlowRef ref = refs.next();
            if (ref.isActive())
            {
                flows.add(ref.getPflow());
            }
        }
        return flows.toArray(new SocialPFlow[flows.size()]);
    }

    /**
     * Gets the contents at flow of user.
     * 
     * @param user the user
     * @param site the site
     * @return the contents at flow of user
     */
    public static PostOut[] getContentsAtFlowOfUser(User user, WebSite site)
    {
        HashSet<PostOut> getContentsAtFlowOfUser = new HashSet<PostOut>();
        if (site != null)
        {
            Iterator<SocialPFlow> flows = SocialPFlow.ClassMgr.listSocialPFlows(site);
            while (flows.hasNext())
            {
                SocialPFlow flow = flows.next();
                Iterator<SocialPFlowInstance> instances = flow.listSocialPFlowinstances();
                while (instances.hasNext())
                {
                    SocialPFlowInstance instance = instances.next();
                    PostOut postOut = instance.getPfinstPostOut();
                    if (postOut != null && postOut.getSocialTopic()!=null && isInFlow(postOut) && postOut.getCreator() != null && postOut.getCreator().equals(user) && isSocialPflowRefActive(postOut, flow))
                    {
                        getContentsAtFlowOfUser.add(postOut);
                    }
                }
            }
        }
        return getContentsAtFlowOfUser.toArray(new PostOut[getContentsAtFlowOfUser.size()]);
    }

    
    /**
     * Gets the contents at flow all.
     * 
     * @param site the site
     * @return the contents at flow all
     */
    /*
    public static PostOut[] getContentsAtFlowAll(WebSite site)
    {
        HashSet<PostOut> getContentsAtFlowAll = new HashSet<PostOut>();
        if (site != null)
        {
            //Iterator<PFlow> flows = site.listPFlows();
            Iterator<SocialPFlow> flows = SocialPFlow.ClassMgr.listSocialPFlows(site);
            while (flows.hasNext())
            {
                SocialPFlow flow = flows.next();
                if(flow.isActive()) //Agregado Jorge
                {
                    Iterator<SocialPFlowInstance> instances = flow.listSocialPFlowinstances(); 
                    while (instances.hasNext())
                    {
                        SocialPFlowInstance instance = instances.next();
                        PostOut postOut = instance.getPfinstPostOut();
                        if(postOut.getSocialTopic()!=null)
                        {
                            if (postOut != null && isInFlow(postOut))
                            {
                                getContentsAtFlowAll.add(postOut);
                            }
                        }
                    }
                }
            }
        }
        return getContentsAtFlowAll.toArray(new PostOut[getContentsAtFlowAll.size()]);
    }
    * */
    
    public static PostOut[] getContentsAtFlowAll(WebSite site)
    {
        HashSet<PostOut> getContentsAtFlowAll = new HashSet<PostOut>();
        if (site != null)
        {
            //Iterator<PFlow> flows = site.listPFlows();
            Iterator<SocialPFlow> flows = SocialPFlow.ClassMgr.listSocialPFlows(site);
            while (flows.hasNext())
            {
                SocialPFlow flow = flows.next();
                if(flow.isActive()) //Agregado Jorge
                {
                    Iterator<SocialPFlowInstance> instances = flow.listSocialPFlowinstances(); 
                    while (instances.hasNext())
                    {
                        SocialPFlowInstance instance = instances.next();
                        PostOut postOut = instance.getPfinstPostOut();
                        if(postOut!=null && postOut.getSocialTopic()!=null)
                        {
                            if (isInFlow(postOut) && isSocialPflowRefActive(postOut, flow)) 
                            {
                                getContentsAtFlowAll.add(postOut);
                            }
                        }
                    }
                }
            }
        }
        return getContentsAtFlowAll.toArray(new PostOut[getContentsAtFlowAll.size()]);
    }
    
    
    private static boolean isSocialPflowRefActive(PostOut postOut, SocialPFlow socialPflow)
    {
        SocialTopic socialTopic=postOut.getSocialTopic();
        Iterator<SocialPFlowRef> itPFlowRefs=socialTopic.listInheritPFlowRefs();
        while(itPFlowRefs.hasNext())
        {
            SocialPFlowRef socialPFlowRef=itPFlowRefs.next();
            if(postOut.getPflowInstance().getPflow().getURI().equals(socialPFlowRef.getPflow().getURI()))
            {
                if(socialPFlowRef.isActive()) return true;
                else return false;
            }
        }
        return false;
    }
    
    
    /*
    public static PostOut[] getContentsAtFlowAll(WebSite site)
    {
        HashSet<PostOut> getContentsAtFlowAll = new HashSet<PostOut>();
        if (site != null)
        {
            //Iterator<PFlow> flows = site.listPFlows();
            Iterator<SocialPFlow> flows = SocialPFlow.ClassMgr.listSocialPFlows(site);
            while (flows.hasNext())
            {
                SocialPFlow flow = flows.next();
                if(flow.isActive()) //Agregado Jorge
                {
                    Iterator<SocialPFlowRef> pflowRefs = flow.listSocialPFlowRefInvs(); 
                    while (pflowRefs.hasNext())
                    {
                        SocialPFlowRef pflowRef = pflowRefs.next();
                        if(pflowRef.isActive())
                        {
                            Iterator <GenericObject> itGenObjs=pflowRef.listRelatedObjects();
                            while(itGenObjs.hasNext())
                            {
                                GenericObject genObj=itGenObjs.next();
                                if(genObj instanceof SocialTopic)
                                {
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
        return getContentsAtFlowAll.toArray(new PostOut[getContentsAtFlowAll.size()]);
    }
    */
    
    

    /**
     * Gets the contents at flow.
     * 
     * @param user the user
     * @param site the site
     * @return the contents at flow
     */
    public static PostOut[] getContentsAtFlow(User user, WebSite site)
    {        
        HashSet<PostOut> getContentsAtFlow = new HashSet<PostOut>();
        if (site != null)
        {
            Iterator<SocialPFlow> flows = SocialPFlow.ClassMgr.listSocialPFlows(site);
            while (flows.hasNext())
            {
                SocialPFlow flow = flows.next();                
                Iterator<SocialPFlowInstance> instances = flow.listSocialPFlowinstances();
                while (instances.hasNext())
                {
                    SocialPFlowInstance instance = instances.next();
                    PostOut postOut = instance.getPfinstPostOut();                    
                    if (postOut != null && postOut.getSocialTopic()!=null && isInFlow(postOut) && isReviewer(postOut, user) && isSocialPflowRefActive(postOut, flow))
                    {
                        getContentsAtFlow.add(postOut);
                    }
                }
            }
        }
        return getContentsAtFlow.toArray(new PostOut[getContentsAtFlow.size()]);
    }

    /**
     * Gets the contents at flow.
     * 
     * @param pflow the pflow
     * @return the contents at flow
     */
    public static PostOut[] getContentsAtFlow(SocialPFlow pflow)
    {
        HashSet<PostOut> getContentsAtFlow = new HashSet<PostOut>();
        Iterator<SocialPFlowInstance> instances = pflow.listSocialPFlowinstances();
        while (instances.hasNext())
        {
            SocialPFlowInstance instance = instances.next();
            PostOut resource = instance.getPfinstPostOut();
            if (resource != null && isInFlow(resource))
            {
                getContentsAtFlow.add(resource);
            }
        }
        return getContentsAtFlow.toArray(new PostOut[getContentsAtFlow.size()]);
    }

    /**
     * Gets the contents at flow.
     * 
     * @param pflow the pflow
     * @param site the site
     * @return the contents at flow
     */
    public static PostOut[] getContentsAtFlow(SocialPFlow pflow, WebSite site)
    {
        HashSet<PostOut> getContentsAtFlow = new HashSet<PostOut>();
        if (site != null)
        {
            Iterator<SocialPFlowInstance> instances = pflow.listSocialPFlowinstances();
            while (instances.hasNext())
            {
                SocialPFlowInstance instance = instances.next();
                if (instance.getPflow().getWebSite().equals(site))
                {
                    PostOut resource = instance.getPfinstPostOut();
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new PostOut[getContentsAtFlow.size()]);
    }

    /**
     * Gets the contents at flow.
     * 
     * @param user the user
     * @return the contents at flow
     */
    public static PostOut[] getContentsAtFlow(User user)
    {
        HashSet<PostOut> getContentsAtFlow = new HashSet<PostOut>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            PostOut[] resources = getContentsAtFlowAll(site);
            for (PostOut resource : resources)
            {
                if (resource != null && isInFlow(resource) && isReviewer(resource, user))
                {
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new PostOut[getContentsAtFlow.size()]);
    }

    /**
     * Gets the contents at flow.
     * 
     * @return the contents at flow
     */
    public static PostOut[] getContentsAtFlow()
    {
        HashSet<PostOut> getContentsAtFlow = new HashSet<PostOut>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            PostOut[] resources = getContentsAtFlowAll(site);
            for (PostOut resource : resources)
            {
                if (resource != null && isInFlow(resource))
                {
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new PostOut[getContentsAtFlow.size()]);
    }

    /**
     * Gets the activity name.
     * 
     * @param resource the resource
     * @return the activity name
     */
    public static String getActivityName(PostOut resource)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();
        if (instance != null)
        {
            return instance.getStep();
        }
        else
        {
            return null;
        }
    }

    /**
     * Checks if is reviewer.
     * 
     * @param resource the resource
     * @param user the user
     * @return true, if is reviewer
     */
    public static boolean isReviewer(PostOut resource, User user)
    {
        boolean isReviewer = false;
        SocialPFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            String activityName = instance.getStep();
            SocialPFlow flow = instance.getPflow();
            int version = instance.getVersion();
            if (activityName != null)
            {
                Document doc = SWBUtils.XML.xmlToDom(flow.getXml());
                NodeList workflows = doc.getElementsByTagName("workflow");
                for (int iworkflow = 0; iworkflow < workflows.getLength(); iworkflow++)
                {
                    Element eworkflow = (Element) doc.getElementsByTagName("workflow").item(iworkflow);
                    if (eworkflow.getAttribute("version").equals(version + ".0"))
                    {
                        NodeList activities = eworkflow.getElementsByTagName("activity");
                        for (int i = 0; i < activities.getLength(); i++)
                        {
                            Element eactivity = (Element) activities.item(i);

                            if (eactivity.getAttribute("name").equals(activityName))
                            {
                                NodeList users = eactivity.getElementsByTagName("user");
                                for (int j = 0; j < users.getLength(); j++)
                                {
                                    Element euser = (Element) users.item(j);
                                    if (euser.getAttribute("id").equals(user.getId()))
                                    {
                                        return true;
                                    }
                                }
                                NodeList roles = eactivity.getElementsByTagName("role");
                                for (int j = 0; j < roles.getLength(); j++)
                                {
                                    Element erole = (Element) roles.item(j);
                                    String idrole = erole.getAttribute("id");
                                    Role role = user.getUserRepository().getRole(idrole);
                                    return user.hasRole(role);
                                }
                            }
                        }
                    }
                }
            }
        }
        return isReviewer;
    }

    /**
     * Approve resource.
     * 
     * @param resource the resource
     * @param user the user
     * @param msg the msg
     */
    public static void approveResource(PostOut resource, User user, String msg)
    {
        approveResource(resource, user, msg, null);
    }
    
    /**
     * Approve resource.
     * 
     * @param resource the resource
     * @param user the user
     * @param msg the msg
     * @param notification the notification
     */
    public static void approveResource(PostOut resource, User user, String msg, SocialFlowNotification notification)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            int version = instance.getVersion();
            SocialPFlow flow = instance.getPflow();
            if (isReviewer(resource, user))
            {
                String activityName = instance.getStep();
                instance.setStatus(1);
                Document docxml = SWBUtils.XML.xmlToDom(flow.getXml());
                NodeList workflows = docxml.getElementsByTagName("workflow");
                for (int i = 0; i < workflows.getLength(); i++)
                {
                    Element workflow = (Element) workflows.item(i);
                    String wfVersion = workflow.getAttribute("version");
                    if (wfVersion.equals(version + ".0"))
                    {
                        Element ecurrentActivity = null;
                        NodeList activities = workflow.getElementsByTagName("activity");
                        for (int iActivity = 0; iActivity < activities.getLength(); iActivity++)
                        {
                            Element eactivity = (Element) activities.item(iActivity);
                            if (eactivity.getAttribute("name").equals(activityName))
                            {
                                ecurrentActivity = eactivity;
                            }
                        }
                        NodeList links = workflow.getElementsByTagName("link");
                        for (int iLink = 0; iLink < links.getLength(); iLink++)
                        {
                            Element eLink = (Element) links.item(iLink);
                            if (eLink.getAttribute("from").equals(ecurrentActivity.getAttribute("name")) && eLink.getAttribute("type").equals("authorized"))
                            {
                                String newActivity = eLink.getAttribute("to");
                                for (int iActivity = 0; iActivity < activities.getLength(); iActivity++)
                                {
                                    Element eactivity = (Element) activities.item(iActivity);
                                    if (eactivity.getAttribute("name").equals(newActivity))
                                    {
                                        try
                                        {
                                            instance.setStep(newActivity);
                                            long tinit = System.currentTimeMillis();
                                            instance.setTime(new Date(tinit));
                                            if (eactivity.getAttribute("type").equals("Activity"))
                                            {
                                                long days = Long.parseLong(eactivity.getAttribute("days"));
                                                long hours = 0;
                                                if (eactivity.getAttribute("hours") != null && !eactivity.getAttribute("hours").equals(""))
                                                {
                                                    hours = Long.parseLong(eactivity.getAttribute("hours"));
                                                }
                                                if (days > 0 || hours > 0)
                                                {
                                                    long milliseconds = ((hours * 3600) + (days * 86400)) * 1000;
                                                    Date timestart = instance.getTime();
                                                    long timefirst = timestart.getTime() + milliseconds;
                                                    // TODO:Como controlar las altertas de tiempo
                                                    /*ControlFlow alert = new ControlFlow(occ, new java.util.Date(timefirst), activityName);
                                                    PFlowSrv.addControlFlow(alert);*/
                                                }
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            log.error(e);
                                        }
                                    }
                                }
                                NodeList services = eLink.getElementsByTagName("service");
                                for (int iService = 0; iService < services.getLength(); iService++)
                                {
                                    Element eService = (Element) services.item(iService);
                                    String serviceName = eService.getFirstChild().getNodeValue();
                                    if (serviceName.equals("mail"))
                                    {
                                        String messageType = "N";
                                        String newactivityName = instance.getStep();
                                        mailToNotify(resource, newactivityName, messageType, msg);
                                    }
                                    else if (serviceName.equals("authorize"))
                                    {
                                        resource.getPflowInstance().setStatus(2);
                                        resource.getPflowInstance().setStep(null);                                        
                                        if (notification != null)
                                        {
                                            notification.autorize(resource);
                                        }
                                        /*  LOS PostOut NO SON VERSIONABLES
                                        if(resource.getResourceData().instanceOf(Versionable.swb_Versionable))
                                        {
                                            Versionable v=(Versionable)resource.getResourceData().createGenericInstance();
                                            if(v.getLastVersion()!=null)
                                            {
                                                v.getLastVersion().setVersionAuthorized(true);
                                                v.setActualVersion(v.getLastVersion());
                                            }
                                        }**/
                                    }
                                    else if (serviceName.equals("noauthorize"))
                                    {
                                        noauthorizeContent(resource);
                                        if (notification != null)
                                        {
                                            notification.noAutorize(resource);
                                        }
                                        /*LOS PostOut NO SON VERSIONABLES
                                        if(resource.getResourceData().instanceOf(Versionable.swb_Versionable))
                                        {
                                            Versionable v=(Versionable)resource.getResourceData().createGenericInstance();
                                            v.getLastVersion().setVersionAuthorized(false);
                                        }
                                        * */
                                    }
                                    else if (serviceName.equals("publish"))
                                    {
                                        System.out.println("ESTA LISTO PARA PUBLICAR EL POSTOut en SocialPFlowMgr/approveResource,notification:"+notification);
                                        try
                                        {
                                            SWBSocialUtil.PostOutUtil.publishPost(resource, null, null);
                                            resource.setPublished(true);
                                            if (notification != null)
                                            {
                                                System.out.println("En approveResource--13:"+notification);
                                                notification.publish(resource);
                                            }
                                        }catch(Exception se)
                                        {
                                            log.error(se);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Reject content.
     * 
     * @param resource the resource
     * @param activity the activity
     * @param pflow the pflow
     * @param msgReject the msg reject
     */
    private static void rejectContent(PostOut resource, String activity, SocialPFlow pflow, String msgReject)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();
        int version = instance.getVersion();
        Document docxml = SWBUtils.XML.xmlToDom(pflow.getXml());
        NodeList workflows = docxml.getElementsByTagName("workflow");
        for (int i = 0; i < workflows.getLength(); i++)
        {
            Element workflow = (Element) workflows.item(i);
            String wfVersion = workflow.getAttribute("version");
            if (wfVersion.equals(version + ".0"))
            {
                Element ecurrentActivity = null;
                NodeList activities = workflow.getElementsByTagName("activity");
                for (int iActivity = 0; iActivity < activities.getLength(); iActivity++)
                {
                    Element eactivity = (Element) activities.item(iActivity);
                    if (eactivity.getAttribute("name").equals(activity))
                    {
                        ecurrentActivity = eactivity;
                    }
                }
                NodeList links = workflow.getElementsByTagName("link");
                for (int iLink = 0; iLink < links.getLength(); iLink++)
                {
                    Element eLink = (Element) links.item(iLink);
                    if (eLink.getAttribute("from").equals(ecurrentActivity.getAttribute("name")) && eLink.getAttribute("type").equals("unauthorized"))
                    {
                        String newActivity = eLink.getAttribute("to");
                        for (int iActivity = 0; iActivity < activities.getLength(); iActivity++)
                        {
                            Element eactivity = (Element) activities.item(iActivity);
                            if (eactivity.getAttribute("name").equals(newActivity))
                            {
                                try
                                {
                                    /*Vector control = PFlowSrv.getControlFlow();
                                    for (int iControl = 0; iControl < control.size(); iControl++)
                                    {
                                    ControlFlow controlFlow = (ControlFlow) control.elementAt(iControl);
                                    if (controlFlow.getOcurrence().equals(occ.getId()) && controlFlow.getTopicMap().equals(occ.getDbdata().getIdtm()))
                                    {
                                    PFlowSrv.removeControlFlow(controlFlow);
                                    break;
                                    }

                                    }*/
                                    long tinit = System.currentTimeMillis();
                                    instance.setTime(new Date(tinit));
                                    instance.setStep(newActivity);
                                    if (eactivity.getAttribute("type").equals("Activity"))
                                    {
                                        long days = Long.parseLong(eactivity.getAttribute("days"));
                                        long hours = 0;
                                        if (eactivity.getAttribute("hours") != null && !eactivity.getAttribute("hours").equals(""))
                                        {
                                            hours = Long.parseLong(eactivity.getAttribute("hours"));
                                        }
                                        if (days > 0 || hours > 0)
                                        {
                                            long milliseconds = ((hours * 3600) + (days * 86400)) * 1000;
                                            // TODO: Constrol de tiempo
                                            /*Timestamp timestart = occ.getDbdata().getFlowtime();
                                            long timefirst = timestart.getTime() + milliseconds;
                                            ControlFlow alert = new ControlFlow(occ, new java.util.Date(timefirst), activity);
                                            PFlowSrv.addControlFlow(alert);*/

                                        }
                                    }

                                }
                                catch (Exception e)
                                {
                                    log.error(e);
                                }

                            }
                        }
                        NodeList services = eLink.getElementsByTagName("service");
                        for (int iService = 0; iService < services.getLength(); iService++)
                        {
                            Element eService = (Element) services.item(iService);
                            String serviceName = eService.getFirstChild().getNodeValue();
                            if (serviceName.equals("mail"))
                            {
                                String messageType = "N";
                                String newactivityName = instance.getStep();
                                mailToNotify(resource, newactivityName, messageType, msgReject);
                            }
                            else if (serviceName.equals("authorize"))
                            {
                                resource.getPflowInstance().setStatus(2);
                                resource.getPflowInstance().setStep(null);
                            }
                            else if (serviceName.equals("noauthorize"))
                            {
                                noauthorizeContent(resource);
                            }
                            else if (serviceName.equals("publish"))
                            {
                                try
                                {
                                    System.out.println("ESTA LISTO PARA PUBLICAR EL POSTOut en SocialPFlowMgr/rejectContent");
                                    SWBSocialUtil.PostOutUtil.publishPost(resource, null, null);
                                    resource.setPublished(true);
                                    /*
                                    if (notification != null)
                                    {
                                        System.out.println("En approveResource--13:"+notification);
                                        notification.publish(resource);
                                    }*/
                                }catch(Exception se)
                                {
                                    log.error(se);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Reject resource.
     * 
     * @param resource the resource
     * @param user the user
     * @param msg the msg
     */
    public static void rejectResource(PostOut resource, User user, String msg)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            if (isReviewer(resource, user))
            {
                String activityName = instance.getStep();
                try
                {
                    rejectContent(resource, activityName, instance.getPflow(), msg);
                    SocialPFlow pflow = instance.getPflow();
                    sendNotificationReject(msg, resource, pflow, String.valueOf(instance.getVersion()), activityName, user);

                }
                catch (Exception ue)
                {
                    instance.setStatus(1);
                    //resource.setActive(false);//.setActive(0);  //TODOSOCIAL: Dado que el PostOut no es activable, ver si desde aqui ya lo publico.
                }
            }
            else
            {
                //throw new AFException("The user "+ user.getId() +" is not a reviewer","RejectOccurrence");
                }

        }
        else
        {
            //throw new AFException("The resource "+occurrence.getId()+" is not in flow","RejectOccurrence");
        }
    }

    /**
     * Inits the content.
     * 
     * @param resource the resource
     * @param pflow the pflow
     * @param message the message
     */
    public static void initContent(PostOut resource, SocialPFlow pflow, String message)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();
        String version = String.valueOf(instance.getVersion());
        String activity = null;
        String xml = pflow.getXml();
        Document docxml = SWBUtils.XML.xmlToDom(xml);
        NodeList workflows = docxml.getElementsByTagName("workflow");
        for (int i = 0; i < workflows.getLength(); i++)
        {
            Element workflow = (Element) workflows.item(i);
            version = version + ".0";
            if ((workflow.getAttribute("version")).equals(version))
            {
                Element ecurrentActivity = null;
                NodeList activities = workflow.getElementsByTagName("activity");
                if (activities.getLength() > 0)
                {
                    ecurrentActivity = (Element) activities.item(0);
                    activity = ((Element) activities.item(0)).getAttribute("name");
                }
                try
                {
                    instance.setStep(activity);
                    long tinit = System.currentTimeMillis();
                    instance.setTime(new Date(tinit));
                    if (ecurrentActivity.getAttribute("type").equals("Activity"))
                    {
                        long days = Long.parseLong(ecurrentActivity.getAttribute("days"));
                        long hours = 0;
                        if (ecurrentActivity.getAttribute("hours") != null && !ecurrentActivity.getAttribute("hours").equals(""))
                        {
                            hours = Long.parseLong(ecurrentActivity.getAttribute("hours"));
                        }
                        if (days > 0 || hours > 0)
                        {
                            long milliseconds = ((hours * 3600) + (days * 86400)) * 1000;
                            //TODO agregar control de tiempo
                            /*Timestamp timestart = occ.getDbdata().getFlowtime();
                            long timefirst = timestart.getTime() + milliseconds;
                            ControlFlow controlFlow = new ControlFlow(occ, new java.util.Date(timefirst), activity);
                            PFlowSrv.addControlFlow(controlFlow);*/
                        }
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
                String messageType = "I";
                System.out.println("InitContent--1");
                mailToNotify(resource, activity, messageType, message);
            }

        }
    }

    /**
     * Send notification reject.
     * 
     * @param message the message
     * @param resource the resource
     * @param pflow the pflow
     * @param version the version
     * @param activityName the activity name
     * @param wbUser the wb user
     */
    private static void sendNotificationReject(String message, PostOut resource, SocialPFlow pflow, String version, String activityName, User wbUser)
    {
        Document docworkflow = SWBUtils.XML.xmlToDom(pflow.getXml());
        NodeList workflows = docworkflow.getElementsByTagName("workflow");
        for (int i = 0; i < workflows.getLength(); i++)
        {
            Element workflow = (Element) workflows.item(i);
            if (workflow.getAttribute("version").equals(version + ".0"))
            {
                NodeList links = workflow.getElementsByTagName("link");
                for (int j = 0; j < links.getLength(); j++)
                {
                    Element link = (Element) links.item(j);
                    if (link.getAttribute("from").equals(activityName) && link.getAttribute("type").equals("unauthorized"))
                    {
                        HashSet toUsers = new HashSet();
                        NodeList notifications = link.getElementsByTagName("notification");
                        for (int k = 0; k < notifications.getLength(); k++)
                        {
                            Element notification = (Element) notifications.item(k);
                            if (notification.getAttribute("type").equals("user"))
                            {
                                //TODOSOCIAL:Revisar si me trae el sitio la sig. línea.
                                WebSite wsite=WebSite.ClassMgr.getWebSite(resource.getSemanticObject().getModel().getName());
                                User recuser = wsite.getUserRepository().getUser(notification.getAttribute("to"));
                                toUsers.add(recuser.getEmail());
                            }
                            else
                            {
                                String roleId = notification.getAttribute("to");
                                String repository = notification.getAttribute("repository");
                                Iterator<User> eusers = SWBContext.getUserRepository(repository).listUsers();
                                while (eusers.hasNext())
                                {
                                    User recuser = eusers.next();
                                    Iterator<Role> itroles = recuser.listRoles();
                                    while (itroles.hasNext())
                                    {
                                        Role role = itroles.next();
                                        if (role.getId().equals(roleId))
                                        {
                                            toUsers.add(recuser.getEmail());
                                        }
                                    }
                                }
                            }
                        }
                        if (toUsers.size() > 0)
                        {
                            String to = "";
                            Iterator itusers = toUsers.iterator();
                            while (itusers.hasNext())
                            {
                                String email = (String) itusers.next();
                                to += email + ";";
                            }
                            if (to.endsWith(";"))
                            {
                                to = to.substring(0, to.length() - 1);
                            }
                            Locale locale = Locale.getDefault();
                            try
                            {
                                locale = new Locale(wbUser.getLanguage());
                            }
                            catch (Exception e)
                            {
                                log.error(e);
                            }
                            ResourceBundle resourceBundle = ResourceBundle.getBundle("org/semanticwb/social/util/pflow/SocialPFlowMgr", locale);
                            String subject = resourceBundle.getString("subjectReject");
                            String msg = resourceBundle.getString("msg1") + " " + resource.getId() + " " + resourceBundle.getString("msg3") + " " + activityName + resourceBundle.getString("msg2") + wbUser.getFirstName() + " " + wbUser.getLastName();
                            msg += "\r\n\r\n" + resourceBundle.getString("title") + " " + resource.getMsg_Text();
                            msg += "\r\n" + resourceBundle.getString("tema") + " " + resource.getSocialTopic().getTitle();
                            WebSite wsite=WebSite.ClassMgr.getWebSite(resource.getSemanticObject().getModel().getName());
                            msg += "\r\n" + resourceBundle.getString("site") + " " + wsite.getTitle();
                            msg += "\r\n" + resourceBundle.getString("mensaje") + " " + message;
                            try
                            {
                                SWBUtils.EMAIL.sendBGEmail(to, subject, msg);
                            }
                            catch (SocketException se)
                            {
                                log.error(se);
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Send resource to authorize.
     * 
     * @param resource the resource
     * @param pflow the pflow
     * @param message the message
     * @param user the user
     */
    public static void sendResourceToAuthorize(PostOut resource, SocialPFlow pflow, String message)
    {
        WebSite wsite=WebSite.ClassMgr.getWebSite(resource.getSemanticObject().getModel().getName());
        if (message == null)
        {
            message = "";
        }
        String typeresource = resource.getSemanticObject().getSemanticClass().getClassId();
        Document docflow = SWBUtils.XML.xmlToDom(pflow.getXml());
        Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
        NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
        for (int ires = 0; ires < resourceTypes.getLength(); ires++)
        {
            Element eres = (Element) resourceTypes.item(ires);
            String iresw = eres.getAttribute("id");
            if (iresw.equals(typeresource) && wsite.getId().equals(eres.getAttribute("topicmap")))
            {
                int version = (int) Double.parseDouble(workflow.getAttribute("version"));
                SocialPFlowInstance instance = SocialPFlowInstance.ClassMgr.createSocialPFlowInstance(wsite);
                instance.setPflow(pflow);
                resource.setPflowInstance(instance);
                instance.setPfinstPostOut(resource);
                instance.setStatus(1);
                instance.setVersion(version);
                System.out.println("sendResourceToAuthorize--1");
                initContent(resource, pflow, message);
            }
        }
    }

    /**
     * Checks if is authorized.
     * 
     * @param resource the resource
     * @return true, if is authorized
     */
    public static boolean isAuthorized(PostOut resource)
    {
        if (resource == null)
        {
            return false;
        }
        if (needAnAuthorization(resource) || isInFlow(resource))
        {
            return false;
        }
        else
        {
            SocialPFlowInstance instance = resource.getPflowInstance();
            if (instance != null)
            {
                if (instance.getStatus() == 2)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Checks if is in flow.
     * 
     * @param resource the resource
     * @return true, if is in flow
     */
    public static boolean isInFlow(PostOut postOut)
    {
        if (postOut.getPflowInstance() == null)
        {
            return false;
        }
        else
        {
            if(postOut.getPflowInstance().getStatus() == 3 && postOut.getPflowInstance().getStep() != null)
            {
                return true;
            }
            if (!(postOut.getPflowInstance().getStatus() == 3 || postOut.getPflowInstance().getStatus() == 2 || postOut.getPflowInstance().getStep() == null))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Need an authorization.
     * Un PostOut va ha requerir autorización siempre y cuando este relacionado a un tema (lo cual siempre debería de ser)
     * @param resource the resource
     * @return true, if successful
     */
    public static boolean needAnAuthorization(PostOut resource)
    {
        SocialPFlow postOutFlow=null;
        if(resource.getPflowInstance()!=null)
        {
            postOutFlow=resource.getPflowInstance().getPflow();
        }
        SocialTopic socialTopic=resource.getSocialTopic();
        Iterator<SocialPFlowRef> refs = socialTopic.listInheritPFlowRefs();
        while (refs.hasNext())
        {
            SocialPFlowRef ref = refs.next();
            if (ref.isActive())
            {
                SocialPFlow pflow = ref.getPflow();
                if (pflow != null)
                {
                    if(!postOutFlow.getURI().equals(pflow.getURI())) continue;
                    String typeresource = resource.getSemanticObject().getSemanticClass().getClassId();
                    Document docflow = SWBUtils.XML.xmlToDom(pflow.getXml());
                    Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
                    NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
                    for (int ires = 0; ires < resourceTypes.getLength(); ires++)
                    {
                        Element eres = (Element) resourceTypes.item(ires);
                        String iresw = eres.getAttribute("id");
                        if (iresw.equals(typeresource))
                        {
                            if (resource.getPflowInstance() == null)
                            {
                                return false;
                            }
                            else
                            {
                                SocialPFlowInstance instance = resource.getPflowInstance();
                                switch (instance.getStatus())
                                {
                                    case -1:
                                    case 0: //no enviado
                                        return true;
                                    case 1: return true;    //TODOSOCIAL:REVISAR-Puesto por Jorge Jiménez    
                                    case 3: //rechazado
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
        }
           
        return false;
        
        
        /*
        Iterator<Resourceable> resourceables = resource.listResourceables();
        while (resourceables.hasNext())
        {
            Resourceable resourceable = resourceables.next();
            if (resourceable instanceof WebPage)
            {
                WebPage page = (WebPage) resourceable;
                Iterator<PFlowRef> refs = page.listInheritPFlowRefs();
                while (refs.hasNext())
                {
                    PFlowRef ref = refs.next();
                    if (ref.isActive())
                    {
                        PFlow pflow = ref.getPflow();
                        if (pflow != null)
                        {
                            String typeresource = resource.getResourceType().getId();
                            Document docflow = SWBUtils.XML.xmlToDom(pflow.getXml());
                            Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
                            NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
                            for (int ires = 0; ires < resourceTypes.getLength(); ires++)
                            {
                                Element eres = (Element) resourceTypes.item(ires);
                                String iresw = eres.getAttribute("id");

                                if (iresw.equals(typeresource))
                                {
                                    if (resource.getPflowInstance() == null)
                                    {
                                        return true;
                                    }
                                    else
                                    {
                                        PFlowInstance instance = resource.getPflowInstance();
                                        switch (instance.getStatus())
                                        {
                                            case -1:
                                            case 0: //no enviado
                                                return true;
                                            case 3: //rechazado
                                                return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
        * **/
    }

     /**
         * Mail to notify.
         *
         * @param resource the resource
         * @param activityName the activity name
         * @param messageType the message type
         * @param message the message
         */
        public static void mailToNotify(PostOut postOut, String activityName, String messageType, String message) 
        {
            System.out.println("mailToNotify--1");
            User wbuser = postOut.getCreator();
            Locale locale = Locale.getDefault();
            try {
                ResourceBundle bundle = null;
                try {
                    bundle = ResourceBundle.getBundle("org/semanticwb/social/util/pflow/SocialPFlowMgr", locale);
                } catch (Exception e) {
                    bundle = ResourceBundle.getBundle("org/semanticwb/social/util/pflow/SocialPFlowMgr");
                }
                if (postOut.getPflowInstance() != null) {
                    WebSite wsite=WebSite.ClassMgr.getWebSite(postOut.getSemanticObject().getModel().getName());
                    SocialPFlow flow = postOut.getPflowInstance().getPflow();
                    Document docdef = SWBUtils.XML.xmlToDom(flow.getXml());
                    int version = postOut.getPflowInstance().getVersion();
                    NodeList workflows = docdef.getElementsByTagName("workflow");
                    for (int iworkflow = 0; iworkflow < workflows.getLength(); iworkflow++) {
                        Element eworkflow = (Element) workflows.item(iworkflow);
                        if (eworkflow.getAttribute("version").equals(version + ".0")) {
                            NodeList activities = eworkflow.getElementsByTagName("activity");
                            for (int i = 0; i < activities.getLength(); i++) {
                                Element activity = (Element) activities.item(i);
                                if (i == 0 && messageType.equalsIgnoreCase("I")) {
                                    activityName = activity.getAttribute("name");
                                }
                                if (activity.getAttribute("name").equalsIgnoreCase(activityName)) {
                                    if (activity.getAttribute("type").equalsIgnoreCase("AuthorActivity")) {
                                        User user = postOut.getCreator();
                                        String msgMail = bundle.getString("msg1") + " " + postOut.getId() + " " + bundle.getString("msg2") + " '" + postOut.getMsg_Text() + "' " + bundle.getString("msg3") + ".";

                                        msgMail += "\r\n" + bundle.getString("msg4") + ": " + wbuser.getFirstName() + " " + wbuser.getLastName();
                                        msgMail += "\r\n" + bundle.getString("msg5") + ": " + wbuser.getLogin();

                                        msgMail += "\r\n" + bundle.getString("msg6") + ": " + message;
                                        msgMail += "\r\n" + bundle.getString("sitio") + ": " + wsite.getTitle() + ".\r\n";
                                        msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                        if (activity.getAttribute("days") != null && activity.getAttribute("hours") != null) {
                                            if (!(activity.getAttribute("days").equals("0") && activity.getAttribute("hours").equals("0"))) {
                                                msgMail += "\r\n" + bundle.getString("msgr1") + " " + activity.getAttribute("days") + " " + bundle.getString("days") + " " + bundle.getString("and") + " " + activity.getAttribute("hours") + " " + bundle.getString("hours") + " .";
                                            }
                                        }
                                        SocialTopic socialTopic = (SocialTopic) postOut.getSocialTopic();
                                        HashMap args = new HashMap();
                                        args.put("language", Locale.getDefault().getLanguage());
                                        msgMail += "\r\n" + bundle.getString("socialTopic") + ": " + socialTopic.getTitle() + ".\r\n";
                                        SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg7") + " " + postOut.getId() + " " + bundle.getString("msg8"), msgMail);
                                    } else if (activity.getAttribute("type").equalsIgnoreCase("EndActivity")) {
                                        User user = postOut.getCreator();
                                        String msgMail = bundle.getString("msg1") + " " + postOut.getId() + " " + bundle.getString("msg2") + " '" + postOut.getMsg_Text() + "' " + bundle.getString("msg9") + ".";
                                        msgMail += "\r\n" + bundle.getString("sitio") + ": " + wsite.getTitle() + ".\r\n";
                                        msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                        if (messageType.equalsIgnoreCase("N") && message != null && !message.equalsIgnoreCase("")) {
                                            msgMail += "\r\n" + bundle.getString("msg6") + ": " + message;
                                        }
                                        SocialTopic socialTopic = (SocialTopic) postOut.getSocialTopic();
                                        HashMap args = new HashMap();
                                        args.put("language", Locale.getDefault().getLanguage());
                                        msgMail += "\r\n" + bundle.getString("socialTopic") + ": " + socialTopic.getTitle() + ".\r\n";
                                        SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg7") + " " + postOut.getId() + " " + bundle.getString("msg10") + "", msgMail);
                                    } else if (activity.getAttribute("type").equalsIgnoreCase("Activity")) {
                                        HashSet<User> husers = new HashSet<User>();
                                        NodeList users = activity.getElementsByTagName("user");
                                        for (int j = 0; j < users.getLength(); j++) {
                                            Element user = (Element) users.item(j);
                                            String userid = user.getAttribute("id");
                                            User recuser = SWBContext.getAdminRepository().getUser(userid);
                                            husers.add(recuser);
                                        }
                                        NodeList roles = activity.getElementsByTagName("role");
                                        for (int j = 0; j < roles.getLength(); j++) {
                                            Element erole = (Element) roles.item(j);
                                            try {
                                                //Enumeration eusers = DBUser.getInstance(erole.getAttribute("repository")).getUsers();
                                                Iterator<User> eusers = SWBContext.getUserRepository(erole.getAttribute("repository")).listUsers();
                                                while (eusers.hasNext()) {
                                                    User user = eusers.next();
                                                    Iterator<Role> itroles = user.listRoles();
                                                    while (itroles.hasNext()) {
                                                        Role role = itroles.next();
                                                        if (role.getId().equals(erole.getAttribute("id"))) {
                                                            husers.add(user);
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                log.error(e);
                                            }
                                        }
                                        //envía correo
                                        String to = "";
                                        Iterator<User> itusers = husers.iterator();
                                        while (itusers.hasNext()) {
                                            User user = itusers.next();
                                            if (user != null && user.getEmail() != null && to.indexOf(user.getEmail()) == -1) {
                                                to += user.getEmail() + ";";
                                            }
                                        }
                                        if (to.endsWith(";")) {
                                            to = to.substring(0, to.length() - 1);
                                        }
                                        if (!to.equalsIgnoreCase("")) {
                                            String subject = bundle.getString("msg7") + " " + postOut.getId() + " " + bundle.getString("msg11");
                                            String msg = bundle.getString("msg1") + " " + postOut.getId() + " " + bundle.getString("msg2") + " '" + postOut.getMsg_Text() + "' " + bundle.getString("msg12") + " '" + activityName + "'.\r\n";
                                            msg += "\r\n" + bundle.getString("sitio") + ": " + wsite.getTitle() + ".\r\n";
                                            msg += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                            SocialTopic socialTopic = (SocialTopic) postOut.getSocialTopic();
                                            HashMap args = new HashMap();
                                            args.put("language", Locale.getDefault().getLanguage());
                                            msg += "\r\n" + bundle.getString("socialTopic") + ": " + socialTopic.getTitle() + ".\r\n";

                                            if ((messageType.equalsIgnoreCase("I") || messageType.equalsIgnoreCase("N")) && message != null && !message.equalsIgnoreCase("")) {
                                                msg += "\r\n" + bundle.getString("msg6") + ": " + message;
                                            }
                                            if (messageType.equalsIgnoreCase("A")) {

                                                // envía correo al creador del contenido
                                                User user = postOut.getCreator();
                                                String msgMail = bundle.getString("sitio") + ": " + wsite.getTitle() + ".\r\n";
                                                msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                                //msgMail+=bundle.getString("url")+": "+ObjRes.getAdminUrl()+".\r\n";
                                                msgMail += "\r\n" + bundle.getString("socialTopic") + ": " + socialTopic.getTitle() + ".\r\n";
                                                msgMail += "\r\n" + bundle.getString("msg13") + " " + postOut.getId() + " " + bundle.getString("msg14");

                                                SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg13") + " " + postOut.getId() + " " + bundle.getString("msg14"), msgMail);

                                                // avisa al los revisores de la expiración de la revisión delc ontenido
                                                msg += "\r\n" + bundle.getString("msg13") + " " + postOut.getId() + " " + bundle.getString("msg14");
                                            }
                                            if (activity.getAttribute("days") != null && activity.getAttribute("hours") != null) {
                                                if (!(activity.getAttribute("days").equals("0") && activity.getAttribute("hours").equals("0"))) {
                                                    msg += "\r\n" + bundle.getString("msgr1") + " " + activity.getAttribute("days") + " " + bundle.getString("days") + " " + bundle.getString("and") + " " + activity.getAttribute("hours") + " " + bundle.getString("hours") + " .";
                                                }
                                            }
                                            SWBUtils.EMAIL.sendBGEmail(to, subject, msg);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            } catch (Exception e) {
                log.error(e);
                return;
            }
        }
    

    /**
     * Noauthorize content.
     * 
     * @param resource the resource
     */
    private static void noauthorizeContent(PostOut resource)
    {
        SocialPFlowInstance instance = resource.getPflowInstance();

        try
        {
            instance.setStatus(3);
            String activityName = instance.getStep();
            int version = instance.getVersion();
            SocialPFlow pflow = instance.getPflow();
            if (instance.getStep() != null)
            {
                Document docdef = SWBUtils.XML.xmlToDom(pflow.getXml());
                NodeList workflows = docdef.getElementsByTagName("workflow");
                for (int iworkflow = 0; iworkflow < workflows.getLength(); iworkflow++)
                {
                    Element eworkflow = (Element) workflows.item(iworkflow);
                    if (eworkflow.getAttribute("version").equals(version + ".0"))
                    {
                        NodeList activities = eworkflow.getElementsByTagName("activity");
                        for (int i = 0; i < activities.getLength(); i++)
                        {
                            Element activity = (Element) activities.item(i);
                            if (activity.getAttribute("name").equalsIgnoreCase(activityName))
                            {
                                if (activity.getAttribute("type").equalsIgnoreCase("AuthorActivity"))
                                {
                                    instance.setStep(null);
                                    instance.setStatus(-1);
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                instance.setStep(null);
                instance.setStatus(0);
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
}
