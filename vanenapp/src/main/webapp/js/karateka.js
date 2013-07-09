var list;
var listController;
Ext.onReady(function(){
    listController = Ext.create("KaratekaListController",{
        url: listKaratekaUrl,
        renderTo: 'karatekaListContainer',
        clickHandler: function(id){
            openKarateka(id);
        },
        crossClickHandler: function(id){
            removeKarateka(id);
        }
    });
    listController.refresh();
    
});

function openKarateka(id){
    var iframe = document.getElementById("editKaratekaFrame");
    var url=""+editKaratekaUrl+"?karateka="+id;
    iframe.src=url;
}
function removeKarateka(id){
    alert(id);
}