using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Forms
{
    public partial class FormAddCategory : Form
    {
        private String repository, categoryid;
        public FormAddCategory(String repository, String categoryid)
        {
            InitializeComponent();
            this.repository = repository;
            this.categoryid = categoryid;
            this.Text = "Agregar categoria a repositorio " + repository;
        }
        public FormAddCategory(String repository)
        {
            InitializeComponent();
            this.repository = repository;            
            this.Text = "Agregar categoria a repositorio " + repository;
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void buttonOK_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(textBoxName.Text))
            {
                MessageBox.Show(this, "¡Debe indicar el nombre de la categoria!", "Error al capturar el nombre de la categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxName.Focus();
                return;
            }
            if (String.IsNullOrEmpty(textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡Debe indicar la descripción de la categoria!", "Error al capturar descripción de la categoria", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxDescription.Focus();
                return;
            }
            this.Cursor = Cursors.WaitCursor;
            bool existe = false;
            try
            {
                foreach (CategoryInfo category in OfficeApplication.OfficeApplicationProxy.getCategories(repository))
                {
                    if (category.title.Equals(textBoxName.Text, StringComparison.InvariantCultureIgnoreCase))
                    {
                        existe = true;
                        break;
                    }
                }
            }
            catch
            {
            }
            if (existe)
            {
                this.Cursor = Cursors.Default;
                MessageBox.Show(this, "¡Ya existe una categoria con ese nombre!", "Error al capturar nombre", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxName.Focus();
                return;
            }
            else
            {
                if (categoryid != null)
                {
                    try
                    {
                        OfficeApplication.OfficeApplicationProxy.createCategory(repository, categoryid, this.textBoxName.Text, textBoxDescription.Text);
                    }
                    catch
                    {
                    }
                }
                else
                {
                    try
                    {
                        OfficeApplication.OfficeApplicationProxy.createCategory(repository, this.textBoxName.Text, textBoxDescription.Text);
                    }
                    catch
                    {
                    }
                }

            }

            this.Cursor = Cursors.Default;

            this.DialogResult = DialogResult.OK;
            this.Close();
        }
    }
}
