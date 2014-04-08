var webservice = {

     callWebservice: function(successCallback, errorCallback){
         // So chama se houver comunicacao
         if (webservice.hasConnection){
             cordova.exec(successCallback, errorCallback, "RBEWebservice", "webservice", []);
         }         
     },
     hasConnection: function(){
         var networkState = navigator.network.connection.type;
     
         // Se tiver conexao
         if  (networkState!=Connection.NONE && networkState==Connection.UNKNOWN){
             return true;
         }
     
         return false;
     },
	 callDataFromDir: function(successCallback, errorCallback){
		cordova.exec(successCallback, errorCallback, "RBEWebservice", "getData", []);
	 }
};

module.exports = webservice;
