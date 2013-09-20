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
    typeChange();    
    getParticipantList();
});
function typeChange(el){
    var val=Ext.query("input:checked[name='type']")[0].value;
    if(val=="KATA"){endWeightRow
        Ext.get('endWeightRow').setVisible(false);
        Ext.get('startWeightRow').setVisible(false);
    }else{
        Ext.get('endWeightRow').setVisible(true);
        Ext.get('startWeightRow').setVisible(true);
    }
}
function getParticipantList(){
    candidateController.refresh();
}
