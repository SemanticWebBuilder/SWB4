using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;

namespace WBOffice4.Steps
{
    internal partial class Search : TSWizards.BaseInteriorStep
    {
        public static readonly String CONTENT = "CONTENT";
        public static readonly String REPOSITORY_ID = "REPOSITORY_ID";
        private DocumentType documentType;
        public Search(DocumentType documentType)
        {
            InitializeComponent();
            this.comboBoxRepositories.Items.Clear();
            foreach (RepositoryInfo rep in OfficeApplication.OfficeApplicationProxy.getRepositories())
            {
                this.comboBoxRepositories.Items.Add(rep);
            }
            this.documentType = documentType;
            if (this.comboBoxRepositories.Items.Count > 0)
            {
                this.comboBoxRepositories.SelectedIndex = 0;
            }
        }

        private void buttonSearch_Click(object sender, EventArgs e)
        {
            if (this.comboBoxRepositories.SelectedItem != null && this.comboBoxRepositories.SelectedItem != null && comboBoxType.SelectedItem != null)
            {
                String rep = this.comboBoxRepositories.SelectedItem.ToString();
                String category = ((CategoryInfo)this.comboBoxCategories.SelectedItem).UDDI;
                String type = ((ContentType)this.comboBoxType.SelectedItem).id;
                this.listView1.Items.Clear();
                this.Cursor = Cursors.WaitCursor;
                ContentInfo[] contents = OfficeApplication.OfficeApplicationProxy.search(rep, this.textBoxTitle.Text, this.textBoxDescription.Text, category, type, this.documentType.ToString().ToUpper());
                this.Cursor = Cursors.Default;
                if (contents.Length == 0)
                {
                    MessageBox.Show(this, "No se encontraron contenidos coincidentes", "Búsqueda de contenido", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                else
                {
                    MessageBox.Show(this, "Se encontraron " + contents.Length + " contenidos coincidentes", this.Wizard.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                foreach (ContentInfo content in contents)
                {
                    ListViewItem item = new ListViewItem(content.title);
                    item.Tag = content;
                    item.SubItems.Add(content.descripcion);
                    String date=String.Format(OfficeApplication.iso8601dateFormat,content.created);
                    item.SubItems.Add(date);
                    item.SubItems.Add(content.categoryTitle);
                    this.listView1.Items.Add(item);
                }
                this.tabControl1.SelectTab(1);
            }
        }

        private void comboBoxRepositories_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.comboBoxRepositories.SelectedItem != null)
            {
                String rep = this.comboBoxRepositories.SelectedItem.ToString();
                this.comboBoxCategories.Items.Clear();
                CategoryInfo all=new CategoryInfo();
                all.UDDI = "*";
                all.title = "Todas";
                ContentType allContentType = new ContentType();
                allContentType.id = "*";
                allContentType.title = "Todas";
                this.comboBoxCategories.Items.Add(all);
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getAllCategories(rep))
                {
                    this.comboBoxCategories.Items.Add(category);                    
                }
                if (this.comboBoxCategories.Items.Count > 0)
                {
                    this.comboBoxCategories.SelectedIndex = 0;
                }
                this.comboBoxType.Items.Clear();
                this.comboBoxType.Items.Add(allContentType);
                foreach(ContentType type in OfficeApplication.OfficeApplicationProxy.getContentTypes(rep))
                {
                    this.comboBoxType.Items.Add(type);
                }
                if (this.comboBoxType.Items.Count > 0)
                {
                    this.comboBoxType.SelectedIndex = 0;
                }
            }

        }
        
        private void Search_ValidateStep(object sender, CancelEventArgs e)
        {
            if (this.listView1.SelectedItems.Count == 0)
            {
                MessageBox.Show(this, "¡Debe seleccionar un contenido a abrir!", "Apertura de contenido", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.tabControl1.SelectTab(0);
                e.Cancel = true;
            }
            else
            {
                this.Wizard.Data[CONTENT] = this.listView1.SelectedItems[0].Tag;
                this.Wizard.Data[REPOSITORY_ID] = this.comboBoxRepositories.SelectedItem.ToString();
            }
        }

        private void Search_ShowStep(object sender, TSWizards.ShowStepEventArgs e)
        {
            this.textBoxTitle.Focus();
        }
    }
}
