package org.semanticwb.bsc.element;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;


public class SummaryViewManager extends org.semanticwb.bsc.element.base.SummaryViewManagerBase {


    /** Realiza operaciones en la bitacora de eventos. */
    private static Logger log = SWBUtils.getLogger(GenericSemResource.class);

    public SummaryViewManager() {
    }

   /**
   * Constructs a SummaryViewManager with a SemanticObject
   * @param base The SemanticObject with the properties for the SummaryViewManager
   */
    public SummaryViewManager(org.semanticwb.platform.SemanticObject base) {

        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        out.println("Hello SummaryViewManager...");
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        if (this.getWorkClass() == null) {
            PrintWriter out=response.getWriter();

            SWBFormMgr mgr=new SWBFormMgr(getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            mgr.setSubmitByAjax(true);
            mgr.setFilterHTMLTags(false);
            mgr.addButton(SWBFormButton.newSaveButton());
            mgr.setType(SWBFormMgr.TYPE_DOJO);
            if ("update".equals(paramRequest.getAction())) {
                try {
                    mgr.processForm(request);
                } catch (FormValidateException e) {
                    log.error(e);
                }
                response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
            } else {
                mgr.setAction(paramRequest.getRenderUrl().setAction("update").toString());
                out.println(mgr.renderForm(request));
            }
        } else {
            doShowForm(request, response, paramRequest);
        }
    }

    public void doShowForm(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) {

        
    }
}
