/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

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
import org.semanticwb.model.PFlow;
import org.semanticwb.model.PFlowInstance;
import org.semanticwb.model.PFlowRef;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 * Si status es -1= enviado al autor del contenido, es como si no fuera enviado
 * Si status es 0 = no enviado ninguna vez
 * Si status es 1= aprovado, pero no necesariamnete autorizado
 * Si status es 2= autorizado // fin de ejecución del
 * Si status es 3 = rechazado   // fin de ejecución, rechazado, no hay que volver a envíar
 */
public class PFlowManager
{

    private static Logger log = SWBUtils.getLogger(PFlowManager.class);

    public PFlowManager()
    {
    }

    public void init()
    {
        log.event("Initializing PFlowManager...");
    }

    public PFlow[] getFlowsToSendContent(Resource resource)
    {
        HashSet<PFlow> flows = new HashSet<PFlow>();
        WebSite site = resource.getWebSite();
        Iterator<Resourceable> resourceables = resource.listResourceables();
        while (resourceables.hasNext())
        {
            Resourceable resourceable = resourceables.next();
            if (resourceable != null && resourceable instanceof WebPage)
            {
                WebPage page = (WebPage) resourceable;
                for (PFlow flow : getFlows(page))
                {
                    String _xml = flow.getXml();
                    Document docflow = SWBUtils.XML.xmlToDom(_xml);
                    Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
                    NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
                    for (int ires = 0; ires < resourceTypes.getLength(); ires++)
                    {
                        Element eres = (Element) resourceTypes.item(ires);
                        String iresw = eres.getAttribute("id");
                        ResourceType type = site.getResourceType(iresw);
                        if (resource.getResourceType().equals(type))
                        {
                            flows.add(flow);
                        }
                    }
                }
            }
        }
        return flows.toArray(new PFlow[flows.size()]);
    }

    public PFlow[] getFlows(WebPage page)
    {
        HashSet<PFlow> flows = new HashSet<PFlow>();
        GenericIterator<PFlowRef> refs = page.listInheritPFlowRefs();
        while (refs.hasNext())
        {
            PFlowRef ref = refs.next();
            flows.add(ref.getPflow());
        }
        return flows.toArray(new PFlow[flows.size()]);
    }

    public Resource[] getContentsAtFlowOfUser(User user, WebSite site)
    {
        HashSet<Resource> getContentsAtFlowOfUser = new HashSet<Resource>();
        Iterator<PFlow> flows = site.listPFlows();
        while (flows.hasNext())
        {
            PFlow flow = flows.next();
            Iterator<PFlowInstance> instances = flow.listPFlowInstances();
            while (instances.hasNext())
            {
                PFlowInstance instance = instances.next();
                Resource resource = instance.getPfinstResource();
                if (isInFlow(resource) && resource.getCreator() != null && resource.getCreator().equals(user))
                {
                    getContentsAtFlowOfUser.add(resource);
                }
            }
        }
        return getContentsAtFlowOfUser.toArray(new Resource[getContentsAtFlowOfUser.size()]);
    }

    public Resource[] getContentsAtFlowAll(WebSite site)
    {
        HashSet<Resource> getContentsAtFlowAll = new HashSet<Resource>();
        Iterator<PFlow> flows = site.listPFlows();
        while (flows.hasNext())
        {
            PFlow flow = flows.next();
            Iterator<PFlowInstance> instances = flow.listPFlowInstances();
            while (instances.hasNext())
            {
                PFlowInstance instance = instances.next();
                Resource resource = instance.getPfinstResource();
                if(isInFlow(resource))
                {
                    getContentsAtFlowAll.add(resource);
                }
            }
        }
        return getContentsAtFlowAll.toArray(new Resource[getContentsAtFlowAll.size()]);
    }

