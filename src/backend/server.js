var http = require('http');
var url = require('url');
var fs = require('fs');


var fileHome = '../frontend/';
var serverFiles = [
	'', // serve serverMain
	'plan-semesters/planner.html',
	'plan-semesters/planner.css',
	'plan-semesters/model/course.js',
	'plan-semesters/controller/courseInputMouse.js',
	'plan-semesters/controller/courseInputKeyboard.js',
	'plan-semesters/controller/courseDetails.js',
	'manage-degrees/manager.html'
  ];
var serverMain = 'index.html';
var fnfHtml = 'fnf.html';

var myCSSEncoding = "UTF-8";
var myPngEncoding = "UTF-8";

// app.use(express.static(path.join(__dirname, '/')));

function log(msg) {
	console.log('[' + new Date(Date.now()).toLocaleTimeString('en-US') + '] ' + msg);
}

/**
 * return 404 file, return main file, return whatever for whatever file is requested
 */
function serveFile(filepath, res) {
	// if no file specified, return main file
	if (filepath == '') {
		serveHTML(serverMain, res);
		log('Serving ' + serverMain);
	} else {
		let filetype = filepath.substring(filepath.lastIndexOf('.'));
		switch (filetype) {
			case '.html':
				serveHTML(filepath, res);
				log('Serving ' + filepath);
				break;
			case '.css':
				serveCSS(filepath, res);
				log('Serving ' + filepath);
				break;
			case '.js':
				serveJS(filepath, res);
				log('Serving ' + filepath);
				break;
			default:
				serve404(res);
				log('404 for ' + filepath);
		}
	}
	
}

function serveHTML(filepath, res) {
	fs.readFile(fileHome + filepath, function(err, data) {
		res.writeHead(200, {'Content-Type': 'text/html'});
		if (data != undefined) {
			res.write(data);
		} else {
			console.log('Error when serving file ' + filepath);
			console.err(err);
		}
		return res.end();
	});
}

function serveCSS(filepath, res) {
    res.writeHead(200, {"Content-Type": "text/css"});
    fs.createReadStream(fileHome + filepath, myCSSEncoding).pipe(res);
	// todo: catch errors reading file
}

function serveJS(filepath, res) {
	// this function's implementation is just a guess
	// it's a near-duplicate of serveHTML
	fs.readFile(fileHome + filepath, function(err, data) {
		res.writeHead(200, {'Content-Type': 'text/js'});
		if (data != undefined) {
			res.write(data);
		} else {
			console.log('Error when serving file ' + filepath);
			console.err(err);
		}
		return res.end();
	});
}

function servePNG(filepath, res) {
	res.writeHead(200, {"Content-Type": "image/png"});
	// todo: maybe fix png encoding type?
    fs.createReadStream(fileHome + filepath, myPngEncoding).pipe(res);
	// todo: catch errors reading file
}

function serve404(res) {
	fs.readFile(fileHome + fnfHtml, function(err, data) {
		res.writeHead(404, {'Content-Type': 'text/html'});
		if (data != undefined) {
			res.write(data);
		}
		return res.end();
	});
}

// --------------------------------
// Main
// --------------------------------
http.createServer(function (req, res) {
	let urlQ = url.parse(req.url, true);
	let fileName = urlQ.pathname.substring(1);
	
	
	serveFile(fileName, res);
	
}).listen(8080);

log(' ----- Starting -----');