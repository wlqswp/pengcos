document.writeln("");
var tab = "09最美快女,90后自拍,by2,艾薇儿,安以轩,滨崎步,蔡少芬,蔡依林,陈宝莲,陈好,陈红,陈慧琳,大s写真,董卿,董卿珍藏照,饭岛爱,高圆圆,巩俐青涩泳装,关之琳,闺房照,郭晶晶罕见旧照,韩国艳照门,何炅夫妻照,何洁,何洁闺房照,黑涩会美眉,极品车模,蒋勤勤,金瓶梅2剧照,金喜善,李丽珍,李孝利,梁洛施,林熙蕾,林依晨,林志玲素颜照,刘嘉玲,刘璇,刘亦菲,刘亦菲巴厘岛风情,麦当娜,美女警花,美女自拍,美腿大赛冠军,名模真空走秀,青春美女写真,邱淑贞,山寨范冰冰,佘诗曼,舒淇,松岛枫,宋慧乔,孙燕姿,台球美女,汤芳,藤原纪香,天心,甜性涩爱剧照,王菲,王小丫,网络第一美女,温碧霞,萧亚轩,谢娜海边写真,性感车模,性感私房照,徐熙媛,杨丞琳,杨思敏,叶璇最新写真,叶子楣,张柏芝,张惠妹,张娜拉,张韶涵,张筱雨,张馨予,章子怡,章子怡狂野照,赵薇,赵薇低胸惊艳,珍藏照,钟欣桐,周笔畅,周海媚,周慧敏,周迅,朱茵";
//随机选取
var arr = tab.split(',');
var le = arr.length;
var temp;
var i;
var si = 18;
var s = new Array([si]);
for(i=0;i<le;i++){if(i==0){temp = i;}else{temp = temp + '|' + i;}}
for(i=0;i<=si-1;i++){
	s[i] = temp.split('|')[Math.floor(Math.random()*temp.split('|').length)];
	temp = '|'+temp+'|';
	temp = temp.replace('|'+s[i]+'|','|');
	temp = temp.substring(1,temp.length-1);
}
//输出
var c;
var h;
var k;
var w;
var j;
calendar = new Date();
var date = calendar.getDate();
var month = calendar.getMonth()+1;
for(i=0;i<si;i++){
	w = cTrim(arr[s[i]],0).split('|');
	k = w[0];
	h = k;
	if(w.length>1){
		h = w[1];
	}
	document.write('<li><span class=\"rplu\">'+month+'-'+date+'</span><a href="http://www.meitushow.com/?query='+h+'&amp;pid=sogou-wsse-99151cecc1246d5f" target="_blank">'+k+'</a></li>');
}
//Function

function cTrim(sInputString,iType){
	var sTmpStr = ' ';
	var i = -1;
	if(iType == 0 || iType == 1){
		while(sTmpStr == ' '){
			++i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(i);
	}
	if(iType == 0 || iType == 2){
		sTmpStr = ' ';
		i = sInputString.length;
		while(sTmpStr == ' '){
			--i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(0,i+1);
	}
	return sInputString;
}