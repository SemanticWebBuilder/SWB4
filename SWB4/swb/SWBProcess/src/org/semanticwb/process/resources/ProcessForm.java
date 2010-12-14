/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.semanticwb.process.model.UserTask;

/**
 *
 * @author javier.solis
 */
public class ProcessForm extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(ProcessForm.class);
    private HashMap<String, SemanticObject> hmFormEle = null;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();

        User user = paramRequest.getUser();

        Resource base = getResourceBase();

        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println("Parámetro no difinido...");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

        //out.println("<a href=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setParameter("suri", suri) + "\">[editar]</a>");

        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);

        SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
        mgr.setAction(paramRequest.getActionUrl().setAction("process").toString());
        mgr.clearProperties();



        HashMap<String, SemanticClass> hmclass = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticProperty> hmprops = new HashMap<String, SemanticProperty>();
        Iterator<ProcessObject> it = foi.listHeraquicalProcessObjects().iterator();
        while (it.hasNext()) {
            ProcessObject obj = it.next();
            SemanticClass cls = obj.getSemanticObject().getSemanticClass();

            hmclass.put(cls.getClassId(), cls);

            Iterator<SemanticProperty> itp = cls.listProperties();
            while (itp.hasNext()) {
                SemanticProperty prop = itp.next();
                hmprops.put(prop.getPropId(), prop);
//                if (isViewProperty(paramRequest, cls, prop)) {
//                    mgr.addProperty(prop, cls, SWBFormMgr.MODE_VIEW);
//                } else if (isEditProperty(paramRequest, cls, prop)) {
//                    mgr.addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
//                }
            }
        }



        

        out.println("    <script type=\"text/javascript\">");
        // scan page for widgets and instantiate them
        out.println("dojo.require(\"dojo.parser\");");
        out.println("dojo.require(\"dijit._Calendar\");");
        out.println("dojo.require(\"dijit.ProgressBar\");");

        // editor:
        out.println("dojo.require(\"dijit.Editor\");");

        // various Form elemetns
        out.println("dojo.require(\"dijit.form.Form\");");
        out.println("dojo.require(\"dijit.form.CheckBox\");");
        out.println("dojo.require(\"dijit.form.Textarea\");");
        out.println("dojo.require(\"dijit.form.FilteringSelect\");");
        out.println("dojo.require(\"dijit.form.TextBox\");");
        out.println("dojo.require(\"dijit.form.DateTextBox\");");
        out.println("dojo.require(\"dijit.form.TimeTextBox\");");
        out.println("dojo.require(\"dijit.form.Button\");");
        out.println("dojo.require(\"dijit.form.NumberSpinner\");");
        out.println("dojo.require(\"dijit.form.Slider\");");
        out.println("dojo.require(\"dojox.form.BusyButton\");");
        out.println("dojo.require(\"dojox.form.TimeSpinner\");");
        out.println("</script>");

        SWBResourceURL urlact = paramRequest.getActionUrl();
        urlact.setAction("process");

        out.println("<form id=\""+foi.getId()+"/form\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""+urlact+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+suri+"\"/>");
        out.println("<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");
        out.println("<fieldset>");
        out.println("<legend>Datos Generales</legend>");
        out.println("<table>");


        int max = 1;
        while (!base.getAttribute("prop" + max, "").equals("")) {

            String val = base.getAttribute("prop" + max);
            String classid = "";
            String propid = "";
            String modo = "";
            String fe = "";
            StringTokenizer stoken = new StringTokenizer(val, "|");
            if (stoken.hasMoreTokens()) {
                classid = stoken.nextToken();
                propid = stoken.nextToken();
                modo = stoken.nextToken();
                fe = stoken.nextToken();
            }

            SemanticProperty semprop = hmprops.get(propid);

            //System.out.println("Class: "+classid);

            String strMode = "";

            SemanticClass semclass = hmclass.get(classid);

            if (semclass != null && semprop != null) {
                if (modo.equals("view")) {
                    mgr.addProperty(semprop, semclass, SWBFormMgr.MODE_VIEW);
                    strMode = SWBFormMgr.MODE_VIEW;
                } else if (modo.equals("edit")) {
                    mgr.addProperty(semprop, semclass, SWBFormMgr.MODE_EDIT);
                    strMode = SWBFormMgr.MODE_VIEW;
                }

                SemanticObject sofe = ont.getSemanticObject(fe);
                SWBFormMgr fmgr = new SWBFormMgr(foi.getSemanticObject(), SWBFormMgr.MODE_EDIT, SWBFormMgr.MODE_EDIT);
                FormElement frme = null;

                out.println("<tr><td width=\"200px\" align=\"right\"><label for=\"title\">"+fmgr.renderLabel(request, semprop, modo)+"</label></td>");
                out.println("<td>");
                if (null != sofe) {
                    System.out.println("Antes del FERender");
                    if (sofe.transformToSemanticClass() != null && sofe.transformToSemanticClass().isSWBFormElement()) {
                        frme = fmgr.getFormElement(semprop);
                        out.println(frme.renderElement(request, foi.getSemanticObject(), semprop, SWBFormMgr.TYPE_DOJO, strMode, user.getLanguage()));
                    }
                }
                out.println("</td></tr>");
            }
            max++;
        }

        /*



        <tr><td width="200px" align="right"><label for="description">Descripción &nbsp;</label></td><td><textarea name="description" dojoType_="dijit.Editor" style="width:300px;height:50px;" >aa</textarea> <a href="#" onClick="javascript:showDialog('/swbadmin/jsp/propLocaleTextAreaEdit.jsp?suri=http%3A%2F%2Fwww.process.swb%23swpt_Incidente%3A68&prop=http%3A%2F%2Fwww.semanticwebbuilder.org%2Fswb4%2Fontology%23description','Idiomas de la Propiedad Descripción');">locale</a></td></tr>
        <tr><td width="200px" align="right"><label for="created">Creación &nbsp;</label></td><td><span _id="created" name="created"></span></td></tr>
        <tr><td width="200px" align="right"><label for="updated">Última Act. &nbsp;</label></td><td><span _id="updated" name="updated"></span></td></tr>


         *
         */
        out.println("    </table>");
        out.println("</fieldset>");
        out.println("<fieldset><span align=\"center\">");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");

        out.println("<button dojoType=\"dijit.form.Button\" name=\"accept\" type=\"submit\">Concluir Tarea</button>");
        out.println("<button dojoType=\"dijit.form.Button\" name=\"reject\" type=\"submit\">Rechazar Tarea</button>");
        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getProcessWebPage().getUrl() + "?suri=" + suri + "'\">Regresar</button>");
        out.println("</span></fieldset>");
        out.println("</form>");

