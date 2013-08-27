var candidateController;
Ext.onReady(function() {
    
    candidateController = Ext.create("CandidateController",{
        url: listParticipantUrl,
        renderTo: "candidateContainer",
        checkedCandidates: savedParticipants,
        poule: pouleId
    });
    
    Ext.get('startAge').on("change",function(el){
        getParticipantList();
    });
    Ext.get('endAge').on("change",function(el){
        getParticipantList();
    });
    Ext.get('startKyu').on("change",function(el){
        getParticipantList();
    });
    Ext.get('endKyu').on("change",function(el){
        getParticipantList();
    });
    getParticipantList();
});

function getParticipantList(){
    candidateController.refresh();
}
