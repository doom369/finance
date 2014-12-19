drawIcon();
setInterval(drawIcon(), 1000 * 60 * 10);

function drawIcon() {
	var data = getData();
	console.log(data);
	alert(data);
	chrome.browserAction.setBadgeBackgroundColor({ color: [0, 255, 0, 255] });
	chrome.browserAction.setBadgeText({text: '' + data});
}

function getData() {
	return httpGet('http://localhost:8181/api/realAverage');
}

function httpGet(theUrl) {
	var xmlHttp = null;

	xmlHttp = new XMLHttpRequest();
	xmlHttp.open( "GET", theUrl, false );
	xmlHttp.send( null );
	return xmlHttp.responseText;
}