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
import static org.semanticwb.bsc.resources.strategicMap.StrategicMap.*;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author carlos.ramos
 */
public class BscDOM_G_Enabled_Test {
    final private String modelId = "DADT";
    final private String periodId = "16";
    
    public BscDOM_G_Enabled_Test() {
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
    
    //@Test
    public void dom2() {
        System.out.println("\n\n----- Obtener el dom del recurso de mapa");
        if(BSC.ClassMgr.hasBSC(modelId))
        {   
            BSC model = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+model);
            
            setUpSessionUser(model);            
            
            Document dom = model.getDom();
            System.out.println("modelo bsc:\n"+SWBUtils.XML.domToXml(dom));
            try {
                getDom(dom);
                System.out.println("mapa:\n"+SWBUtils.XML.domToXml(dom));
            }catch(XPathExpressionException xpathe) {
                fail("XPath con problemas... "+xpathe);
            }
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
    
    @Test
    public void dom3() {
        System.out.println("\n\n----- Armar el svg del mapa");
        if(BSC.ClassMgr.hasBSC(modelId))
        {
            BSC model = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+model);
            
            setUpSessionUser(model);            
            
            Document dom = model.getDom();
            System.out.println("xml:\n"+SWBUtils.XML.domToXml(dom));
            
            try {
                getDom(dom);
                System.out.println("map:\n"+SWBUtils.XML.domToXml(dom));
            }catch(XPathExpressionException xpathe) {
                fail("XPath con problemas... "+xpathe);
            }
            
            try {
                String svg = getSvg(dom);
                System.out.println("svg:\n"+svg);
            }catch(XPathExpressionException xpe) {
                fail(xpe.toString());
            }
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
    
    String urlBase = "";
    public void getDom(Document documentBSC) throws XPathExpressionException
    {
        //Resource base = getResourceBase();
        //final BSC scorecard = (BSC)base.getWebSite();
        //final int width = Integer.parseInt(base.getAttribute("width", "800"));
        //final int height = Integer.parseInt(base.getAttribute("height", "600"));
        //final Period period = getPeriod(request);
        final int width = 1024;
        final int height = 768;
        //Document documentBSC = scorecard.getDom(period);
        
        //root: period, width, height
        Element root = documentBSC.getDocumentElement();
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        //header
        Element header = documentBSC.createElement("header");
        header.setAttribute("id", HEADER_PREFIX+"DADT");
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER_HEIGHT));
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
        
        
        final int px;
        int py;
        final int pw, ph;
        final int perspCount;
        String uri;
        Boolean hiddenTheme;
        
        //para cada perspectiva: width, height, x, y
        expression = "/bsc/perspective";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        perspCount = nodeList.getLength();
        pw = width-MARGEN_LEFT-MARGEN_RIGHT;
        ph = (height-HEADER_HEIGHT)/perspCount -  MARGEN_TOP - MARGEN_BOTTOM;
        px = MARGEN_LEFT;
        //py = MARGEN_TOP;
        //lista de perspectivas
        for(int j=0; j<perspCount; j++) {
            int h_ = ph;
            Node nodep = nodeList.item(j);
            if(nodep.getNodeType()==Node.ELEMENT_NODE)
            {
                //perspectiva
                Element p = (Element)nodep;
                uri = p.getAttribute("id");
                //py = j*(ph+MARGEN_BOTTOM+MARGEN_TOP);
                py = MARGEN_TOP +HEADER_HEIGHT+ j*(ph+MARGEN_BOTTOM+MARGEN_TOP);
                //diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='"+uri+"']/diffgroup[1]/diff";
                NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlDiffsCount = nlDiffs.getLength();
                final boolean hasDifferentiators = nlDiffsCount>0;
                if(hasDifferentiators) {
                    final int dw = pw/nlDiffsCount;  
                    //int dx = px;
                    for(int k=0; k<nlDiffsCount; k++) {
                        Node noded = nlDiffs.item(k);
                        Element d = (Element)noded;
                        d.setAttribute("width", Integer.toString(dw-BOX_SPACING));
                        d.setAttribute("height", "14");
                        d.setAttribute("x", Integer.toString(px + k*dw));
                        d.setAttribute("y", Integer.toString(py));
                    }
                    //py = py + DIFF_TITLE + BOX_SPACING;
                }
                
                //perspectiva
                p.setAttribute("width", Integer.toString(pw));
                if(hasDifferentiators) {
                    h_ = ph - HEADER_4 - BOX_SPACING;
                }
                p.setAttribute("height",Integer.toString(h_));
                p.setAttribute("x", Integer.toString(px));
                p.setAttribute("y", Integer.toString(py));
                
                //lista de temas por perspectiva                
                expression = "/bsc/perspective[@id='"+uri+"']/themes/theme";
                NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlThmsCount = nlThms.getLength();
                final  boolean hasThemes = nlThmsCount>0;
                if(hasThemes)
                {
                    final int tw = pw/nlThmsCount;
                    int tx;                    
                    if(hasDifferentiators) {
                        py = py + HEADER_4 + BOX_SPACING;
                    }
                    int ty = py + PADDING_TOP;
                    
                    for(int k=0; k<nlThmsCount; k++)
                    {
                        Node nodet = nlThms.item(k);
                        //int ty = py + BIND_TOP_SPACING;
                        if(nodet.getNodeType()==Node.ELEMENT_NODE) {
                            Element t = (Element)nodet;
                            uri = t.getAttribute("id");
                            hiddenTheme = Boolean.parseBoolean(t.getAttribute("hidden"));
                            t.setAttribute("width", Integer.toString(tw-BOX_SPACING));
                            tx = px + k*tw + BOX_SPACING;
                            t.setAttribute("x", Integer.toString(tx));
                            t.setAttribute("y", Integer.toString(ty));
                            
                            //relaciones con este tema
                            expression = "//rel[@to='"+uri+"']";
                            NodeList nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            for(int v=0; v<nlRels.getLength(); v++) {
                                Node noder = nlRels.item(v);
                                if(noder!=null && noder.getNodeType()==Node.ELEMENT_NODE) {
                                    Element rel = (Element)noder;
                                    rel.setAttribute("rx", Integer.toString(tx+tw/2));
                                    rel.setAttribute("ry", Integer.toString(ty));
                                }
                            }
                            
                            //lista de objetivos por tema
                            expression = "//theme[@id='"+uri+"']/obj";
                            NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            final int nlObjsCount = nlObjs.getLength();
                            if(nlObjsCount>0)
                            {
                                int hObj;
                                if(hiddenTheme) {
                                    hObj = h_/(nlObjsCount);
                                    t.setAttribute("height","0");
                                }else {
                                    //hObj = h_/(nlObjsCount+1);
                                    hObj = (h_-HEADER_3)/nlObjsCount;
                                    //t.setAttribute("height",Integer.toString(hObj));
                                    t.setAttribute("height", Integer.toString(HEADER_3));
                                }
                                for(int l=0; l<nlObjsCount; l++)
                                {
                                    Node nodeo = nlObjs.item(l);
                                    int ox = tx;
                                    int oy = hiddenTheme ? 0 : HEADER_3;
                                    if(nodeo.getNodeType()==Node.ELEMENT_NODE) {
                                        Element o = (Element)nodeo;
                                        uri = o.getAttribute("id");                                
                                        o.setAttribute("width", Integer.toString(tw-PADDING_RIGHT));
                                        o.setAttribute("height", Integer.toString(hObj-PADDING_TOP));
                                        o.setAttribute("x", Integer.toString(ox));  
                                        oy += ty + l*hObj + PADDING_TOP;
                                        o.setAttribute("y", Integer.toString(oy));
                                        o.setAttribute("href",  urlBase+uri);

                                        //relaciones con este objetivo
                                        expression = "//rel[@to='"+uri+"']";
                                        nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                        for(int m=0; m<nlRels.getLength(); m++) {
                                            Node noder = nlRels.item(m);
                                            if(noder.getNodeType()==Node.ELEMENT_NODE) {
                                                Element rel = (Element)noder;
                                                rel.setAttribute("rx", Integer.toString(ox+tw/2));
                                                rel.setAttribute("ry", Integer.toString(oy));
                                            }
                                        }
                                    }
                                } //lista de objetivos por tema
                            }
                        }
                    } //lista de temas
                }
            }
        } //lista de perspectivas
    }
    
    public String getSvg(Document documentBSC) throws XPathExpressionException
    {
        String id, expression, txt;
        final int width, height;
        int w, h, w_, h_;
        int x=0, y=0, x_, y_;
        int rx, ry;
        StringBuilder SVGjs = new StringBuilder();
        final String emapId = "DAC";
        
        // XML del BSC
        Element rootBSC = documentBSC.getDocumentElement();
        width = 1024;
        height = 968;
      
        SVGjs.append("<script type=\"text/javascript\">").append("\n");
SVGjs.append(" var SVG_ = '"+SVG_NS_URI+"';").append("\n");
SVGjs.append(" var XLINK_ = '"+XLNK_NS_URI+"';").append("\n");
SVGjs.append(" var svg = document.createElementNS(SVG_, 'svg'); ").append("\n");
//SVGjs.append(" svg.setAttributeNS(null,'xmlns:xlink', '"+XLNK_NS_URI+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'id','"+emapId+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'width','"+width+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'height','"+height+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'viewBox','0,0,"+width+","+height+"');").append("\n");
SVGjs.append(" svg.setAttributeNS(null,'version','1.0');").append("\n");
SVGjs.append(" document.body.appendChild(svg);").append("\n");

SVGjs.append(" var defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs');").append("\n");
SVGjs.append(" var marker = document.createElementNS('http://www.w3.org/2000/svg', 'marker');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'id', 'arrow_1');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'viewBox', '0 0 10 10');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'refX', '8');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'refY', '5');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'markerUnits', 'strokeWidth');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'markerWidth', '4');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'markerHeight', '4');").append("\n");
SVGjs.append(" marker.setAttributeNS(null,'orient', 'auto');").append("\n");
SVGjs.append(" var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');").append("\n");
SVGjs.append(" path.setAttributeNS(null,'d', 'M0 0 L10 5 L0 10 z');").append("\n");
SVGjs.append(" path.setAttributeNS(null,'fill', 'red');").append("\n");
SVGjs.append(" path.setAttributeNS(null,'stroke', 'red');").append("\n");
SVGjs.append(" path.setAttributeNS(null,'stroke-width', '0');").append("\n");
SVGjs.append(" marker.appendChild(path);").append("\n");
SVGjs.append(" defs.appendChild(marker);").append("\n");
SVGjs.append(" svg.appendChild(defs);").append("\n");


SVGjs.append(" var g;").append("\n");
SVGjs.append(" var txt;").append("\n");
SVGjs.append(" var rect;").append("\n");
SVGjs.append(" var path;").append("\n");
SVGjs.append(" var lnk;").append("\n");
        
        
XPath xPath = XPathFactory.newInstance().newXPath();
expression = "/bsc/header";
Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
if(node!=null && node instanceof Element) {
    NamedNodeMap attrs = node.getAttributes();
    id = attrs.getNamedItem("id").getNodeValue();
    w = assertValue(attrs.getNamedItem("width").getNodeValue());
    h = assertValue(attrs.getNamedItem("height").getNodeValue());
    x = assertValue(attrs.getNamedItem("x").getNodeValue());
    y = assertValue(attrs.getNamedItem("y").getNodeValue());
    
    SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
    SVGjs.append(" g.setAttributeNS(null,'id','"+id+"');").append("\n");
    SVGjs.append(" svg.appendChild(g);").append("\n");
    
    x_ = x;
    y_ = y + topPadding(HEADER_1);
    w_ = w;
    
    // título mapa
    expression = "/bsc/header/title";
    txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
    SVGjs.append(" txt = createText('"+txt+"','"+id+"_title"+"',"+x_+","+y_+","+HEADER_1+",'Verdana');").append("\n");
    SVGjs.append(" g.appendChild(txt);").append("\n");
    SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_1+","+x_+","+y_+");").append("\n");
    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
    SVGjs.append(" framingRect(rect,"+w_+","+vPadding(HEADER_1)+","+x_+","+y_+","+HEADER_1+");").append("\n");
    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
    
    y_ = y + vPadding(HEADER_1) + topPadding(HEADER_2) + BOX_SPACING;
    w_ = w/3;
    h_ = h - vPadding(HEADER_1) - BOX_SPACING;
    // pleca Mision
    SVGjs.append(" txt = createText('Mision','"+id+"_pms"+"',"+x_+","+y_+","+HEADER_2+",'Verdana');").append("\n");
    SVGjs.append(" g.appendChild(txt);").append("\n");
    SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+x_+","+y_+");").append("\n");
    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
    SVGjs.append(" framingRect(rect,"+w_+","+vPadding(HEADER_2)+","+x_+","+y_+","+HEADER_2+");").append("\n");
    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
    // pleca Vision
    SVGjs.append(" txt = createText('Vision','"+id+"_pvs"+"',"+(x_+2*w_)+","+y_+","+HEADER_2+",'Verdana');").append("\n");
    SVGjs.append(" g.appendChild(txt);").append("\n");
    SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+(x_+2*w_)+","+y_+");").append("\n");
    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
    SVGjs.append(" framingRect(rect,"+w_+","+vPadding(HEADER_2)+","+(x_+2*w_)+","+y_+","+HEADER_2+");").append("\n");
    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
    // logo
    expression = "/bsc/header/logo";
    node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        SVGjs.append(" rect = createRect('"+id+"_lg"+"',"+w_+","+h_+","+(x_+w_)+","+(y_-HEADER_2)+",0,0,'none',1,'red',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
    }

