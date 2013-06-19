
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


public class StatesManager extends GenericResource {
    private Logger log = SWBUtils.getLogger(StatesManager.class);
    private static final String formId = State.bsc_State.getClassName()+"/bhvr";
    private static final String sgId = "sg";
    private static final String stId = "st";
        
    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
System.out.println("\n\n\n");

        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
        
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 


        SWBResourceURL url = paramRequest.getActionUrl();
        StringBuilder ret = new StringBuilder();
        ret.append("<script type=\"text/javascript\">\n");
        ret.append("  dojo.require('dojo.parser');\n");
        ret.append("  dojo.require('dijit.layout.ContentPane');\n");
        ret.append("  dojo.require('dijit.form.FilteringSelect');\n");
        ret.append("  dojo.require('dijit.form.CheckBox');\n");
        /*ret.append("  function validpwd(pwd){\n");
        ret.append("    var ret=true;\n");
        ret.append(((SWBPlatform.getSecValues().isDifferFromLogin())?"  if (dijit.byId('Ulogin').textbox.value == pwd) { ret=false;}":""));
        ret.append("\n");
        ret.append(((SWBPlatform.getSecValues().getMinlength()>0)?"     if (pwd.length < "+SWBPlatform.getSecValues().getMinlength()+") { ret=false;}":""));
        ret.append("\n");
        ret.append(((SWBPlatform.getSecValues().getComplexity()==1)?"   if (!pwd.match(/^.*(?=.*[a-zA-Z])(?=.*[0-9])().*$/) ) { ret=false;}":""));
        ret.append("\n");
        ret.append((SWBPlatform.getSecValues().getComplexity()==2)?"    if (!pwd.match(/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])().*$/) ) { ret=false;}":"");
        ret.append("\n");
        ret.append("    return ret;\n");
        ret.append("  }\n");*/
        
        //out.println("dojo.addOnLoad(function(){getHtml('"+url+"'+'?site="+websiteId+"','slave');})");
        
        ret.append("</script>\n");
        
        ret.append("<form id=\"").append(formId).append("\" dojoType=\"dijit.form.Form\" class=\"swbform\"");
        ret.append(" action=\"").append(url).append("\" ");
        ret.append(" onSubmit=\"submitForm('").append(formId).append("');return false;\" method=\"post\">");
        ret.append("<fieldset>");
        
        SWBResourceURL surl = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setCallMethod(SWBResourceURL.Call_DIRECT);
        
        String lang = user.getLanguage();
        ret.append("  <select onchange=\"postHtml('"+surl+"'+'?sg='+this.options[this.selectedIndex],'st')\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"sg\" id=\"sg\">");
        //ret.append("  <select onchange=\"alert(dijit.byId('sg').get('value'));\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"sg\" id=\"sg\">");
        Iterator<StateGroup> groups = StateGroup.ClassMgr.listStateGroups(SWBContext.getAdminWebSite());
        while(groups.hasNext()) {
            StateGroup group = groups.next();
            if(!group.isValid() || !user.haveAccess(group)) {
                continue;
            }
            ret.append("<option value=\""+group.getId()+"\">"+group.getDisplayTitle(lang)+"</option>"); 
        }        
        ret.append("  </select>");
        ret.append("  <div id=\"st\"> ");
        ret.append("  </div>");
        ret.append("  <button dojoType='dijit.form.Button' type=\"submit\">guardar</button>\n");
        ret.append("</fieldset>");
        ret.append("</form>");
        response.getWriter().println(ret.toString());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("statesManager.doEdit............");
        System.out.println("grupo id="+request.getParameter("sg"));
        
        /*User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
        
        if(StateGroup.ClassMgr.hasStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite())) {
            StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite());
            System.out.println("stateGroup="+stateGroup);
            
            String lang = user.getLanguage();
            
            StringBuilder ret = new StringBuilder();
            
System.out.println("AdminWebSite="+SWBContext.getAdminWebSite());
            Iterator<State> states = State.ClassMgr.listStateByStateGroup(stateGroup, SWBContext.getAdminWebSite());
            while(states.hasNext()) {
                State state = states.next();
                if(!state.isValid() || !user.haveAccess(state)) {
                    continue;
                }
                ret.append("<label for=\""+state.getId()+"\"><input type=\"checkbox\" name=\"abc\" id=\""+state.getId()+"\" value=\""+state.getId()+"\"/>"+state.getDisplayTitle(lang)+"</label>"); 
            }
            response.getWriter().println(ret.toString());
        }*/
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("statesManager.processAction............");
        System.out.println("grupo id="+request.getParameter("sg"));
        if(StateGroup.ClassMgr.hasStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite())) {
            StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(request.getParameter("sg"), SWBContext.getAdminWebSite());
            System.out.println("stateGroup="+stateGroup);
        }
    }
}
