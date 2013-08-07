var list;
var listControllerKarateka;
var listControllerParticipant;
Ext.onReady(function() {
    listControllerKarateka = Ext.create("KaratekaListController", {
        url: listKaratekaUrl,
        renderTo: 'karatekaListContainer',
        clickHandler: function(id) {
            addKarateka(id);
        }
    });
    listControllerKarateka.refresh();
    
    listControllerParticipant = Ext.create("KaratekaListController", {
        //url: listKaratekaUrl,
        data: participants,
        renderTo: 'participantListContainer',
        crossClickHandler: function(id) {
            removeKarateka(id);
        }
    });
    listControllerParticipant.refresh();
});

function addKarateka(id) {
    var k=listControllerKarateka.removeKarateka(id);
    listControllerKarateka.update();
    listControllerParticipant.addKarateka(k);
    listControllerParticipant.update();
}
function removeKarateka(id){
    var k = listControllerParticipant.removeKarateka(id);
    listControllerParticipant.update();
    listControllerKarateka.addKarateka(k);
    listControllerKarateka.update();
}

function onSubmitForm(){
    var list = listControllerParticipant.getList();
    var intList=[];
    for (var i=0; i < list.length; i++){
        intList.push(list[i].id);
    }
    document.getElementById("participants").value = intList.join(",");
    return true;
}