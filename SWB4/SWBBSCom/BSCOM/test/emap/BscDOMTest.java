/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author carlos.ramos
 */
public class BscDOMTest {
    final private String modelId = "DADT";
    final private String periodId = "16";
    
    public BscDOMTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }
    
    public void setUpSessionUser(BSC model) {
        User user = model.getUserRepository().listUsers().next();
        SWBContext.setSessionUser(user);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    //@Test
    public void dom1() {
        System.out.println("\n\n----- Obtener el dom de un scorecard");
        if(BSC.ClassMgr.hasBSC(modelId))
        {   
            BSC model = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+model);
            
            setUpSessionUser(model);            
            
            Document dom = model.getDom();
            System.out.println("xml:\n"+SWBUtils.XML.domToXml(dom));
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
    
    @Test
    public void dom2() {
        System.out.println("\n\n----- Obtener el dom de un scorecard");
        if(BSC.ClassMgr.hasBSC(modelId))
        {   
            BSC model = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+model);
            
            setUpSessionUser(model);            
            
            Document dom = model.getDom();
            System.out.println("xml:\n"+SWBUtils.XML.domToXml(dom));
            try {
                getDom(dom);
                System.out.println("svg:\n"+SWBUtils.XML.domToXml(dom));
            }catch(XPathExpressionException xpathe) {
                fail("XPath con problemas... "+xpathe);
            }
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
        
    public void getDom(Document documentBSC) throws XPathExpressionException
    {
        //Resource base = getResourceBase();
        //BSC scorecard = (BSC)base.getWebSite();
        final int width = 800;
        final int height = 600;
        //Document documentBSC = scorecard.getDom();
        
        //root: period, width, height
        Element root = documentBSC.getDocumentElement();
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
                
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        //header
//        Element header = documentBSC.createElement("header");
//        header.setAttribute("width", "0");
//        header.setAttribute("height", "0");
//        header.setAttribute("x", "0");
//        header.setAttribute("y", "0");
//        String expression = "/bsc/title";
//        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
//        header.appendChild(node);
//        expression = "/bsc/mission";
//        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
//        header.appendChild(node);
//        expression = "/bsc/vision";
//        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
//        header.appendChild(node);
//        expression = "/bsc/logo";
//        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
//        header.appendChild(node);
//        root.appendChild(header);
        //header
        Element header = documentBSC.createElement("header");
        header.setAttribute("width", "0");
        header.setAttribute("height", "0");
        header.setAttribute("x", "0");
        header.setAttribute("y", "0");
        String expression = "/bsc/title";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/mission";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/vision";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);        
        expression = "/bsc/perspective[1]";
        Node p1 = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);        
        root.insertBefore(header, p1);
        
        
        int length, w, h, px, py;
        String uri;
        Boolean hiddenTheme;
        
        //para cada perspectiva: width, height, x, y
        expression = "/bsc/perspective";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        length = nodeList.getLength();
        px = 0;
        w = width;
        h = height/(length+1);
        //lista de perspectivas
        for(int j=0; j<length; j++) {
            Node nodep = nodeList.item(j);
            if(nodep.getNodeType()==Node.ELEMENT_NODE) {
                //perspectiva
                Element p = (Element)nodep;
                uri = p.getAttribute("id");
                p.setAttribute("width", Integer.toString(w));
                p.setAttribute("height",Integer.toString(h));
                py = j*h;
                p.setAttribute("x", Integer.toString(px));
                p.setAttribute("y", Integer.toString(py));
                
                //lista de diferenciadores por perspectiva
                expression = "/bsc/perspective[@id='"+uri+"']/diffgroup/diff";
                NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlDiffsCount = nlDiffs.getLength();
                final boolean hasDifferentiators = nlDiffsCount>0;
                if(hasDifferentiators) {
                    final int wDiff = width/nlDiffsCount;  
                    int dx = px;
                    for(int k=0; k<nlDiffsCount; k++) {
                        Node noded = nlDiffs.item(k);
                        Element d = (Element)noded;
                        d.setAttribute("width", Integer.toString(wDiff));
                        d.setAttribute("x", Integer.toString(wDiff*k+dx));
                        d.setAttribute("y", Integer.toString(py));
                    }
                }
                
                //lista de temas por perspectiva
                expression = "/bsc/perspective[@id='"+uri+"']/themes/theme";
                NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlThmsCount = nlThms.getLength()==0?1:nlThms.getLength();
                final int wThm = width/nlThmsCount;
                for(int k=0; k<nlThmsCount; k++) {
                    Node nodet = nlThms.item(k);
                    int tx;
                    int ty = py;
                    if(nodet.getNodeType()==Node.ELEMENT_NODE) {
                        Element t = (Element)nodet;
                        uri = t.getAttribute("id");
                        hiddenTheme = Boolean.parseBoolean(t.getAttribute("hidden"));
                        t.setAttribute("width", Integer.toString(wThm));
                        tx = k*wThm;
                        t.setAttribute("x", Integer.toString(tx));
                        t.setAttribute("y", Integer.toString(ty));
                        expression = "//theme[@id='"+uri+"']/obj";
                        NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                        final int nlObjsCount = nlObjs.getLength()==0?1:nlObjs.getLength();
                        int hObj;
                        if(hiddenTheme) {
                            hObj = h/(nlObjsCount);
                        }else {
                            hObj = h/(nlObjsCount+1);
                        }
                        
                        //lista de objetivos por tema
                        for(int l=0; l<nlObjsCount; l++) {
                            Node nodeo = nlObjs.item(l);
                            int ox = tx;
                            int oy;
                            if(nodeo.getNodeType()==Node.ELEMENT_NODE) {
                                Element o = (Element)nodeo;
                                uri = o.getAttribute("id");                                
                                o.setAttribute("width", Integer.toString(wThm));
                                o.setAttribute("height", Integer.toString(hObj));
                                o.setAttribute("x", Integer.toString(ox));  
                                oy = l*hObj;
                                o.setAttribute("y", Integer.toString(oy));
                                
                                //relaciones con este objetivo
                                expression = "//rel[@to='"+uri+"']";
                                NodeList nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                for(int m=0; m<nlRels.getLength(); m++) {
                                    Node noder = nlRels.item(m);
                                    if(noder.getNodeType()==Node.ELEMENT_NODE) {
                                        Element rel = (Element)noder;
                                        rel.setAttribute("rx", Integer.toString(ox));
                                        rel.setAttribute("ry", Integer.toString(oy));
                                    }
                                }
                            }
                        }
                        t.setAttribute("height",Integer.toString(hObj));
                    }
                }
            }
        }
    }
}
