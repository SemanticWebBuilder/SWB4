using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Linq;
using System.Diagnostics;

namespace XmlRpcLibrary
{
    [RunInstaller(true)]
    public partial class XmlRpcInstallerEventLog : Installer
    {
        private EventLogInstaller myEventLogInstaller;
        public XmlRpcInstallerEventLog()
        {
            InitializeComponent();
            myEventLogInstaller = new EventLogInstaller();

            // Set the source name of the event log.
            myEventLogInstaller.Source = XmlRpcTraceEventLogListener.sourceEvent;

            // Set the event log that the source writes entries to.
            myEventLogInstaller.Log = XmlRpcTraceEventLogListener.eventLogName;

            // Add myEventLogInstaller to the Installer collection.
            Installers.Add(myEventLogInstaller);   
        }
    }
}
