using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WBOffice4.Controls
{
    public interface ICellEditor
    {
        Object getCellEditorValue();
        Control getTableCellEditorComponent(EditableListView table, Object value,
                int row, int column);

    }
}