//        mgr.addButton(SWBFormButton.newSaveButton());
//        SWBFormButton bt = new SWBFormButton().setTitle("Concluir Tarea", "es").setAttribute("type", "submit");
//        bt.setAttribute("name", "accept");
//        mgr.addButton(bt);
//        SWBFormButton rej = new SWBFormButton().setTitle("Rechazar Tarea", "es").setAttribute("type", "submit");
//        rej.setAttribute("name", "reject");
//        mgr.addButton(rej);
//        SWBFormButton ret = new SWBFormButton().setTitle("Regresar", "es");
//        ret.setAttribute("onclick", "window.location='" + foi.getProcessWebPage().getUrl() + "?suri=" + suri + "'");
//        mgr.addButton(ret);


        //out.println(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
//        SemanticVocabulary voc = SWBPlatform.getSemanticMgr().getVocabulary();

        //System.out.println("Action: " + response.getAction());

        UserTask ut = null;
        String suri = request.getParameter("suri");
        //System.out.println("SURI: " + suri);
        FlowNodeInstance foi = null;

        if ("savecnf".equals(response.getAction())) {
            if (suri == null) {
                return;
            }
            foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            String data = "";
            Iterator<ProcessObject> it = foi.listHeraquicalProcessObjects().iterator();
            while (it.hasNext()) {
                ProcessObject obj = it.next();
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    String name = cls.getClassId() + "|" + prop.getPropId();
                    if (request.getParameter(name) != null) {
                        data += name;
                        data += "|" + request.getParameter(name + "|s");
                        data += "|" + request.getParameter(name + "|fe");
                        data += "\n";
                    }
                }
            }
            response.getResourceBase().setData(response.getWebPage(), data);
            response.setMode(response.Mode_VIEW);
        } else if ("process".equals(response.getAction())) {
            if (suri == null) {
                return;
            }
            foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            System.out.println("Processing action... :" + foi);
            SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
            mgr.clearProperties();

            HashMap<String, String> hmprops = new HashMap();

            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {

                String sprop = base.getAttribute("prop" + i);
                StringTokenizer stoken = new StringTokenizer(sprop, "|");

                String classid = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    classid = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                hmprops.put(classid + "|" + propid, base.getAttribute("prop" + i));
                i++;
            }



            Iterator<ProcessObject> it = foi.listHeraquicalProcessObjects().iterator();
            while (it.hasNext()) {
                ProcessObject obj = it.next();
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    if (isViewProperty(response, cls, prop, hmprops)) {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_VIEW);
                    } else if (isEditProperty(response, cls, prop, hmprops)) {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
                    }
                }
            }
            try {
                mgr.processForm(request);
                if (request.getParameter("accept") != null) {
                    foi.close(response.getUser(), Instance.ACTION_ACCEPT);
                    response.sendRedirect(foi.getProcessWebPage().getUrl());
                } else if (request.getParameter("reject") != null) {
                    foi.close(response.getUser(), Instance.ACTION_REJECT);
                    response.sendRedirect(foi.getProcessWebPage().getUrl());
                }

            } catch (Exception e) {
                response.setRenderParameter("err", "invalidForm");
            }

        } else if ("savepropcnf".equals(response.getAction())) {
            // System.out.println("savepropcnf....");


            //find last prop
            //System.out.println("revisando ultima prop...");
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                System.out.println(i + " = " + base.getAttribute("prop" + i));
                i++;
            }

            int max = i;

            //System.out.println("Siguiente prop..." + max);

            String prop = request.getParameter("prop");

            String default_FE = null;
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            SemanticProperty sempro = ont.getSemanticProperty(prop);
            SemanticObject dp = sempro.getDisplayProperty();
            if (dp != null) {
                //tiene displayproperty
                DisplayProperty disprop = (DisplayProperty) dp.createGenericInstance();

                //FormElement por defecto
                SemanticObject semobjFE = disprop.getFormElement();
                if (semobjFE != null) {
                    default_FE = semobjFE.getURI();
                } else {
                    default_FE = (new GenericFormElement()).getURI();
                }

            }

            String modo = request.getParameter("mode");
            String fe = request.getParameter("formElement");
            String value = prop + "|" + modo + "|" + fe;

            boolean isTaken = Boolean.FALSE;
            // revisando que la propiedad no exista

            //System.out.println("Revisando que no exista la prop... ");

            for (int j = 1; j < max; j++) {
                String val = base.getAttribute("prop" + j);

                //System.out.println(j + " = " + val);

                if (val.startsWith(prop)) {
                    isTaken = Boolean.TRUE;
                    //System.out.println("Propiedad previamente configurada...");
                    break;
                }
            }

            if (!isTaken) {
                //System.out.println("Guardando prop " + max + " = " + value);
                base.setAttribute("prop" + i, value);
                try {
                    base.updateAttributesToDB();
                    //System.out.println("Actualizando prop...");
                } catch (Exception e) {
                    log.error("Error al actualizar atributos del recurso.", e);
                }
            } else {

                response.setRenderParameter("errormsg", "Esa propiedad ya esta seleccionada. selecciona una diferente.");
            }

        } else if ("removeprop".equals(response.getAction())) {
            String prop = request.getParameter("prop");

            HashMap<Integer, String> hmprops = new HashMap();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                //System.out.println(i + " = " + base.getAttribute("prop" + i));
                if (!prop.equals("" + i)) {
                    hmprops.put(new Integer(i), base.getAttribute("prop" + i));
                }
                base.removeAttribute("prop" + i);
                i++;
            }

            ArrayList list = new ArrayList(hmprops.keySet());
            Collections.sort(list);

            i = 1;
            Iterator<Integer> itprop = list.iterator();
            while (itprop.hasNext()) {
                Integer integer = itprop.next();
                String thisprop = hmprops.get(integer);
                base.setAttribute("prop" + i, thisprop);
                i++;
            }

            //base.removeAttribute("prop"+prop);
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
            }
        } else if ("addprops".equals(response.getAction())) {

            String[] props = request.getParameterValues("existentes");

            HashMap<Integer, String> hmprops = new HashMap();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                hmprops.put(new Integer(i), base.getAttribute("prop" + i));
                base.removeAttribute("prop" + i);
                i++;
            }

            HashMap<String, String> hmparam = new HashMap();

            int j = 0;
            for (j = 0; j < props.length; j++) {
                hmparam.put(props[j], props[j]);
            }

            Iterator<Integer> itstr = hmprops.keySet().iterator();
            while (itstr.hasNext()) {
                Integer intg = itstr.next();
                String sprop = hmprops.get(intg);

                StringTokenizer stoken = new StringTokenizer(sprop, "|");

                String classid = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    classid = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                if (hmparam.get(classid + "|" + propid) != null) {
                    hmparam.remove(classid + "|" + propid);

                } else if (hmparam.get(classid + "|" + propid) == null) {
                    itstr.remove();
                }

            }

            int maximo = 0;
            Iterator<Integer> itint = hmprops.keySet().iterator();
            while (itint.hasNext()) {
                Integer num = itint.next();
                //System.out.println("prop no." + num.intValue());
                if (num.intValue() > maximo) {
                    maximo = num.intValue();
                }
            }

            //Agregar propiedades faltantes

            String defaultFE = "http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextBox";
            String defaultMode = "view";
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();


            // agregando al hmprops las propiedades nuevas
            Iterator<String> itpar = hmparam.keySet().iterator();
            while (itpar.hasNext()) {
                maximo++;
                String strnew = itpar.next();

                StringTokenizer stoken = new StringTokenizer(strnew, "|");

                String classid = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    classid = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                SemanticProperty sempro = ont.getSemanticProperty(propid);
                SemanticObject dp = sempro.getDisplayProperty();
                if (dp != null) {
                    //tiene displayproperty
                    DisplayProperty disprop = (DisplayProperty) dp.createGenericInstance();

                    //FormElement por defecto
                    SemanticObject semobjFE = disprop.getFormElement();
                    if (semobjFE != null) {
                        defaultFE = semobjFE.getURI();
                    } else {
                        defaultFE = (new GenericFormElement()).getURI();
                    }

                }

                String value = strnew + "|" + defaultMode + "|" + defaultFE;
                hmprops.put(new Integer(maximo), value);
            }

            // ordenando las propiedades
            ArrayList list = new ArrayList(hmprops.keySet());
            Collections.sort(list);

            //guardando las propiedades
            i = 1;
            Iterator<Integer> itprop = list.iterator();
            while (itprop.hasNext()) {
                Integer integer = itprop.next();
                String thisprop = hmprops.get(integer);
                base.setAttribute("prop" + i, thisprop);
                i++;
            }

            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
            }


        } else if ("swap".equals(response.getAction())) {
            String direction = request.getParameter("direction");
            String propid = request.getParameter("prop");
            if (null != direction && "down".equals(direction)) {
                String tmp = base.getAttribute("prop" + propid);
                if (tmp != null) {
                    int pos = 0;
                    int posdown = 0;
                    try {
                        pos = Integer.parseInt(propid);
                        posdown = pos + 1;
                    } catch (Exception e) {
                    }
                    String tmp2 = base.getAttribute("prop" + posdown);
                    if (tmp2 != null && pos > 0 && posdown > 0 && pos < posdown) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + posdown, tmp);
                    }
                }

            } else if (null != direction && "up".equals(direction)) {
                String tmp = base.getAttribute("prop" + propid);
                if (tmp != null) {
                    int pos = 0;
                    int posup = 0;
                    try {
                        pos = Integer.parseInt(propid);
                        posup = pos - 1;
                    } catch (Exception e) {
                    }
                    String tmp2 = base.getAttribute("prop" + posup);
                    if (tmp2 != null && pos > 0 && posup > 0 && pos > posup) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + posup, tmp);
                    }
                }
            }
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
            }

        } else if ("updtItem".equals(response.getAction())) {

            String pid = request.getParameter("prop");
            System.out.println("updtItem..." + pid);
            if (pid != null) {
                String prop2change = base.getAttribute("prop" + pid);
                if (null != prop2change) {
                    StringTokenizer stoken = new StringTokenizer(prop2change, "|");
                    String classid = "";
                    String propid = "";
                    String modo = "";
                    String fele = "";

                    try {
                        if (stoken.hasMoreTokens()) {
                            classid = stoken.nextToken();
                            propid = stoken.nextToken();
                            modo = stoken.nextToken();
                            fele = stoken.nextToken();
                        }

                        if (request.getParameter("feuri") != null) {
                            fele = request.getParameter("feuri");
                        }
                        if (request.getParameter("mode") != null) {
                            modo = request.getParameter("mode");
                        }

                        String newvalue = classid + "|" + propid + "|" + modo + "|" + fele;

                        base.setAttribute("prop" + pid, newvalue);
                        base.updateAttributesToDB();

                    } catch (Exception e) {
                    }
                }
            }
        }

        response.setRenderParameter("suri", suri);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String suri = request.getParameter("suri");

        HashMap<String, String> hmsc = new HashMap();
        HashMap<String, SemanticProperty> hmprops = new HashMap();
        HashMap<String, SemanticProperty> hmselected = new HashMap();

        UserTask ut = null;
        if (base.getResourceable() instanceof UserTask) {
            ut = (UserTask) base.getResourceable();

        }

