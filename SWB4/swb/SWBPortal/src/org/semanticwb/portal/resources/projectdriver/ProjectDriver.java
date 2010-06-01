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
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class ProjectDriver extends org.semanticwb.portal.resources.projectdriver.base.ProjectDriverBase 
{
    public static Logger log=SWBUtils.getLogger(ProjectDriver.class);
    public ProjectDriver()
    {
    }

    public ProjectDriver(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action= response.getAction();
        if(action.equals("update")&& request.getParameter("uri")!=null){
            String status_ini=request.getParameter("status_ini");
            String status_act=request.getParameter("status");
            String vals[]=request.getParameterValues("hasPredecessor");
            String valsParts[] =request.getParameterValues("hasParticipants");
            if(vals==null)
                vals=new String[0];
            if(valsParts==null)
                valsParts=new String[0];
            SemanticObject obj = SemanticObject.createSemanticObject(request.getParameter("uri"));
            SWBFormMgr mgr = new SWBFormMgr(obj,null,SWBFormMgr.MODE_EDIT);
            Activity act=(Activity)obj.createGenericInstance();
            if((status_act.equals("develop")&&act.getStartDate()==null)||(status_act.equals("develop")&&status_ini.equals("paused"))||status_act.equals("develop")){
                act.setStartDate(new Timestamp(new Date().getTime()));
            }
            if(status_act.equals("ended")||status_act.equals("canceled")){
                act.setEndDate(new Timestamp(new Date().getTime()));
            }
            try
            {
                mgr.processElement(request, act.swbproy_actType);//mgr.processForm(request);
                mgr.processElement(request, act.swbproy_critical);
                mgr.processElement(request, act.swbproy_currentHour);
                mgr.processElement(request, act.swbproy_currentPercentage);
                mgr.processElement(request, act.swbproy_endDate);
                mgr.processElement(request, act.swbproy_plannedHour);
                mgr.processElement(request, act.swbproy_responsible);
                mgr.processElement(request, act.swbproy_startDate);
                mgr.processElement(request, act.swbproy_status);
                obj.removeProperty(act.swbproy_hasParticipants);
                for(int x = 0; x<valsParts.length;x++){
                    if(!valsParts[x].equals(""))
                        obj.addObjectProperty(act.swbproy_hasParticipants, SemanticObject.createSemanticObject(valsParts[x]));
                }
                obj.removeProperty(act.swbproy_hasPredecessor);
                for (int x = 0; x < vals.length; x++) {
                    if(!vals[x].equals(""))
                        obj.addObjectProperty(act.swbproy_hasPredecessor, SemanticObject.createSemanticObject(vals[x]));
                }
                getListPredecessores(act,response.getUser());
            }catch(Exception e){
                log.error(e);
            }
        }else if(action.equals("updatepro")&&request.getParameter("uri")!=null){
            SemanticObject obj = SemanticObject.createSemanticObject(request.getParameter("uri"));
            obj.removeProperty(Project.swbproy_leader);
            try{
                String val = request.getParameter("leader");
                if(!val.equals("")||val!=null)
                    obj.addObjectProperty(Project.swbproy_leader, SemanticObject.createSemanticObject(val));
            }catch(Exception e){
                log.error(e);
            }
        }else if(action.equals("updateus")&&request.getParameter("uri")!=null){
            SemanticObject obj = SemanticObject.createSemanticObject(request.getParameter("uri"));
            obj.removeProperty(UserWebPage.swbproy_userWP);
            obj.removeProperty(UserWebPage.swbproy_speciality);
            try{
                String us1 = request.getParameter("userWP");
                String us2 =  request.getParameter("speciality");
                if(us1!=null&&us2!=null){
                    obj.addObjectProperty(UserWebPage.swbproy_userWP, SemanticObject.createSemanticObject(us1));
                    obj.addLiteralProperty(UserWebPage.swbproy_speciality, new SemanticLiteral(us2));
                }
            }catch(Exception e){
                log.error(e);
            }
        }
        response.setMode(response.Mode_VIEW);
    }
    private void getListPredecessores(Activity act,User user){
        ArrayList values=new ArrayList();
        ArrayList values1=new ArrayList();
        Iterator<Activity> it = act.listPredecessors();

        Activity act1;
         while(it.hasNext()){
             act1 = it.next();
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
    private boolean hasChild(WebPage wp){
        boolean valid=false;
        wp = wp.getChild();
        if(wp != null && wp.isVisible() && wp.isActive() && !wp.isHidden() && wp.isValid() && !wp.isDeleted())
            valid = true;
        else
            valid = false;
        return valid;
    }


}
