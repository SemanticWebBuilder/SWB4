/**  
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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
using System.Text;
using System.Reflection;
namespace WBOffice4
{
    public sealed class SwbEventLog
    {
        private const string lineReturn = "\r\n";
        private const string EventSource = "Semantic INFOTEC WebBuilder Office 4";
        private const string LogSource = "Application";
        private static EventLog eventlog = new EventLog();
        private SwbEventLog()
        {

        }
        static SwbEventLog()
        {
            if (!EventLog.SourceExists(EventSource))
            {
                EventLog.CreateEventSource(EventSource, LogSource);
            }
            eventlog.Source = EventSource;
        }
        public static void Log(string message)
        {
            Debug.WriteLine(message);
            eventlog.WriteEntry(message);
        }
        public static void Log(string message, EventLogEntryType type)
        {
            Debug.WriteLine(message);
            eventlog.WriteEntry(message, type);
        }
        public static void Log(Exception exception, EventLogEntryType type)
        {
            StringBuilder message = new StringBuilder(exception.Message);


            message.Append(lineReturn);
            message.Append(exception.StackTrace);

            message.Append(lineReturn);
            message.Append(exception.Source);

            if (exception.InnerException != null)
            {
                message.Append(lineReturn);
                message.Append(exception.InnerException);
            }
            message.Append(lineReturn);
            message.Append("Assembly Name: " + Assembly.GetExecutingAssembly().GetName());
            if (exception.HelpLink != null)
            {
                message.Append(lineReturn);
                message.Append("HelpLink: " + exception.HelpLink);
            }
            eventlog.WriteEntry(message.ToString(), type);
        }
        public static void Log(Exception exception)
        {
            Log(exception, EventLogEntryType.Error);
        }

    }
}
