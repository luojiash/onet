var sim_pro = {
	textarea: $('#content'),
	toolbar: ['title','bold','italic','underline','strikethrough','color','|','ol','ul','blockquote','code','table','|','link','image','hr','|','indent','outdent',],
	upload : {  
		url : '/file/upload',  
		params: null,
		fileKey: 'uploadFileName', //服务器端获取文件数据的参数名  
		connectionCount: 3,  
		leaveConfirm: '正在上传文件'  
	},
	pasteImage: true,
	defaultImage: '//oimg.com/img/image.jpg',
}
$(document).ready(function(){
	if(localStorage.mdEnabled == undefined) {
		$.ajax({
			type: "GET",
			url: "/user/setting/get",
			error: function(data) {
				alert("get user setting error");
			},
			success: function(data){
				if (data) {
					localStorage.mdEnabled = data.mdEnabled;
				} else {
					alert(data);
				}
			}
		});
	}
	if(localStorage.mdEnabled == 'true') {
		sim_pro.markdown = true;
	}
	var editor = new Simditor(sim_pro);
	window.editor = editor;
});