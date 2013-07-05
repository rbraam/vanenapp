var list;
var listController;
Ext.onReady(function(){
    listController = Ext.create("KaratekaListController",{
        url: listKaratekaUrl,
        renderTo: 'karatekaList'
    });
    
    Ext.create('Ext.form.field.Text',{
        renderTo: 'filterTextBox',
        listeners: {
            change:{
                fn: function(){
                    this.filterList(this.value());
                },
                scope: this
            }
        }
    });
    listController.refresh();
    
});