document.writeln("");
var tab = "09������Ů,90������,by2,��ޱ��,������,���鲽,���ٷ�,������,�±���,�º�,�º�,�»���,��sд��,����,���������,������,��ԲԲ,������ɬӾװ,��֮��,�뷿��,��������������,����������,����������,�ν�,�ν�뷿��,��ɬ����ü,��Ʒ��ģ,������,��ƿ÷2����,��ϲ��,������,��Т��,����ʩ,������,������,��־��������,������,���,�����,����ư��嵺����,����,��Ů����,��Ů����,���ȴ����ھ�,��ģ�������,�ഺ��Ůд��,������,ɽկ������,��ʫ��,���,�ɵ���,�λ���,������,̨����Ů,����,��ԭ����,����,����ɬ������,����,��СѾ,�����һ��Ů,�±�ϼ,������,л�Ⱥ���д��,�Ըг�ģ,�Ը�˽����,������,��ة��,��˼��,Ҷ�����д��,Ҷ���,�Ű�֥,�Ż���,������,���غ�,������,��ܰ��,������,��������Ұ��,��ޱ,��ޱ���ؾ���,�����,����ͩ,�ܱʳ�,�ܺ���,�ܻ���,��Ѹ,����";
//���ѡȡ
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
//���
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