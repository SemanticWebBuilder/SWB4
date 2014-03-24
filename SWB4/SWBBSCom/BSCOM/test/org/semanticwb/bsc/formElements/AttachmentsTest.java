/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.formElements;

import java.util.Enumeration;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.catalogs.Attachment;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.formelement.AttachmentElement;
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
public class AttachmentsTest {

    final private String modelId = "InfotecPEMP2";
    final private String indicatorId = "58";
    final private String userGroupId = "Actualizador";
    final private String attachmentId = "2759";
    private SWBRequest request = new SWBRequest();
    private static final String Action_ADD = "aAdd";
    private static final String Action_EDIT = "aEdit";
    //final private String periodId = "16";

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

    // @Test
    public void toRenderViewTest() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);

            Indicator indicator = Indicator.ClassMgr.getIndicator(indicatorId, model);
            if (indicator != null) {
                SWBFormMgr formMgr = new SWBFormMgr(indicator.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Indicator.bsc_hasAttachments);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    FormElement formElement = formMgr.getFormElement(prop1);
                    AttachmentElement feAttachment = (AttachmentElement) formElement;
                    System.out.println(feAttachment.renderLabel(request, indicator.getSemanticObject(),
                            prop1, "render", "inlineEdit", "es"));
                    request.addParameter("suri", indicator.getEncodedURI());
                    request.addParameter("usrWithGrants", "true");
                    System.out.println(feAttachment.renderElementView(request, indicator.getSemanticObject(),
                            prop1, prop1.getName(), "render", "inlineEdit", "es"));

                }

            }
        }

    }

    //@Test
    public void toAddAttachment() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);

            Indicator indicator = Indicator.ClassMgr.getIndicator(indicatorId, model);
            if (indicator != null) {
                SWBFormMgr formMgr = new SWBFormMgr(indicator.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Indicator.bsc_hasAttachments);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    System.out.println(formMgr.renderLabel(request, prop1, prop1.getName(),
                            SWBFormMgr.MODE_EDIT));
                    System.out.println("prop1.getName(): " + prop1.getName());
                    FormElement formElement = formMgr.getFormElement(prop1);
                    AttachmentElement feAttachment = (AttachmentElement) formElement;
                    request.addParameter("suri", indicator.getEncodedURI());
                    request.addParameter("usrWithGrants", "true");
                    System.out.println(feAttachment.renderElementAdd(request, indicator.getSemanticObject(),
                            prop1, prop1.getName(), "render", "inlineEdit", "es"));

                }

            }
        }

    }

    /*//@Test
    public void toSaveNewAttachment() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);

            Indicator indicator = Indicator.ClassMgr.getIndicator(indicatorId, model);
            if (indicator != null) {
                SWBFormMgr formMgr = new SWBFormMgr(indicator.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Indicator.bsc_hasAttachments);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    System.out.println(formMgr.renderLabel(request, prop1, prop1.getName(),
                            SWBFormMgr.MODE_EDIT));
                    System.out.println("prop1.getName(): " + prop1.getName());
                    FormElement formElement = formMgr.getFormElement(prop1);
                    AttachmentElement feAttachment = (AttachmentElement) formElement;
                    //request.addParameter("suri", indicator.getEncodedURI());
                    //request.addParameter("usrWithGrants", "true");
                    request.addParameter("_action", Action_ADD);
                    request.addParameter(Attachment.swb_title.getName(), "Prueba 16.03.2014");
                    request.addParameter(Attachment.swb_description.getName(), "Descripcion de prueba 16.03.2014");
                    //request.addParameter(Attachment.bsc_file.getName() + "_new", "fu631707853");
                    request.addParameter("sref", "http://www.InfotecPEMP2.swb#bsc_Indicator:58");
                    request.addParameter("scls", "http://www.semanticwebbuilder.org/swb4/bsc#Attachment");
                    request.addParameter("smode", "create");
                    //request.addParameter("_swb_urltp", "process");
                    //request.addParameter("_swb_prop", "http://www.semanticwebbuilder.org/swb4/bsc#hasAttachments");
                    //request.addParameter("_swb_obj", "http://www.InfotecPEMP2.swb#bsc_Indicator:58");
                    //request.addParameter("_swb_frmele", "http://www.semanticwebbuilder.org/swb4/bsc#AttachmentElement_1");
                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        Object object = e.nextElement();
                        System.out.println("El: " + object + ", " + request.getParameter(object.toString()));
                    }
                    feAttachment.process(request,
                            indicator.getSemanticObject(), prop1);
                    System.out.println("Creación exitosa!!");

                }

            }
        }

    }*/

    /*@Test
    public void toSaveEditAttachment() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            Attachment att = Attachment.ClassMgr.getAttachment(attachmentId, model);

            Indicator indicator = Indicator.ClassMgr.getIndicator(indicatorId, model);
            if (indicator != null && att != null) {
                SWBFormMgr formMgr = new SWBFormMgr(indicator.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Indicator.bsc_hasAttachments);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    System.out.println(formMgr.renderLabel(request, prop1, prop1.getName(),
                            SWBFormMgr.MODE_EDIT));
                    FormElement formElement = formMgr.getFormElement(prop1);
                    AttachmentElement feAttachment = (AttachmentElement) formElement;
                    request.addParameter("suri", att.getURI());
                    System.out.println("suri: " + att.getURI());
                    //request.addParameter("usrWithGrants", "true");
                    request.addParameter("_action", Action_EDIT);
                    request.addParameter(Attachment.swb_title.getName(), "Prueba 16.03.2014");
                    request.addParameter(Attachment.swb_description.getName(), "Descripcion de prueba 16.03.2014");
//                    request.addParameter(Attachment.bsc_file.getName() + "_" + attachmentId, "fu631707853");
                    //request.addParameter("sref", "http://www.InfotecPEMP2.swb#bsc_Indicator:58");
                    request.addParameter("scls", "http://www.semanticwebbuilder.org/swb4/bsc#Attachment");
                    request.addParameter("smode", "edit");
                    //request.addParameter("_swb_urltp", "process");
                    //request.addParameter("_swb_prop", "http://www.semanticwebbuilder.org/swb4/bsc#hasAttachments");
                    //request.addParameter("_swb_obj", "http://www.InfotecPEMP2.swb#bsc_Indicator:58");
                    //request.addParameter("_swb_frmele", "http://www.semanticwebbuilder.org/swb4/bsc#AttachmentElement_1");
                    Enumeration e = request.getParameterNames();
//                    while (e.hasMoreElements()) {
//                        Object object = e.nextElement();
//                        System.out.println("El: " + object + ", " + request.getParameter(object.toString()));
//                    }
                    System.out.println("bd, algo raro...");
                    att.setTitle("cambiado...");
//                    feAttachment.process(request,
//                            indicator.getSemanticObject(), prop1);
                    System.out.println("Edición exitosa!!");

                }

            }
        }
    }*/

    /*//@Test
    public void toEditAttachment() {
        if (BSC.ClassMgr.hasBSC(modelId)) {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            Attachment att = Attachment.ClassMgr.getAttachment(attachmentId, model);
            Indicator indicator = Indicator.ClassMgr.getIndicator(indicatorId, model);
            if (indicator != null && att != null) {
                SWBFormMgr formMgr = new SWBFormMgr(indicator.getSemanticObject(), null,
                        SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                formMgr.addProperty(Indicator.bsc_hasAttachments);
                Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                        getProperties().iterator());
                while (it.hasNext()) {
                    SemanticProperty prop1 = it.next();
                    FormElement formElement = formMgr.getFormElement(prop1);
                    AttachmentElement feAttachment = (AttachmentElement) formElement;
                    request.addParameter("suri", indicator.getEncodedURI());
                    request.addParameter("usrWithGrants", "true");
                    request.addParameter("suriAttach", att.getEncodedURI());
                    System.out.println(feAttachment.renderElementEdit(request, indicator.getSemanticObject(),
                            prop1, prop1.getName(), "render", "inlineEdit", "es"));

                }

            }
        }

    }

    @Test
    public void toSaveRemoveAttachment() {
    }*/
}
