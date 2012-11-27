/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticClassIterator;
import org.semanticwb.platform.SemanticModel;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorResources {

    public CodeGeneratorResources() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public Iterator<SemanticModel> getModels(String models[], File basepath) {
        ArrayList arr = new ArrayList();
        for (int i = 0; i < models.length; i++) {
            String m = models[i];
            String pref = models[i];
            File mf = null;
            if (m.startsWith("/")) {
                mf = new File(basepath, m);
            } else {
                mf = new File(basepath, SWBUtils.TEXT.replaceAll(m, ".", "/"));
            }
            System.out.println("OWL File:" + mf.toString());
            SemanticModel model = SWBPlatform.getSemanticMgr().readRDFFile(m, mf.toString());
            if (model == null) {
                System.out.println("File Not Found:" + mf.toString());
            }
            if (model != null) {
                arr.add(model);
            }
        }
        return arr.iterator();
    }

    @Test
    public void generateCP() {

        try {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path + "../../src");

            String models[] = {"/org/semanticwb/portal/resources/sem/directory/Directory.owl"};

            Iterator<SemanticModel> it = getModels(models, dir);
            while (it.hasNext()) {
                SemanticModel model = it.next();
                SWBPlatform.getSemanticMgr().getSchema().addSubModel(model, false);

                Iterator<SemanticClass> tpcit = new SemanticClassIterator(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().listClasses());
                while (tpcit.hasNext()) {
                    SemanticClass cls = tpcit.next();
                    SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(cls.getURI());
                }

                String nsb = model.getNameSpace();
                System.out.println(nsb);
                CodeGenerator codeGeneration = new CodeGenerator();
                codeGeneration.generateCodeByNamespace(nsb, false, dir);
            }
//            CodeGenerator codeGeneration = new CodeGenerator(dir, sPakage);
//            codeGeneration.generateCode("swbres",false);
            System.out.println("Generación de clases completa");

        } catch (Exception cge) {
            fail(cge.getMessage());
        }
    }
}
