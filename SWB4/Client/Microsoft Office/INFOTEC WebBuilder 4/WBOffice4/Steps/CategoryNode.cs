using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    public class CategoryNode : TreeNode
    {
        private RepositoryInfo repository;
        private CategoryInfo category;
        public CategoryNode(CategoryInfo category, RepositoryInfo repository) : base(category.title,0,1)
        {
            this.repository = repository;
            this.category = category;
            this.ToolTipText = category.description;
        }
        public RepositoryInfo Repository
        {
            get
            {
                return repository;
            }
        }
        public CategoryInfo Category
        {
            get
            {
                return category;
            }
        }
    }
}
