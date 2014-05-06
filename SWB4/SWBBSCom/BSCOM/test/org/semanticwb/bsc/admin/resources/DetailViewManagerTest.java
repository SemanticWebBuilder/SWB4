/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources;

import com.arthurdo.parser.HtmlException;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author martha.jimenez
 */
public class DetailViewManagerTest {

    final private String userGroupId = "Actualizador";
    final private String filePath = "C:/Desarrollos5/SWB4/SWBBSCom/build/web/work/models/InfotecPEMP2/Template/6/2/template.html";
    final private String baserequest = "http://localhost:8080/";
    final private String links = "<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"http://localhost:8080//swbadmin/js/dojo/dijit/themes/soria/soria.css\" /><link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"http://localhost:8080//swbadmin/css/swb_portal.css\" /><link href=\"http://localhost:8080//swbadmin/css/estilo-strategy.css\" rel=\"stylesheet\" type=\"text/css\" />";
    final private String filePathTemplate = "C:/Desarrollos5/SWB4/SWBBSCom/build/web/work/models/InfotecPEMP2/bsc_DetailView/6/templateContent.html";
    final private String idDetailView = "9";
    final private String suri = "http%3A%2F%2Fwww.InfotecPEMP2.swb%23bsc_Objective%3A12";
    final private String modelName = "InfotecPEMP2";
    final private String periodId = "24";
    private SWBRequest request = new SWBRequest();

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
        //request = new SWBRequest();
    }

    @Test
    public void getLinks() throws IOException {
        FileReader reader = null;
        StringBuilder view = new StringBuilder(256);
        reader = new FileReader(filePath);


        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(reader);
        HtmlTag tag = new HtmlTag();
        while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
            int ttype = tok.getTokenType();

            if (ttype == HtmlStreamTokenizer.TT_TAG) {
                try {
                    tok.parseTag(tok.getStringValue(), tag);
                } catch (HtmlException htmle) {
                    Assert.fail("Al parsear la plantilla , " + filePath + htmle);
                }
                if (tag.getTagString().toLowerCase().equals("link")) {
                    String tagTxt = tag.toString();
                    if (tagTxt.contains("type=\"text/css\"")) {
                        if (!tagTxt.contains("/>")) {
                            tagTxt = SWBUtils.TEXT.replaceAll(tagTxt, ">", "/>");
                        }
                        if (!tagTxt.contains("{webpath}")) {
                            String tmpTxt = tagTxt.substring(0, (tagTxt.indexOf("href") + 6));
                            String tmpTxtAux = tagTxt.substring((tagTxt.indexOf("href") + 6),
                                    tagTxt.length());
                            tagTxt = tmpTxt + baserequest + tmpTxtAux;
                        } else {
                            tagTxt = SWBUtils.TEXT.replaceAll(tagTxt, "{webpath}", baserequest);
                        }
                        view.append(tagTxt);
                    }
                }
            }
        }
        System.out.println("view: " + view.toString());
    }

    @Test
    public void getHtml() throws IOException {
        StringBuilder output = new StringBuilder(256);
        output.append("<html>");
        output.append("<head>");
        output.append(links);
        output.append("</head>");
        output.append("<body>");
        output.append("<div id=\"detalle\" class=\"detalleObjetivo\">\n");


        FileReader reader = null;
        try {
            reader = new FileReader(filePathTemplate);
        } catch (FileNotFoundException fnfe) {
            Assert.fail("Al leer plantilla de vista detalle con Id: " + idDetailView);
        }
        SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
        WebSite ws = WebSite.ClassMgr.getWebSite(modelName);
        //Declarar variable para el per&iacte;odo, obteniendo el valor del request

        Period period = Period.ClassMgr.getPeriod(periodId, ws);
        PeriodStatus periodStatus = null;


        //Si el semObj es hijo de PeriodStatusAssignable se debe:
        GenericObject generic = semObj.createGenericInstance();
        if (generic != null && generic instanceof Objective) {
            Objective objective = (Objective) generic;
            periodStatus = objective.getPeriodStatus(period);
        } else if (generic != null && generic instanceof Indicator) {
            Indicator indicator = (Indicator) generic;
            Measure measure = indicator != null && indicator.getStar() != null
                    ? indicator.getStar().getMeasure(period) : null;
            if (measure != null && measure.getEvaluation() != null) {
                periodStatus = measure.getEvaluation();
            }
        }

        //-Agrega encabezado al cuerpo de la vista detalle, en el que se muestre el estado del objeto
        // para el per&iacte;odo especificado y el t&iacte;tulo del objeto, para lo que:
        //    - Se pide el listado de objetos PeriodStatus asociado al semObj
        //    - Se recorre uno por uno los PeriodStatus relacionados
        //    - Cuando el per&iacte;odo del PeriodStatus = per&iacte;odo del request:
        //        - Se obtiene el status correspondiente y su &iacte;cono relacionado
        //        - Se agrega el &iacte;cono al encabezado y el t&iacte;tulo del objeto semObj
        output.append("<h2");
        output.append(" class=\"");
        if (periodStatus != null && periodStatus.getStatus() != null
                && periodStatus.getStatus().getIconClass() != null) {
            output.append(periodStatus.getStatus().getIconClass());
        } else {
            output.append("indefinido");
        }
        output.append("\"");
        output.append(">");
        output.append(semObj.getDisplayName());
        output.append("</h2>\n");

        if (reader != null) {
            output.append(generateDisplayPDF(request, reader, semObj));
        } else {
            Assert.fail("Elemento no cargado..");
        }
        System.out.println(output.toString());

    }

    private String generateDisplayPDF(HttpServletRequest request,
            FileReader template, SemanticObject elementBSC) throws IOException {
        StringBuilder view = new StringBuilder(512);
        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(template);
        HtmlTag tag = new HtmlTag();
        String lang = "es";
//        SWBResourceURL url = null;
//        url.setAction("updateProp");
//        url.setParameter("suri", request.getParameter("suri"));
//        request.setAttribute("urlRequest", url.toString());

        while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
            int ttype = tok.getTokenType();
            if (ttype == HtmlStreamTokenizer.TT_TAG) {
                try {
                    tok.parseTag(tok.getStringValue(), tag);
                } catch (HtmlException htmle) {
                    Assert.fail("Al leer plantilla de vista detalle con Id: " + idDetailView);
                    view = new StringBuilder(16);
                }
                if (!tag.getTagString().toLowerCase().equals("img")) {
                    view.append(tag.toString());
                } else if (tag.getTagString().toLowerCase().equals("img") && !tag.hasParam("tagProp")) {
                    view.append(tag.toString());
                } else if (tag.getTagString().toLowerCase().equals("img") && tag.hasParam("tagProp")) {
                    String propUri = tag.getParam("tagProp");
                    if (propUri != null) {
                        view.append(renderPropertyValuePDF(request, elementBSC, propUri, lang));
                    }
                }
            } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                view.append(tok.getStringValue());
            }
            view.append("\n");
        }
        return (view.toString());
    }

    private String renderPropertyValuePDF(HttpServletRequest request, SemanticObject elementBSC,
            String propUri, String lang) {
        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);

        //Codigo para obtener el displayElement
        Statement st = semProp.getRDFProperty().getProperty(
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
                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        ret = fe.renderElement(request, elementBSC, semProp, semProp.getName(),
                                SWBFormMgr.TYPE_XHTML,
                                SWBFormMgr.MODE_VIEW,
                                lang);
                    }
                }
            }
        }
        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(semProp);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, semProp, semProp.getName(),
                        SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }
        return ret != null ? ret : "";
    }
}
