var amqp = require('amqp'); 

var app = require('express')()
  , server = require('http').createServer(app)
  , io = require('socket.io').listen(server);

server.listen(8099);

// routing
app.get('/', function (req, res) {
  res.sendfile(__dirname + '/index.html');
});

function consumeAndEmit(socket) {
	var url = "amqp://xvjpxzlg:IXe4AHnR9vflfNZUS0bMTIbB2_dBEMcq@bunny.cloudamqp.com/xvjpxzlg"
	var connection = amqp.createConnection({url: url});
	connection.on('ready', function() {
	connection.exchange("trafficExchange", options={type:'fanout'}, function(exchange) {
  		var queue = connection.queue('nodeQueue', {}, function() {
  			queue.bind(exchange, '');
    		queue.subscribe(function(msg) {
    		console.log(msg.data.toString()); 
    	  		socket.emit('colours', { colour: msg.data.toString() });
    		 });
    	}); 
     }); 
  });
}

io.sockets.on('connection', function (socket) {
	consumeAndEmit(socket);
});
