var http = require('http');
var url = require('url');
var fs = require('fs');


var serverFiles = [
	'plan-semesters/planner.html',
	'plan-semesters/planner.css',
	'plan-semesters/dragNDrop.js',
	'plan-semesters/courses.js',
	'plan-semesters/keyboardNav.js',
	'manage-degrees/manager.html'
  ];
var serverFileHome = '../frontend/';
var serverMain = 'main.html';
var fnfHtml = 'fnf.html';

// app.use(express.static(path.join(__dirname, '/')));


http.createServer(function (req, res) {
	let urlQ = url.parse(req.url, true);
	let fileName = urlQ.pathname.substring(1);
	
	// if no file specified, return main file
	if (fileName == '') {
		fileName = serverMain;
	}
	// if 404
	else if (!serverFiles.includes(fileName)) {
		console.info("Returning 404 for " + fileName);
		fs.readFile(serverFileHome + fnfHtml, function(err, data) {
			res.writeHead(404, {'Content-Type': 'text/html'});
			if (data != undefined) { res.write(data); }
			return res.end();
		});
		return;
	}
	// if normal 200
	fs.readFile(serverFileHome + fileName, function(err, data) {
		console.info("Returning 200 for " + fileName);
		res.writeHead(200, {'Content-Type': 'text/html'});
		if (data != undefined) {
			res.write(data);
		} else {
			console.error("Error: data undefined for " + fileName);
		}
		return res.end();
	});
	
}).listen(8080);
console.log("Server Starting");