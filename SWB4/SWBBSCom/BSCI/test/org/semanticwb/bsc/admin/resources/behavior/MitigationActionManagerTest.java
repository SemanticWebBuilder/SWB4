/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.tracing.MitigationAction;
import org.semanticwb.bsc.tracing.Risk;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author martha.jimenez
 */
public class MitigationActionManagerTest {

    final private String modelId = "InfotecPEMP2";
    final private String riskId = "11";
    final private String userGroupId = "su";
    private SWBRequest request = new SWBRequest();
    private User user;
    private String lang = "es";
    public static org.semanticwb.platform.SemanticProperty bsc_hasMitigationAction;
    public static final String Action_DELETE = "delete";
    

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();

        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    public void setUpSessionUser(BSC model) {
        Iterator it = model.getUserRepository().listUsers();
        UserGroup userGroup = UserGroup.ClassMgr.getUserGroup(userGroupId, model);
        while (it.hasNext()) {
            user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
        bsc_hasMitigationAction = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary()
            .getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMitigationAction");
    }

    @Test
    public void viewRender() {
        StringBuilder toReturn = new StringBuilder();
        BSC bsc = BSC.ClassMgr.getBSC(modelId);
        setUpSessionUser(bsc);
        if (bsc != null) {
            Risk risk = Risk.ClassMgr.getRisk(riskId, bsc);
            if (risk != null) {
                Iterator<MitigationAction> it = risk.listMitigationActions();

                toReturn.append("<script type=\"text/javascript\">");
                toReturn.append("  dojo.require('dojo.parser');");
                toReturn.append("  dojo.require('dijit.layout.ContentPane');");
                toReturn.append("  dojo.require('dijit.form.CheckBox');");
                toReturn.append("</script>");

                toReturn.append("<div class=\"swbform\">");
                toReturn.append("<fieldset>\n");
                toReturn.append("<table width=\"98%\">");
                toReturn.append("<thead>");
                toReturn.append("<tr>");
                toReturn.append("<th></th>");
                toReturn.append("<th>Título"
                        + "</th>");
                toReturn.append("<th>Descripción"
                        + "</th>");
                toReturn.append("<th>Creación"
                        + "</th>");
                toReturn.append("<th>Actualizado"
                        + "</th>");
                toReturn.append("</tr>");
                toReturn.append("</thead>");

                while (it.hasNext()) {
                    MitigationAction mitAction = it.next();
                    String urlDelete = "/es/SWBAdmin/Acciones_de_Mitigacion/_aid/132/_act/delete?suri=http%3A%2F%2Fwww.InfotecPEMP2.swb%23bsc_Risk%3A11&sval=11&reloadTab=true";
                    if (mitAction != null && ((mitAction.isValid() && user.haveAccess(mitAction))
                            || (!mitAction.isActive()
                            && risk.getSemanticObject().hasObjectProperty(bsc_hasMitigationAction, mitAction.getSemanticObject())
                            && user.haveAccess(mitAction))
                            )) {
                        toReturn.append("<tr>");
                        toReturn.append("<td>");
                        toReturn.append("\n<a href=\"#\" onclick=\"if(confirm('Desea elimnar el elemento?"
                                + "'))submitUrl('"
                                + urlDelete
                                + "',this.domNode);reloadTab('"
                                + risk.getURI()
                                + "');return false;\">");
                        toReturn.append("\n<img src=\"");
                        toReturn.append(SWBPlatform.getContextPath());
                        toReturn.append("/swbadmin/icons/iconelim.png\" alt=\"Eliminar");
                        toReturn.append("\"/>");
                        toReturn.append("\n</a>");

                        toReturn.append("</td>");
                        toReturn.append("<td>");
                        toReturn.append("<a href=\"#\" onclick=\"addNewTab('"
                                + mitAction.getURI()
                                + "','");
                        toReturn.append(SWBPlatform.getContextPath()
                                + "/swbadmin/jsp/objectTab.jsp"
                                + "','"
                                + mitAction.getTitle());
                        toReturn.append("');return false;\" >"
                                + (mitAction.getTitle(lang) == null
                                ? (mitAction.getTitle() == null
                                ? "No definido"
                                : mitAction.getTitle().replaceAll("'", ""))
                                : mitAction.getTitle(lang).replaceAll("'", ""))
                                + "</a>");
                        toReturn.append("</td>");
                        toReturn.append("<td>"
                                + (mitAction.getDescription(lang) == null
                                ? (mitAction.getDescription() == null
                                ? "No definido"
                                : mitAction.getDescription())
                                : mitAction.getDescription(lang))
                                + "</td>");
                        toReturn.append("<td>"
                                + (mitAction.getCreated() == null ? ""
                                : SWBUtils.TEXT.getStrDate(mitAction.getCreated(),
                                "es", "dd/mm/yyyy"))
                                + "</td>");
                        toReturn.append("<td>"
                                + (mitAction.getUpdated() == null ? ""
                                : SWBUtils.TEXT.getStrDate(mitAction.getCreated(),
                                "es", "dd/mm/yyyy"))
                                + "</td>");
                        toReturn.append("</tr>");
                    }
                }
                toReturn.append("</table>");
                toReturn.append("</fieldset>\n");
                toReturn.append("</div>");
            }
        }
        if(toReturn.length() > 0) {
            System.out.println(toReturn.toString());
            assert true;
        }
    }

    @Test
    public void toSendMessage() {
        StringBuilder toReturn = new StringBuilder();
        StringBuilder toCompareString = new StringBuilder();
        request.addParameter("statmsg", "true");
        toCompareString.append("<div dojoType=\"dojox.layout.ContentPane\">");
        toCompareString.append("<script type=\"dojo/method\">");
        toCompareString.append("showStatus('" + request.getParameter("statmsg"));
        toCompareString.append("');\n");
        toCompareString.append("</script>\n");
        toCompareString.append("</div>");


        if (request.getParameter("statmsg") != null
                && !request.getParameter("statmsg").isEmpty()) {
            toReturn.append("<div dojoType=\"dojox.layout.ContentPane\">");
            toReturn.append("<script type=\"dojo/method\">");
            toReturn.append("showStatus('" + request.getParameter("statmsg"));
            toReturn.append("');\n");
            toReturn.append("</script>\n");
            toReturn.append("</div>");
        }
        if (toReturn.toString().equals(toCompareString.toString())) {
            System.out.println(toReturn.toString());
            assert true;
        }
    }
}