//        if (suri == null) {
//            out.println("Parámetro no definido...");
//            return;
//        }

        if (request.getParameter("errormsg") != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('" + request.getParameter("errormsg") + "');");
            out.println("</script>");
        }


        if (ut == null) {
            out.println("Parámetro no definido...");
            return;
        }

        //FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
        FlowNodeInstance foi = ut.getFlowObjectInstance();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();

        if (hmFormEle == null) {
            //System.out.println("Cargando HM - Form Element");
            hmFormEle = new HashMap<String, SemanticObject>();

            Iterator<SemanticObject> itfe = sv.getSemanticClass(sv.SWB_SWBFORMELEMENT).listInstances(false);//sont.listInstancesOfClass(sv.getSemanticClass(sv.SWB_FORMELEMENT));
            while (itfe.hasNext()) {
                SemanticObject sofe = itfe.next();
                hmFormEle.put(sofe.getURI(), sofe);
            }
        }

        Iterator<SemanticClass> itsub = sv.getSemanticClass(sv.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scfe = itsub.next();
            //System.out.println("SemanticClass: " + scfe.getDisplayName(user.getLanguage()));
            Iterator<SemanticObject> itsco = scfe.listInstances(true);
            while (itsco.hasNext()) {
                SemanticObject sOfe = itsco.next();
                //System.out.println("class instance: --=> " + sOfe.getDisplayName(user.getLanguage()));
            }
        }

