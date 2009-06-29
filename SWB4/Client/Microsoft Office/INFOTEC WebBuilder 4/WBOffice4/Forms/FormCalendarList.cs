using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using WBOffice4.Interfaces;
using System.Diagnostics;

namespace WBOffice4.Forms
{
    public partial class FormCalendarList : Form
    {
        private ResourceInfo resourceInfo;
        public FormCalendarList(ResourceInfo resourceInfo)
        {
            InitializeComponent();
            this.resourceInfo = resourceInfo;
            fillCalendarList();

        }
        private void fillCalendarList()
        {

            this.listBoxCalendars.Items.Clear();
            try
            {

                foreach (CalendarInfo cal in OfficeApplication.OfficeDocumentProxy.getCatalogCalendars(resourceInfo.page.site))
                {
                    this.listBoxCalendars.Items.Add(cal);
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.StackTrace);
            }
        }

        private void buttonClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.OK;
            this.Close();
        }

        private void toolStripButtonAdd_Click(object sender, EventArgs e)
        {
            FrmPeriodicidad dialogCalendar = new FrmPeriodicidad(false);
            DialogResult res = dialogCalendar.ShowDialog(this);
            if (res == DialogResult.OK)
            {
                XmlDocument xmlCalendar = dialogCalendar.Document;

                String xml = xmlCalendar.OuterXml;
                String title = dialogCalendar.textBoxTitle.Text;
                try
                {
                    this.Cursor = Cursors.WaitCursor;
                    OfficeApplication.OfficeApplicationProxy.createCalendar(resourceInfo.page.site, title, xml);
                    fillCalendarList();
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

        private void toolStripButtonDelete_Click(object sender, EventArgs e)
        {
            if (this.listBoxCalendars.SelectedItem != null)
            {

                CalendarInfo cal = (CalendarInfo)this.listBoxCalendars.SelectedItem;
                DialogResult res = MessageBox.Show(this, "¿Deseas eliminar la calendarización?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.Yes)
                {
                    try
                    {
                        this.Cursor = Cursors.WaitCursor;
                        bool canDelete = OfficeApplication.OfficeApplicationProxy.canDeleteCalendar(this.resourceInfo.page.site, cal);
                        if (canDelete)
                        {
                            OfficeApplication.OfficeDocumentProxy.deleteCalendarFromCatalog(this.resourceInfo.page.site, cal);
                            fillCalendarList();
                            if (this.listBoxCalendars.SelectedItem == null || this.listBoxCalendars.Items.Count == 0)
                            {
                                this.toolStripButtonDelete.Enabled = false;
                                this.toolStripButtonEdit.Enabled = false;
                            }
                            else
                            {
                                this.toolStripButtonDelete.Enabled = true;
                                this.toolStripButtonEdit.Enabled = true;
                            }
                        }
                        else
                        {
                            MessageBox.Show(this, "¡El calendario no se puede borrar, esta siendo utilizado por otro contenido u otro elemento del portal!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Warning);
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

        private void toolStripButtonEdit_Click(object sender, EventArgs e)
        {
            if (this.listBoxCalendars.SelectedItem != null)
            {
                CalendarInfo cal = (CalendarInfo)this.listBoxCalendars.SelectedItem;
                if (cal.xml != null)
                {
                    FrmPeriodicidad dialogCalendar = new FrmPeriodicidad(cal.active);
                    dialogCalendar.textBoxTitle.Text = cal.title;
                    XmlDocument document = new XmlDocument();
                    document.LoadXml(cal.xml);
                    dialogCalendar.Document = document;
                    DialogResult res = dialogCalendar.ShowDialog(this);
                    if (res == DialogResult.OK)
                    {
                        XmlDocument xmlCalendar = dialogCalendar.Document;
                        String xml = xmlCalendar.OuterXml;
                        String title = dialogCalendar.textBoxTitle.Text;
                        cal.title = title;
                        cal.xml = xml;
                        try
                        {
                            this.Cursor = Cursors.WaitCursor;
                            OfficeApplication.OfficeDocumentProxy.updateCalendar(resourceInfo.page.site, cal);
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
        }
    }
}
