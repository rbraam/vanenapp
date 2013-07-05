var list;
var listController;
Ext.onReady(function(){
    listController = Ext.create("KaratekaListController",{
        url: listKaratekaUrl,
        renderTo: 'karatekaList',
        clickHandler: function(id){
            openKarateka(id);
        }
    });
    
    Ext.create('Ext.form.field.Text',{
        renderTo: 'filterTextBox',
        listeners: {
            change:{
                fn: function(){
                    listController.setFilter(this.value);
                }
            }
        }
    });
    listController.refresh();
    
});

function openKarateka(id){
    var iframe = document.getElementById("editKaratekaFrame");
    var url=""+editKaratekaUrl+"?karateka="+id;
    iframe.src=url;
}