//        out.println("<form action=\"" + paramRequest.getActionUrl().setAction("savecnf") + "\" method=\"post\">");
//        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
        Iterator<ProcessObject> it = foi.listHeraquicalProcessObjects().iterator();
//        while (it.hasNext()) {
//            ProcessObject obj = it.next();
//            //System.out.println("inst:"+foi+" obj:"+obj);
//            SemanticClass cls = obj.getSemanticObject().getSemanticClass();
//            out.println("<h3>" + cls.getDisplayName(lang) + "</h3>");
//            Iterator<SemanticProperty> itp = cls.listProperties();
//            while (itp.hasNext()) {
//                SemanticProperty prop = itp.next();
//                String name = cls.getClassId() + "|" + prop.getPropId();
//                out.print("<input type=\"checkbox\" name=\"" + name + "\"");
//                if (hasProperty(paramRequest, cls, prop)) {
//                    out.print(" checked");
//                }
//                out.println(">");
//                out.println("<select name=\"" + name + "|s\">");
//                out.println("<option value=\"edit\"");
//                if (isEditProperty(paramRequest, cls, prop)) {
//                    out.print(" selected");
//                }
//                out.println(">Edit</option>");
//                out.println("<option value=\"view\"");
//                if (isViewProperty(paramRequest, cls, prop)) {
//                    out.print(" selected");
//                }
//                out.println(">View</option>");
//                out.println("</select>");
////                out.println("<select name=\"" + name + "|fe\" >");
////                out.println(getFESelect(cls.getClassId() + "|" + prop.getPropId() + "|", paramRequest));
////                out.println("</select>");
//                out.println(prop.getDisplayName(lang));
//                out.println("<BR>");
//            }
//        }
//        out.println("<input type=\"submit\" value=\"Guardar\">");
//
//        SWBResourceURL urlbck = paramRequest.getRenderUrl();
//        urlbck.setMode(SWBResourceURL.Mode_VIEW);
//        urlbck.setParameter("suri", suri);
//
//        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"window.location='" + urlbck + "'; return false;\">");
//        out.println("</form>");
//
//
//        out.println("<form action=\"" + paramRequest.getActionUrl().setAction("savepropcnf") + "\" method=\"post\">");
//        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
//
//        out.println("<select name=\"prop\">");
//        it = foi.listHeraquicalProcessObjects().iterator();
        while (it.hasNext()) {
            ProcessObject obj = it.next();
            SemanticClass cls = obj.getSemanticObject().getSemanticClass();
            Iterator<SemanticProperty> itp = cls.listProperties();
            while (itp.hasNext()) {
                SemanticProperty prop = itp.next();
                String name = cls.getClassId() + "|" + prop.getPropId();
                hmsc.put(name, prop.getDisplayName(lang));
//                out.println("<option value=\"" + name + "\">");
//                out.println(prop.getDisplayName(lang));
//                out.println("</option>");
                hmprops.put(name, prop);
            }
        }
