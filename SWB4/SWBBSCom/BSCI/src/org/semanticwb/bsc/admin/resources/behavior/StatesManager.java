
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.Status;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


public class StatesManager extends GenericResource {
    private static final Logger log = SWBUtils.getLogger(StatesManager.class);
    private static final String formId = State.bsc_State.getClassName()+"/bhvr";
    
    public static final String Action_SELECT_GRP = "selgrp";
    public static final String Action_UPDT_ACTIVE = "updactv";
    public static final String Action_ACTIVE_ALL = "actall";
    public static final String Action_DEACTIVE_ALL = "deactall";
        
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
        final String lang = user.getLanguage();
        
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        if(obj!=null)
        {
            final String action = paramRequest.getAction();
            if(request.getParameter("sg")!=null)
            {
                PrintWriter out = response.getWriter();
                
                if(obj.getGenericInstance() instanceof Indicator)
                {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("<p>"+paramRequest.getLocaleString("msgObjectiveWithoutStates")+"</p>");
                    out.println("</fieldset>");
                    out.println("</div>");
                }
                else
                {
                    WebSite model = SWBContext.getGlobalWebSite();
                    final String sgId = request.getParameter("sg");
                    if(StateGroup.ClassMgr.hasStateGroup(sgId, model)) {
                        StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(sgId, model);                        
                        out.println("<script type=\"text/javascript\">");
                        out.println("  dojo.require('dojo.parser');");
                        out.println("  dojo.require('dijit.layout.ContentPane');");
                        out.println("  dojo.require('dijit.form.CheckBox');");
                        out.println("</script>");

                        out.println("<div class=\"swbform\">");
                        out.println("<fieldset>");
                        out.println("<table width=\"98%\">"); 
                        out.println("<thead>");
                        out.println("<tr>");
                        out.println("<th>"+paramRequest.getLocaleString("lblIndex")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblState")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblPrevius")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblNext")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblStateGroup")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblActive")+"</th>");
                        out.println("<th>"+paramRequest.getLocaleString("lblRelate")+"</th>");
                        out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                        List lstates = stateGroup.listValidStates();
                        Collections.sort(lstates);
                        Iterator<State> groupedStates = lstates.iterator();
                        boolean hasGroupedStates = groupedStates.hasNext();
                        while(groupedStates.hasNext()) {
                            State state = groupedStates.next();
                            out.println("<tr>");
                            out.println("");

                            // Orden
                            out.println(" <td>"+state.getOrden()+"</td>");

                            // Estado
                            out.println(" <td>");
                            out.println("<a href=\"#\" onclick=\"addNewTab('" + state.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle().replaceAll("'","")):state.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle().replaceAll("'","")):state.getTitle(lang).replaceAll("'","")) + "</a>");
                            out.println(" </td>");

                            // Estado anterior
                            if(state.getPrevius()==null) {
                                out.println(" <td>Not set</td>");
                            }else {
                                State previus = (State)state.getPrevius();
                                out.println(" <td>");
                                out.println("<a href=\"#\" onclick=\"addNewTab('" + previus.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (previus.getTitle(lang)==null?(previus.getTitle()==null?"Sin título":previus.getTitle().replaceAll("'","")):previus.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (previus.getTitle(lang)==null?(previus.getTitle()==null?"Sin título":previus.getTitle().replaceAll("'","")):previus.getTitle(lang).replaceAll("'","")) + "</a>");
                                out.println(" </td>");
                            }

                            // Estado siguiente
                            if(state.getNext()==null) {
                                out.println(" <td>Not set</td>");
                            }else {
                                State next = (State)state.getNext();
                                out.println(" <td>");
                                out.println("<a href=\"#\" onclick=\"addNewTab('" + next.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (next.getTitle(lang)==null?(next.getTitle()==null?"Sin título":next.getTitle().replaceAll("'","")):next.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (next.getTitle(lang)==null?(next.getTitle()==null?"Sin título":next.getTitle().replaceAll("'","")):next.getTitle(lang).replaceAll("'","")) + "</a>");
                                out.println(" </td>");
                            }

                            // Grupo del estado
                            out.println(" <td>");
                            out.println((stateGroup.getTitle(lang)==null?(stateGroup.getTitle()==null?"Sin título":stateGroup.getTitle().replaceAll("'","")):stateGroup.getTitle(lang).replaceAll("'","")));
                            out.println(" </td>");
                            
                            // Activo?
                            out.println(" <td align=\"center\">" + 
                            (state.isActive()
                             ? paramRequest.getLocaleString("lblYes")
                             : paramRequest.getLocaleString("lblNot")) +
                            "</td>");

                            // Asigna?
                            SWBResourceURL urlactv = paramRequest.getActionUrl();
                            urlactv.setParameter("suri", suri);
                            urlactv.setParameter("sval", state.getId());
                            urlactv.setParameter("sg", sgId);
                            urlactv.setAction(Action_UPDT_ACTIVE);
                            out.println("   <td align=\"center\"><input type=\"checkbox\" name=\"act\" onchange=\"submitUrl('" + urlactv + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.CheckBox\" value=\"\" /></td>");
                            out.println("</tr>");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                        out.println("</fieldset>");

                        SWBResourceURL urlGrp = paramRequest.getRenderUrl();
                        urlGrp.setParameter("suri", suri);
                        urlGrp.setAction(Action_SELECT_GRP);
                        out.println("<fieldset>");
                        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlGrp + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblSelectOtherGroup") + "</button>");
                        if(hasGroupedStates)
                        {
                            SWBResourceURL urlAll = paramRequest.getActionUrl();
                            urlAll.setParameter("suri", suri);
                            urlAll.setParameter("sval", stateGroup.getId());
                            urlAll.setAction(Action_ACTIVE_ALL);
                            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblActiveAll") + "</button>");
                        }
                        out.println("</fieldset>");
                        out.println("</div>");
                    }
                }
            }
            else if(SWBResourceURL.Action_EDIT.equalsIgnoreCase(action))
            {
                doEdit(request, response, paramRequest);
            }
            else if(Action_SELECT_GRP.equalsIgnoreCase(action))
            {
                doChooseStatesGroup(request, response, paramRequest);
            }
        }
        else
        {
            response.getWriter().print("objeto semántico no ubicado");
        }
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

        final String lang = user.getLanguage();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        if(obj!=null)
        {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require('dojo.parser');");
            out.println("  dojo.require('dijit.layout.ContentPane');");
            out.println("  dojo.require('dijit.form.CheckBox');");
            out.println("</script>");
            out.println("<script type=\"text/javascript\">");
            if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
                out.println("   showStatus('" + request.getParameter("statmsg") + "');");
                out.println("updateTreeNodeByURI('" + obj.getURI() + "');");
                String icon = SWBContext.UTILS.getIconClass(obj);
                out.println("setTabTitle('" + obj.getURI() + "','" + obj.getDisplayName(user.getLanguage()) + "','" + icon + "');");
            }
