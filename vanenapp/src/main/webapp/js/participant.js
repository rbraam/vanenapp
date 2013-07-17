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
});

function addKarateka(id) {
    console.log('Add karateka: '+id);
}
function removeKarateka(id){
    console.log("Remove karateka: "+id);
}
