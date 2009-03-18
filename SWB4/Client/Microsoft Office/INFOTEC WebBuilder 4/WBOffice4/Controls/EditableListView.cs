using System;
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
