using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Linq;
using System.Diagnostics;

namespace WBOffice4.Utils
{
    [RunInstaller(true)]
    public partial class SBWInstallerEventLog : Installer
    {
        private EventLogInstaller myEventLogInstaller;

        public SBWInstallerEventLog()
        {
            InitializeComponent();
            myEventLogInstaller = new EventLogInstaller();

            // Set the source name of the event log.
            myEventLogInstaller.Source = TraceEventLogListener.sourceEvent;

            // Set the event log that the source writes entries to.
            myEventLogInstaller.Log = TraceEventLogListener.eventLogName;

            // Add myEventLogInstaller to the Installer collection.
            Installers.Add(myEventLogInstaller);   
        }
    }
}
