var Zoiper;
var ActiveCall;
var sessionTypePending;
var logoutPending;

var username;
var peer;
var password;
var domain;

var c_marcha = 0; // control del temporizador
var c_cro = 0; // estado inicial del cronómetro.

var h_marcha = 0; // control del temporizador
var h_cro = 0; // estado inicial del cronómetro.

var callsHistory = 1;

var h_emp = null;
var c_emp = null;

var url_zoiper = "http://www.geainternacional.com:8080/softphone/activex.certificate";

//var fromLogout = false;

function updateCallHistoryHold() {

	var rowCount = document.getElementById('phoneHistory').rows.length;
	var x = document.getElementById('phoneHistory').rows[rowCount - 1].cells;

	var anterior = x[3].innerHTML;
	var anteriorInt = 0;

	if (anterior != '') {
		// alert('ingreso1'+anterior+'1ingreso');
		var arreglo = anterior.split(' : ');
		anteriorInt = ((((arreglo[0]) * 60) * 60) + (arreglo[1] * 60) + arreglo[2]) * 1000;
	} else {
		// alert('no ingreso');
	}
	// alert(anteriorInt);

	// alert(sumatoria);
	// alert(x[3].innerHTML);

	// x[3].innerHTML=ho + " : " + mn + " : " + sg;

	var h_cro = 0;

	if (null != h_emp) {

		h_actual = new Date(); // fecha en el instante

		h_cro = h_actual - h_emp;
	}

	// alert(h_cro);

	var sumatoria = anteriorInt + h_cro;
	ch = new Date(); // fecha donde guardamos el tiempo transcurrido
	ch.setTime(sumatoria);

	/*
	 * cs = cr.getMilliseconds(); // milisegundos del cronómetro cs = cs / 10; //
	 * paso a centésimas de segundo. cs = Math.round(cs);
	 */
	var sg = ch.getSeconds(); // segundos del cronómetro
	var mn = ch.getMinutes(); // minutos del cronómetro
	var ho = ch.getHours() - 19; // horas del cronómetro
	/*
	 * if (cs < 10) { cs = "0" + cs; }
	 */// poner siempre 2 cifras en los números
	if (sg < 10) {
		sg = "0" + sg;
	}
	if (mn < 10) {
		mn = "0" + mn;
	}

	x[3].innerHTML = ho + " : " + mn + " : " + sg;

}

function updateCallHistoryDuration(duration) {

	var ch = new Date(); // fecha donde guardamos el tiempo transcurrido
	ch.setTime(duration * 1000);

	var sg = ch.getSeconds(); // segundos del cronómetro
	var mn = ch.getMinutes(); // minutos del cronómetro
	var ho = ch.getHours() - 19; // horas del cronómetro

	if (sg < 10) {
		sg = "0" + sg;
	}
	if (mn < 10) {
		mn = "0" + mn;
	}

	var rowCount = document.getElementById('phoneHistory').rows.length;
	var x = document.getElementById('phoneHistory').rows[rowCount - 1].cells;
	x[2].innerHTML = ho + " : " + mn + " : " + sg;

}

function addCallHistory(callNumber) {
	var table = document.getElementById("phoneHistory");

	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	var cell1 = row.insertCell(0);
	cell1.innerHTML = callsHistory;

	var cell2 = row.insertCell(1);
	cell2.innerHTML = callNumber;

	var cell3 = row.insertCell(2);
	cell3.innerHTML = '';

	var cell4 = row.insertCell(3);
	cell4.innerHTML = '';

	callsHistory++;

	if (rowCount > 5) {

		table.deleteRow(rowCount - 5);
	}

}

function showButtoms() {
	document.getElementById("buttom_hang").style.display = "block";
	document.getElementById("buttom_transfer").style.display = "block";
	document.getElementById("buttom_hide").style.display = "block";
	document.getElementById("buttom_show").style.display = "none";
}

function hideButtoms() {

	document.getElementById("buttom_hang").style.display = "none";
	document.getElementById("buttom_transfer").style.display = "none";
	document.getElementById("buttom_hide").style.display = "none";
	document.getElementById("buttom_show").style.display = "block";
}

