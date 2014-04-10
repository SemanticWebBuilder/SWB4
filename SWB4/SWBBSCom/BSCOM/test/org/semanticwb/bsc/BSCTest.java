
package org.semanticwb.bsc;

import java.util.Iterator;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.tracing.Risk;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author carlos.ramos
 */
public class BSCTest {
    final private String modelId = "DADT";
    final private String periodId = "16";
    
    public BSCTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTORE);
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
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listValidInitiative method, of class BSC.
     */
    //@Test
    public void testListValidInitiative() {
        System.out.println("listValidInitiative");
        BSC instance = null;
        List<Initiative> expResult = null;
        List<Initiative> result = instance.listValidInitiative();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listPeriods method, of class BSC.
     */
    //@Test
    public void testListPeriods_0args() {
        System.out.println("listPeriods");
        BSC instance = null;
        Iterator<Period> expResult = null;
        Iterator<Period> result = instance.listPeriods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listPeriods method, of class BSC.
     */
    //@Test
    public void testListPeriods_boolean() {
        System.out.println("listPeriods");
        boolean ascendent = false;
        BSC instance = null;
        Iterator<Period> expResult = null;
        Iterator<Period> result = instance.listPeriods(ascendent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listValidPeriods method, of class BSC.
     */
    //@Test
    public void testListValidPeriods() {
        System.out.println("listValidPeriods");
        BSC instance = null;
        List<Period> expResult = null;
        List<Period> result = instance.listValidPeriods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listValidPerspectives method, of class BSC.
     */
    //@Test
    public void testListValidPerspectives() {
        System.out.println("listValidPerspectives");
        BSC instance = null;
        List<Perspective> expResult = null;
        List<Perspective> result = instance.listValidPerspectives();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listValidRisks method, of class BSC.
     */
    //@Test
    public void testListValidRisks() {
        System.out.println("listValidRisks");
        BSC instance = null;
        List<Risk> expResult = null;
        List<Risk> result = instance.listValidRisks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDom method, of class BSC.
     */
    @Test
    public void testGetDom_0args() {
        System.out.println("getDom");
        BSC instance = null;
        Document map = null;
        if(BSC.ClassMgr.hasBSC(modelId))
        {   
            instance = BSC.ClassMgr.getBSC(modelId);
            System.out.println("model: "+instance);            
            setUpSessionUser(instance);
            Document result = instance.getDom();
            System.out.println("xml:\n"+SWBUtils.XML.domToXml(result));
            
            try {
                map = getDom(result);
                System.out.println("map:\n"+SWBUtils.XML.domToXml(map));
            }catch(XPathExpressionException xpee) {
                fail("Fallo algun xpath");
            }
            
            try {
                String svg = getSvg(map);
                System.out.println("svg:\n"+svg);
            }catch(XPathExpressionException xpee) {
                fail("Fallo algun xpath");
            }
        }
        else
        {
            fail("No existen operacion con id "+modelId);
        }
    }
    
    
    public static final String HEADER_PREFIX = "head_";
    public static final int MARGEN_LEFT = 12; // Especifica el margen izquierdo del rectángulo de una perspectiva
    public static final int MARGEN_RIGHT = 100; // Especifica el margen derecho del rectángulo de una perspectiva
    public static final int MARGEN_TOP = 20; // Especifica el margen superior del rectángulo de una perspectiva
    public static final int MARGEN_BOTTOM = 20; // Especifica el margen inferior del rectángulo de una perspectiva
    
    public static final int HEADER_HEIGHT = 150; // altura del encabezado
    public static final int HEADER_1 = 24; // tamaño de fuente para título del mapa
    public static final int HEADER_2 = 18; // tamaño de fuente para misión, visión
    public static final int HEADER_3 = 16; // tamaño de fuente para temas
    public static final int HEADER_4 = 14; // tamaño de fuente para diferenciadores
    public static final int HEADER_5 = 12; // tamaño de fuente para objetivos
    
    public static final int BOX_SPACING = 16; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_LEFT = 15; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_RIGHT = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_TOP = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_BOTTOM = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    
    public static final int PADDING_TOP = 4; // Especifica el espacio libre arriba entre rectángulos para pintar las ligas
    public static final int PADDING_LEFT = 2; // Especifica el espacio libre a la izquieerda entre rectángulos para pintar las ligas
    public static final int PADDING_RIGHT = 2; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final int PADDING_DOWN = 4; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final String SVG_NS_URI = "http://www.w3.org/2000/svg";
    public static final String XLNK_NS_URI = "http://www.w3.org/1999/xlink";
    
    public Document getDom(Document documentBSC) throws XPathExpressionException, NumberFormatException
    {
//        Resource base = getResourceBase();
//        final BSC scorecard = (BSC)base.getWebSite();
//        Document documentBSC = scorecard.getDom();
//        setWidth(assertValue(base.getAttribute("width", "1024")));
//        setHeight(assertValue(base.getAttribute("height", "1400")));
        int width = 1024;
        int height = 1400;
        //final Period period = getPeriod(request);
        //Document documentBSC = scorecard.getDom(period);
        Element bsc = documentBSC.getDocumentElement();
        
        Document map = SWBUtils.XML.getNewDocument();
        Element root = map.createElement("riskmap");
        root.setAttribute("id", bsc.getAttribute("id"));
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
        map.appendChild(root);
        
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node node;
        String expression;
        Element e;
        
        //header
        Element header = map.createElement("header");
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER_HEIGHT));
        header.setAttribute("x", Integer.toString(PADDING_LEFT));
        header.setAttribute("y", Integer.toString(PADDING_TOP));
        root.appendChild(header);
        
        
        expression = "/bsc/title";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            e = map.createElement("title");
            e.appendChild(map.createTextNode(node.getFirstChild().getNodeValue()));
            header.appendChild(e);
//            header.appendChild(node.cloneNode(true));
        }
        
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            NamedNodeMap attrs = node.getAttributes();
            e = map.createElement("logo");
            e.setAttribute("src", attrs.getNamedItem("src").getNodeValue());
            e.setAttribute("title", attrs.getNamedItem("title").getNodeValue());
            header.appendChild(e);
        }
        
        //riesgos
        expression = "/bsc/risks/risk";
        NodeList nlRisks = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        final int nlRisksCount = nlRisks.getLength();
        if(nlRisks.getLength()>0) {
            Element riskgroup = map.createElement("risks");
            root.appendChild(riskgroup);
            for(int k=0; k<nlRisksCount; k++) {
                node = nlRisks.item(k);
                if(node!=null && node.getNodeType()==Node.ELEMENT_NODE)
                {
                    NamedNodeMap attrs = node.getAttributes();
                    e = map.createElement("risk");
                    e.setAttribute("id", attrs.getNamedItem("id").getNodeValue());
                    e.setAttribute("prefix", attrs.getNamedItem("prefix").getNodeValue());
                    e.setAttribute("likehood", attrs.getNamedItem("likehood").getNodeValue());
                    e.setAttribute("impact", attrs.getNamedItem("impact").getNodeValue());
                    e.appendChild(map.createTextNode(node.getFirstChild().getNodeValue()));
                    riskgroup.appendChild(e);
                }
            }
        }
        return map;
    }
    
    public String getSvg(Document map) throws XPathExpressionException, NumberFormatException
    {
        final int width, height;
        int x, y=BOX_SPACING_TOP, w, h;
        Node node;
        NamedNodeMap attrs;
        String expression;
        String txt;
        
        Element root = map.getDocumentElement();
        XPath xPath = XPathFactory.newInstance().newXPath();        
        width = assertValue(root.getAttribute("width"), 1024);
        height = assertValue(root.getAttribute("height"), 1400); 
        StringBuilder SVGjs = new StringBuilder();
        
        SVGjs.append("<script type=\"text/javascript\">").append("\n");
        SVGjs.append(" var SVG_ = '"+SVG_NS_URI+"';").append("\n");
        SVGjs.append(" var XLINK_ = '"+XLNK_NS_URI+"';").append("\n");
        SVGjs.append(" var svg = document.createElementNS(SVG_,'svg'); ").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'id','"+root.getAttribute("id")+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'width','"+width+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'height','"+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'viewBox','0,0,"+width+","+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'version','1.1');").append("\n");
        SVGjs.append(" document.body.appendChild(svg);").append("\n");
        
        SVGjs.append(" var defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs');").append("\n");
        SVGjs.append(" svg.appendChild(defs);").append("\n");
        
        SVGjs.append(" var w;").append("\n");       // anchura
        SVGjs.append(" var g;").append("\n");       // grupo
        SVGjs.append(" var txt;").append("\n");     // texto
        SVGjs.append(" var rect;").append("\n");    // elemento
        SVGjs.append(" var path;").append("\n");    // flecha de la relación causa/efecto
        SVGjs.append(" var lnk;").append("\n");     // liga
        
        // encabezado del mapa
        expression = "/riskmap/header";
        node = (Node) xPath.compile(expression).evaluate(map, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
            SVGjs.append(" svg.appendChild(g);").append("\n");
            
            attrs = node.getAttributes();
            x = assertValue(attrs.getNamedItem("x").getNodeValue(), PADDING_LEFT);
            y = assertValue(attrs.getNamedItem("y").getNodeValue(), PADDING_TOP);
            w = assertValue(attrs.getNamedItem("width").getNodeValue(), width);
            h = assertValue(attrs.getNamedItem("height").getNodeValue(), height);
            
            // título mapa
            expression = "/bsc/header/title";
            txt = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
            SVGjs.append(" txt = createText('"+txt+"',"+(x+w/2)+","+y+","+HEADER_1+",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            
            // logo mapa
            
        }
        
        // plano cartesiano
        x = MARGEN_LEFT;
        y += HEADER_1+MARGEN_TOP;
        int x_ = 0;
        int y_ = 0;
        int w_ = 150;
        int h_ = 150;
        
        // grupo
        SVGjs.append("").append("\n");
        SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
        SVGjs.append(" svg.appendChild(g);").append("\n");
        SVGjs.append(" g.setAttributeNS(null,'transform','translate("+x+","+y+")');").append("\n");
        
//SVGjs.append(" var ctmOffset = g.getCTM();").append("\n");
//SVGjs.append(" var pos = svg.createSVGPoint();").append("\n");
//SVGjs.append(" pos.x = rect.x.baseVal.value;").append("\n");
//SVGjs.append(" pos.y = rect.y.baseVal.value;").append("\n");
//SVGjs.append(" pos = pos.matrixTransform(ctmOffset);").append("\n");        
        
        // eje |----|----|----|----|----|
        SVGjs.append(" path = document.createElementNS(SVG_,'path');").append("\n");
        SVGjs.append(" path.setAttributeNS(null, 'id', 'axis');").append("\n");
        SVGjs.append(" path.setAttributeNS(null, 'd', 'M0,0 L"+2*w_+",0);").append("\n");
        SVGjs.append(" path.setAttributeNS(null, 'fill', 'none');").append("\n");
        SVGjs.append(" defs.appendChild(path);").append("\n");
        // escala 1  2  3  4  5...
        
//        SVGjs.append(" path = createPath('abscissa',"+x_+","+(y_+2*h_+2)+","+(x_+2*w_)+","+(y_+2*h_+20)+");").append("\n");
//        SVGjs.append(" defs.appendChild(path);").append("\n");
        
        SVGjs.append(" var use = document.createElementNS(SVG_,'use');").append("\n");
        SVGjs.append(" use.setAttributeNS(XLINK_,'xlink:href','#axis');").append("\n");
        
        //<use xlink:href="#geometry" stroke="#000000" stroke-width="4" stroke-dasharray="1,20" />
        SVGjs.append(" g.appendChild(use);").append("\n");
        SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
//        SVGjs.append(" txt.setAttributeNS(null,'x',"+x_+");").append("\n");
//        SVGjs.append(" txt.setAttributeNS(null,'y',"+(y_+2*h_+BOX_SPACING_BOTTOM)+");").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-size',14);").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
SVGjs.append(" var txtpth = document.createElementNS(SVG_, 'textPath');").append("\n");
SVGjs.append(" txtpth.setAttributeNS(XLINK_,'xlink:href', '#abscissa');").append("\n");
SVGjs.append(" txtpth.setAttributeNS(null,'fill', '#000000');").append("\n");
SVGjs.append(" txtpth.setAttributeNS(null,'color', '#000000');").append("\n");
SVGjs.append(" txtpth.textContent = '0 1 2 3 4 5 6 7 8 9 10'; ").append("\n");
SVGjs.append(" txt.appendChild(txtpth);").append("\n");

        SVGjs.append(" ").append("\n");
        // eje y (ordenadas)
        SVGjs.append(" path = createPath('ordinate',"+(x_-BOX_SPACING_LEFT)+","+y_+","+(x-BOX_SPACING_LEFT)+","+(y+2*h_)+");").append("\n");
        SVGjs.append(" defs.appendChild(path);").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        SVGjs.append(" ").append("\n");
        
        // cuadrantes
        SVGjs.append(" rect = createRect('quadrant_"+0+"_lg"+"',"+w_+","+h_+","+x_+","+y_+",0,0,'red',1,'red',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+1+"_lg"+"',"+w_+","+h_+","+(x_+w_+1)+","+y_+",0,0,'blue',1,'blue',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+2+"_lg"+"',"+w_+","+h_+","+x_+","+(y_+h_+1)+",0,0,'green',1,'green',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+3+"_lg"+"',"+w_+","+h_+","+(x_+w_+1)+","+(y_+h_+1)+",0,0,'yellow',1,'yellow',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        
        SVGjs.append("//---------------").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");
        
        
        
        
        // funciones
        SVGjs.append("function createLink(url) {").append("\n");
        SVGjs.append("  var a = document.createElementNS(SVG_ ,'a');").append("\n");
        SVGjs.append("  a.setAttributeNS(XLINK_,'xlink:href',url);").append("\n");
        SVGjs.append("  a.setAttributeNS(null,'title','Ver detalle...');").append("\n");
        SVGjs.append("  return a;").append("\n");
        SVGjs.append("}").append("\n");
        
        SVGjs.append("function createPath(id,x1,y1,x2,y2) {").append("\n");
        SVGjs.append("  var path = document.createElementNS(SVG_,'path');").append("\n");
        SVGjs.append("  var d = 'M'+x1+','+y1+' L'+x2+','+y2;").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'id', id);").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'd', d);").append("\n");
        SVGjs.append("  path.style.fill = 'none';").append("\n");
        SVGjs.append("  path.style.stroke = '#000000';").append("\n");
        SVGjs.append("  path.style.strokeWidth = '1';").append("\n");
        SVGjs.append("  return path;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createText(text,x,y,fontsize,fontfamily) {").append("\n");
        SVGjs.append("  var txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'x',x);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'y',y);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-size',fontsize);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-family',fontfamily);").append("\n");
        SVGjs.append("  txt.textContent=text;").append("\n");
        SVGjs.append("  return txt;").append("\n");
        SVGjs.append("}").append("\n");
        
        SVGjs.append("function createCircle(id,cx,cy,r,fill,fillopacity,stroke,strokewidth, strokeopacity) {").append("\n");
        SVGjs.append("  var circle = document.createElementNS(SVG_,'circle');").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'cx',cx);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'cy',cy);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'r',r);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'fill',fill);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'fill-opacity',fillopacity);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'stroke',stroke);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null, 'stroke-width',strokewidth);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null, 'stroke-opacity',strokeopacity);").append("\n");
        SVGjs.append("  return circle;").append("\n");
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

        SVGjs.append("function framingRect(rect,id,width, height, x, y) {").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  //rect.x.baseVal.value = x;").append("\n");
        SVGjs.append("  //rect.y.baseVal.value = y;").append("\n");
        SVGjs.append("  rect.width.baseVal.value = width;").append("\n");
        SVGjs.append("  //rect.height.baseVal.value = height;").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill','#660000');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',0.1);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'stroke','#660000');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',0.3);").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append(" function fixParagraphAtBounding(text_element, width, height, x, y) {").append("\n");
        SVGjs.append("     var dy = getFontSize(text_element);").append("\n");
        SVGjs.append("     if(dy<13) {").append("\n");
        SVGjs.append("     	createParagraph(text_element, width, height, x, y);").append("\n");
        SVGjs.append("     }else {").append("\n");
        SVGjs.append("     	fixParagraphAtBounding_(text_element, width, height, x, y, dy);").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function fixParagraphAtBounding_(text_element, width, height, x, y, dy) {").append("\n");
        SVGjs.append("     var text = text_element.textContent;").append("\n");
        SVGjs.append("     var words = text.split(' ');").append("\n");
        SVGjs.append("     var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("     var text_node = document.createTextNode(words[0]);").append("\n");
        SVGjs.append("     text_element.textContent='';").append("\n");
        SVGjs.append("     tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("     text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("     var h;").append("\n");
        SVGjs.append("     for(var i=1; i<words.length; i++) {").append("\n");
        SVGjs.append("         h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("         var len = tspan_element.firstChild.data.length;").append("\n");
        SVGjs.append("         tspan_element.firstChild.data += ' ' + words[i];").append("\n");
        SVGjs.append("         if (tspan_element.getComputedTextLength() > width) {").append("\n");
        SVGjs.append("             dy = dy - (h/height);").append("\n");
        SVGjs.append("             text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
        SVGjs.append("             var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("             for (var j=0; j<childElements.length; j++) {").append("\n");
        SVGjs.append("                 if(childElements[j].getAttribute('dy')) {").append("\n");
        SVGjs.append("                     childElements[j].setAttributeNS(null,'dy',dy);").append("\n");
        SVGjs.append("                 }").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("             h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("             if (tspan_element.getComputedTextLength() > width) {").append("\n");
        SVGjs.append("                 tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);").append("\n");
        SVGjs.append("                 var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("                 tspan_element.setAttributeNS(null, 'x', x);").append("\n");
        SVGjs.append("                 tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
        SVGjs.append("                 text_node = document.createTextNode(words[i]);").append("\n");
        SVGjs.append("                 tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("                 text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("         }").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append("     h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("     while(h>height && dy>0) {").append("\n");
        SVGjs.append("         dy--;").append("\n");
        SVGjs.append("         text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
        SVGjs.append("         var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("         for (var i=0; i < childElements.length; i++) {").append("\n");
        SVGjs.append("             if(childElements[i].getAttribute('dy')) {").append("\n");
        SVGjs.append("                 childElements[i].setAttributeNS(null,'dy',dy-0.5);").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("         }").append("\n");
        SVGjs.append("         h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append(" }").append("\n");

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
        SVGjs.append(" }").append("\n");
        
        SVGjs.append("function createParagraph(text_element, width, height, x, y) {").append("\n");
        SVGjs.append("    fixParagraphToWidth(text_element, width, x);").append("\n");
        SVGjs.append("    fixParagraphToHeight(text_element, height);").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function fixParagraphToWidth(text_element, width, x) {").append("\n");
        SVGjs.append("    var dy = getFontSize(text_element);").append("\n");
        SVGjs.append("    var text = text_element.textContent;").append("\n");
        SVGjs.append("    var words = text.split(' ');").append("\n");
        SVGjs.append("    var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("    var text_node = document.createTextNode(words[0]);").append("\n");

        SVGjs.append("    text_element.textContent='';").append("\n");
        SVGjs.append("    tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("    text_element.appendChild(tspan_element);").append("\n");

        SVGjs.append("    for(var i=1; i<words.length; i++)").append("\n");
        SVGjs.append("    {").append("\n");
        SVGjs.append("        var len = tspan_element.firstChild.data.length;").append("\n");
        SVGjs.append("        tspan_element.firstChild.data += ' ' + words[i];").append("\n");

        SVGjs.append("        if (tspan_element.getComputedTextLength() > width)").append("\n");
        SVGjs.append("        {").append("\n");
        SVGjs.append("            tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);  // Remove added word").append("\n");

        SVGjs.append("            var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("            tspan_element.setAttributeNS(null, 'x', x);").append("\n");
        SVGjs.append("            tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
        SVGjs.append("            text_node = document.createTextNode(words[i]);").append("\n");
        SVGjs.append("            tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("            text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("    }").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function fixParagraphToHeight(text_element, height) {").append("\n");
        SVGjs.append("    var fs = getFontSize(text_element);").append("\n");
        SVGjs.append("    var h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    while(h>height) {").append("\n");
        SVGjs.append("        fs--;").append("\n");
        SVGjs.append("        text_element.setAttributeNS(null, 'font-size', fs);").append("\n");
        SVGjs.append("        var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("        for (var i=0; i < childElements.length; i++) {").append("\n");
        SVGjs.append("            if(childElements[i].getAttribute('dy')) {").append("\n");
        SVGjs.append("                childElements[i].setAttributeNS(null,'dy',fs-0.5);").append("\n");
        SVGjs.append("            }").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    }").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("</script>").append("\n");
        return SVGjs.toString();
    }
    
    private int assertValue(final String textVal, int defvalue)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = defvalue;
        }catch(NullPointerException nulle) {
            val = defvalue;
        }
        return val;
    }
    
    
    

    /**
     * Test of getDom method, of class BSC.
     */
    //@Test
    public void testGetDom_Period() {
        System.out.println("getDom");
        Period period = null;
        BSC instance = null;
        Document expResult = null;
        Document result = instance.getDom(period);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLogo method, of class BSC.
     */
    //@Test
    public void testGetLogo() {
        System.out.println("getLogo");
        BSC instance = null;
        String expResult = "";
        String result = instance.getLogo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
