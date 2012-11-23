package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AdminAlert;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.servlet.internal.Monitor;

/**
 *
 * @author serch
 */
public class SWBASetAlert extends GenericResource {
    private Logger log = SWBUtils.getLogger(SWBASetAlert.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        AdminAlert aa = AdminAlert.ClassMgr.getAdminAlert("1", SWBContext.getAdminWebSite());
        if (null==aa){
            aa = AdminAlert.ClassMgr.createAdminAlert("1", SWBContext.getAdminWebSite());
            aa.setAlertSistemStatus(false);
            aa.setAlertMailList("webbuilder@infotec.com.mx");
            aa.setAlertCPUTH(85.0f);
            aa.setAlertTimeTH(250);
            aa.setAlertPPSTH(50);
        }
        PrintWriter out=response.getWriter();
        SWBFormMgr fm = new SWBFormMgr(aa.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        fm.setSubmitByAjax(true);
        fm.setFilterHTMLTags(false);
        fm.addButton(SWBFormButton.newSaveButton());
        fm.setType(SWBFormMgr.TYPE_DOJO);
        if("update".equals(paramRequest.getAction()))
        {
            try
            {
                fm.processForm(request);
                Monitor.setAlertParameter(aa);
            }catch(FormValidateException e){log.error(e);}
            response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
        }else
        {
            fm.setAction(paramRequest.getRenderUrl().setAction("update").toString());
            out.print(fm.renderForm(request));
        }
    }

}