    // contenido Mision
    y_ = y_ + vPadding(HEADER_2);
    h_ = h_ - vPadding(HEADER_2);    
    expression = "/bsc/header/mission";
    txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
    SVGjs.append(" txt = createText('"+txt+"','"+id+"_ms"+"',"+x_+","+y_+",14,'Verdana');").append("\n");
    SVGjs.append(" g.appendChild(txt);").append("\n");
    SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
    SVGjs.append(" framingRect(rect,"+w_+","+h_+","+x_+","+y_+","+HEADER_2+");").append("\n");
    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
    // contenido Vision
    expression = "/bsc/header/vision";
    txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
    SVGjs.append(" txt = createText('"+txt+"','"+id+"_vs"+"',"+(x_+2*w_)+","+y_+",14,'Verdana');").append("\n");
    SVGjs.append(" g.appendChild(txt);").append("\n");
    SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+(x_+2*w_)+","+y_+");").append("\n");
    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
    SVGjs.append(" framingRect(rect,"+w_+","+h_+","+(x_+2*w_)+","+y_+","+HEADER_2+");").append("\n");
    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
}


String title;
y = y + HEADER_HEIGHT + BOX_SPACING_TOP;
SVGjs.append(" var y = "+y+";").append("\n");
StringBuilder info;
//lista de perspectivas
expression = "/bsc/perspective";
NodeList nlPersp = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
for(int j=0; j<nlPersp.getLength(); j++) {
    node = nlPersp.item(j);
    if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
        NamedNodeMap attrs = node.getAttributes();
        String pid = attrs.getNamedItem("id").getNodeValue();
        int pw = assertValue(attrs.getNamedItem("width").getNodeValue());
        //int ph = assertValue(attrs.getNamedItem("height").getNodeValue());
        int px = assertValue(attrs.getNamedItem("x").getNodeValue());
        
        // título de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/title";
        String title_ = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
        
SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
SVGjs.append(" g.setAttributeNS(null,'id','"+pid+"');").append("\n");
SVGjs.append(" svg.appendChild(g);").append("\n");
//SVGjs.append(" g.setAttributeNS(null,'transform','translate("+px+",'+y+')');").append("\n");
SVGjs.append(" g.setAttributeNS(null,'transform','translate(1,5)');").append("\n");
        

        
        SVGjs.append(" var y_;").append("\n");

        
        //diferenciadores de la perspectiva
        expression = "/bsc/perspective[@id='"+pid+"']/diffgroup[1]/diff";
        NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        boolean hasDifferentiators = nlDiffs.getLength()>0;
        if(hasDifferentiators) {
            SVGjs.append(" y = y + "+BOX_SPACING_TOP+";").append("\n");

            for(int k=0; k<nlDiffs.getLength(); k++) {
                Node nodeD = nlDiffs.item(k);
                if(nodeD!=null && nodeD.getNodeType()==Node.ELEMENT_NODE) {
                    attrs = nodeD.getAttributes();
                    String did = attrs.getNamedItem("id").getNodeValue();
                    w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                    //h_ = assertValue(attrs.getNamedItem("height").getNodeValue());
                    x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                    //y_ = assertValue(attrs.getNamedItem("y").getNodeValue());
                    //y_ = y_ + topPadding(HEADER_4);
                    SVGjs.append(" txt = createText('"+nodeD.getFirstChild().getNodeValue()+"','"+did+"',"+x_+",y,"+HEADER_4+",'Verdana');").append("\n");
                    SVGjs.append(" g.appendChild(txt);").append("\n");
                    SVGjs.append(" fixParagraphToWidth(txt,"+w_+","+x_+");").append("\n");
                    SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                    SVGjs.append(" framingRect(rect,"+w_+",rect.height.baseVal.value,"+x_+",y,"+HEADER_4+");").append("\n");
                    SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                }
            }
        }



    
        // temas de la perspectiva
        if(hasDifferentiators) {
            SVGjs.append(" y = y + rect.height.baseVal.value + "+BOX_SPACING_TOP+";").append("\n");
        }else {
            SVGjs.append(" y = y + "+BOX_SPACING_TOP+";").append("\n");
        }
        expression = "/bsc/perspective[@id='"+pid+"']/themes/theme";
        NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        for(int l=0; l<nlThms.getLength(); l++) {
            Node nodeT = nlThms.item(l);
            if(nodeT!=null && nodeT.getNodeType()==Node.ELEMENT_NODE) {
                attrs = nodeT.getAttributes();
                boolean isHidden = Boolean.parseBoolean(attrs.getNamedItem("hidden").getNodeValue());
                String tid = attrs.getNamedItem("id").getNodeValue();
                w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                
                if(!isHidden) {
                expression = "/bsc/perspective[@id='"+pid+"']/themes/theme[@id='"+tid+"']/title";
                title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
SVGjs.append(" txt = createText('"+title+"','"+tid+"',"+x_+",y,"+HEADER_3+",'Verdana');").append("\n");
SVGjs.append(" g.appendChild(txt);").append("\n");
SVGjs.append(" fixParagraphToWidth(txt,"+w_+","+x_+");").append("\n");
SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" framingRect(rect,"+w_+",rect.height.baseVal.value,"+x_+",y,"+HEADER_3+");").append("\n");
SVGjs.append(" g.insertBefore(rect,txt);").append("\n"); 
                }
                

                
                //lista de objetivos
                if(!isHidden) {
                    SVGjs.append(" y_ = y + rect.height.baseVal.value + "+BOX_SPACING_BOTTOM+";").append("\n"); 
                }else {
                    SVGjs.append(" y_ = y;").append("\n"); 
                }
                
                expression = "//theme[@id='"+tid+"']/obj";
                NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                for(int m=0; m<nlObjs.getLength(); m++) {
                    Node nodeO = nlObjs.item(m);
                    if(nodeO!=null && nodeO.getNodeType()==Node.ELEMENT_NODE) {
                        attrs = nodeO.getAttributes();
                        String oid = attrs.getNamedItem("id").getNodeValue();
                        String href = attrs.getNamedItem("href").getNodeValue();
                        w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                        x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                        txt = attrs.getNamedItem("status").getNodeValue();
                        
                        info = new StringBuilder();
                        expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/prefix";
                        info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                        info.append(" ");
                        expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/title";
                        info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                        info.append(" ");
                        expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/sponsor";
                        info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                        info.append(" ");
                        expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/frequency";
                        info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
// rectángulo objetivo
SVGjs.append(" lnk = createLink('"+href+"');").append("\n");
SVGjs.append(" g.appendChild(lnk);").append("\n");
SVGjs.append(" txt = createText('"+info+"','"+oid+"',"+x_+",y_,"+HEADER_5+",'Verdana');").append("\n");
SVGjs.append(" lnk.appendChild(txt);").append("\n");
SVGjs.append(" fixParagraphToWidth(txt,"+w_+","+x_+");").append("\n");
SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'fill','yellow');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'fill-opacity','0.5');").append("\n");
SVGjs.append(" rect.setAttributeNS(null,'stroke','yellow');").append("\n");
SVGjs.append(" rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
SVGjs.append(" rect.setAttributeNS(null, 'stroke-opacity','1');").append("\n");
SVGjs.append(" framingRect(rect,"+w_+",0,"+x_+",y_,"+HEADER_5+");").append("\n");
SVGjs.append(" g.insertBefore(rect,lnk);").append("\n");

SVGjs.append(" y_ = y_ + rect.height.baseVal.value + "+BOX_SPACING_BOTTOM+";").append("\n"); 
                    }
                } //lista de objetivos 
            } //tema
        } // lista de temas
        
        
        // caja de la perspectiva
        SVGjs.append(" rect = getBBoxAsRectElement(g);").append("\n");
        //SVGjs.append(" framingRect(rect,"+pw+","+0+","+px+",y,"+HEADER_5+");").append("\n");
        SVGjs.append(" if(rect.height.baseVal.value<120) {").append("\n");
        SVGjs.append("   rect.height.baseVal.value = 120;").append("\n");
        SVGjs.append(" }").append("\n");
        SVGjs.append(" var h_ = Math.round(rect.height.baseVal.value);").append("\n");
        SVGjs.append(" rect.height.baseVal.value = h_;").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'fill','green');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'fill-opacity','0.3');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null,'stroke','red');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
        SVGjs.append(" rect.setAttributeNS(null, 'stroke-opacity','1');").append("\n");
        SVGjs.append(" svg.appendChild(rect);").append("\n");
        // título de la perspectiva
        SVGjs.append(" txt = createText('"+title_+"','"+pid+"_pt"+"',"+px+",(y+h_),"+HEADER_4+",'Verdana');").append("\n");
        SVGjs.append(" txt.setAttribute('textLength',rect.height.baseVal.value);").append("\n");
        SVGjs.append(" txt.setAttribute('lengthAdjust','spacingAndGlyphs');").append("\n");
        SVGjs.append(" txt.setAttribute('transform','rotate(270,"+px+",'+(y+h_)+')');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        
        SVGjs.append(" y = y + h_ + "+MARGEN_BOTTOM+";").append("\n");
    } // perspectiva
} // lista de perspectivas


