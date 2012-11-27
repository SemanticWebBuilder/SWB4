/*
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
 */
package org.semanticwb.portal.resources.projectdriver;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDriver.
 */
public class ProjectDriver extends org.semanticwb.portal.resources.projectdriver.base.ProjectDriverBase 
{
    
    /** The log. */
    public static Logger log=SWBUtils.getLogger(ProjectDriver.class);
    
    /**
     * Instantiates a new project driver.
     */
    public ProjectDriver()
    {
    }

    /**
     * Instantiates a new project driver.
     * 
     * @param base the base
     */
    public ProjectDriver(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path="";
        WebPage wp=paramRequest.getWebPage();
        WebPage parent=wp.getParent();
        SemanticObject obj = SemanticObject.createSemanticObject(wp.getURI());
        boolean haveParent=false;
        while(parent!=null){
            if(!parent.isActive())
               haveParent=true;
            parent=parent.getParent();
        }
        if(obj.instanceOf(Activity.sclass)&&wp.isActive()&&!haveParent)
        {
               path="/swbadmin/jsp/projectDriver/activityForm.jsp";
        }else if(obj.instanceOf(Project.sclass)&&wp.isActive()&&!haveParent)
        {
                path="/swbadmin/jsp/projectDriver/projectForm.jsp";
        }else if(obj.instanceOf(UserWebPage.sclass)&&wp.isActive()&&!haveParent)
        {
                path="/swbadmin/jsp/projectDriver/userForm.jsp";
        }else if(obj.instanceOf(ActivityContainer.sclass)&&wp.isActive()&&!haveParent){
            path="/swbadmin/jsp/projectDriver/containerActivity.jsp";
        }else if(obj.instanceOf(UserWebPageContainer.sclass)&&wp.isActive()&&!haveParent){
            path="/swbadmin/jsp/projectDriver/containerUser.jsp";
        }else if(obj.instanceOf(WebPage.sclass)&&wp.isActive()&&!haveParent)
        {
                path="/swbadmin/jsp/projectDriver/wpForm.jsp";
        }
        RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath()+path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){
            log.error(e);
        }
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(HttpServletRequest, SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action= response.getAction();
        if(action.equals("update")&& request.getParameter("uri")!=null){
            SemanticObject obj = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Activity act=(Activity)obj.createGenericInstance();
            int currHour=0,planHour=0;
            float currPerc=0;
            String critical="",actType="",status="",status_ini="",status_act="";
            String vals[]=new String[0], valsParts[]=new String[0];
            try
            {
                if(request.getParameter("status_ini")!=null)
                    status_ini=request.getParameter("status_ini");
                if(request.getParameter("status")!=null)
                    status_act=request.getParameter("status");
                if((status_act.equals("develop")&&act.getStartDate()==null)||(status_act.equals("develop")&&status_ini.equals("paused"))||status_act.equals("develop"))
                    act.setStartDate(new Timestamp(new Date().getTime()));
                if(status_act.equals("ended")||status_act.equals("canceled"))
                    act.setEndDate(new Timestamp(new Date().getTime()));
                if(request.getParameter("currentHour")!=null&&!request.getParameter("currentHour").equals("")){
                    currHour = Integer.parseInt(request.getParameter("currentHour"));
                    act.setCurrentHour(currHour);
                }
                if(request.getParameter("currentPercentage")!=null&&!request.getParameter("currentPercentage").equals("")){
                    currPerc = Float.parseFloat(request.getParameter("currentPercentage"));
                    act.setCurrentPercentage(currPerc);
                }
                if(request.getParameter("plannedHour")!=null&&!request.getParameter("plannedHour").equals("")){
                    planHour = Integer.parseInt(request.getParameter("plannedHour"));
                    act.setPlannedHour(planHour);
                }
                if(request.getParameter("actType")!=null){
                    actType = request.getParameter("actType");
                    act.setActType(actType);
                }
                if(request.getParameter("status")!=null){
                    status=request.getParameter("status");
                    act.setStatus(status);
                }
                if(request.getParameter("critical")!=null)
                    critical = request.getParameter("critical");
                if(critical!=null && (critical.equals("true")||(critical.equals("on"))))
                    act.setCritical(true);
                else
                    act.setCritical(false);
                if(request.getParameterValues("hasParticipants")!=null)
                    valsParts = request.getParameterValues("hasParticipants");
                Iterator<User> usr = act.listParticipantses();
                while(usr.hasNext()){
                    User usr1 = usr.next();
                    act.removeParticipants(usr1);
                }
                for(int x = 0; x<valsParts.length;x++){
                    if(!valsParts[x].equals("")){
                        SemanticObject obj1 = SemanticObject.createSemanticObject(valsParts[x]);
                        User us = (User)obj1.createGenericInstance();
                        act.addParticipants(us);
                    }
                }
                if(request.getParameterValues("hasPredecessor")!=null)
                    vals=request.getParameterValues("hasPredecessor");
                Iterator<Activity> ix=act.listPredecessors();
                while(ix.hasNext()){
                    Activity actsx = ix.next();
                    act.removePredecessor(actsx);
                }
                for (int x = 0; x < vals.length; x++) {
                    if(!vals[x].equals("")){
                        SemanticObject obj1 = SemanticObject.createSemanticObject(vals[x]);
                        Activity actsx1 = (Activity)obj1.createGenericInstance();
                        act.addPredecessor(actsx1);
                    }
                }
                getListPredecessores(act,response.getUser());
            }catch(Exception e){
                log.error(e);
            }
        }else if(action.equals("upduser")&& request.getParameter("uri")!=null){
            try{
                String speciality = request.getParameter("speciality");
                SemanticObject obj = SemanticObject.createSemanticObject(request.getParameter("uri"));
                UserWebPage uwp = (UserWebPage)obj.createGenericInstance();
                uwp.setSpeciality(speciality);
            }catch(Exception e){
                log.error(e);
            }
        }
        response.setMode(response.Mode_VIEW);
    }
    
