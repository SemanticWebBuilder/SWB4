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
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Xml;
using System.Windows.Forms;
using System.Diagnostics;
using WBOffice4.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
namespace WBOffice4.Forms
{
    public partial class FormEditPorlet : Form
    {
        private ResourceInfo pageInformation;
        private String repositoryName, contentID;
        List<CalendarInfo> added = new List<CalendarInfo>();
        public FormEditPorlet(ResourceInfo pageInformation, String repositoryName, String contentID)
        {
            InitializeComponent();
            this.pageInformation = pageInformation;
            this.repositoryName = repositoryName;
            this.contentID = contentID;
            initizalize();
        }
        private void initizalize()
        {
            this.checkBoxActivePag.Checked = pageInformation.page.active;
            this.dateTimePickerEndDate.Value = DateTime.Now;

            this.textBoxTitle.Text = pageInformation.title;
            this.textBoxDescription.Text = pageInformation.description;
            this.checkBoxActive.Checked = pageInformation.active;
            this.labelSite.Text = pageInformation.page.site.title;
            this.labelPage.Text = pageInformation.page.title;
            loadCalendars();
            VersionInfo info = new VersionInfo();
            info.nameOfVersion = "*";

            comboBoxVersiones.Items.Add(info);
            foreach (VersionInfo versionInfo in OfficeApplication.OfficeDocumentProxy.getVersions(repositoryName, contentID))
            {
                comboBoxVersiones.Items.Add(versionInfo);
            }
            VersionInfo selected = new VersionInfo();
            selected.nameOfVersion = pageInformation.version;
            if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(pageInformation))
            {
                this.toolTip1.SetToolTip(this.checkBoxActive, "Necesita enviar a flujo el contenido para activarlo");
                this.buttonSenttoAuthorize.Visible = true;
            }
            else
            {
                this.buttonSenttoAuthorize.Visible = false;
            }
            if (OfficeApplication.OfficeDocumentProxy.isInFlow(pageInformation))
            {
                this.toolTip1.SetToolTip(this.checkBoxActive, "El contenido se encuentra en proceso de ser autorizado, para activarlo, necesita terminar este proceso");
            }
            comboBoxVersiones.SelectedItem = selected;
            loadProperties();
            loadRules();
            loadTitles();
            try
            {
                DateTime date = OfficeApplication.OfficeDocumentProxy.getEndDate(pageInformation);
                if (date == null)
                {
                    this.checkBoxEndDate.Checked = false;
                    this.dateTimePickerEndDate.Enabled = false;
                }
                else
                {
                    this.checkBoxEndDate.Checked = true;
                    this.dateTimePickerEndDate.Enabled = true;
                    this.dateTimePickerEndDate.Value = date;
                }
            }
            catch (Exception ue)
            {
                OfficeApplication.WriteError(ue);
            }
        }
        private void loadTitles()
        {
            this.titleEditor1.Languages = OfficeApplication.OfficeDocumentProxy.getLanguages(this.pageInformation.page.site);
            String[] titles = new String[this.titleEditor1.Languages.Length];
            int i = 0;
            foreach(LanguageInfo lang in this.titleEditor1.Languages)
            {
                String title = OfficeApplication.OfficeDocumentProxy.getTitleOfWebPage(this.pageInformation.page, lang);
                titles[i] = title;
                i++;
            }
            this.titleEditor1.Titles = titles;
        }
        private void loadRules()
        {
            this.listViewRules.Items.Clear();
            this.Cursor = Cursors.WaitCursor;
            try
            {
                foreach (ElementInfo info in OfficeApplication.OfficeDocumentProxy.getElementsOfResource(pageInformation))
                {
                    this.listViewRules.Items.Add(new ElementListView(info));
                }
            }
            catch (Exception ue)
            {
                OfficeApplication.WriteError(ue);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }
        private void loadProperties()
        {
            PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
            this.propertyEditor1.Properties = props;
            if (props == null || props.Length == 0)
            {
                this.tabControlProperties.Controls.Remove(this.tabPageProperties);
            }
            else
            {
                String[] values = new String[props.Length];
                int i = 0;
                foreach (PropertyInfo prop in props)
                {
                    String value = OfficeApplication.OfficeDocumentProxy.getViewPropertyValue(this.pageInformation, prop);
                    values[i] = value;
                    i++;
                }
                this.propertyEditor1.Values = values;
            }

        }
        private void loadCalendars()
        {
            this.listViewCalendar.Items.Clear();

            foreach (CalendarInfo info in OfficeApplication.OfficeDocumentProxy.getCalendarsOfResource(pageInformation))
            {
                CalendarItem item = new CalendarItem(info);
                listViewCalendar.Items.Add(item);
            }
            foreach (CalendarInfo info in added)
            {
                CalendarItem item = new CalendarItem(info);
                listViewCalendar.Items.Add(item);
            }


        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }



        private void toolStripButtonAdd_Click(object sender, EventArgs e)
        {
            FormCalendarList list = new FormCalendarList(pageInformation);
            DialogResult res = list.ShowDialog(this);
            if (res == DialogResult.OK)
            {
                if (list.listBoxCalendars.SelectedItem != null)
                {

                    CalendarInfo calendar = (CalendarInfo)list.listBoxCalendars.SelectedItem;
                    bool exists = false;
                    int count = this.listViewCalendar.Items.Count;
                    for (int i = 0; i < count; i++)
                    {
                        CalendarItem calactual = (CalendarItem)this.listViewCalendar.Items[i];
                        if (calactual.CalendarInfo.id.Equals(calendar.id))
                        {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists)
                    {
                        try
                        {
                            added.Add(calendar);
                        }
                        catch (Exception ue)
                        {
                            OfficeApplication.WriteError(ue);
                        }
                    }

                }
            }
            loadCalendars();
        }

        /*private void toolStripButtonEdit_Click(object sender, EventArgs e)
        {
            if (this.listViewCalendar.SelectedItems.Count > 0)
            {
                CalendarItem calendarItem = (CalendarItem)this.listViewCalendar.SelectedItems[0];
                CalendarInfo cal = calendarItem.CalendarInfo;
                FrmPeriodicidad frmPeriodicidad = new FrmPeriodicidad(cal.active);
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(cal.xml);
                frmPeriodicidad.textBoxTitle.Text = cal.title;
                frmPeriodicidad.Document = doc;
                DialogResult res = frmPeriodicidad.ShowDialog(this);
                if (res == DialogResult.OK)
                {
                    cal.xml = frmPeriodicidad.Document.OuterXml;
                    calendarItem.Title = frmPeriodicidad.textBoxTitle.Text;
                    calendarItem.Active = frmPeriodicidad.isActive();
                }

            }
        }*/

        private void buttonOK_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text))
            {
                MessageBox.Show(this, "¡Debe indicar el título!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxTitle.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡Debe indicar la descripción!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxDescription.Focus();
                return;
            }
            DialogResult res = MessageBox.Show(this, "Se va a realizar los cambios de la información de publicación.\r\n¿Desea continuar?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (res == DialogResult.Yes)
            {
                pageInformation.title = this.textBoxTitle.Text;
                pageInformation.description = this.textBoxDescription.Text;
                pageInformation.version = ((VersionInfo)this.comboBoxVersiones.SelectedItem).nameOfVersion;
                OfficeApplication.OfficeDocumentProxy.updatePorlet(pageInformation);
                if (this.checkBoxActive.Checked)
                {
                    if (this.pageInformation.active)
                    {
                        OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
                    }
                    else
                    {
                        if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(pageInformation))
                        {
                            this.checkBoxActive.Checked = pageInformation.active;
                            res = MessageBox.Show(this, "El documento requiere una autorización para activarse"+"\r\n"+"¿Desea envíar a publicar el contenido?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                            if (res == DialogResult.Yes)
                            {
                                FormSendToAutorize formSendToAutorize = new FormSendToAutorize(pageInformation);
                                formSendToAutorize.ShowDialog();
                                if (formSendToAutorize.DialogResult == DialogResult.OK)
                                {
                                    OfficeApplication.OfficeDocumentProxy.sendToAuthorize(pageInformation, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                                    this.buttonSenttoAuthorize.Visible = false;
                                }
                                else
                                {
                                    MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                                }
                            }
                            else
                            {
                                MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }
                        }
                        else if (OfficeApplication.OfficeDocumentProxy.isInFlow(pageInformation))
                        {
                            this.checkBoxActive.Checked = pageInformation.active;
                            MessageBox.Show(this, "El contenido se encuentra en proceso de ser autorizado.\r\nPara activarlo necesita terminar el proceso de autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                        else if (OfficeApplication.OfficeDocumentProxy.isAuthorized(pageInformation))
                        {
                            OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
                        }
                        else
                        {
                            this.checkBoxActive.Checked = pageInformation.active;
                            res = MessageBox.Show(this, "El contenido fue rechazado.\r\nPara activarlo necesita enviarlo a autorización de nuevo\r\n¿Desea enviarlo a autorización?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                            if (res == DialogResult.Yes)
                            {
                                FormSendToAutorize formSendToAutorize = new FormSendToAutorize(pageInformation);
                                formSendToAutorize.ShowDialog();
                                if (formSendToAutorize.DialogResult == DialogResult.OK)
                                {
                                    OfficeApplication.OfficeDocumentProxy.sendToAuthorize(pageInformation, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                                }
                                else
                                {
                                    MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                                }
                            }
                            else
                            {
                                MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                            }
                        }
                    }
                }
                else
                {
                    OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
                }
                OfficeApplication.OfficeDocumentProxy.setTitlesOfWebPage(this.pageInformation.page, this.titleEditor1.Languages, this.titleEditor1.Titles);
                foreach (ListViewItem calendar in this.listViewCalendar.Items)
                {
                    CalendarItem calendarItem = (CalendarItem)calendar;
                    CalendarInfo cal = (CalendarInfo)calendarItem.CalendarInfo;
                    bool active = cal.active;
                    OfficeApplication.OfficeDocumentProxy.insertCalendartoResource(pageInformation, cal);
                    added.Remove(cal);
                    OfficeApplication.OfficeDocumentProxy.activeCalendar(pageInformation, cal, active);
                }
                OfficeApplication.OfficeApplicationProxy.activePage(this.pageInformation.page, this.checkBoxActivePag.Checked);

                PropertyInfo[] properties = null;
                String[] values = null;

                properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
                values = this.propertyEditor1.Values;
                try
                {
                    OfficeApplication.OfficeDocumentProxy.validateViewValues(repositoryName, contentID, properties, values);
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                    MessageBox.Show(this, ue.Message, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    tabPageProperties.Focus();
                    return;
                }

                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    if (properties != null && values != null)
                    {
                        int i = 0;
                        foreach (PropertyInfo prop in properties)
                        {
                            String value = values[i];
                            OfficeApplication.OfficeDocumentProxy.setViewPropertyValue(this.pageInformation, prop, value);
                            i++;
                        }
                    }
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                    MessageBox.Show(this, ue.Message, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }

            this.DialogResult = DialogResult.OK;
        }

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.listViewCalendar.SelectedItems.Count > 0)
            {
                DialogResult res = MessageBox.Show(this, "¿Desea eliminar la calendarización?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    CalendarInfo cal = ((CalendarItem)this.listViewCalendar.SelectedItems[0]).CalendarInfo;
                    this.listViewCalendar.Items.Remove((CalendarItem)this.listViewCalendar.SelectedItems[0]);


                    if (this.listViewCalendar.Items.Count == 0 || listViewCalendar.SelectedItems.Count == 0)
                    {
                        this.toolStripButtonDelete.Enabled = false;
                    }
                    try
                    {
                        added.Remove(cal);
                        OfficeApplication.OfficeDocumentProxy.deleteCalendar(pageInformation, cal);
                        loadCalendars();
                    }
                    catch (Exception ue)
                    {
                        OfficeApplication.WriteError(ue);
                    }
                }
            }
            if (this.listViewCalendar.SelectedItems.Count == 0)
            {
                this.toolStripButtonDelete.Enabled = false;
            }
        }

        private void listViewCalendar_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonDelete.Enabled = false;            
            if (this.listViewCalendar.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete.Enabled = true;                
            }
        }

        private void toolStripButtonAddRule_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            FormAddElement frm = new FormAddElement(this.pageInformation.page.site, this.pageInformation);
            this.Cursor = Cursors.Default;
            DialogResult res = frm.ShowDialog(this);
            if (res == DialogResult.OK)
            {
                ElementInfo element = frm.ElementInfo;
                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    OfficeApplication.OfficeDocumentProxy.addElementToResource(this.pageInformation, element);
                    loadRules();
                    if (listViewRules.Items.Count == 0 || listViewRules.SelectedIndices.Count == 0)
                    {
                        toolStripButtonDeleteRule.Enabled = false;
                        toolStripButtonActivate.Enabled = false;
                    }
                    else
                    {
                        toolStripButtonDeleteRule.Enabled = true;
                        toolStripButtonActivate.Enabled = true;
                    }
                }
                catch (Exception ue)
                {
                    OfficeApplication.WriteError(ue);
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }
        }

        private void toolStripButtonDeleteRule_Click(object sender, EventArgs e)
        {
            if (this.listViewRules.Items.Count > 0 && this.listViewRules.SelectedIndices.Count > 0)
            {
                ElementInfo info = ((ElementListView)listViewRules.SelectedItems[0]).ElementInfo;
                DialogResult res = MessageBox.Show(this, "¿Desea eliminar la regla / rol ó grupo de usuario " + info.title, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Question);
                if (res == DialogResult.OK)
                {
                    try
                    {
                        this.Cursor = Cursors.WaitCursor;
                        OfficeApplication.OfficeDocumentProxy.deleteElementToResource(pageInformation, info);
                        loadRules();
                        if (listViewRules.Items.Count == 0 || listViewRules.SelectedIndices.Count == 0)
                        {
                            toolStripButtonDeleteRule.Enabled = false;
                            toolStripButtonActivate.Enabled = false;
                        }
                        else
                        {
                            toolStripButtonDeleteRule.Enabled = true;
                            toolStripButtonActivate.Enabled = true;
                        }
                    }
                    catch (Exception ue)
                    {
                        OfficeApplication.WriteError(ue);
                    }
                    finally
                    {
                        this.Cursor = Cursors.Default;
                    }
                }
            }
        }

        private void buttonMove_Click(object sender, EventArgs e)
        {
            FormMoveContent FormMoveContent = new FormMoveContent(this.pageInformation);
            DialogResult res = FormMoveContent.ShowDialog(this);
            if (res == DialogResult.OK)
            {
                this.Cursor = Cursors.WaitCursor;
                foreach (ResourceInfo portletInfo in OfficeApplication.OfficeDocumentProxy.listResources(repositoryName, contentID))
                {
                    if (portletInfo.id.Equals(this.pageInformation.id))
                    {
                        pageInformation = portletInfo;
                        break;
                    }
                }
                initizalize();
                this.Cursor = Cursors.Default;
            }
        }

        private void buttonSenttoAuthorize_Click(object sender, EventArgs e)
        {
            FormSendToAutorize formSendToAutorize = new FormSendToAutorize(pageInformation);
            formSendToAutorize.ShowDialog();
            if (formSendToAutorize.DialogResult == DialogResult.OK)
            {
                OfficeApplication.OfficeDocumentProxy.sendToAuthorize(pageInformation, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                this.buttonSenttoAuthorize.Visible = false;
            }            
        }

        private void listView1_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete && this.listViewRules.SelectedItems.Count > 0)
            {
                this.toolStripButtonDeleteRule_Click(null, null);
            }
        }

        private void listViewCalendar_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete && this.listViewRules.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete_Click(null, null);
            }
        }

        private void listViewRules_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.toolStripButtonDeleteRule.Enabled = false;
            this.toolStripButtonActivate.Enabled = false;
            this.toolStripButtonActivate.Text = "Activar";
            if (this.listViewRules.SelectedItems.Count > 0)
            {
                this.toolStripButtonDeleteRule.Enabled = true;
                ElementListView item=(ElementListView) this.listViewRules.SelectedItems[0];
                if(item.ElementInfo.active)
                {
                    this.toolStripButtonActivate.Text = "Desactivar";
                }
                else
                {
                    this.toolStripButtonActivate.Text = "Activar";
                }
                this.toolStripButtonActivate.Enabled = true;
            }
        }

        private void toolStripButtonActivate_Click(object sender, EventArgs e)
        {
            if (this.listViewRules.SelectedItems.Count > 0)
            {
                ElementListView item = (ElementListView)this.listViewRules.SelectedItems[0];
                item.Active = !item.Active;
                this.Cursor = Cursors.WaitCursor;
                OfficeApplication.OfficeDocumentProxy.addElementToResource(this.pageInformation, item.ElementInfo);
                this.listViewRules_SelectedIndexChanged(null, null);
                this.listViewRules.Refresh();
                this.Cursor = Cursors.Default;

            }
        }


    }
}