function c_empezar() {
	if (c_marcha == 0) { // solo si el cronómetro esta parado
		c_emp = new Date(); // fecha actual
		c_elcrono = setInterval(c_tiempo, 1000); // función del temporizador.
		c_marcha = 1; // indicamos que se ha puesto en marcha.
	}
}
function c_tiempo() { // función del temporizador
	c_actual = new Date(); // fecha en el instante

	c_cro = c_actual - c_emp; // tiempo transcurrido en milisegundos

	cr = new Date(); // fecha donde guardamos el tiempo transcurrido
	// alert("cr1"+cr);
	cr.setTime(c_cro);
	// alert("cr2"+cr);
	/*
	 * cs = cr.getMilliseconds(); // milisegundos del cronómetro cs = cs / 10; //
	 * paso a centésimas de segundo. cs = Math.round(cs);
	 */
	sg = cr.getSeconds(); // segundos del cronómetro
	mn = cr.getMinutes(); // minutos del cronómetro
	ho = cr.getHours() - 19; // horas del cronómetro
	/*
	 * if (cs < 10) { cs = "0" + cs; }
	 */// poner siempre 2 cifras en los números
	if (sg < 10) {
		sg = "0" + sg;
	}
	if (mn < 10) {
		mn = "0" + mn;
	}
	c_visor = document.getElementById("croncall");
	c_visor.innerHTML = ho + " : " + mn + " : " + sg /* + " : " + cs */; // pasar
	// a
	// pantalla.
}
function c_parar() {
	if (c_marcha == 1) { // sólo si está en funcionamiento ...
		clearInterval(c_elcrono); // parar el crono
		c_marcha = 0; // indicar que está parado.
	}
}
// Continuar una cuenta empezada y parada.
function c_continuar() {
	if (c_marcha == 0) { // sólo si el crono está parado
		emp2 = new Date(); // fecha actual
		emp2 = emp2.getTime(); // pasar a tiempo Unix
		emp3 = emp2 - c_cro; // restar tiempo anterior
		c_emp = new Date(); // nueva fecha inicial para pasar al temporizador
		c_emp.setTime(emp3); // datos para nueva fecha inicial.
		c_elcrono = setInterval(c_tiempo, 1000); // activar temporizador
		c_marcha = 1;
	}
}

// Volver al estado inicial
function c_reiniciar() {
	if (c_marcha == 1) { // si el cronómetro está en marcha:
		clearInterval(c_elcrono); // parar el crono
		c_marcha = 0; // indicar que está parado
	}
	c_cro = 0; // tiempo transcurrido a cero

	c_visor = document.getElementById("croncall");

	c_visor.innerHTML = ""; // visor a cero
}

// HOLD
function h_empezar() {
	if (h_marcha == 0) { // solo si el cronómetro esta parado
		h_emp = new Date(); // fecha actual
		h_elcrono = setInterval(h_tiempo, 1000); // función del temporizador.
		h_marcha = 1; // indicamos que se ha puesto en marcha.
	}
}
function h_tiempo() { // función del temporizador
	h_actual = new Date(); // fecha en el instante

	h_cro = h_actual - h_emp; // tiempo transcurrido en milisegundos

	cr = new Date(); // fecha donde guardamos el tiempo transcurrido
	// alert("cr1"+cr);
	cr.setTime(h_cro);
	// alert("cr2"+cr);
	/*
	 * cs = cr.getMilliseconds(); // milisegundos del cronómetro cs = cs / 10; //
	 * paso a centésimas de segundo. cs = Math.round(cs);
	 */
	sg = cr.getSeconds(); // segundos del cronómetro
	mn = cr.getMinutes(); // minutos del cronómetro
	ho = cr.getHours() - 19; // horas del cronómetro
	/*
	 * if (cs < 10) { cs = "0" + cs; }
	 */// poner siempre 2 cifras en los números
	if (sg < 10) {
		sg = "0" + sg;
	}
	if (mn < 10) {
		mn = "0" + mn;
	}
	h_visor = document.getElementById("cronhold");
	h_visor.innerHTML = ho + " : " + mn + " : " + sg /* + " : " + cs */; // pasar
	// a
	// pantalla.
}

