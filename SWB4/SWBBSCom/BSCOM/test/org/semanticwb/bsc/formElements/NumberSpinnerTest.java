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
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.formelement.AttachmentElement;
import org.semanticwb.bsc.formelement.NumberSpinner;
import org.semanticwb.bsc.tracing.MitigationAction;
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
public class NumberSpinnerTest {

    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "su";
    final private String mitActionId = "16";
    private SWBRequest request = new SWBRequest();    
    private User user;

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
    }

    @Test
    public void viewRender() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);

           MitigationAction mitAct = MitigationAction.ClassMgr.getMitigationAction(mitActionId, model);
            if (mitAct != null) {
                SWBFormMgr formMgr = new SWBFormMgr(mitAct.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(MitigationAction.bsc_progressPercentage);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    FormElement formElement = formMgr.getFormElement(prop1);
                    NumberSpinner feNSppiner = (NumberSpinner) formElement;
                    System.out.println(feNSppiner.renderLabel(request, mitAct.getSemanticObject(),
                            prop1, "render", "edit", "es"));
                    System.out.println(feNSppiner.renderElement(request, mitAct.getSemanticObject(),
                            prop1, prop1.getName(), "render", "edit", "es"));

                }

            }
        }

    }
}
