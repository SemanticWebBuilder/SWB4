/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.formElements;

import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.tracing.Risk;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author martha.jimenez
 */
public class ElementBSCRelatedTest {

    private SWBRequest request = new SWBRequest();
    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "Actualizador";
    final private String riskId = "11";

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
            User user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
        //request = new SWBRequest();
    }

    @Test
    public void toRenderTest() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);

            Risk risk = Risk.ClassMgr.getRisk(riskId, model);
            if (risk != null) {
                SWBFormMgr formMgr = new SWBFormMgr(risk.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Risk.bsc_elementRelated);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    FormElement formElement = formMgr.getFormElement(prop1);
                    //ElementBSCRelated feElementRelated = (ElementBSCRelatedTest) formElement;
                    System.out.println(formElement.renderLabel(request, risk.getSemanticObject(),
                            prop1, "render", "edit", "es"));
                    request.addParameter("suri", risk.getEncodedURI());
                    System.out.println(formElement.renderElement(request, risk.getSemanticObject(),
                            prop1, prop1.getName(), "render", "edit", "es"));

                }

            }
        }

    }
}
