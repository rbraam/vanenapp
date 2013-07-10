var list;
var listController;
Ext.onReady(function() {
    listController = Ext.create("KaratekaListController", {
        url: listKaratekaUrl,
        renderTo: 'karatekaListContainer',
        clickHandler: function(id) {
            openKarateka(id);
        },
        crossClickHandler: function(id) {
            removeKaratekaConfirm(id);
        }
    });
    listController.refresh();

});

function openKarateka(id) {
    var iframe = document.getElementById("editKaratekaFrame");
    var url = "" + editKaratekaUrl;
    url = Ext.urlAppend(url, "karateka=" + id);
    iframe.src = url;
}
function removeKaratekaConfirm(id) {
    var me = this;
    Ext.MessageBox.confirm("Karateka verwijderen","Weet u zeker dat u de karateka wilt verwijderen",
            function(btn) {
                if (btn == 'yes') {
                    me.removeKarateka(id);
                }
            }
    );
}
function removeKarateka(id) {
    var iframe = document.getElementById("editKaratekaFrame");
    var url = "" + deleteKaratekaUrl;
    url = Ext.urlAppend(url, "karateka=" + id);
    iframe.src = url;
}