    public Resource[] getContentsAtFlow(User user, WebSite site)
    {
        HashSet<Resource> getContentsAtFlow = new HashSet<Resource>();
        Iterator<PFlow> flows = site.listPFlows();
        while (flows.hasNext())
        {
            PFlow flow = flows.next();
            Iterator<PFlowInstance> instances = flow.listPFlowInstances();
            while (instances.hasNext())
            {
                PFlowInstance instance = instances.next();
                Resource resource = instance.getPfinstResource();
                if (isInFlow(resource) && resource.getCreator().equals(user) && this.isReviewer(resource, user))
                {
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new Resource[getContentsAtFlow.size()]);
    }

    public Resource[] getContentsAtFlow(PFlow pflow)
    {
        HashSet<Resource> getContentsAtFlow = new HashSet<Resource>();
        Iterator<PFlowInstance> instances = pflow.listPFlowInstances();
        while (instances.hasNext())
        {
            PFlowInstance instance = instances.next();
            Resource resource = instance.getPfinstResource();
            if(isInFlow(resource))
            {
                getContentsAtFlow.add(resource);
            }
        }
        return getContentsAtFlow.toArray(new Resource[getContentsAtFlow.size()]);
    }

    public Resource[] getContentsAtFlow(PFlow pflow, WebSite site)
    {
        HashSet<Resource> getContentsAtFlow = new HashSet<Resource>();
        Iterator<PFlowInstance> instances = pflow.listPFlowInstances();
        while (instances.hasNext())
        {
            PFlowInstance instance = instances.next();
            if (instance.getPflow().getWebSite().equals(site))
            {
                Resource resource = instance.getPfinstResource();
                getContentsAtFlow.add(resource);
            }
        }
        return getContentsAtFlow.toArray(new Resource[getContentsAtFlow.size()]);
    }

    public Resource[] getContentsAtFlow(User user)
    {
        HashSet<Resource> getContentsAtFlow = new HashSet<Resource>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            Resource[] resources = getContentsAtFlowAll(site);
            for (Resource resource : resources)
            {
                if (isInFlow(resource) && isReviewer(resource, user))
                {
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new Resource[getContentsAtFlow.size()]);
    }

    public Resource[] getContentsAtFlow()
    {
        HashSet<Resource> getContentsAtFlow = new HashSet<Resource>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            Resource[] resources = getContentsAtFlowAll(site);
            for (Resource resource : resources)
            {
                if(isInFlow(resource))
                {
                    getContentsAtFlow.add(resource);
                }
            }
        }
        return getContentsAtFlow.toArray(new Resource[getContentsAtFlow.size()]);
    }

    public String getActivityName(Resource resource)
    {
        PFlowInstance instance = resource.getPflowInstance();
        if (instance != null)
        {
            return instance.getStep();
        }
        else
        {
            return null;
        }
    }

    public boolean isReviewer(Resource resource, User user)
    {
        boolean isReviewer = false;
        PFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            String activityName = instance.getStep();
            PFlow flow = instance.getPflow();
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
                                    Iterator<Role> itroles = user.listRoles();
                                    while (itroles.hasNext())
                                    {
                                        Role role = itroles.next();
                                        if (role.getId().equals(erole.getAttribute("id")))
                                        {
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
        return isReviewer;
    }

    public void approveResource(Resource resource, User user, String msg)
    {
        PFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            int version = instance.getVersion();
            PFlow flow = instance.getPflow();
            if (this.isReviewer(resource, user))
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
                                    }
                                    else if (serviceName.equals("noauthorize"))
                                    {
                                        noauthorizeContent(resource);
                                    }
                                    else if (serviceName.equals("publish"))
                                    {
                                        resource.setActive(true);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private void rejectContent(Resource resource, String activity, PFlow pflow, String msgReject)
    {
        PFlowInstance instance = resource.getPflowInstance();
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
                                resource.setActive(true);
                            }
                        }
                    }
                }
            }
        }

    }

    public void rejectResource(Resource resource, User user, String msg)
    {
        PFlowInstance instance = resource.getPflowInstance();
        if (instance != null && instance.getStatus() > 0)
        {
            if (this.isReviewer(resource, user))
            {
                String activityName = instance.getStep();
                try
                {
                    rejectContent(resource, activityName, instance.getPflow(), msg);
                    PFlow pflow = instance.getPflow();
                    sendNotificationReject(msg, resource, pflow, String.valueOf(instance.getVersion()), activityName, user);

                }
                catch (Exception ue)
                {
                    instance.setStatus(1);
                    resource.setActive(false);//.setActive(0);
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
    

    private void initContent(Resource resource, PFlow pflow, String message)
    {
        PFlowInstance instance = resource.getPflowInstance();
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
                mailToNotify(resource, activity, messageType, message);
            }

        }
    }

    private void sendNotificationReject(String message, Resource resource, PFlow pflow, String version, String activityName, User wbUser)
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
                                User recuser = resource.getWebSite().getUserRepository().getUser(notification.getAttribute("to"));
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
                            ResourceBundle resourceBundle = ResourceBundle.getBundle("com/infotec/wb/services/PFlowSrv", locale);
                            String subject = resourceBundle.getString("subjectReject");
                            String msg = resourceBundle.getString("msg1") + " " + resource.getId() + " " + resourceBundle.getString("msg3") + " " + activityName + resourceBundle.getString("msg2") + wbUser.getFirstName() + " " + wbUser.getLastName();
                            msg += "\r\n\r\n" + resourceBundle.getString("title") + " " + resource.getTitle();
                            msg += "\r\n" + resourceBundle.getString("section") + " " + ((WebPage) resource.getResourceable()).getTitle();
                            msg += "\r\n" + resourceBundle.getString("site") + " " + resource.getWebSite().getTitle();
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

    public void sendResourceToAuthorize(Resource resource, PFlow pflow, String message, User user)
    {
        if (message == null)
        {
            message = "";
        }
        String typeresource = resource.getResourceType().getId();
        Document docflow = SWBUtils.XML.xmlToDom(pflow.getXml());
        Element workflow = (Element) docflow.getElementsByTagName("workflow").item(0);
        NodeList resourceTypes = workflow.getElementsByTagName("resourceType");
        for (int ires = 0; ires < resourceTypes.getLength(); ires++)
        {
            Element eres = (Element) resourceTypes.item(ires);
            String iresw = eres.getAttribute("id");
            if (iresw.equals(typeresource) && resource.getWebSite().getId().equals(eres.getAttribute("topicmap")))
            {
                int version = (int) Double.parseDouble(workflow.getAttribute("version"));
                PFlowInstance instance = PFlowInstance.createPFlowInstance(resource.getWebSite());
                instance.setPflow(pflow);
                resource.setPflowInstance(instance);
                instance.setPfinstResource(resource);
                instance.setStatus(1);
                instance.setVersion(version);
                initContent(resource, pflow, message);
            }
        }
    }

    public boolean isAuthorized(Resource resource)
    {
        if (needAnAuthorization(resource) || isInFlow(resource))
        {
            return false;
        }
        else
        {
            PFlowInstance instance = resource.getPflowInstance();
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

    public boolean isInFlow(Resource resource)
    {
        if (resource.getPflowInstance() == null)
        {
            return false;
        }
        else
        {
            if (!(resource.getPflowInstance().getStatus()==3 || resource.getPflowInstance().getStatus()==2 || resource.getPflowInstance().getStep()==null))
            {
                return true;
            }
        }
        return false;
    }
    

    public boolean needAnAuthorization(Resource resource)
    {
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
        return false;
    }

    private void mailToNotify(Resource resource, String activityName, String messageType, String message)
    {
        User wbuser = resource.getCreator();
        Locale locale = Locale.getDefault();
        try
        {
            ResourceBundle bundle =null;
            try
            {
                bundle=ResourceBundle.getBundle("/org/semanticwb/portal/PFlowManager", locale);
            }
            catch(Exception e)
            {
                bundle=ResourceBundle.getBundle("/org/semanticwb/portal/PFlowManager");
            }
            if (resource.getPflowInstance() != null)
            {
                WebSite site = resource.getWebSite();
                PFlow flow = resource.getPflowInstance().getPflow();
                Document docdef = SWBUtils.XML.xmlToDom(flow.getXml());
                int version = resource.getPflowInstance().getVersion();
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
                            if (i == 0 && messageType.equalsIgnoreCase("I"))
                            {
                                activityName = activity.getAttribute("name");
                            }
                            if (activity.getAttribute("name").equalsIgnoreCase(activityName))
                            {
                                if (activity.getAttribute("type").equalsIgnoreCase("AuthorActivity"))
                                {
                                    User user = resource.getCreator();
                                    String msgMail = bundle.getString("msg1") + " " + resource.getId() + " " + bundle.getString("msg2") + " '" + resource.getTitle() + "' " + bundle.getString("msg3") + ".";

                                    msgMail += "\r\n" + bundle.getString("msg4") + ": " + wbuser.getFirstName() + " " + wbuser.getLastName();
                                    msgMail += "\r\n" + bundle.getString("msg5") + ": " + wbuser.getLogin();

                                    msgMail += "\r\n" + bundle.getString("msg6") + ": " + message;
                                    msgMail += "\r\n" + bundle.getString("sitio") + ": " + site.getTitle() + ".\r\n";
                                    msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                    if (activity.getAttribute("days") != null && activity.getAttribute("hours") != null)
                                    {
                                        if (!(activity.getAttribute("days").equals("0") && activity.getAttribute("hours").equals("0")))
                                        {
                                            msgMail += "\r\n" + bundle.getString("msgr1") + " " + activity.getAttribute("days") + " " + bundle.getString("days") + " " + bundle.getString("and") + " " + activity.getAttribute("hours") + " " + bundle.getString("hours") + " .";
                                        }
                                    }
                                    WebPage page = (WebPage) resource.getResourceable();
                                    HashMap args = new HashMap();
                                    args.put("language", Locale.getDefault().getLanguage());
                                    msgMail += "\r\n" + bundle.getString("seccion") + ": " + page.getTitle() + ".\r\n";
                                    SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg7") + " " + resource.getId() + " " + bundle.getString("msg8"), msgMail);
                                }
                                else if (activity.getAttribute("type").equalsIgnoreCase("EndActivity"))
                                {
                                    User user = resource.getCreator();
                                    String msgMail = bundle.getString("msg1") + " " + resource.getId() + " " + bundle.getString("msg2") + " '" + resource.getTitle() + "' " + bundle.getString("msg9") + ".";
                                    msgMail += "\r\n" + bundle.getString("sitio") + ": " + site.getTitle() + ".\r\n";
                                    msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                    if (messageType.equalsIgnoreCase("N") && message != null && !message.equalsIgnoreCase(""))
                                    {
                                        msgMail += "\r\n" + bundle.getString("msg6") + ": " + message;
                                    }
                                    WebPage page = (WebPage) resource.getResourceable();
                                    HashMap args = new HashMap();
                                    args.put("language", Locale.getDefault().getLanguage());
                                    msgMail += "\r\n" + bundle.getString("seccion") + ": " + page.getTitle() + ".\r\n";
                                    SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg7") + " " + resource.getId() + " " + bundle.getString("msg10") + "", msgMail);
                                }
                                else if (activity.getAttribute("type").equalsIgnoreCase("Activity"))
                                {
                                    HashSet<User> husers = new HashSet<User>();
                                    NodeList users = activity.getElementsByTagName("user");
                                    for (int j = 0; j < users.getLength(); j++)
                                    {
                                        Element user = (Element) users.item(j);
                                        String userid = user.getAttribute("id");
                                        User recuser = site.getUserRepository().getUser(userid);
                                        husers.add(recuser);
                                    }
                                    NodeList roles = activity.getElementsByTagName("role");
                                    for (int j = 0; j < roles.getLength(); j++)
                                    {
                                        Element erole = (Element) roles.item(j);
                                        try
                                        {
                                            //Enumeration eusers = DBUser.getInstance(erole.getAttribute("repository")).getUsers();
                                            Iterator<User> eusers = SWBContext.getUserRepository(erole.getAttribute("repository")).listUsers();
                                            while (eusers.hasNext())
                                            {
                                                User user = eusers.next();
                                                Iterator<Role> itroles = user.listRoles();
                                                while (itroles.hasNext())
                                                {
                                                    Role role = itroles.next();
                                                    if (role.getId().equals(erole.getAttribute("id")))
                                                    {
                                                        husers.add(user);
                                                    }
                                                }
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            log.error(e);
                                        }
                                    }
                                    //envía correo
                                    String to = "";
                                    Iterator<User> itusers = husers.iterator();
                                    while (itusers.hasNext())
                                    {
                                        User user = (User) itusers.next();
                                        if (to.indexOf(user.getEmail()) == -1)
                                        {
                                            to += user.getEmail() + ";";
                                        }
                                    }
                                    if (to.endsWith(";"))
                                    {
                                        to = to.substring(0, to.length() - 1);
                                    }
                                    if (!to.equalsIgnoreCase(""))
                                    {
                                        String subject = bundle.getString("msg7") + " " + resource.getId() + " " + bundle.getString("msg11");
                                        String msg = bundle.getString("msg1") + " " + resource.getId() + " " + bundle.getString("msg2") + " '" + resource.getTitle() + "' " + bundle.getString("msg12") + " '" + activityName + "'.\r\n";
                                        msg += "\r\n" + bundle.getString("sitio") + ": " + site.getTitle() + ".\r\n";
                                        msg += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                        WebPage page = (WebPage) resource.getResourceable();
                                        HashMap args = new HashMap();
                                        args.put("language", Locale.getDefault().getLanguage());
                                        msg += "\r\n" + bundle.getString("seccion") + ": " + page.getTitle() + ".\r\n";

                                        if ((messageType.equalsIgnoreCase("I") || messageType.equalsIgnoreCase("N")) && message != null && !message.equalsIgnoreCase(""))
                                        {
                                            msg += "\r\n" + bundle.getString("msg6") + ": " + message;
                                        }
                                        if (messageType.equalsIgnoreCase("A"))
                                        {

                                            // envía correo al creador del contenido
                                            User user = resource.getCreator();
                                            String msgMail = bundle.getString("sitio") + ": " + site.getTitle() + ".\r\n";
                                            msgMail += "\r\n" + bundle.getString("paso") + ": " + activityName + ".\r\n";
                                            //msgMail+=bundle.getString("url")+": "+ObjRes.getAdminUrl()+".\r\n";
                                            msgMail += "\r\n" + bundle.getString("seccion") + ": " + page.getTitle() + ".\r\n";
                                            msgMail += "\r\n" + bundle.getString("msg13") + " " + resource.getId() + " " + bundle.getString("msg14");

                                            SWBUtils.EMAIL.sendBGEmail(user.getEmail(), bundle.getString("msg13") + " " + resource.getId() + " " + bundle.getString("msg14"), msgMail);

                                            // avisa al los revisores de la expiración de la revisión delc ontenido
                                            msg += "\r\n" + bundle.getString("msg13") + " " + resource.getId() + " " + bundle.getString("msg14");
                                        }
                                        if (activity.getAttribute("days") != null && activity.getAttribute("hours") != null)
                                        {
                                            if (!(activity.getAttribute("days").equals("0") && activity.getAttribute("hours").equals("0")))
                                            {
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

        }
        catch (Exception e)
        {
            log.error(e);
            return;
        }
    }

    private void noauthorizeContent(Resource resource)
    {
        PFlowInstance instance = resource.getPflowInstance();

        try
        {
            instance.setStatus(3);
            String activityName = instance.getStep();
            int version = instance.getVersion();
            PFlow pflow = instance.getPflow();
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

