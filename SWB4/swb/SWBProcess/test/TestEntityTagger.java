/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

/**
 *
 * @author hasdai
 */
public class TestEntityTagger {
    XWPFDocument document = null;

    @Test
    public void testPOI(HashMap<String, String> bookmarksMap) throws FileNotFoundException, IOException, InvalidFormatException, ParserConfigurationException, TransformerConfigurationException, TransformerException {
        //HWPFDocument document = new HWPFDocument(new FileInputStream(new File("/home/hasdai/test.docx")));
        document = new XWPFDocument(new FileInputStream(new File("/home/hasdai/Documentos/INFOTEC/Proyectos/SWP/TRANSFERENCIA/CFE/FU-PE-02-A2F1_DocEstraTemplate.docx")));

        //Range r = document.getRange();
        //System.out.println(r.text());
        Iterator<XWPFHeader> headers = document.getHeaderList().iterator();
        while (headers.hasNext()) {
            XWPFHeader header = headers.next();
            getBookmarks(header.getListParagraph().iterator(), bookmarksMap);
        }

        Iterator<XWPFTable> tables = document.getTablesIterator();

        //POIXMLDocument _doc = new XWPFDocument(new FileInputStream("/home/hasdai/test.docx"));

//        XWPFWordExtractor extr = new XWPFWordExtractor(document);
        while (tables.hasNext()) {
            XWPFTable table = tables.next();
            Iterator<XWPFTableRow> rows = table.getRows().iterator();
            while (rows.hasNext()) {
                XWPFTableRow row = rows.next();
                Iterator<XWPFTableCell> cells = row.getTableCells().iterator();
                while (cells.hasNext()) {
                    XWPFTableCell cell = cells.next();
                    getBookmarks(cell.getParagraphs().iterator(), bookmarksMap);
                }
            }
        }

        Iterator<XWPFParagraph> paragraphs = document.getParagraphsIterator();
        while (paragraphs.hasNext()) {
            XWPFParagraph paragraph = paragraphs.next();
            getParagraphBookmarks(paragraph, bookmarksMap);
        }

        document.write(new FileOutputStream("/home/hasdai/out.docx"));
    }

    public void getParagraphBookmarks(XWPFParagraph paragraph, HashMap<String, String> map) {
        CTP ctp = paragraph.getCTP();
        Iterator<CTBookmark> bookmarks = ctp.getBookmarkStartList().iterator();

        //System.out.println("c:"+C);
        int i=0;
        while (bookmarks.hasNext()) {
            CTBookmark bookmark = bookmarks.next();
            if (map.containsKey(bookmark.getName())) {
                CTR ctr = ctp.addNewR();
                CTText text = ctr.addNewT();
                text.setStringValue(map.get(bookmark.getName()));
            }
//            if (bookmark.getName().equals("mision")) {
//                //CTR ctr = ctp.addNewR();
//                //XWPFRun run = paragraph.getRun(ctr);
//                createTable(bookmark.newCursor());
//            }
//            CTR ctr = ctp.addNewR();
//            CTText text = ctr.addNewT();
//            String val = "";
//            System.out.println("--"+bookmark.getName());
//            System.out.println("-->"+bookmark.xgetColFirst());
//            if (bookmark.getName().equals("mision")) {
//                val = "Hacemos posible que las organizaciones y las personas se desarrollen mediante el apropiamiento de las tecnologías de información y comunicaciones (TIC).";
//            } else if (bookmark.getName().equals("vision")) {
//                val="Infotec es un centro público de investigación, innovación y servicios que hace posible la instrumentación de proyectos clave para acelerar la incursión de México en la sociedad de la información y el conocimiento (SIC).";
//            } else if (bookmark.getName().equals("objetivos")) {
//                val="Como institución de gobierno, en Infotec estamos comprometidos a ofrecer permanentemente mejores servicios para los ciudadanos.\n"+
//                "Apoyamos todas aquellas iniciativas encaminadas a mejorar la eficiencia gubernamental de las dependencias públicas y colaboramos con otras organizaciones en la adopción de estándares, mejores prácticas y metodologías nacionales e internacionales de reconocida calidad y aplicación exitosa.\n"+
//                "En esta sección exponemos algunas de las actividades que realizamos en relación con nuestros compromisos institucionales para ser una mejor empresa pública, líder en el sector de las tecnologías de la información y la comunicación (TIC).";
//            }
//            CTBookmark bk = null;
//            text.setStringValue(val);
            //i++;
//            text.newCursor().toChild(0);
//            text.newCursor().setTextValue("hola");//setStringValue(val);
//            System.out.println("fin: "+bookmark.xmlText());
//            System.out.println("::"+bookmark.getName());
        }
    }

    public void getBookmarks(Iterator<XWPFParagraph> paragraphs, HashMap<String, String> map) throws TransformerConfigurationException, TransformerException {
        while (paragraphs.hasNext()) {
            XWPFParagraph paragraph = paragraphs.next();
            getParagraphBookmarks(paragraph, map);
        }
    }

    public void createTable(XmlCursor cursor) {
        // New 2x2 table
        XWPFTable tableOne = document.insertNewTbl(cursor);
        XWPFTableRow tableOneRowOne = tableOne.getRow(0);
        tableOneRowOne.getCell(0).setText("Hello");
        tableOneRowOne.addNewTableCell().setText("World");
        tableOneRowOne.addNewTableCell().setText("World2");

        XWPFTableRow tableOneRowTwo = tableOne.createRow();
        tableOneRowTwo.getCell(0).setText("This is");
        tableOneRowTwo.getCell(1).setText("a table");

        //Add a break between the tables
//        document.createParagraph().createRun().addBreak();

        // New 3x3 table
//        XWPFTable tableTwo = document.createTable();
//        XWPFTableRow tableTwoRowOne = tableTwo.getRow(0);
//        tableTwoRowOne.getCell(0).setText("col one, row one");
//        tableTwoRowOne.addNewTableCell().setText("col two, row one");
//        tableTwoRowOne.addNewTableCell().setText("col three, row one");
//
//        XWPFTableRow tableTwoRowTwo = tableTwo.createRow();
//        tableTwoRowTwo.getCell(0).setText("col one, row two");
//        tableTwoRowTwo.getCell(1).setText("col two, row two");
//        tableTwoRowTwo.getCell(2).setText("col three, row two");
//
//        XWPFTableRow tableTwoRowThree = tableTwo.createRow();
//        tableTwoRowThree.getCell(0).setText("col one, row three");
//        tableTwoRowThree.getCell(1).setText("col two, row three");
//        tableTwoRowThree.getCell(2).setText("col three, row three");
    }
}
