
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.Status;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


public class StatesManager extends GenericResource {
    private Logger log = SWBUtils.getLogger(StatesManager.class);
    private static final String formId = State.bsc_State.getClassName()+"/bhvr";
        
    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
         
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        
        final String stateGroupId = request.getParameter("sg");
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        Status status = (Status)obj.createGenericInstance();

        SWBResourceURL url = paramRequest.getActionUrl();
        StringBuilder ret = new StringBuilder();
        ret.append("<script type=\"text/javascript\">\n");
        ret.append("  dojo.require('dojo.parser');\n");
        ret.append("  dojo.require('dijit.layout.ContentPane');\n");
        ret.append("  dojo.require('dijit.form.FilteringSelect');\n");
        ret.append("  dojo.require('dijit.form.CheckBox');\n");
        ret.append("</script>\n");
        
        ret.append("<form id=\"").append(formId).append("/"+obj.getId()+"\" dojoType=\"dijit.form.Form\" class=\"swbform\"");
        ret.append(" action=\"").append(url).append("\" ");
        ret.append(" onSubmit=\"submitForm('").append(formId).append("');return false;\" method=\"post\">");
        ret.append("<fieldset>");
        
        SWBResourceURL surl = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setCallMethod(SWBResourceURL.Call_DIRECT);
        
        String lang = user.getLanguage();
        ret.append("  <select onchange=\"postHtml('").append(surl).append("'+'?sg='+this.attr('value')+'&suri=").append(URLEncoder.encode(suri,"UTF-8")).append("','st_"+obj.getId()+"')\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"sg\" id=\"sg_"+obj.getId()+"\">");
        StateGroup aux;
        if(StateGroup.ClassMgr.hasStateGroup(stateGroupId, SWBContext.getAdminWebSite())) {
            aux = StateGroup.ClassMgr.getStateGroup(stateGroupId, SWBContext.getAdminWebSite());
        }else {
            aux = null;
        }
        Iterator<StateGroup> groups = StateGroup.ClassMgr.listStateGroups(SWBContext.getAdminWebSite());
        while(groups.hasNext()) {
            StateGroup group = groups.next();
            if(!group.isValid() || !user.haveAccess(group)) {
                continue;
            }
            if(aux==null) {
                aux = group;
            }
            ret.append("<option ").append(group.equals(aux)?"selected=\"selected\"":"").append(" value=\"").append(group.getId()).append("\">").append(group.getDisplayTitle(lang)).append("</option>"); 
        }        
        ret.append("  </select>");
        ret.append("  <div id=\"st_"+obj.getId()+"\"> ");
        String list = renderStatesList(status, aux, user);
        ret.append(list==null?"":list);
        ret.append("  </div>");        
        ret.append("  <button dojoType='dijit.form.Button' type=\"submit\">guardar</button>\n");
        ret.append("  <input type=\"hidden\" name=\"suri\" value=\""+suri+"\" />");
        ret.append("</fieldset>");
        ret.append("</form>");
        response.getWriter().println(ret.toString());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {      
        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        Status status = (Status)obj.createGenericInstance();
        
        if(StateGroup.ClassMgr.hasStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite())) {
            StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite());
            String ret = renderStatesList(status, stateGroup, user);
            response.getWriter().println(ret==null?"<p>Este grupo no posee estados</p>":ret);
        }
    }
    
    private String renderStatesList(Status status, StateGroup stateGroup, User user) {
        if(status==null || stateGroup==null) {
            return null;
        }
        
        GenericIterator<State> states = stateGroup.listStates();
        if(states.hasNext())
        {
            String lang = user.getLanguage();
            StringBuilder ret = new StringBuilder();
            ret.append("<ul>");
            while(states.hasNext()) {
                State state = states.next();
                if(!state.isValid() || !user.haveAccess(state)) {
                    continue;
                }
                ret.append("<li><label for=\"chk_"+state.getId()+"\"><input type=\"checkbox\" "+(status.hasState(state)?"checked=\"checked\"":"")+" name=\"abc\" id=\"chk_"+state.getId()+"\" value=\""+state.getId()+"\"/>"+state.getDisplayTitle(lang)+"</label></li>"); 
            }
            ret.append("</ul>");
            return ret.toString();
        }
        else
        {
            return null;
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final String sgId = request.getParameter("sg");
        final String suri=request.getParameter("suri");
        if(StateGroup.ClassMgr.hasStateGroup(sgId, SWBContext.getAdminWebSite()))
        {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            SemanticObject obj = ont.getSemanticObject(suri);
            Status status = (Status)obj.createGenericInstance();
            
            
            
            System.out.println("\n\nTODO");
System.out.println("objetivo="+status);
            Iterator<State> it = status.listStates();
            while(it.hasNext()) {
                State state = it.next();
System.out.println("\n---estado="+state);
                boolean stateRelated = false;
                Iterator<Status> it2 = state.listStatuses();
                while(it2.hasNext() && !stateRelated) {
                    Status stus = it2.next();
                    if(stus instanceof StateGroup) {
System.out.println("----- relacionado con stategroup "+stus);
                        continue;
                    }
                    if(status.equals(stus)) {
System.out.println("----- relacionado con mismo status "+stus);
                        continue;
                    }
                    stateRelated = true;
                    System.out.println("----- relacionado con "+stus);
                }
                if(!stateRelated) {
                    state.setUndeleteable(false);
                }
            }
            
                       
            
            status.removeAllState();
            
            String[] values = request.getParameterValues("abc");            
            if(values!=null)
            {
                StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(sgId, SWBContext.getAdminWebSite());
                State state;
                for(int i=0; i<values.length; i++) {
                    if(State.ClassMgr.hasState(values[i], SWBContext.getAdminWebSite())) {
                        state = State.ClassMgr.getState(values[i], SWBContext.getAdminWebSite());
                        status.addState(state);
                        state.setUndeleteable(true);
                    }
                }
                stateGroup.setUndeleteable(true);
            }
        }
        response.setRenderParameter("suri", suri);
        response.setRenderParameter("sg", sgId);
    }
}
