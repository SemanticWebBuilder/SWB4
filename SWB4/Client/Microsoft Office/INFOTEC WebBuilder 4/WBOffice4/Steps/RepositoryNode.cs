using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WBOffice4.Interfaces;
namespace WBOffice4.Steps
{
    class RepositoryNode :  TreeNode
    {
        private RepositoryInfo repository;
        public RepositoryNode(RepositoryInfo repository) : base(repository.name,0,1)
        {
            this.repository = repository;
        }
        public RepositoryInfo Repository
        {
            get
            {
                return repository;
            }
        }
    }
}