    /**
     * Gets the list predecessores.
     * 
     * @param act the act
     * @param user the user
     * @return the list predecessores
     */
    private void getListPredecessores(Activity act,User user){
        ArrayList values=new ArrayList();
        ArrayList values1=new ArrayList();
        Iterator<Activity> it = act.listPredecessors();

        while(it.hasNext()){
           Activity act1 = it.next();
           values=listPredecessor(act1,values,user);
           Iterator val = values.iterator();
           while(val.hasNext())
               values1.add(val.next());
           values=new ArrayList();
        }
        SemanticObject obj = SemanticObject.createSemanticObject(act.getURI());
        obj.removeProperty(act.swbproy_hasPredecessor);
        Iterator itval = values1.iterator();
        while(itval.hasNext()){
            String val = itval.next().toString();
            obj.addObjectProperty(act.swbproy_hasPredecessor, SemanticObject.createSemanticObject(val));
        }
    }
    
    /**
     * List predecessor.
     * 
     * @param actChild the act child
     * @param values the values
     * @param user the user
     * @return the array list
     */
    private ArrayList listPredecessor(Activity actChild, ArrayList values,User user){
            values.add(actChild.getURI());
            Iterator itc=actChild.listVisibleChilds(user.getLanguage());
            while(itc.hasNext()){
                Activity acts = (Activity)itc.next();
                if(hasChild(acts))
                    listPredecessor(acts, values,user);
                else
                    values.add(acts.getURI());
            }
        return values;
    }
    
    /**
     * Checks for child.
     * 
     * @param wp the wp
     * @return true, if successful
     */
    private boolean hasChild(WebPage wp){
        boolean valid=false;
        wp = wp.getChild();
        if(wp != null && wp.isVisible() && wp.isActive() && !wp.isHidden() && wp.isValid() && !wp.isDeleted())
            valid = true;
        return valid;
    }
}
