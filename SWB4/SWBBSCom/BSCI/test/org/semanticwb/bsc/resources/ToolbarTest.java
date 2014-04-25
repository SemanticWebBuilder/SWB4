/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 *
 * @author martha.jimenez
 */
public class ToolbarTest {

    final private String modelId = "InfotecPEMP2";
    final private String wpId = "Pruebas_PDF2";
    final private String resIdExpScorecard = "32";
    final private String userGroupId = "Actualizador";
    final private String resIdUsrProfile = "28";

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTORE);
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
            User user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
    }

    @Test
    public void viewStratExportScorecard() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        WebPage wp = WebPage.ClassMgr.getWebPage(wpId, ws);
        String surl = wp.getUrl();
        Resource base = Resource.ClassMgr.getResource(resIdExpScorecard, ws);
        Iterator<Resourceable> res = base.listResourceables();
        while (res.hasNext()) {
            Resourceable re = res.next();
            if (re instanceof WebPage) {
                surl = ((WebPage) re).getUrl();
                break;
            }
        }
        String webWorkPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";
        String image = "iconelim.png";
        String alt = base.getAttribute("alt", "image");
        System.out.println("<a href=\"" + surl + "\" class=\"swb-toolbar-stgy\" title=\"image\">");
        System.out.println("<img src=\"" + webWorkPath + image + "\" alt=\"" + alt + "\" class=\"toolbar-img\" />");
        System.out.println("</a>");

    }

    @Test
    public void viewStratUserProfile() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        WebPage wp = WebPage.ClassMgr.getWebPage(wpId, ws);
        String surl = wp.getUrl();
        Resource base = Resource.ClassMgr.getResource(resIdUsrProfile, ws);
        Iterator<Resourceable> res = base.listResourceables();
        while (res.hasNext()) {
            Resourceable re = res.next();
            if (re instanceof WebPage) {
                surl = ((WebPage) re).getUrl();
                break;
            }
        }
        String webWorkPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";
        String image = "iconelim.png";
        String alt = base.getAttribute("alt", "image");
        System.out.println("<a href=\"" + surl + "\" class=\"swb-toolbar-stgy\" title=\"image\">");
        System.out.println("<img src=\"" + webWorkPath + image + "\" alt=\"" + alt + "\" class=\"toolbar-img\" />");
        System.out.println("</a>");

    }
}
