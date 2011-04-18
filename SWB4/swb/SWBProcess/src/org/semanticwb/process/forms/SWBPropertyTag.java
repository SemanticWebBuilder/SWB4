/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import com.arthurdo.parser.HtmlStreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.process.model.SWBProcessFormMgr;

/**
 *
 * @author jorge.jimenez
 */
public class SWBPropertyTag implements SWBFormLayer {

    private static Logger log = SWBUtils.getLogger(SWBPropertyTag.class);
    private HtmlStreamTokenizer tok = null;
    private String htmlType = "dojo";
    HttpServletRequest request = null;
    SWBParamRequest paramRequest = null;
    SWBProcessFormMgr mgr = null;
    User user = null;
    String stag="";
    HashMap hmapClasses=null;
    HashMap hMapProperties=null;
    String sTagClassComplete = null;
    String sTagProp = null;
    String sformElement=null;
    String smode=null;
    String sTagVarName=null;
    private static HashMap hMapFE=new HashMap();



    public SWBPropertyTag(HttpServletRequest request, SWBParamRequest paramRequest, HashMap<String,ArrayList<SemanticProperty>> hmapClasses, HashMap <String, String> hMapProperties, SWBProcessFormMgr mgr, HtmlStreamTokenizer tok, String htmlType) {
        this.tok = tok;
        stag=tok.getRawString();
        this.request = request;
        this.paramRequest = paramRequest;
        if(htmlType!=null) this.htmlType=htmlType;
        user = paramRequest.getUser();
        this.mgr = mgr;
        this.hmapClasses=hmapClasses;
        this.hMapProperties=hMapProperties;
        setAttributes();
        if(hMapFE.size()==0){
            getFormElement4Display();
        }
    }

    private void setAttributes(){
        Iterator <String> itTagKeys=hMapProperties.keySet().iterator();
        while(itTagKeys.hasNext()){
            String sTagKey=itTagKeys.next();
            if(sTagKey.equalsIgnoreCase("name")){
                sTagClassComplete=(String)hMapProperties.get(sTagKey);
                if(sTagClassComplete!=null){
                    int pos=sTagClassComplete.indexOf(".");
                    if(pos>-1){
                        sTagVarName=sTagClassComplete.substring(0,pos);
                        sTagProp=sTagClassComplete.substring(pos+1);
                    }
                }

            }if(sTagKey.equalsIgnoreCase("formElement")){
                sformElement=(String)hMapProperties.get(sTagKey);
            }if(sTagKey.equalsIgnoreCase("mode")){
                smode=(String)hMapProperties.get(sTagKey);
            }
        }
    }


    public String getHtml() {
        String renderElement = null;
        if (request != null) {
            try {
                Iterator <String> itClasses=hmapClasses.keySet().iterator();
                while(itClasses.hasNext()){
                    String varName=itClasses.next();
                    if(sTagVarName.equalsIgnoreCase(varName)){
                        Iterator <SemanticProperty> itClassProps=((ArrayList)hmapClasses.get(varName)).iterator();
                        while(itClassProps.hasNext()){
                            SemanticProperty semProp=itClassProps.next();
                            if(semProp.getName().endsWith(sTagProp)){
                                //Manejo de modo
                                String swbMode=mgr.MODE_EDIT;
                                FormElement frme=null;
                                if(smode!=null && smode.length()>0) swbMode=smode;
                                if(swbMode.equalsIgnoreCase("edit")) swbMode=mgr.MODE_EDIT;
                                else if(swbMode.equalsIgnoreCase("view")) swbMode = mgr.MODE_VIEW;
                                if(sformElement!=null && sformElement.length()>0){
                                    if(hMapFE.size()>0){
                                        SemanticObject sofe=(SemanticObject)hMapFE.get(sformElement);
                                        if(sofe!=null){
                                            frme = (FormElement)sofe.createGenericInstance();
                                        }
                                    }
                                }
                                if(frme!=null) {
                                    renderElement=mgr.renderElement(request, varName, semProp, frme, swbMode);
                                }
                                else {
                                    renderElement = mgr.renderElement(request, varName, semProp, swbMode);
                                }
                            }
                        }
                         
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        return renderElement;
    }


    private static void getFormElement4Display(){
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
        Iterator<SemanticClass> itsub = sv.getSemanticClass(sv.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scfe = itsub.next();
            Iterator<SemanticObject> itsco = scfe.listInstances(); //TODO:Revisar si es con este metodo o pasandole un true
            while (itsco.hasNext()) {
                SemanticObject sofe = itsco.next();
                String stypeFE=null;
                int pos=sofe.getURI().indexOf("#");
                if(pos>-1){
                    stypeFE=sofe.getURI().substring(pos+1);
                }
                hMapFE.put(sofe.getPrefix()+":"+stypeFE, sofe);
            }
        }
    }


    public String getTag() {
        return stag;
    }

    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
