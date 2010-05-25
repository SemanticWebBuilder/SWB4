package org.semanticwb.portal.resources.projectdriver;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class ProjectDriver extends org.semanticwb.portal.resources.projectdriver.base.ProjectDriverBase 
{
    private boolean act,us;
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
        String action="";
        WebPage wp=paramRequest.getWebPage();


        //wp.isParentof(wp);
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
            //haveParent=false;
            //Activity act=(Activity)obj.createGenericInstance();
            //if(!checkParents(wp,"Activity"))haveParent=true;
            //if(!haveParent)
               path="/swbadmin/jsp/projectDriver/activityForm.jsp";
            //else
            //    Path="/swbadmin/jsp/projectDriver/pageInvalid.jsp";
        }else if(obj.instanceOf(Project.sclass)&&wp.isActive()&&!haveParent)
        {
            //if(!checkParentProject(wp))
                path="/swbadmin/jsp/projectDriver/projectForm.jsp";
            //else
            //    Path="/swbadmin/jsp/projectDriver/pageInvalid.jsp";

        }else if(obj.instanceOf(UserWebPage.sclass)&&wp.isActive()&&!haveParent)
        {
            //haveParent=false;
            //if(!checkParents(wp,"User"))
            //   haveParent=true;
            //if(!haveParent)
                path="/swbadmin/jsp/projectDriver/userForm.jsp";
             //else
             //   Path="/swbadmin/jsp/projectDriver/pageInvalid.jsp";
        }else if(obj.instanceOf(ActivityContainer.sclass)&&wp.isActive()&&!haveParent){
            path="/swbadmin/jsp/projectDriver/containerActivity.jsp";
        }else if(obj.instanceOf(UserWebPageContainer.sclass)&&wp.isActive()&&!haveParent){
            path="/swbadmin/jsp/projectDriver/containerUser.jsp";
        }else if(obj.instanceOf(WebPage.sclass)&&wp.isActive()&&!haveParent)
        {
          //  parent=wp.getParent();
          //  if(parent!=null){
          //      haveParent=false;
          //      if(!checkParents(wp,"WebPage"))
          //          haveParent=true;
          //      if(!haveParent){
          //          if(act)
          //              action="hasAct";
          //          else if(us)
          //              action="hasUs";
          //          else
          //              action="hasAll";
          //          path="/swbadmin/jsp/projectDriver/wpForm.jsp";
          //      }else
          //          path="/swbadmin/jsp/projectDriver/pageInvalid.jsp";
          //  }
          //  else{
          //      action="hasAll";
                path="/swbadmin/jsp/projectDriver/wpForm.jsp";
         //   }
        }//else{
        //    Path="/swbadmin/jsp/projectDriver/pageInvalid.jsp";
        //}
        RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath()+path);
        try {
        //    request.setAttribute("action", action);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){
            log.error(e);
        }
    }
     /*   private boolean checkParentProject(WebPage wp){
            ArrayList parents=new ArrayList();
            SemanticObject obj1;
            boolean haveParent=false;
            WebPage parent;
            Iterator it;
            parent=wp.getParent();
            while(parent!=null){
                parents.add(parent);
                parent=parent.getParent();
            }
            haveParent=false;
            Collections.reverse(parents);
            it=parents.iterator();
            while(it.hasNext())
            {
                parent=(WebPage)it.next();
                obj1=SemanticObject.createSemanticObject(parent.getURI());
                String name=obj1.getGenericInstance().getSemanticObject().getSemanticClass().getName();
                if(name.equals("Activity")||name.equals("UserWebPage"))
                    haveParent=true;
            }
            return haveParent;
    }*/
   /* private boolean checkParents(WebPage wp,String currentPage){
        ArrayList parents=new ArrayList();
        WebPage wp1,parent;
        SemanticObject obj1;
        Iterator itp;
        boolean a,w,p,u,ret;
        act=us=false;
        parent=wp.getParent();
        if(parent!=null)
            parents.add(wp);
        while(parent!=null){
            parents.add(parent);
            parent=parent.getParent();
        }
        Collections.reverse(parents);
        itp=parents.iterator();
        while(itp.hasNext()){
            parent=(WebPage)itp.next();
            obj1=SemanticObject.createSemanticObject(parent.getURI());
            String name=obj1.getGenericInstance().getSemanticObject().getSemanticClass().getName();
            if(name.equals("Project")||name.equals("WebPage")){
                while(itp.hasNext()){
                    wp1=(WebPage)itp.next();
                    obj1=SemanticObject.createSemanticObject(wp1.getURI());
                    name=obj1.getGenericInstance().getSemanticObject().getSemanticClass().getName();
                    if(name.equals("Project"))
                        parent=wp1;
                    else if(name.equals("Activity")||name.equals("UserWebPage"))
                    {
                        parents=new ArrayList();
                        parents.add(parent);
                        parents.add(wp1);
                        while(itp.hasNext())
                            parents.add(itp.next());
                        if(name.equals("Activity"))
                            act=true;
                        else if(name.equals("UserWebPage"))
                            us=true;
                    }
                }
            }
        }
        a=w=u=p=ret=true;
        itp=parents.iterator();
        while(itp.hasNext()){
            wp1=(WebPage)itp.next();
            obj1=SemanticObject.createSemanticObject(wp1.getURI());
            String name=obj1.getGenericInstance().getSemanticObject().getSemanticClass().getName();
            if(name.equals("Activity")){
                u=p=false;
                if(!a) a=w=false;
            }
            else if(name.equals("UserWebPage")){
                a=p=false;
                if(!u) u=w=false;
            }
            else if(name.equals("WebPage")){
                a=false;
                if(!w) p=u=w=false;
            }
            else if(name.equals("Project")){
                if(!p) a=u=p=w=false;
            }
        }
        if(currentPage.equals("Activity"))
            ret=a;
        else if(currentPage.equals("User"))
            ret=u;
        else if(currentPage.equals("WebPage"))
            ret=w;
        return ret;
    }*/

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
            if((status_act.equals("develop")&&act.getStartDate()==null)||(status_act.equals("develop")&&status_ini.equals("paused"))){
                act.setStartDate(new Timestamp(new Date().getTime()));
            }
            if(status_act.equals("ended")||status_act.equals("canceled")){
                act.setEndDate(new Timestamp(new Date().getTime()));
            }
            try
            {
                mgr.processForm(request);
                obj.removeProperty(act.swbproy_hasParticipants);
                //if(valsParts.length>0){
                    for(int x = 0; x<valsParts.length;x++){
                        obj.addObjectProperty(act.swbproy_hasParticipants, SemanticObject.createSemanticObject(valsParts[x]));
                    }
                //}
                obj.removeProperty(act.swbproy_hasPredecessor);
                //if(vals.length>0){
                    for (int x = 0; x < vals.length; x++) {
                        obj.addObjectProperty(act.swbproy_hasPredecessor, SemanticObject.createSemanticObject(vals[x]));
                    }
                //}
                getListPredecessores(act,response.getUser());
            }catch(FormValidateException e){
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
