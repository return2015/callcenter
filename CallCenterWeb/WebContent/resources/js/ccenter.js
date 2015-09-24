var websocket;
var url_websocket = "ws://192.168.23.15:8026/pbxserver/pbxendpoint/";

var control = 1;
var new_message = false;

function startWebsocket() {
	
	//alert("ingreso a la funcion startWebSocket");

	var hiddAgentId = document.getElementById("formHeader:hiddAgentId");
	
	//alert(hiddAgentId);
	
	if (hiddAgentId!=null) {
		
		//alert("hiddAgentId no es nulo");	
		
		if (websocket==null) {
			//alert("websocket es nulo");	
			
			var uri = url_websocket;
			websocket = new WebSocket(uri + parseInt(hiddAgentId.value));
			websocket.onopen = function(evt) {
				onOpen(evt);
			};
			websocket.onclose = function(evt) {
				onClose(evt);
			};
			websocket.onerror = function(evt) {
				onError(evt);
			};
			websocket.onmessage = function(evt) {
				onMessage(evt);
			};
		}else{
			//alert("websocket no es nulo");
		}
		
	}else{
		//alert("hiddAgentId es nulo");
		//writeToScreen("no existe agentId");
	}

	

}

function stopWebSocket() {
	websocket.close();
}

function sendMessage() {

	/*var inSendMessage = document.getElementById("inSendMessage");
	var checkRoomDestiny = document.getElementById("checkRoomDestiny");

	var hiddRoomDestinyId = document.getElementById("hiddRoomDestinyId");
	var txtRoomDestinyAlias = document.getElementById("txtRoomDestiny");

	var hiddMemberDestinyId = document.getElementById("hiddMemberDestinyId");
	var txtMemberDestinyAlias = document.getElementById("txtMemberDestiny");

	var hiddChatterId = document.getElementById("hiddChatterId");
	var txtChatterAlias = document.getElementById("txtChatterAlias");

	if (Boolean(document.getElementById("checkRoomDestiny").checked)) {
		if (hiddRoomDestinyId.value == '') {
			alert('debes seleccionar una sala destino');
		} else {
			websocket.send(JSON.stringify({
				body : inSendMessage.value,

				chatterSourceId : parseInt(hiddChatterId.value),
				chatterSourceAlias : txtChatterAlias.innerHTML,

				chatterDestinyId : 0,
				chatterDestinyAlias : '',

				roomDestinyId : parseInt(hiddRoomDestinyId.value),
				roomDestinyName : txtRoomDestinyAlias.innerHTML,

				isRoomDestiny : Boolean(checkRoomDestiny.checked)
			}));
			writeToScreen('<span style="color: blue;">'
					+ txtChatterAlias.innerHTML + ' : ' + inSendMessage.value
					+ '</span>');
			inSendMessage.value = "";
		}

	} else {
		if (hiddMemberDestinyId.value == '') {
			alert('debes seleccionar un usuario destino');
		} else {
			websocket.send(JSON.stringify({
				body : inSendMessage.value,

				chatterSourceId : parseInt(hiddChatterId.value),
				chatterSourceAlias : txtChatterAlias.innerHTML,

				chatterDestinyId : parseInt(hiddMemberDestinyId.value),
				chatterDestinyAlias : txtMemberDestinyAlias.innerHTML,

				roomDestinyId : parseInt(hiddRoomDestinyId.value),
				roomDestinyName : txtRoomDestinyAlias.innerHTML,

				isRoomDestiny : Boolean(checkRoomDestiny.checked)
			}));
			writeToScreen('<span style="color: blue;">'
					+ txtChatterAlias.innerHTML + ' : ' + inSendMessage.value
					+ '</span>');
			inSendMessage.value = "";
		}

	}*/

}

function onMessage(event) {
	/*var msg = JSON.parse(event.data);

	if (msg.chatterSourceId == 0 && msg.chatterSourceAlias == 'SYSTEM') {

		updateChatterStatus([ {
			name : 'message',
			value : msg.body
		} ]);
	} else {
		writeToScreen('<span style="color: green;">' + msg.chatterSourceAlias
				+ ' : ' + msg.body + '</span>');
		var clickSound = new Audio('../resources/snd/MSN-alert.wav');
		clickSound.canPlayType('audio/wav');
		clickSound.play();
		if (!new_message) {
			new_message = true;
			flash();
		}

	}*/
	 if (typeof event.data == "string") {
		 
		 writeToScreen(event.data);
		 
		document.getElementById("frmClaro").src =
			 'http://www.geainternacional.com:8080/ccenter/cliente_socket.jsp?numero='+
			 event.data;
		 
	        /*$("#received_messages").append(
	                        $('<tr/>')
	                        .append($('<td/>').text("1"))
	                        .append($('<td/>').text(evnt.data.substring(0,evnt.data.indexOf(":"))))
	                        .append($('<td/>').text(evnt.data.substring(evnt.data.indexOf(":")+1))));*/
	    } 

}


function onOpen(event) {
	writeToScreen("conectado con pbx.");
}

function onClose(event) {
	writeToScreen("desconectado de pbx." + event.code +":"+ event.reason+":"+event.wasClean);
}

function onError(evt) {
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
	//alert("mensaje:"+message);
	
	$('#current_call').val(message);
	
	/*var current_call = document.getElementById("formPanel:current_call");
	if (current_call!=null) {
		current_call.innerHTML = message + "<br/>";	
	}else{
		alert("no se encontro element current_call ");
	}*/
	
	/*panel_chat.scrollTop = panel_chat.scrollHeight;*/
}

function flash() {

	/*if (new_message) {
		if (control == 1) {
			document.title = "Nuevo mensaje!";
			control = 0;
		} else {
			document.title = ">>>>>>>>>>>>>>>";
			control = 1;
		}
		setTimeout("flash();", 1000);
	}*/

}

function unflash() {
	/*if (new_message) {
		document.title = "Messenger Application";
		new_message = false;
	}*/
}
