using System;
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
                Debug.WriteLine(ue.StackTrace);
            }
        }
        private void loadRules()
        {
            this.listView1.Items.Clear();
            this.Cursor = Cursors.WaitCursor;
            try
            {
                foreach (ElementInfo info in OfficeApplication.OfficeDocumentProxy.getElementsOfResource(pageInformation))
                {
                    this.listView1.Items.Add(new ElementListView(info));
                }
            }
            catch (Exception ue)
            {
                Debug.WriteLine(ue.StackTrace);
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
                            Debug.WriteLine(ue.StackTrace);
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
                            res = MessageBox.Show(this, "El documento requiere una autorización para activarse\r\n¿Desea envíar a publicar el contenido?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
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
                        Debug.WriteLine(ue.StackTrace);
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
            //this.toolStripButtonEdit.Enabled = false;
            if (this.listViewCalendar.SelectedItems.Count > 0)
            {
                this.toolStripButtonDelete.Enabled = true;
                //this.toolStripButtonEdit.Enabled = true;
            }
        }

        private void toolStripButtonAddRule_Click(object sender, EventArgs e)
        {
            FormAddElement frm = new FormAddElement(this.pageInformation.page.site, this.pageInformation);
            DialogResult res = frm.ShowDialog(this);
            if (res == DialogResult.OK)
            {
                ElementInfo element = frm.ElementInfo;
                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    OfficeApplication.OfficeDocumentProxy.addElementToResource(this.pageInformation, element);
                    loadRules();
                    if (listView1.Items.Count == 0 || listView1.SelectedIndices.Count == 0)
                    {
                        toolStripButtonDeleteRule.Enabled = false;
                    }
                    else
                    {
                        toolStripButtonDeleteRule.Enabled = true;
                    }
                }
                catch (Exception ue)
                {
                    Debug.WriteLine(ue.StackTrace);
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }
        }

        private void toolStripButtonDeleteRule_Click(object sender, EventArgs e)
        {
            if (this.listView1.Items.Count > 0 && this.listView1.SelectedIndices.Count > 0)
            {
                ElementInfo info = ((ElementListView)listView1.SelectedItems[0]).ElementInfo;
                DialogResult res = MessageBox.Show(this, "¿Desea eliminar la regla / rol ó grupo de usuario " + info.title, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Question);
                if (res == DialogResult.OK)
                {
                    try
                    {
                        this.Cursor = Cursors.WaitCursor;
                        OfficeApplication.OfficeDocumentProxy.deleteElementToResource(pageInformation, info);
                        loadRules();
                        if (listView1.Items.Count == 0 || listView1.SelectedIndices.Count == 0)
                        {
                            toolStripButtonDeleteRule.Enabled = false;
                        }
                        else
                        {
                            toolStripButtonDeleteRule.Enabled = true;
                        }
                    }
                    catch (Exception ue)
                    {
                        Debug.WriteLine(ue.StackTrace);
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


    }
}
