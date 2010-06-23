/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFormMgrLayer{

    private String xml=null;
    private Document dom=null;
    private User user=null;
    private Resource base=null;
    HttpServletRequest request=null;
    ArrayList <SWBClass> aClasses=null;
    SWBParamRequest paramRequest=null;
    private String htmlType="";

    private static Logger log = SWBUtils.getLogger(SWBFormMgrLayer.class);

    public SWBFormMgrLayer(String xml, SWBParamRequest paramRequest, HttpServletRequest request){
        this.xml=xml;
        this.request=request;
        this.paramRequest=paramRequest;
        init();
        createObjs();
    }

    private void init(){
        aClasses=new ArrayList();
        user=paramRequest.getUser();
        base=paramRequest.getResourceBase();
    }

    private void createObjs(){
        dom = SWBUtils.XML.xmlToDom(xml);
        NodeList ndllevel1 = dom.getChildNodes();
        if(ndllevel1.getLength() > 0) { //en el tag swbForm pueden venir algunos atributos para la forma, ej. si se utilizara Dojo como framework
            if(ndllevel1.item(0).getNodeName().equalsIgnoreCase("swbForm")){
                NamedNodeMap nnodemap = ndllevel1.item(0).getAttributes();
                if (nnodemap.getLength() > 0) {
                    for (int i = 0; i < nnodemap.getLength(); i++) { //Preparado para si se requiere agregar más parametros al tag swbForm
                        String attrName = nnodemap.item(i).getNodeName();
                        String attrValue = nnodemap.item(i).getNodeValue();
                        if (attrValue != null && !attrValue.equals("")) {
                            if (attrName.equalsIgnoreCase("htmlType")) {
                                htmlType=attrValue;
                            }
                        }
                    }
                }
                System.out.println("htmlType k sale:"+htmlType);
                NodeList ndllevel2 = ndllevel1.item(0).getChildNodes();
                if(ndllevel2.getLength() > 0) {
                    for(int i = 0; i < ndllevel2.getLength(); i++) {
                        if(ndllevel2.item(i).getNodeName().startsWith("#text"))
                            continue;

                        if(ndllevel2.item(i).getNodeName().equalsIgnoreCase("swbClass")){ //para clases y elementos de la misma
                            SWBClass swbClass = null;
                            swbClass = createObj(ndllevel2.item(i), swbClass);

                            //Hijos del tag swbClass
                            NodeList ndllevel3 = ndllevel2.item(i).getChildNodes();
                            if(ndllevel3.getLength() > 0) {
                                for(int j = 0; j < ndllevel3.getLength(); j++)
                                    if(!ndllevel3.item(j).getNodeName().startsWith("#text"))
                                        swbClass = createObj(ndllevel3.item(j), swbClass);

                            }

                            aClasses.add(swbClass); //se agrega la clase
                        }
                        //else if(ndllevel2.item(i).getNodeName().equalsIgnoreCase("statictext") || ndllevel2.item(i).getNodeName().equalsIgnoreCase("script")){ //para statictext al mismo nivel de formas
                        //    HtmlFE htmlfe=new HtmlFE(ndllevel2.item(i));
                        //    add(htmlfe);
                        //}
                    }
                }
            }
        }
    }


    private SWBClass createObj(Node tag, SWBClass swbClass) {
        if(tag.getNodeName().equalsIgnoreCase("swbClass"))
        {
            System.out.println("SWBFormMgrLayer-Se agrega clase");
            swbClass= new SWBClass(tag, paramRequest, htmlType);
            if(user!=null) swbClass.setLocale(new java.util.Locale(user.getLanguage()));
            else swbClass.setLocale(SWBUtils.TEXT.getLocale());
        }else if(tag.getNodeName().equalsIgnoreCase("swbprop")){ //Tags que pueden existir
            System.out.println("SWBFormMgrLayer-Se agrega propiedad");
            SWBProperty property=new SWBProperty(tag, swbClass.getSWBFormMgr(), request, paramRequest);
            swbClass.addElement(property);
        }else if(tag.getNodeName().equalsIgnoreCase("fieldset")){ //Tags que pueden existir
            //Puede haber propiedades dentro de un fielset o fieldsets anidados o tambien statictext dentro
            FieldSet fieldSet=new FieldSet(tag, swbClass.getSWBFormMgr(), request);
            swbClass.addElement(fieldSet);
        }else if(tag.getNodeName().equalsIgnoreCase("statictext")){ //Tags que pueden existir

        }
        return swbClass;
    }

    public String getHtml(){ //En estos momentos solo funcionando para una sola clase
        StringBuilder strb=new StringBuilder();
        SWBResourceURL actionUrl=paramRequest.getActionUrl();
        actionUrl.setAction("update");
        SWBFormMgr swbSWBFormMgrTmp=null;
        strb.append("<form name=\"SWBFormMgrLayer\" method=\"post\" action=\""+actionUrl+"\" id=\"SWBFormMgrLayer\" dojoType=\"dijit.form.Form\" class=\"swbform\">");
        Iterator <SWBClass> itaClasses=aClasses.iterator();
        while(itaClasses.hasNext()){
            SWBClass swbClass=itaClasses.next();
            swbSWBFormMgrTmp=swbClass.getSWBFormMgr();
            Iterator <SWBFormLayer> itElements=swbClass.getElements().iterator();
            while(itElements.hasNext()){
                SWBFormLayer swbFormLayerElement=itElements.next();
                strb.append(swbFormLayerElement.getHtml());
            }
            String hiddens=swbClass.getSWBFormMgr().getFormHiddens();
            strb.append(hiddens);
        }
        strb.append(SWBFormButton.newSaveButton().renderButton(request, htmlType, user.getLanguage()));
        strb.append(SWBFormButton.newCancelButton().renderButton(request, htmlType, user.getLanguage()));
        
        strb.append("</form>");
        return strb.toString();
    }

    public static SemanticObject update2DB(HttpServletRequest request, SWBActionResponse response){ //En estos momentos solo funcionando para una sola clase
        String action=response.getAction();
        Resource base=response.getResourceBase();
        try{
            if(action!=null && action.equals("update"))
            {
                SWBFormMgr mgr=null;
                SemanticObject semObj=null;
                if(base.getAttribute("objInst")!=null && base.getAttribute("objInst").trim().length()>0) {
                    System.out.println("update2DB Actualiza..");
                    SemanticObject semObject = SemanticObject.createSemanticObject(base.getAttribute("objInst"));
                    mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                }else{
                    System.out.println("update2DB Crea..");
                    SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("scls"));
                    mgr = new SWBFormMgr(cls, base.getSemanticObject(), null);
                }
                if(mgr!=null){
                    System.out.println("LO HACE..");
                    semObj=mgr.processForm(request);
                }
                if(semObj!=null) return semObj;
            }else if(action.equals("remove")){
                System.out.println("Entra a update2DB Delete");
            }
        }catch(Exception e){
            log.error(e);
        }
        return null;
    }

}