//            if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
//                out.println("   closeTab('" + request.getParameter("closetab") + "');");
//            }
            out.println("</script>");

            String action = paramRequest.getAction();
            if(SWBResourceURL.Action_EDIT.equalsIgnoreCase(action))
            {
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<table width=\"98%\">"); 
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>"+paramRequest.getLocaleString("lblIndex")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblState")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblPrevius")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblNext")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblStateGroup")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblActive")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblRelate")+"</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                Status status = (Status)obj.getGenericInstance();
//                if(status.getState()!=null)
//                {
//                    List<State> lstates = null;
//                    if(status instanceof Indicator) {
//                        lstates = ((Indicator)status).getObjective().listValidStates();
//                    }else if( status instanceof Deliverable) {
//                    }else if(status instanceof Objective || status instanceof Initiative) {
//                        if(status.getState()!=null) {
//                            lstates = status.getState().getStateGroup().listValidStates();
//                        }
//                    }
                Iterator<State> istates = status.listStates();
                List<State> lstates = SWBUtils.Collections.copyIterator(istates);
                    
                    if(!lstates.isEmpty())
                    {
                        Collections.sort(lstates);
                        Iterator<State> groupedStates = lstates.iterator();
                        while(groupedStates.hasNext()) {                            
                            State state = groupedStates.next();
                            if(  (state.isValid() && user.haveAccess(state)) || (!state.isActive() && status.hasState(state) && user.haveAccess(state))  )
                            {
                                out.println("<tr>");
                                // Orden
                                out.println(" <td>"+state.getOrden()+"</td>");

                                // Estado
                                out.println(" <td>");
                                out.println("<a href=\"#\" onclick=\"addNewTab('" + state.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle().replaceAll("'","")):state.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle().replaceAll("'","")):state.getTitle(lang).replaceAll("'","")) + "</a>");
                                out.println(" </td>");

                                // Estado anterior
                                if(state.getPrevius()==null) {
                                    out.println(" <td>Not set</td>");
                                }else {
                                    State previus = (State)state.getPrevius();
                                    out.println(" <td>");
                                    out.println("<a href=\"#\" onclick=\"addNewTab('" + previus.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (previus.getTitle(lang)==null?(previus.getTitle()==null?"Sin título":previus.getTitle().replaceAll("'","")):previus.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (previus.getTitle(lang)==null?(previus.getTitle()==null?"Sin título":previus.getTitle().replaceAll("'","")):previus.getTitle(lang).replaceAll("'","")) + "</a>");
                                    out.println(" </td>");
                                }

                                // Estado siguiente
                                if(state.getNext()==null) {
                                    out.println(" <td>Not set</td>");
                                }else {
                                    State next = (State)state.getNext();
                                    out.println(" <td>");
                                    out.println("<a href=\"#\" onclick=\"addNewTab('" + next.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (next.getTitle(lang)==null?(next.getTitle()==null?"Sin título":next.getTitle().replaceAll("'","")):next.getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (next.getTitle(lang)==null?(next.getTitle()==null?"Sin título":next.getTitle().replaceAll("'","")):next.getTitle(lang).replaceAll("'","")) + "</a>");
                                    out.println(" </td>");
                                }

                                // Grupo del estado
                                out.println(" <td>");
                                out.println((state.getStateGroup().getTitle(lang)==null?(state.getStateGroup().getTitle()==null?"Sin título":state.getStateGroup().getTitle().replaceAll("'","")):state.getStateGroup().getTitle(lang).replaceAll("'","")));
                                out.println(" </td>");

                                // Activo?
                                out.println(" <td align=\"center\">" + 
                                (state.isActive()
                                 ? paramRequest.getLocaleString("lblYes")
                                 : paramRequest.getLocaleString("lblNot")) +
                                "</td>");

                                // Asigna?
                                SWBResourceURL urlactv = paramRequest.getActionUrl();
                                urlactv.setParameter("suri", suri);
                                urlactv.setParameter("sval", state.getId());
                                urlactv.setAction(Action_UPDT_ACTIVE);
                                out.println("   <td align=\"center\"><input type=\"checkbox\" name=\"act\" onchange=\"submitUrl('" + urlactv + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.CheckBox\" value=\""+status.hasState(state) +"\" "+(status.hasState(state) ?"checked=\"checked\"":"")+" /></td>");
                                out.println("</tr>");
                            }
                        }
                    }
