/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.formElements;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.formelement.DeliverableElement;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author martha.jimenez
 */
public class DeliverableElementTest {

    private SWBRequest request = new SWBRequest();
    final private String modelId = "InfotecPEMP2";
    final private String initiativeId = "3";
    final private String userGroupId = "Actualizador";

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
    public void toRenderViewTest() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            setUpSessionUser(model);
            Initiative initiative = Initiative.ClassMgr.getInitiative(initiativeId, model);
            if (initiative != null) {
                SWBFormMgr formMgr = new SWBFormMgr(initiative.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                org.semanticwb.platform.SemanticProperty bsc_hasDeliverable = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDeliverable");
                Statement st = bsc_hasDeliverable.getRDFProperty().getProperty(
                        SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                        "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
                if (st != null) {
                    //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
                    SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
                    if (soDisplayElement != null) {
                        SemanticObject formElement = soDisplayElement.getObjectProperty(
                                org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(
                                "http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                        if (formElement != null) {
                            FormElement fe = (FormElement) formElement.createGenericInstance();
                            DeliverableElement feDeliverable = (DeliverableElement) fe;
                            System.out.println(feDeliverable.renderLabel(request, initiative.getSemanticObject(),
                                    bsc_hasDeliverable, "render", "inlineEdit", "es"));
                            request.addParameter("suri", initiative.getEncodedURI());
                            request.addParameter("usrWithGrants", "true");
                            System.out.println(feDeliverable.renderElementView(request, initiative.getSemanticObject(),
                                    bsc_hasDeliverable, bsc_hasDeliverable.getName(), "render", "inlineEdit", "es"));
                        }
                    }
                }
            }
        }

    }
}
