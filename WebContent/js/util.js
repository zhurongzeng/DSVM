/* fix for ie8 */
if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(elt) {
		var len = this.length >>> 0;
		var from = Number(arguments[1]) || 0;
		from = (from < 0) ? Math.ceil(from) : Math.floor(from);
		if (from < 0) from += len;
		for (; from < len; from++) {
			if (from in this && this[from] === elt) return from;
		}
		return -1;
	};
}
Date.prototype.format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	};
	o['S'] = ('000' + o['S']).substr(('' + o['S']).length);
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

String.prototype.fill = function() {
	if (arguments.length === 0) return this;
	var str = this;
	for (var x in arguments[0]) {
		var re = new RegExp('\\{' + x + '\\}', 'gm');
		str = str.replace(re, arguments[0][x]);
	}
	return str;
};

String.prototype.format = function() {
	if (arguments.length === 0) return this;
	var str = this;
	for (var i = 0; i < arguments.length; i++) {
		var re = new RegExp('\\{' + i + '\\}', 'gm');
		str = str.replace(re, arguments[i]);
	}
	return str;
};
Array.prototype.minus = function(arr) {
	for (var i = this.length - 1; i >= 0; i--) {
		for (var j = 0; j < arr.length; j++) {
			if (arr[j] == this[i]) {
				this.splice(i, 1);
				break;
			}
		}
	}
};

function getParam(pname) {
	var urlt = window.location.href.split("?");
	theValue = '';
	if (!urlt[1]) {} else {
		var clearurl = urlt[1].split("#");
		var gets = clearurl[0].split("&");
		for (var i = 0; i < gets.length; i++) {
			var get = gets[i].split("=");
			if (get[0] == pname) {
				theValue = get[1];
				break;
			}
		}
	}
	return theValue;
}

function ajaxGetJson(url, data, successCallBack, failCallBack) {
	var param = data || {};
	param.rnd = Math.random();
	webix.ajax().get(url, param, function(text, data, XmlHttpRequest) {
		var json = data.json();
		if(json.code === '9998') {
			window.location.href = 'login.html' + '?_t=' + Math.random();
		} else if (json.code === '0000') {
			successCallBack(json);
		} else {
			failCallBack(json);
		}
	});
}
function ajaxPostJson(url, data, successCallBack, failCallBack) {
	var param = data || {};
	param.rnd = Math.random();
	webix.ajax().post(url, param, function(text, data, XmlHttpRequest) {
		var json = data.json();
		if(json.code === '9998') {
			window.location.href = 'login.html' + '?_t=' + Math.random();
		} else if (json.code === '0000') {
			successCallBack(json);
		} else {
			failCallBack(json);
		}
	});
}
