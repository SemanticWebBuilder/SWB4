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

import bsh.Interpreter;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;

public class ScriptTask extends org.semanticwb.process.model.base.ScriptTaskBase 
{
    public static Logger log=SWBUtils.getLogger(ProcessRule.class);
    
    public ScriptTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }  
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        String code=getScriptCode();

        try
        {
            Interpreter i = SWBPClassMgr.getInterpreter(instance, user);
            if (code != null && code.length() > 0) {
                Object ret=i.eval(code);
            }
        }catch(Exception e)
        {
            log.error(e);

            Iterator<GraphicalElement> it=listChilds();
            while (it.hasNext())
            {
                GraphicalElement graphicalElement = it.next();
                if(graphicalElement instanceof ErrorIntermediateCatchEvent)
                {
                    ErrorIntermediateCatchEvent event=(ErrorIntermediateCatchEvent)graphicalElement;
                    //TODO:Validar excepciones
                    //String c1=event.getActionCode();
                    //String c2=((Event)instance.getFlowNodeType()).getActionCode();
                    //if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                    {
                        FlowNodeInstance source=(FlowNodeInstance)instance;
                        source.close(user, Instance.STATUS_ABORTED, Instance.ACTION_EVENT, false);

                        FlowNodeInstance fn=((FlowNodeInstance)instance).getRelatedFlowNodeInstance(event);
                        fn.setSourceInstance(instance);
                        event.notifyEvent(fn, instance);
                        return;
                    }
                }
            }

        }
        instance.close(user);
    }

}