// Volver al estado inicial
function h_reiniciar() {
	if (h_marcha == 1) { // si el cronómetro está en marcha:
		clearInterval(h_elcrono); // parar el crono
		h_marcha = 0; // indicar que está parado
	}
	h_cro = 0; // tiempo transcurrido a cero
	h_emp = null;
	h_visor = document.getElementById("cronhold");

	h_visor.innerHTML = ""; // visor a cero
}

/*
 * function GetValue(name) { return document.getElementById(name).value; }
 */

function Quit(peer) {
	 //alert('ingreso a quit');

	if (Zoiper != null) {
		Zoiper.DelAccount(peer);
		setRegistrationStatus(peer + " has been deleted");
	}

	PF('updateSoftphone').stop();

	// alert('antes de zoiper a');
	// document.getElementById('ZoiperA').innerHTML = "";
	// alert('termino a quit');
}

function Hang() {

	if (null != ActiveCall) {
		ActiveCall.Hang();
		ActiveCall = null;
	}

}

function Dial() {
	ActiveCall = Zoiper.Dial(GetValue("number"));
}

function Hold() {
	if (null != ActiveCall) {
		// ActiveCall.Hold();
		if (ActiveCall.IsHold == 'false') {
			setCallStatus(ActiveCall.Phone + " hold");
			// Status(ActiveCall.Phone + " hold");
			ActiveCall.Hold();
			// var node = document.getElementById("#{p:component('hold')}");
			// node.innerHTML = "&lt;span class=\"ui-button-text\">Retomar
			// minuto de espera&lt;/span>";
			h_empezar();

		} else {
			// Status(ActiveCall.Phone + " unhold");
			setCallStatus(ActiveCall.Phone + " unhold");
			ActiveCall.UnHold();
			// var node = document.getElementById("#{p:component('hold')}");
			// node.innerHTML = "&lt;span class=\"ui-button-text\">Minuto de
			// espera&lt;/span>";
			updateCallHistoryHold();
			h_reiniciar();

		}
	}

}

function SendDTMFSequence() {
	if (null != ActiveCall) {
		ActiveCall.SendDTMF(GetValue("dtmfsequence"));
	}
}

function ShowAudioWizard() {
	if (null != Zoiper) {
		Zoiper.ShowAudioWizard();
	}
}

function ShowLog() {
	if (null != Zoiper) {
		Zoiper.ShowLog();
	}
}

function MuteSpeakerToggle() {
	if (Zoiper.MuteSpeaker == "true")
		Zoiper.MuteSpeaker = "false";
	else
		Zoiper.MuteSpeaker = "true";
}

function MuteMicToggle() {
	if (Zoiper.MuteMicrophone == "true")
		Zoiper.MuteMicrophone = "false";
	else
		Zoiper.MuteMicrophone = "true";
}
/*
 * function Login() { var user = GetValue("user"); var pass = GetValue("pass");
 * Zoiper.Login(user, pass); }
 */
/*
 * function Logout() { Zoiper.Logout(); }
 */
/*
 * function Status(text) { var panel_chat =
 * document.getElementById("panel_chat"); panel_chat.innerHTML += text + "<br/>";
 * panel_chat.scrollTop = panel_chat.scrollHeight; }
 */
/*
 * function registerZoiper() { alert('ingreso a registerZoiper');
 * 
 * //writeToScreen("zoiper: register"); writeToScreen("zoiper: peer: " +peer);
 * 
 * var peerName = document.getElementById("hiddenPeerName").value; var
 * peerPassword = document.getElementById("hiddenPeerPassword").value; var
 * peerServer = document.getElementById("hiddenPeerServer").value;
 * 
 * Account = Zoiper.AddAccount(peerName, "sip"); Account.Domain = peerServer;
 * Account.CallerID = peerName; Account.UserName = peerName; Account.Password =
 * peerPassword; Account.AddCodec("u-law"); Account.AddCodec("a-law");
 * Account.DTMFType = "media_outband"; Account.Apply(); Account.Register();
 * Zoiper.UseAccount(peerName); }
 */
function setRegistrationStatus(status) {
	var peerStatus = document.getElementById("peerStatus");
	peerStatus.innerHTML = status;
}

function setCallStatus(status) {
	var callNumber = document.getElementById("callNumber");
	callNumber.innerHTML = status;
}

