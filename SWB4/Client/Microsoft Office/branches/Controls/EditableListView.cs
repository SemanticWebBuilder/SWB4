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
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace WBOffice4.Controls
{
    public partial class EditableListView : ListView
    {
        private int X = 0;
        private int Y = 0;
        private string subItemText;
        private int subItemSelected = 0;
        private ListViewItem li;
        private ICellEditor editor;
        private int editingRow = -1;
        private int editingCol = -1;
        public EditableListView()
        {
            InitializeComponent();
        }
        public int EditiongRow
        {
            get
            {
                return editingRow;
            }
        }
        public int EditiongCol
        {
            get
            {
                return editingCol;
            }
        }
       

        private void EditableListView_MouseDown(object sender, MouseEventArgs e)
        {
            editingCol = -1;
            editingRow = -1;
            li = this.GetItemAt(e.X, e.Y);             
            X = e.X;
            Y = e.Y;
        }

        private void EditableListView_Click(object sender, EventArgs e)
        {
            editingCol = -1;
            editingRow = -1;
            if (editor != null)
            {
                int nStart = X;
                int spos = 0;
                int epos = this.Columns[0].Width;
                for (int i = 0; i < this.Columns.Count; i++)
                {
                    if (nStart > spos && nStart < epos)
                    {
                        subItemSelected = i;
                        break;
                    }

                    spos = epos;
                    epos += this.Columns[i].Width;
                }
                
                subItemText = li.SubItems[subItemSelected].Text;
                int row = li.Index;
                int col = subItemSelected;               
                Control control=editor.getTableCellEditorComponent(this, li.Text, row, col);                
                if (control != null)
                {
                    editingCol = col;
                    editingRow = row;
                    //Rectangle r = new Rectangle(spos, li.Bounds.Y, epos, li.Bounds.Bottom);
                    System.Drawing.Size size = new System.Drawing.Size(epos - spos, li.Bounds.Bottom - li.Bounds.Top);
                    System.Drawing.Point location = new System.Drawing.Point(spos, li.Bounds.Y);
                    control.Size = size;
                    control.Location = location;
                    control.Show();
                    control.Focus();
                    control.LostFocus += new EventHandler(control_LostFocus);
                    control.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.control_KeyPress);
                }
               
            }
        }        
        private void control_KeyPress(object sender, System.Windows.Forms.KeyPressEventArgs e)
        {           
                if (sender is Control)
                {
                    if (e.KeyChar == 13 || e.KeyChar == 27)
                    {
                        Control control = (Control)sender;
                        if (editor != null)
                        {
                            Object value = editor.getCellEditorValue();
                            if (value != null)
                            {
                                li.Text = value.ToString();
                            }
                        }
                        control.Hide();
                    }
                }            
        }
        private void control_LostFocus(object sender, System.EventArgs e)
        {
            if (sender is Control)
            {
                Control control = (Control)sender;
                if (editor != null)
                {
                    Object value = editor.getCellEditorValue();
                    if (value != null)
                    {
                        li.Text = value.ToString();
                    }
                }
                control.Hide();
            }
        }
        public void SetCellEditor(ICellEditor editor)
        {
            this.editor = editor;
        }

        
    }
    
    
}