//                }/////////////////////////////////////
                out.println("</tbody>");
                out.println("</table>");
                out.println("</fieldset>");
                
                SWBResourceURL urlGrp = paramRequest.getRenderUrl();
                urlGrp.setParameter("suri", suri);
                urlGrp.setAction(Action_SELECT_GRP);
                out.println("<fieldset>");
                if(status instanceof Objective || status instanceof Initiative) {
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlGrp + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblSelectOtherGroup") + "</button>");
                }
                if(status.getState()!=null)
                {
                    SWBResourceURL urlAll = paramRequest.getActionUrl();
                    urlAll.setParameter("suri", suri);
                    urlAll.setParameter("sval", status.getState().getStateGroup().getId());
                    urlAll.setAction(Action_ACTIVE_ALL);
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblActiveAll") + "</button>");

                    urlAll.setAction(Action_DEACTIVE_ALL);
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblDeactiveAll") + "</button>");
                }
                out.println("</fieldset>");
                out.println("</div>");
            }
        }
    }
    
    private void doChooseStatesGroup(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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

        final String lang = user.getLanguage();        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        
        if(obj!=null)
        {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require('dojo.parser');");
            out.println("  dojo.require('dijit.layout.ContentPane');");
            out.println("  dojo.require('dijit.form.Form');");
            out.println("  dojo.require('dijit.form.RadioButton');");
            out.println("</script>");
            out.println("<script type=\"text/javascript\">");
            if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
                out.println("   showStatus('" + request.getParameter("statmsg") + "');");
                out.println("updateTreeNodeByURI('" + obj.getURI() + "');");
                String icon = SWBContext.UTILS.getIconClass(obj);
                out.println("setTabTitle('" + obj.getURI() + "','" + obj.getDisplayName(user.getLanguage()) + "','" + icon + "');");
            }
            if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
                out.println("   closeTab('" + request.getParameter("closetab") + "');");
            }
            out.println("</script>");

            String action = paramRequest.getAction();
            if(Action_SELECT_GRP.equalsIgnoreCase(action))
            {
                SWBResourceURL url = paramRequest.getRenderUrl();                
                url.setAction(SWBResourceURL.Action_EDIT);
                url.setParameter("suri", suri);
                
                String formId_ = formId+"/_"+obj.getId()+"_"+(new Date()).getTime();
                
                out.println("<div class=\"swbform\">");                
                out.print("<form id=\""+formId_+"\" dojoType=\"dijit.form.Form\" class=\"swbform\"");
                out.print(" action=\""+url+"\" ");
                out.print(" onSubmit=\"submitForm('"+formId_+"');return false;\" ");
                out.println(" method=\"post\">");              
                
                out.println("<fieldset>");
                out.println("<table width=\"57%\">"); 
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>"+paramRequest.getLocaleString("lblStateGroup")+"</th>");
                out.println("<th>"+paramRequest.getLocaleString("lblDescriptionGroup")+"</th>");
                out.println("<th>&nbsp;</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                WebSite model = SWBContext.getGlobalWebSite();
                Iterator<StateGroup> groups = StateGroup.ClassMgr.listStateGroups(model);
                while(groups.hasNext()) {
                    StateGroup stateGroup = groups.next();
                    if(!stateGroup.isValid() || !user.haveAccess(stateGroup)) {
                        continue;
                    }
                    out.println("<tr>");

                    // Grupo de estados
                    out.println(" <td><label for=\"sg_"+formId_+stateGroup.getId()+"\">");
                    out.println((stateGroup.getTitle(lang)==null?(stateGroup.getTitle()==null?"Sin título":stateGroup.getTitle().replaceAll("'","")):stateGroup.getTitle(lang).replaceAll("'","")));
                    out.println(" </label></td>");
                    out.println(" <td>");
                    out.println((stateGroup.getDescription(lang)==null?(stateGroup.getDescription()==null?"Sin descripción":stateGroup.getDescription().replaceAll("'","")):stateGroup.getDescription(lang).replaceAll("'","")));
                    out.println("</td>");

                    // Selección
                    out.println(" <td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sg\" value=\""+stateGroup.getId()+"\" id=\"sg_"+formId_+stateGroup.getId()+"\" />");
                    out.println(" </td>");
                    out.println("</tr>");
                }
//                    }
//                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</fieldset>");                
                
                SWBResourceURL urlGrp = paramRequest.getActionUrl();
                urlGrp.setParameter("suri", suri);
                urlGrp.setAction(Action_SELECT_GRP);
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("lblAddSelected") + "</button>");
                
                SWBResourceURL urlBack = paramRequest.getRenderUrl();
                urlBack.setParameter("suri", suri);
                urlBack.setAction(SWBResourceURL.Action_EDIT);
                urlBack.setMode(SWBResourceURL.Mode_VIEW);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlBack + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblBack") + "</button>");
                  
                out.println("</fieldset>");
                out.println("</form>");
                out.println("</div>");
            }
        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {        
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        
        response.setAction(SWBResourceURL.Action_EDIT);
        response.setRenderParameter("suri", suri);
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        if(semObj==null) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            return;
        }
        
        User user = response.getUser();
        if(!user.isSigned() || !user.haveAccess(semObj.getGenericInstance())) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgUnauthorizedUser"));
            return;
        }
        
        
        SWBModel model = SWBContext.getGlobalWebSite();
        if(Action_UPDT_ACTIVE.equalsIgnoreCase(action))
        {
            Status status = (Status)semObj.getGenericInstance();
            final String stateId = request.getParameter("sval");
            if(stateId!=null)
            {
                State state;
                if(State.ClassMgr.hasState(stateId, model)) {
                    state = State.ClassMgr.getState(stateId, model);
                    boolean act = status.hasState(state);
                    if(act) {
                        status.removeState(state);                                                
                        if(!state.listStatuses().hasNext())
                        {
                            state.setUndeleteable(false);
                            Iterator<State> it = state.getStateGroup().listGroupedStateses();
                            boolean stateRelated = false;
                            while(it.hasNext() && !stateRelated) {
                                State aux = it.next();
                                stateRelated = stateRelated || aux.listStatuses().hasNext();
                                if(!stateRelated) {
                                    aux.setUndeleteable(false);
                                }
                            }
                            if(!stateRelated) {
                                 state.getStateGroup().setUndeleteable(false);
                            }
                        }
                        response.setRenderParameter("statmsg", response.getLocaleString("msgDeallocatedState"));
                    }else {
                        status.addState(state);
                        state.setUndeleteable(true);
                        state.getStateGroup().setUndeleteable(true);
                        response.setRenderParameter("statmsg", response.getLocaleString("msgAssignedState"));
                    }                    
                }else {
                    response.setRenderParameter("statmsg", "objeto semantico no ubicado");
                }
            }
            else
            {
                response.setRenderParameter("statmsg", "objeto semantico no ubicado");
            }
        }
        else if(Action_ACTIVE_ALL.equalsIgnoreCase(action))
        {
            Status status = (Status)semObj.getGenericInstance();
            final String sgId = request.getParameter("sval");
            if(StateGroup.ClassMgr.hasStateGroup(sgId, model)) {
                StateGroup stateGroup = StateGroup.ClassMgr.getStateGroup(sgId, model);
                Iterator<State> groupedStates = stateGroup.listValidStates().iterator();
                if(groupedStates.hasNext()) {
                    stateGroup.setUndeleteable(true);
                    while(groupedStates.hasNext()) {
                        State state = groupedStates.next();
                        status.addState(state);
                        state.setUndeleteable(true);
                    }
                }
            }
        }
        else if(Action_DEACTIVE_ALL.equalsIgnoreCase(action))
        {
            Status status = (Status)semObj.getGenericInstance();
            State state = status.getState();
            if(state!=null)
            {
                status.removeAllState();
                StateGroup sg = state.getStateGroup();
                Iterator<State> it = sg.listGroupedStateses();
                if(it.hasNext())
                {
                    State aux = null;
                    boolean stateRelated = false;
                    while(it.hasNext() && !stateRelated) {
                        aux = it.next();
                        stateRelated = aux.listStatuses().hasNext();
                        if(!stateRelated) {
                            aux.setUndeleteable(false);
                        }
                    }
                    if(!stateRelated) {
                        sg.setUndeleteable(false);
                    }
                }
            }
        }
    }
}