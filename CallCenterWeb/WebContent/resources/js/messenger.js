var websocket;


var control = 1;
var new_message = false;

function startWebsocket() {

	var hiddChatterId = document.getElementById("hiddChatterId");
	
	var hiddServerIp = document.getElementById("hiddServerIp");

	var uri = "ws://"+hiddServerIp.value+":8025/msnserver/messengerendpoint/";
	
	alert(uri+ parseInt(hiddChatterId.value));
	
	websocket = new WebSocket(uri + parseInt(hiddChatterId.value));
	
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

}

function stopWebSocket() {
	websocket.close();
}

function sendMessage() {

	var inSendMessage = document.getElementById("inSendMessage");
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

	}

}

function onMessage(event) {
	var msg = JSON.parse(event.data);

	if (msg.chatterSourceId == 0 && msg.chatterSourceAlias == 'SYSTEM') {
		/*
		 * writeToScreen('<span style="color: red;">' + msg.chatterSourceAlias + ' : ' +
		 * msg.body + '</span>');
		 */
		updateChatterStatus([ {
			name : 'message',
			value : msg.body
		} ]);
		// alert('is system message');
	} else {
		// alert('is not system messages');
		writeToScreen('<span style="color: green;">' + msg.chatterSourceAlias
				+ ' : ' + msg.body + '</span>');
		var clickSound = new Audio('../resources/snd/MSN-alert.wav');
		clickSound.canPlayType('audio/wav');
		clickSound.play();
		if (!new_message) {
			new_message = true;
			flash();
		}

	}

}

/*
 * function searchKeyPress(e) { // look for window.event in case event isn't
 * passed in if (typeof e == 'undefined' && window.event) { e = window.event; }
 * if (e.keyCode == 13) { //document.getElementById('btnSendMessage').click(); } }
 */

function onOpen(event) {
	writeToScreen("messenger is started.");
}

function onClose(event) {
	writeToScreen("messenger is stopped." + event.code);
	writeToScreen("messenger is stopped." + event.reason);
	writeToScreen("messenger is stopped." + event.wasClean);
}

function onError(evt) {
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
	var panel_chat = document.getElementById("pnlMessages");
	panel_chat.innerHTML += message + "<br/>";
	panel_chat.scrollTop = panel_chat.scrollHeight;
}

function flash() {

	if (new_message) {
		if (control == 1) {
			document.title = "Nuevo mensaje!";
			control = 0;
		} else {
			document.title = ">>>>>>>>>>>>>>>";
			control = 1;
		}
		setTimeout("flash();", 1000);
	}

}

function unflash() {
	if (new_message) {
		document.title = "Messenger Application";
		new_message = false;
	}
}
