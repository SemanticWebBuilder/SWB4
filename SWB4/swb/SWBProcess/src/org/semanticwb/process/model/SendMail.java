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

package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;


public class SendMail extends org.semanticwb.process.model.base.SendMailBase 
{
    private static Logger log=SWBUtils.getLogger(SendMail.class);

    public SendMail(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        try
        {
            //replaceTags(instance, getContent());
            SWBUtils.EMAIL.sendMail(getTo(), getSubject(), getContent());
        }catch(Exception e)
        {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public String replaceTags(FlowNodeInstance instance, String str) {
        String ret = str;
        SemanticClass sclss = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org.mx/process/processobjects/cfe_po#CFEFreeFormatDocument");
        Iterator<SemanticProperty> props = sclss.listProperties();
        while (props.hasNext()) {
                SemanticProperty semanticProperty = props.next();
                if(semanticProperty.isDataTypeProperty()) {
                    System.out.println("D-"+semanticProperty.getName());
                } else if (semanticProperty.isObjectProperty()) {
                    System.out.println("O-"+semanticProperty.getName());
                }
            }
//        Iterator<SemanticClass> itclases = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
//        while (itclases.hasNext()) {
//            SemanticClass object = itclases.next();
//            System.out.println("-"+object.getName() + " uri="+object.getURI());
//            
//        }
//        System.out.println("Llamando a replaceTags en "+instance.getProcessInstance().getProcessType().getTitle()+":"+instance.getId());
//        List<ItemAwareReference> list = instance.listHeraquicalItemAwareReference();
//        for (ItemAwareReference item : list) {
//            System.out.println("Objeto de datos: " + item.getItemAware().getName() + "uri de datos");
//            String tag = item.getItemAware().getName();
//            Iterator<SemanticProperty> props = item.getItemAware().getDataObjectClass().listProperties();
//            while (props.hasNext()) {
//                SemanticProperty semanticProperty = props.next();
//                if(semanticProperty.isDataTypeProperty()) {
//                    System.out.println("D-"+semanticProperty.getDisplayName() + "=" + item.getItemAware().getDataObjectClass().getProperty(semanticProperty));
//                } else if (semanticProperty.isObjectProperty()) {
//                    System.out.println("O-"+semanticProperty.getDisplayName() + "=" + item.getItemAware().getDataObjectClass().getProperty(semanticProperty));
//                }
//            }
//        }

        //SWBUtils.TEXT.replaceAll(ret, "{instance.title}", getServiceTask().getProcess().getTitle());
        return ret;
    }
}
//instance.owner
//instance.name
//instance.id
//instance.owner.name