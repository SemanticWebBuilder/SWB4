/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.Seasonable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Undeleteable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * PeriodsManager es una clase que permite asociar o desasociar periodos a un
 * objetivo
 *
 *
 * @author Carlos Ramos Inchaustegui
 * @version %I%, %G%
 * @since 1.0
 */
public class PeriodsManager extends GenericResource {
    public static final String Action_UPDT_ACTIVE = "updactv";
    public static final String Action_ACTIVE_ALL = "actall";
    public static final String Action_DEACTIVE_ALL = "deactall";    

    /**
     * M&eacute;todo que se encarga de presentar la forma al usuario para que
     * elija un conjunto de periodos
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException Excepti&oacute;n utilizada para recursos de
     * SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        User user = paramRequest.getUser();
        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require('dojo.parser');\n");
        out.println("  dojo.require('dijit.layout.ContentPane');");
        out.println("  dojo.require('dijit.form.CheckBox');");
        out.println("  dojo.require('dijit.form.Button');");
        out.println("</script>");

        if (semObj != null) {
            BSC bsc = (BSC) semObj.getModel().getModelObject().getGenericInstance();
            SWBResourceURL urlAdd;

            Iterator<Period> itPeriods;
            GenericObject genericObject = semObj.getGenericInstance();
            
//                SemanticObject objParent = semObj.getObjectProperty(Indicator.bsc_objectiveInv);
//                itPeriods = new GenericIterator<Period>(objParent.listObjectProperties(Seasonable.bsc_hasPeriod));            
            
            if (genericObject instanceof Indicator) {
                Indicator indicator = (Indicator)genericObject;
                itPeriods = indicator.getObjective().listPeriods(true);
            }else if(genericObject instanceof Objective || genericObject instanceof Initiative) {
                itPeriods = bsc.listPeriods(true);
            }else {
                itPeriods = new GenericIterator<Period>(semObj.listObjectProperties(Seasonable.bsc_hasPeriod));
            }
            
            boolean hasPeriods = itPeriods.hasNext();
            final String data = semObj.getSemanticClass().getName() + semObj.getId();

            //Colocar encabezado del listado con las columnas del mismo
            out.println("<div class=\"swbform\">"); //class=\"swbform\"
            out.println(" <fieldset>");
            out.println("  <table width=\"98%\">"); 
            out.println("   <thead>");
            out.println("    <tr>");
            out.println("     <th>" + paramRequest.getLocaleString("lbl_index") + "</th>");
            out.println("     <th>" + paramRequest.getLocaleString("lbl_period") + "</th>");
            out.println("     <th>" + paramRequest.getLocaleString("lbl_fromDate") + "</th>");            
            out.println("     <th>" + paramRequest.getLocaleString("lbl_toDate") + "</th>");            
            out.println("     <th>" + paramRequest.getLocaleString("lbl_former") + "</th>");            
            out.println("     <th>" + paramRequest.getLocaleString("lbl_next") + "</th>");            
            out.println("     <th>" + paramRequest.getLocaleString("lbl_active") + "</th>");            
            out.println("     <th>" + paramRequest.getLocaleString("lbl_relate") + "</th>");            
            out.println("    </tr>");
            out.println("   </thead>");
            out.println("   <tbody>");

            Seasonable seasonable = (Seasonable) semObj.getGenericInstance();
            
            while (itPeriods.hasNext())
            {
                Period period = itPeriods.next();
                if (  (period.isValid() && user.haveAccess(period)) || (!period.isActive() && seasonable.hasPeriod(period) && user.haveAccess(period))  )
                {
                    urlAdd = paramRequest.getActionUrl();
                    urlAdd.setParameter("suri", suri);
                    urlAdd.setParameter("sval", period.getId());
                    urlAdd.setAction(Action_UPDT_ACTIVE);
                    
                    String title = period.getTitle(user.getLanguage()) == null
                                   ? period.getTitle()
                                   : period.getTitle(user.getLanguage());
                    String titleFormer = null;
                    String titleNext = null;
                    boolean hasFormer = false;
                    boolean hasNext = false;

                    if (period.getPrevius() != null && period.getPrevius() instanceof Period) {
                        Period former = (Period) period.getPrevius();
                        titleFormer = former.getTitle(user.getLanguage()) == null 
                                      ? former.getTitle() : former.getTitle(user.getLanguage());
                        hasFormer = true;
                    }else {
                        titleFormer = paramRequest.getLocaleString("lbl_notAssigned");
                    }
                    if (period.getNext() != null && period.getNext() instanceof Period) {
                        Period next = (Period) period.getNext();
                        titleNext = next.getTitle(user.getLanguage()) == null
                                    ? next.getTitle() : next.getTitle(user.getLanguage());
                        hasNext = true;
                    } else {
                        titleFormer = paramRequest.getLocaleString("lbl_notAssigned");
                    }

                    //mostrar las columnas del listado con sus valores
                    out.println("    <tr>");
                    out.append("      <td>"+period.getOrden()+"</td>");
                    out.println("     <td>");
                    out.print("<a href=\"#\" onclick=\"addNewTab('" + period.getURI() + "','");
                    out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + title);
                    out.println("');return false;\" >" + title + "</a>");
                    out.println("     </td>");
                    out.println("     <td>" + period.getStart() + "</td>");            
                    out.println("     <td>" + period.getEnd() + "</td>");            
                    out.println("     <td>");
                    if (hasFormer) {
                        out.print("<a href=\"#\" onclick=\"addNewTab('" + period.getPrevius().getURI() + "','");
                        out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + 
                                (titleFormer != null ? titleFormer : paramRequest.getLocaleString("lbl_noTitle")));
                        out.println("');return false;\" >" +
                                (titleFormer != null ? titleFormer : paramRequest.getLocaleString("lbl_noTitle")) +
                                "</a>");
                    }
                    out.println("     </td>");
                    out.println("     <td>");
                    if (hasNext) {
                        out.print("<a href=\"#\" onclick=\"addNewTab('" + period.getNext().getURI() + "','");
                        out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + 
                                (titleNext != null ? titleNext : paramRequest.getLocaleString("lbl_noTitle")));
                        out.println("');return false;\" >" +
                                    (titleNext != null ? titleNext : paramRequest.getLocaleString("lbl_noTitle")) +
                                    "</a>");
                    }
                    out.println("     </td>");

                    out.println("     <td align=\"center\">" + 
                            (period.isActive()
                             ? paramRequest.getLocaleString("lbl_isActive")
                             : paramRequest.getLocaleString("lbl_isNotActive")) +
                            "</td>");

                    out.println("     <td align=\"center\"><input name=\"period\""
                            + " type=\"checkbox\" value=\"" + period.getId() + "\" "
                            + " onchange=\"submitUrl('" + urlAdd + "',this.domNode)\" "
                            + " dojoType=\"dijit.form.CheckBox\" " + (seasonable.hasPeriod(period)?"checked=\"checked\"":"") + "/></td>");
                    out.println("     </tr>");

                }
            }
            out.println("   </tbody>");
            out.println("  </table>");
            out.println(" </fieldset>");

            if(hasPeriods)
            {
                SWBResourceURL urlAll = paramRequest.getActionUrl();
                urlAll.setParameter("suri", suri);
                urlAll.setAction(Action_ACTIVE_ALL);
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lbl_markAll") + "</button>");

                urlAll.setAction(Action_DEACTIVE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lbl_unMarkAll") + "</button>");
                out.println("</fieldset>");
            }
            out.println("</div>");
                
            if (request.getParameter("statmsg") != null && !request.getParameter("statmsg").isEmpty()) {
                out.println("<div dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("showStatus('" + request.getParameter("statmsg") + "');\n");
                out.println("</script>\n");
                out.println("</div>");
            }
        }
    }
    
    /**
     * M&eacute;todo que se encarga de persistir la informaci&oacute;n de forma
     * segura en un objetivo
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException Excepti&oacute;n utilizada para recursos de
     * SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        
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
        BSC model = (BSC)semObj.getModel().getModelObject().getGenericInstance();
        
        if(Action_UPDT_ACTIVE.equalsIgnoreCase(action))
        {
            final String periodId = request.getParameter("sval");
            if(periodId!=null)
            {
                if(Period.ClassMgr.hasPeriod(periodId, model)) {
                    Seasonable seasonable = (Seasonable) semObj.getGenericInstance();                    
                    Period period = Period.ClassMgr.getPeriod(periodId, model);
                    if(seasonable.hasPeriod(period)) {
                        seasonable.removePeriod(period);
                        response.setRenderParameter("statmsg", response.getLocaleString("msgDeallocatedPeriod"));
                    }else {
                        seasonable.addPeriod(period);
                        response.setRenderParameter("statmsg", response.getLocaleString("msgAssignedPeriod"));
                    }
                }else {
                    response.setRenderParameter("statmsg", "Objeto semantico no ubicado");
                }
            }
            else
            {
                response.setRenderParameter("statmsg", "Objeto semantico no ubicado.");
            }
        }
        else if(Action_ACTIVE_ALL.equalsIgnoreCase(action))
        {
            Iterator<Period> itPeriods = null;
            GenericObject genericObject = semObj.getGenericInstance();
            if (genericObject instanceof Indicator) {
                Indicator indicator = (Indicator)genericObject;
                itPeriods = indicator.getObjective().listValidPeriods().iterator();
            }else if(genericObject instanceof Objective || genericObject instanceof Initiative) {
                itPeriods = model.listValidPeriods().iterator();
            }
            if(itPeriods!=null) {
                Seasonable seasonable = (Seasonable) semObj.getGenericInstance();
                seasonable.removeAllPeriod();
                while(itPeriods.hasNext()) {
                    seasonable.addPeriod(itPeriods.next());
                }
            }
        }
        else if(Action_DEACTIVE_ALL.equalsIgnoreCase(action))
        {
            Seasonable seasonable = (Seasonable) semObj.getGenericInstance();
            seasonable.removeAllPeriod();
        }
    }

    /**
     * M&eacute;todo que se encarga de eliminar los periodos asociados a un
     * objeto Sem&aacute;ntico
     *
     * @param semObj
     */
    private void removePeriodsInSemObj(SemanticObject semObj) {
        Iterator<SemanticObject> itPeriods = (Iterator<SemanticObject>) semObj.listObjectProperties(Seasonable.bsc_hasPeriod);

        if (itPeriods != null && itPeriods.hasNext()) {
            while (itPeriods.hasNext()) {
                SemanticObject period = itPeriods.next();

                semObj.removeObjectProperty(Seasonable.bsc_hasPeriod, period);
                Iterator<SemanticObject> itObjs = period.listRelatedObjects();
                if(itObjs != null && itObjs.hasNext()){
                    period.setBooleanProperty(Undeleteable.swb_undeleteable, false);
                }
            }
        }
    }
}