function registerZoiper() {

	var peerName = document.getElementById("hiddenPeerName").value;
	var peerPassword = document.getElementById("hiddenPeerPassword").value;
	var peerServer = document.getElementById("hiddenPeerServer").value;

	// alert(''+peerName+'-'+peerPassword+'-'+peerServer+'');

	Account = Zoiper.AddAccount(peerName, "sip");
	Account.Domain = peerServer;
	Account.CallerID = peerName;
	Account.UserName = peerName;
	Account.Password = peerPassword;
	Account.AddCodec("u-law");
	Account.AddCodec("a-law");
	Account.DTMFType = "media_outband";
	Account.Apply();
	Account.Register();
	Zoiper.UseAccount(peerName);
}

function unregisterZoiper(accountName) {

	if (Zoiper != null) {
		alert('ingreso...');
		alert(accountName);
		alert(Zoiper.GetAccount(accountName));
		Account = Zoiper.GetAccount(accountName);
		Account.Unregister();

		// writeToScreen(peer + " is unregister.");
	}

}

function OnZoiperReady(phone) {

	// alert(widgetSessionTypes.getSelectedValue());

	Zoiper = phone;

	Zoiper.AllowMultipleInstances();
	Zoiper.CheckCertificate(url_zoiper);
	var Config = Zoiper.GetConfig();
	Config.NumberOfCallsLimit("1");
	Config.PopupMenuOnIncomingCall = "false";

	Config.RingWhenTalking = "true";

	var peerName = document.getElementById("hiddenPeerName").value;

	if (peerName != '') {

		registerZoiper();

	}

	/*
	 * var peerName = document.getElementById("hiddenPeerName").value; var
	 * peerPassword = document.getElementById("hiddenPeerPassword").value; var
	 * peerServer = document.getElementById("hiddenPeerServer").value;
	 */

	/*
	 * Account = Zoiper.AddAccount(peerName, "sip"); Account.Domain =
	 * peerServer; Account.CallerID = peerName; Account.UserName = peerName;
	 * Account.Password = peerPassword; Account.AddCodec("u-law");
	 * Account.AddCodec("a-law"); Account.DTMFType = "media_outband";
	 * Account.Apply(); Account.Register();
	 */
	// Zoiper.UseAccount(peerName);
}

function OnZoiperCallFail(call) {
	setCallStatus(call.Phone + " failed");
}
function OnZoiperCallRing(call) {
	setCallStatus(call.Phone + " ring");
}
function OnZoiperCallHang(call) {

	setCallStatus(call.Phone + " hang");

	updateCallHistoryHold();
	updateCallHistoryDuration(call.Duration);

	if (sessionTypePending != null && sessionTypePending!='logout') {
		doPause([ {
			name : 'sessionType',
			value : sessionTypePending
		} ]);

		sessionTypePending = null;
	}

	if (logoutPending) {
		logoutPending = null;
		doLogout();
		/*var peerName = document.getElementById("hiddenPeerName").value;
		Quit(peerName);*/
		//fromLogout = true;
	}
	//alert('fin');
	/*
	 * onCallHangup(); if (pendingLogout) { pendingLogout = false;
	 * forceLogout(); } if (pendingPause) { pendingPause = false; forcePause(); }
	 */
	c_reiniciar();
	h_reiniciar();
	ActiveCall = null;

}
function OnZoiperCallHold(call) {
	alert('ingreso1');
	setCallStatus(call.Phone + " hold");
}
function OnZoiperCallUnhold(call) {
	alert('ingreso2');
	setCallStatus(call.Phone + " unhold");
}
function OnZoiperCallAccept(call) {

	setCallStatus(call.Phone + " accept");

	addCallHistory(call.Phone);

	/*
	 * onCallAccept([ { name : 'number', value : call.Phone } ]);
	 */

	c_empezar();

	// document.getElementById("frmPorta").src =
	// 'http://www.geainternacional.com:8080/softphone/cliente_socket.jsp?numero='+
	// call.Phone;

}

function forcePause() {

	var sessionType = 'pause';
	logoutPending = false;

	if (ActiveCall != null) {
		sessionTypePending = sessionType;
		sessionType += "withwindow";

	}

	doPause([ {
		name : 'sessionType',
		value : sessionType
	} ]);

}