//        out.println("</select>");
//        out.println("<select name=\"mode\">");
//        out.println("<option value=\"edit\">Edit</option>");
//        out.println("<option value=\"view\">View</option>");
//        out.println("</select>");
//        out.println("<select name=\"formElement\" >");
//        out.println(getFESelect("", paramRequest));
//        out.println("</select>");
//
//        out.println("<input type=\"submit\" value=\"Agregar\">");
//        out.println("<BR>");
//
//        urlbck = paramRequest.getRenderUrl();
//        urlbck.setMode(SWBResourceURL.Mode_VIEW);
//        urlbck.setParameter("suri", suri);
//
//        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"window.location='" + urlbck + "'; return false;\">");
//        out.println("</form>");




        out.println("<script type=\"text/javascript\">");
        out.println("function MoveItems(lstbxFrom,lstbxTo) ");
        out.println("{ ");
        out.println("	var varFromBox = document.getElementById(lstbxFrom); ");
        out.println("	var varToBox = document.getElementById(lstbxTo); ");
        out.println("	if ((varFromBox != null) && (varToBox != null))  ");
        out.println("	{  ");
        out.println("		if(varFromBox.length < 1)  ");
        out.println("		{ ");
        out.println("			alert('No hay propiedades en la lista.'); ");
        out.println("			return false; ");
        out.println("		} ");
        out.println("		if(varFromBox.options.selectedIndex == -1) // no hay elementos seleccionados");
        out.println("		{ ");
        out.println("			alert('Selecciona una propiedad de la lista.'); ");
        out.println("			return false; ");
        out.println("		} ");
        out.println("		while ( varFromBox.options.selectedIndex >= 0 )  ");
        out.println("		{  ");
        out.println("			var newOption = new Option(); // crea una opcion en el select  ");
        out.println("			newOption.text = varFromBox.options[varFromBox.options.selectedIndex].text;  ");
        out.println("			newOption.value = varFromBox.options[varFromBox.options.selectedIndex].value;  ");
        out.println("			varToBox.options[varToBox.length] = newOption; //agrega la opción al final del select destino");
        out.println("			varFromBox.remove(varFromBox.options.selectedIndex); //quita la opción del select origen ");
        out.println("		}  ");
        out.println("	} ");
        out.println("	return false;  ");
        out.println("} ");

        out.println("function enviatodos(lstbox)");
        out.println("{");
        out.println("	var list = document.getElementById(lstbox);");
        out.println("	if(list.options.length==0) return false;");
        out.println("	for (var i=0; i<list.options.length; i++){");
        out.println("	 list.options[i].selected=true;");
        out.println("	}");
        out.println("	return true;");
        out.println("}");