SVGjs.append("").append("\n");
SVGjs.append("").append("\n");
SVGjs.append("").append("\n");
SVGjs.append("").append("\n");
SVGjs.append("").append("\n");

/*SVGjs.append("function createLink(url) {").append("\n");
SVGjs.append("  var a = document.createElementNS(SVG_ ,'a');").append("\n");
SVGjs.append("  a.setAttributeNS(XLINK_,'href',url);").append("\n");
SVGjs.append("  a.setAttributeNS(null,'title','Ver detalle...');").append("\n");
SVGjs.append("  return a;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function createArrow(id,x1,y1,x2,y2) {").append("\n");
SVGjs.append("  var arrow = createPath(id,x1,y1,x2,y2);").append("\n");
SVGjs.append("  arrow.setAttributeNS(null, 'marker-end', 'url(#arrow_1)');").append("\n");
SVGjs.append("  return arrow;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function createPath(id,x1,y1,x2,y2) {").append("\n");
SVGjs.append("  var path = document.createElementNS(SVG_,'path');").append("\n");
SVGjs.append("  var d = 'M'+x1+','+y1'+' L'+(x1-5)+','+y1+' L'+(width-3)+','+y1+' L'+(width-3)+','+y2+' L'+x2+','+y2").append("\n");
SVGjs.append("  path.setAttributeNS(null, 'd', 'M'+x1+','+y1+' L'+x2+','+y2);").append("\n");
SVGjs.append("  path.style.stroke = '#000';").append("\n");
SVGjs.append("  path.style.strokeWidth = '3px';").append("\n");
//SVGjs.append("  path.style.markerEnd = '3px';").append("\n");
SVGjs.append("  ").append("\n");
SVGjs.append("  return path;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function createText(text,id,x,y,fontsize,fontfamily) {").append("\n");
SVGjs.append("  var txt = document.createElementNS(SVG_,'text');").append("\n");
SVGjs.append("  txt.setAttributeNS(null,'id',id);").append("\n");
SVGjs.append("  txt.setAttributeNS(null,'x',x);").append("\n");
SVGjs.append("  txt.setAttributeNS(null,'y',y);").append("\n");
SVGjs.append("  txt.setAttributeNS(null,'font-size',fontsize);").append("\n");
SVGjs.append("  txt.setAttributeNS(null,'font-family',fontfamily);").append("\n");
SVGjs.append("  txt.textContent=text;").append("\n");
SVGjs.append("  return txt;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function createRect(id,width,height,x,y,rx,ry,fill,fillopacity,stroke,strokewidth, strokeopacity) {").append("\n");
SVGjs.append("  var rect = document.createElementNS(SVG_,'rect');").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'id',id);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'width',width);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'height',height);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'x',x);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'y',y);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'rx',rx);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'ry',ry);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'fill',fill);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',fillopacity);").append("\n");
SVGjs.append("  rect.setAttributeNS(null,'stroke',stroke);").append("\n");
SVGjs.append("  rect.setAttributeNS(null, 'stroke-width',strokewidth);").append("\n");
SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',strokeopacity);").append("\n");
SVGjs.append("  return rect;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function framingRect(rect,width, height, x, y) {").append("\n");
SVGjs.append("    rect.x.baseVal.value = x;").append("\n");
SVGjs.append("    rect.y.baseVal.value = y;").append("\n");
SVGjs.append("    rect.width.baseVal.value = width;").append("\n");
SVGjs.append("    rect.height.baseVal.value = height;").append("\n");
SVGjs.append("    rect.setAttributeNS(null,'fill','none');").append("\n");
SVGjs.append("    rect.setAttributeNS(null,'stroke','black');").append("\n");
SVGjs.append("    rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
SVGjs.append("}").append("\n");
        
SVGjs.append("function fixParagraphAtBounding(text_element, width, height, x, y) {").append("\n");
SVGjs.append("    var dy = getFontSize(text_element);").append("\n");
SVGjs.append("    var text = text_element.textContent;").append("\n");
SVGjs.append("    var words = text.split(' ');").append("\n");
SVGjs.append("    var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
SVGjs.append("    var text_node = document.createTextNode(words[0]);").append("\n");

SVGjs.append("    text_element.textContent='';").append("\n");
SVGjs.append("    tspan_element.appendChild(text_node);").append("\n");
SVGjs.append("    text_element.appendChild(tspan_element);").append("\n");

SVGjs.append("    var h;").append("\n");

SVGjs.append("    for(var i=1; i<words.length; i++)").append("\n");
SVGjs.append("    {").append("\n");
SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("        var len = tspan_element.firstChild.data.length;").append("\n");
SVGjs.append("        tspan_element.firstChild.data += ' ' + words[i];").append("\n");

SVGjs.append("        if (tspan_element.getComputedTextLength() > width)").append("\n");
SVGjs.append("        {").append("\n");
SVGjs.append("            dy = dy - (h/height);").append("\n");
SVGjs.append("            text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
SVGjs.append("            var childElements = text_element.getElementsByTagName('tspan');").append("\n");
SVGjs.append("            for (var j=0; j<childElements.length; j++) {").append("\n");
SVGjs.append("                if(childElements[j].getAttribute('dy')) {").append("\n");
SVGjs.append("                    childElements[j].setAttributeNS(null,'dy',dy);").append("\n");
SVGjs.append("                }").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("            h = getBoundingHeight(text_element);").append("\n");

SVGjs.append("            if (tspan_element.getComputedTextLength() > width)").append("\n");
SVGjs.append("            {").append("\n");
SVGjs.append("                tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);").append("\n");  // Remove added word

SVGjs.append("                var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
SVGjs.append("                tspan_element.setAttributeNS(null, 'x', x);").append("\n");
SVGjs.append("                tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
SVGjs.append("                text_node = document.createTextNode(words[i]);").append("\n");
SVGjs.append("                tspan_element.appendChild(text_node);").append("\n");
SVGjs.append("                text_element.appendChild(tspan_element);").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("        }").append("\n");
SVGjs.append("    }").append("\n");

SVGjs.append("    h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("    while(h>height && dy>0) {").append("\n");
SVGjs.append("        dy--;").append("\n");
SVGjs.append("        text_element.setAttributeNS(null, 'font-size', dy);").append("\n");

SVGjs.append("        var childElements = text_element.getElementsByTagName('tspan');").append("\n");
SVGjs.append("        for (var i=0; i < childElements.length; i++) {").append("\n");
SVGjs.append("            if(childElements[i].getAttribute('dy')) {").append("\n");
SVGjs.append("                childElements[i].setAttributeNS(null,'dy',dy-0.5);").append("\n");
SVGjs.append("            }").append("\n");
SVGjs.append("        }").append("\n");
SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
SVGjs.append("    }").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append(" function getFontSize(text_element) {").append("\n");
SVGjs.append("  var fs_ = window.getComputedStyle(text_element, null).getPropertyValue('font-size');").append("\n");
SVGjs.append("  var fs = fs_.replace( /\\D+/g, '');").append("\n");
SVGjs.append("  return fs;").append("\n");
SVGjs.append(" }").append("\n");

SVGjs.append(" function getBoundingHeight(objNode) {").append("\n");
SVGjs.append("  if(!objNode.getBBox) {").append("\n");
SVGjs.append("      if(objNode.correspondingUseElement) {").append("\n");
SVGjs.append("          objNode = objNode.correspondingUseElement;").append("\n");
SVGjs.append("      }").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
SVGjs.append("  return bbox.height;").append("\n");
SVGjs.append(" }").append("\n");

SVGjs.append(" function getBBoxAsRectElement(objNode) {").append("\n");
SVGjs.append("  if(!objNode.getBBox) {").append("\n");
SVGjs.append("    if(objNode.correspondingUseElement)").append("\n");
SVGjs.append("      objNode = objNode.correspondingUseElement;").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
SVGjs.append("  var rect = document.createElementNS(SVG_, 'rect');").append("\n");
SVGjs.append("  rect.x.baseVal.value = bbox.x;").append("\n");
SVGjs.append("  rect.y.baseVal.value = bbox.y;").append("\n");
SVGjs.append("  rect.width.baseVal.value = bbox.width;").append("\n");
SVGjs.append("  rect.height.baseVal.value = bbox.height;").append("\n");
SVGjs.append("  return rect;").append("\n");
SVGjs.append(" }").append("\n");*/

        SVGjs.append("</script>");
        return SVGjs.toString();
    }
    
    private int assertValue(final String textVal)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = 0;
        }catch(NullPointerException nulle) {
            val = 0;
        }
        return val;
    }
    
    private int vPadding(int value) {
        return value+PADDING_TOP+PADDING_DOWN;
    }
    
    private int topPadding(int value) {
        return value+PADDING_TOP;
    }
    
    private int bottomPadding(int value) {
        return value+PADDING_TOP;
    }
    
    private int hPadding(int value) {
        return value+PADDING_LEFT+PADDING_RIGHT;
    }
}