function forceLogout(){
	doLogoutPeer();
}

function beforeSave() {

	if (ActiveCall == null) {

		doSave();

	} else {
		alert('no es nulo');
	}

}
/*
function selectSessionType() {

	var element = $("select[name='sessionTypes'] option:selected").val();

	alert(element);
}
*/
function doError(){
	widgetGrowl.renderMessage({summary:'Error de conexion', detail: 'Error de conexion', severity: 0});
}

function beforePause() {

	// var sessionTypeSelected = document.getElementById("sessionTypes").value;
	var sessionTypeSelected = widgetSessionTypes.getSelectedValue();

	if (ActiveCall != null) {
		sessionTypePending = sessionTypeSelected;
		sessionTypeSelected += "withcall";
		logoutPending = false;

	}

	// alert(sessionTypeSelected);

	if (sessionTypeSelected == 'ready') {
		doUnpause();
	} else if (sessionTypeSelected == 'logout'
			|| sessionTypeSelected == 'logoutwithcall') {
		beforeLogout();
	} else {
		doPause([ {
			name : 'sessionType',
			value : sessionTypeSelected
		} ]);
	}

}

function beforeLogout() {

	if (ActiveCall != null) {
		logoutPending = true;

		doPause([ {
			name : 'sessionType',
			value : 'logoutwithcall'
		} ]);

	} else {

		doLogout();
		//fromLogout = true;
		//Quit();
	}

}

function OnZoiperCallReject(call) {
	setCallStatus(call.Phone + " reject");
}
function OnZoiperCallIncoming(call) {

	setCallStatus(call.Phone + " incoming");

	ActiveCall = call;
	setTimeout("ActiveCall.Accept()", 1000);

}

function OnZoiperAccountRegister(account) {
	setRegistrationStatus(account.Name + " is registered");
}
function OnZoiperAccountUnregister(account) {
	alert('ingreso');
	setRegistrationStatus(account.Name + " is unregistered");
}
function OnZoiperAccountUnregisterFail(account) {
	alert('ingreso a fail');
	setRegistrationStatus(account.Name + " failed to unregister");
}
function OnZoiperAccountRegisterFail(account) {
	setRegistrationStatus(account.Name + " failed to register");
}
function OnZoiperContactStatus(contact, status) {
	writeToScreen(contact.Name + " is " + status);
}

function Transfer() {
	if (ActiveCall != null) {
		var transferSelected = widgetTransfer.getSelectedValue();
		setCallStatus(ActiveCall.Phone + " transfer to " + transferSelected);
		ActiveCall.Transfer(transferSelected);
	}
}

// window.onbeforeunload = checkCall;

function checkCall(){
	alert('aqui');
}

function sleep(milliseconds) {
	//alert('sleep');
	  var start = new Date().getTime();
	  //for (var i = 0; i < 1e7; i++) {
	  while(true) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	}

function sleepCall() {
	  //var start = new Date().getTime();
	  //for (var i = 0; i < 1e7; i++) {
	  while(true) {
	    //if ((new Date().getTime() - start) > milliseconds){
		  if (ActiveCall == null){
	      break;
	    }
	  }
	}

window.addEventListener("beforeunload", function (e) {

	// if (!alreadyLogout) {
	if (ActiveCall != null) {
		
		forcePause();
		
		//var message="EL SISTEMA SE CERRARA AL TERMINAR SU LLAMADA";
		var message="DEBE PERMANECER EN LA PAGINA PORQUE ESTA EN LLAMADA";

		e = e || window.event;
		e.returnValue = message;
		return message;
		
	} else {
		
		//return "estas en estado ready";
		
		var sessionTypeSelected = widgetSessionTypes.getSelectedValue();
		
		if(sessionTypeSelected=='ready'){
			
			forceLogout();
			//sleep(1000);
			e = e || window.event;
			e.returnValue = "estas en estado ready";
			return "estas en estado ready";

			
		}
		
		/*var peerName = document.getElementById("hiddenPeerName").value;
		Quit(peerName);*/
		
		
		
	}
	/* } */
});
