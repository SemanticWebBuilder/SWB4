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
import org.semanticwb.bsc.PDFExportable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;

/**
 *
 * @author martha.jimenez
 */
public class ResourceExportPDFTest {

    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "Actualizador";
    final private String viewGral = "org.semanticwb.bsc.element.Indicator";

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
    public void getResourceStrategicMap() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        String nameClass = PDFExportable.PDF_StrategyMap;
        Iterator<Resource> itp = ws.listResources();
        Resource base2 = null;
        while (itp.hasNext()) {
            Resource resource = itp.next();
            String itemType = resource.getData(PDFExportable.bsc_itemType);
            if (itemType != null && itemType.equals(nameClass)) {
                if (resource.isActive()) {
                    base2 = resource;
                    break;
                }
            }
        }
        if (base2 != null) {
            System.out.println("Vista Mapa Estrátegico - Recurso: " + base2.getId());
        } else {
            System.out.println("Vista Mapa Estrátegico - No existe recurso para esta vista");
        }
    }

    @Test
    public void getSummaryView() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        String nameClass = viewGral;
        Iterator<Resource> itp = ws.listResources();
        Resource base2 = null;
        while (itp.hasNext()) {
            Resource resource = itp.next();
            String itemType = resource.getData(PDFExportable.bsc_itemType);
            String view = resource.getData(PDFExportable.viewType);
            if ((view != null && view.equals(PDFExportable.VIEW_Detail))
                    && (itemType != null && itemType.equals(nameClass))) {
                if (resource.isActive()) {
                    base2 = resource;
                    break;
                }
            }
        }
        if (base2 != null) {
            System.out.println("Vista Resumen Indicador - Recurso: " + base2.getId());
        } else {
            System.out.println("Vista Resumen Indicador - No existe recurso para esta vista");
        }
    }
    @Test
    public void getDetailView() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        String nameClass = viewGral;
        Iterator<Resource> itp = ws.listResources();
        Resource base2 = null;
        while (itp.hasNext()) {
            Resource resource = itp.next();
            String itemType = resource.getData(PDFExportable.bsc_itemType);
            String view = resource.getData(PDFExportable.viewType);
            if ((view != null && view.equals(PDFExportable.VIEW_Summary))
                    && (itemType != null && itemType.equals(nameClass))) {
                if (resource.isActive()) {
                    base2 = resource;
                    break;
                }
            }
        }
        if (base2 != null) {
            System.out.println("Vista Detalle Indicador - Recurso: " + base2.getId());
        } else {
            System.out.println("Vista Detalle Indicador - No existe recurso para esta vista");
        }
    }
}