//        out.println("</script> ");
//
//        out.println("<script type=\"text/javascript\">");
        out.println("   function updItem(uri,param,sel) {");
        //out.println("   if(sel.options.selectedIndex >= 0){");
        out.println("       var valor = sel.options[sel.options.selectedIndex].value;");
        out.println("       var url = uri+'&'+param+'='+escape(valor);");
        out.println("       alert(url);");
        out.println("       window.location=url;");
//        out.println("   }");
        //out.println("   return false;");
        out.println("}");
        out.println("</script>");

        int max = 1;
        while (!base.getAttribute("prop" + max, "").equals("")) {

            String val = base.getAttribute("prop" + max);
            String classid = "";
            String propid = "";
            String modo = "";
            String fe = "";
            StringTokenizer stoken = new StringTokenizer(val, "|");
            if (stoken.hasMoreTokens()) {
                classid = stoken.nextToken();
                propid = stoken.nextToken();
                modo = stoken.nextToken();
                fe = stoken.nextToken();
            }
            if (hmprops.get(classid + "|" + propid) != null) {
                SemanticProperty sprop = ont.getSemanticProperty(propid);
                hmselected.put(classid + "|" + propid, sprop);
            }
            max++;
        }

        SWBResourceURL urladd = paramRequest.getActionUrl();
        urladd.setAction("addprops");

        out.println("<div class=\"swbform\">");
        out.println("<form action=\"" + urladd + "\" id=\"" + suri + "/forma\" method=\"post\" onsubmit=\"if(enviatodos('existentes')) { this.form.submit(); return false; } else { return false;}\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
        out.println("<fieldset>");
        out.println("<legend>" + "Configuración" + "</legend>");
        out.println("<table border=\"0\" >");
        out.println("<tr>");
        out.println("<th>Propiedades");
        out.println("</th>");
        out.println("<th>");
        out.println("</th>");
        out.println("<th>Seleccionadas");
        out.println("</th>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        // select con la lista de propiedades existentes
        out.println("<select size=\"10\" name=\"propiedades\" id=\"" + suri + "/propiedades\" multiple style=\"width:250px;\">");
        Iterator<String> its = hmprops.keySet().iterator();
        while (its.hasNext()) {
            String str = its.next();
            if (hmselected.get(str) == null) {
                SemanticProperty sp = hmprops.get(str);
                out.println("<option value=\"" + str + "\">");
                out.println(sp.getName());
                out.println("</option>");
            }
        }
        out.println("</select>");
        out.println("</td>");
        out.println("<td valign=\"middle\">");
        // botones
        out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + suri + "btnMoveLeft\" onclick = \"MoveItems('" + suri + "/existentes','" + suri + "/propiedades');\"><-</button><br>");
        out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + suri + "btnMoveRight\" onclick = \"MoveItems('" + suri + "/propiedades','" + suri + "/existentes');\">-></button>");
        out.println("</td>");
        out.println("<td>");
        // select con la lista de propiedades seleccionadas
        out.println("<select size=\"10\" name=\"existentes\" id=\"" + suri + "/existentes\" multiple style=\"width:250px;\">");
        its = hmselected.keySet().iterator();
        while (its.hasNext()) {
            String str = its.next();
            SemanticProperty sp = hmselected.get(str);
            out.println("<option value=\"" + str + "\">");
            out.println(sp.getName());
            out.println("</option>");
        }
        out.println("</select>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan=\"3\" align=\"center\">");
        // botones para guadar cambios
        out.println("<button  dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
//        out.println("<script type=\"dojo/method\" event=\"onClick\" >");
//        out.println(" if(enviatodos('existentes')) {");
//        out.println("   submitForm('"+suri+"/forma'); ");
//        out.println(" } ");
//        out.println(" return false; ");
//        out.println("</script>");
//        out.println("");
//        SWBResourceURL urlbck = paramRequest.getRenderUrl();
//        urlbck.setMode(SWBResourceURL.Mode_VIEW);
//        urlbck.setParameter("suri", suri);
//
//        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\">Cancelar");
//        out.println("<script type=\"dojo/method\" event=\"onClick\" >");
//        //out.println(" var miform = dojo.byId('"+ id + "/collectionconfig'); ");
//        out.println(" submitUrl('" + urlbck + "',this.domNode); ");
//        out.println(" return false; ");
//        out.println("</script>");
//        out.println("</button>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        //out.println("</form>");
        out.println("</fieldset>");
        out.println("<fieldset>");

        out.println("<table border=\"0\" width=\"100%\">");
        out.println("<tr>");
        out.println("<th>Acción");
        out.println("</th>");
        out.println("<th>Propiedad");
        out.println("</th>");
        out.println("<th>FormElement");
        out.println("</th>");
        out.println("<th>Modo");
        out.println("</th>");
        out.println("</tr>");
        int i = 1;
        while (!base.getAttribute("prop" + i, "").equals("")) {
            String val = base.getAttribute("prop" + i);
            String classid = "";
            String propid = "";
            String modo = "";
            String fe = "";
            StringTokenizer stoken = new StringTokenizer(val, "|");
            if (stoken.hasMoreTokens()) {
                classid = stoken.nextToken();
                propid = stoken.nextToken();
                modo = stoken.nextToken();
                fe = stoken.nextToken();
            }
            //System.out.println(i + " = " + base.getAttribute("prop" + i));

            out.println("<tr>");

            SWBResourceURL urlrem = paramRequest.getActionUrl();
            urlrem.setAction("removeprop");
            urlrem.setParameter("prop", "" + i);
            urlrem.setParameter("suri", suri);

            out.println("<td>");
            out.println("<a href=\"#\" onclick=\"window.location='" + urlrem + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"eliminar\"></a>");

            if (i != max - 1) {
                SWBResourceURL urldown = paramRequest.getActionUrl();
                urldown.setAction("swap");
                urldown.setParameter("suri", suri);
                urldown.setParameter("prop", "" + i);
                urldown.setParameter("direction", "down");
                out.println("<a href=\"#\" onclick=\"window.location='" + urldown + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/down.jpg\" border=\"0\" alt=\"bajar\"></a>");
            } else {
                out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/space.jpg\" border=\"0\" >");
            }

            if (i != 1) //sorderdown>0
            {
                SWBResourceURL urlup = paramRequest.getActionUrl();
                urlup.setAction("swap");
                urlup.setParameter("suri", suri);
                urlup.setParameter("prop", "" + i);
                urlup.setParameter("direction", "up");
                urlup.setParameter("ract", "config");
                out.println("<a href=\"#\" onclick=\"window.location='" + urlup + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/up.jpg\" border=\"0\" alt=\"subir\"></a>");
            }
            out.println("</td>");
            out.println("<td>");

            out.println(hmsc.get(classid + "|" + propid));

            out.println("</td>");
            out.println("<td>");

            SWBResourceURL urlupd = paramRequest.getActionUrl();
            urlupd.setAction("updtItem");
            urlupd.setParameter("prop", "" + i);
            urlupd.setParameter("suri", suri);

            out.println("<select id=\"" + suri + "/" + i + "/sfe\" name=\"formElement\" style=\"width:200px;\" onchange=\"updItem('" + urlupd + "','feuri',this);\" >"); //

//            out.println("<script type=\"dojo/connect\" event=\"onChange\">");
//            out.println(" var sel=this;   ");
//            out.println("   if(sel.options.selectedIndex >= 0){");
//            out.println("       var valor = sel.options[sel.options.selectedIndex].value;");  //
//            out.println("       var url = '" + urlupd + "&feuri='+valor;");
//            out.println("       alert(url);");
//            //out.println(" submitUrl('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),self1.domNode);");
//            out.println("       submitUrl(url,sel);");
//            out.println("   }");
//            out.println("   return false;");
//
//
//            //out.println("updItem('" + urlupd + "','feuri',self1);");
//            //out.println(" showStatusURL('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),true);");
//
//            out.println("</script>");



            out.println(getFESelect(fe, paramRequest));
            out.println("</select>");
            out.println("</td>");
            out.println("<td>");
            out.println("<select id=\"" + suri + "/" + i + "/smode\" name=\"mode\" style=\"width:80px;\" onchange=\"updItem('" + urlupd + "','mode',this);\">"); //

//            out.println("<script type=\"dojo/connect\" event=\"onChange\">");
//            out.println(" var sel=this;   ");
//
////            out.println("   if(sel.options.selectedIndex >= 0){");
//            out.println("       var valor = sel.attr(\"value\"); ");  //sel.options[sel.options.selectedIndex].value;
//            out.println("       var url = '" + urlupd + "&mode='+valor;");
//            out.println("       alert(url);");
//            //out.println(" submitUrl('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),self1.domNode);");
//            out.println("       submitUrl(url,sel.domNode);");
////            out.println("   }");
//            out.println("   return false;");
//
//
//            //out.println("updItem('" + urlupd + "','feuri',self1);");
//            //out.println(" showStatusURL('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),true);");
//
//            out.println("</script>");



            out.println("<option value=\"edit\" " + (modo.equals("edit") ? "selected" : "") + " >Edit</option>");
            out.println("<option value=\"view\" " + (modo.equals("view") ? "selected" : "") + " >View</option>");
            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");
            i++;
        }

        out.println("</table>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    public String getFESelect(String FEsel, SWBParamRequest paramRequest) {

        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
        StringBuilder ret = new StringBuilder();
        ret.append("\n<optgroup label=\"Genérico\">");
        ret.append("\n<option value=\"generico\" selected >Label</option>");
        ret.append("\n</optgroup>");

        Iterator<SemanticClass> itsub = sv.getSemanticClass(sv.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scfe = itsub.next();
            ret.append("\n<optgroup label=\"");
            ret.append(scfe.getDisplayName(paramRequest.getUser().getLanguage()));
            ret.append("\">");
            Iterator<SemanticObject> itsco = scfe.listInstances(true);
            while (itsco.hasNext()) {
                SemanticObject sofe = itsco.next();
                ret.append("\n<option value=\"");
                ret.append(sofe.getURI());
                ret.append("\"");
                String stmp = FEsel + "edit|" + sofe.getURI();
                String stmp2 = FEsel + "view|" + sofe.getURI();
                String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
                if (FEsel != null && !FEsel.equals("") && (data != null && (data.indexOf(stmp) > -1 || data.indexOf(stmp2) > -1) || FEsel.equals(sofe.getURI()))) {
                    ret.append(" selected ");
                }
                ret.append(">");
                ret.append(sofe.getDisplayName(paramRequest.getUser().getLanguage()));
                ret.append("\n</option>");
            }
            ret.append("\n</optgroup>");
        }

        return ret.toString();
    }

    public boolean hasProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop) {
        boolean ret = false;
        String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if (data != null && data.indexOf(cls.getClassId() + "|" + prop.getPropId()) > -1) {
            return ret = true;
        }
        return ret;
    }

    public boolean isViewProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(cls.getClassId() + "|" + prop.getPropId());
        if (data != null && data.indexOf(cls.getClassId() + "|" + prop.getPropId() + "|view") > -1) {
            return ret = true;
        }
        return ret;
    }

    public boolean isEditProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(cls.getClassId() + "|" + prop.getPropId());
        if (data != null && data.indexOf(cls.getClassId() + "|" + prop.getPropId() + "|edit") > -1) {
            return ret = true;
        }
        return ret;
    }
}
