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
            SWBUtils.EMAIL.sendMail(getTo(), getSubject(), replaceTags(instance, getContent()));
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