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
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.mail.internet.InternetAddress;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.process.utils.SWBScriptParser;


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
            ArrayList<InternetAddress> address = new ArrayList<InternetAddress>();
            String from = getFrom();
            if (from == null || from.trim().length() == 0) {
                from = SWBPortal.getEnv("af/adminEmail");
            } else {
                from = SWBScriptParser.parser(instance, user, getFrom());
            }
            String subject = SWBScriptParser.parser(instance, user, getSubject());
            String content = SWBScriptParser.parser(instance, user, getContent());
            String to = SWBScriptParser.parser(instance, user, getTo());
            
            StringTokenizer strTk = null;
            if (to != null && to.trim().length() > 0) {
                if (to.indexOf(";") > 0) {
                    strTk = new StringTokenizer(to, ";");
                } else if (to.indexOf(",") > 0) {
                    strTk = new StringTokenizer(to, ",");
                }
                
                if (strTk != null) {
                    while (strTk.hasMoreTokens()) {
                        String token = strTk.nextToken();
                        if (token == null) {
                            continue;
                        }
                        InternetAddress toAddress = new InternetAddress();
                        toAddress.setAddress(token);
                        address.add(toAddress);
                    }
                } else {
                    InternetAddress toAddress = new InternetAddress();
                    toAddress.setAddress(to);
                    address.add(toAddress);
                }
                
                if (SWBUtils.EMAIL.isValidEmailAddress(from) && !address.isEmpty()) {
                    SWBUtils.EMAIL.sendMail(from, user.getFullName(), address, null, null, subject, null, content, null, null, null);
                } else {
                    throw new SWBRuntimeException("SendMail - Ocurrió un problema al enviar el correo, las direcciones no son válidas");
                }
            }
            //replaceTags(instance, getContent());
            
            //SWBUtils.EMAIL.sendMail(SWBScriptParser.parser(instance, user, getTo()), SWBScriptParser.parser(instance, user, getSubject()), SWBScriptParser.parser(instance, user, getContent()));
        } catch(Exception e)
        {
            log.error(e);
            FlowNode node=instance.getFlowNodeType();

            Iterator<GraphicalElement> it=node.listChilds();
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
    }
}
//instance.owner
//instance.name
//instance.id
//instance.owner.name