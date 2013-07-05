Ext.onReady(function(){
  Ext.Ajax.request({
        url: listKaratekaUrl,
        success: function(response){
            var karatekas = response.responseText;
            alert(karatekas);
            // process server response here
        }
    });
});