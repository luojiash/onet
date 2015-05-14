$(document).ready(function(){
	initCateMenu($('#note-category-list .tree li > div'));
	/**
	 * input 获取焦点
	 */
	$('#category-edit-modal').on('shown.bs.modal', function (e) {
		$(this).find('input:last').focus();
	});
	// 目录编辑表单提交事件
	$('#category-edit-modal form').submit(function(e){
		e.preventDefault();
		var oper = $('#category-edit-modal input[name=op]').val();
		var cid = $('#category-edit-modal input[name=id]').val();
		var pid = $('#category-edit-modal input[name=pid]').val();
		var cName = $('#category-edit-modal input:last').val();
		var data = {'oper':oper, 'cid':cid, 'pid':pid, 'cname':cName};
		$.ajax({
			type: "POST",
			url: "/note/category/oper",
			data: data,
			error: function(data) {
				alert("category edit error");
			},
			success: function(data){
				if(data.result){
					$('#category-edit-modal').modal('hide');
					if(oper == 'ren') {
						$('#cName'+cid).text(cName);
					} else if(oper == 'cre') {
						var html = '<li id="cate'+data.returnCode+'"> <div> <a class="btn"> <span class="glyphicon glyphicon-minus"></span> </a> <a href="/note/list/more?cid='+data.returnCode+'"> <span id="cName'+data.returnCode+'">'+cName+'</span> <span class="badge">0</span> </a> <div class="dropdown"> <button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false"> <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span> </button> <ul class="dropdown-menu dropdown-menu-right" role="menu"> <li><a href="#" id="del-'+data.returnCode+'">删除</a></li> <li><a href="#" id="ren-'+data.returnCode+'">重命名</a></li> <li><a href="#" id="cre-'+data.returnCode+'">新建子文件夹</a></li> </ul> </div> </div> <ul class="collapse"> </ul> </li>';
						var _parent = $('#cate'+pid)
						var _ul = _parent.children('ul');
						_ul.append(html);
						_ul.collapse('show');
						if(_ul.children().length == 1) {
							initCateCollapse(_parent.children('div').children('a:nth-child(1)'));
						}
						initCateMenu(_ul.find(' > :last > div'));
					}
				} else {
					alert(data.returnMessage);
				}
			}
		});
	});
	// 目录点击事件
	$('#note-category-list li > div > a:nth-child(2)').click(function(e){
		e.preventDefault();
		var _this = $(this);
		$.ajax({
		   type: "GET",
		   url: _this.attr('href'),
		   error: function(data) {
	           alert("query note error");
	       },
		   success: function(data){
			   $('#tids').val('');
			   $('.col-md-9').html(data);
			   jQuery.ias().reinitialize();
			   $('#note-category-list li > div.active').removeClass('active');
			   _this.parent().addClass('active');
			   resetTag();
		   }
		});
	});
	// 清空标签筛选项
	$('#note-tag-list a.reset').click(function(){
		$.ajax({
		   type: "GET",
		   url: "/note/list/more",
		   data: "cid="+$('#cid').val(),
		   error: function(data) {
	           alert("query note error");
	       },
		   success: function(data){
			   $('.col-md-9').html(data);
			   jQuery.ias().reinitialize();
			   resetTag();
		   }
		});
		
	});
	function resetTag(){
		$('#tids').val('');
		var label_success = $('#note-tag-list a.label-success');
		label_success.removeClass('label-success');
		label_success.addClass('label-default');
	}
	// 标签点击事件
	$('#note-tag-list a.label').click(function(e){
		e.preventDefault();
		var _this = $(this);
		var mul = $('#note-tag-list input:checkbox').is(':checked');
		var tids = '';
		var tid_clicked = _this.attr('href').substr(1);
		if(mul){
			var tids_old = $('#tids').val();
			if(_this.hasClass('label-default')){
				tids = tids_old == '' ? tid_clicked:tids_old+','+tid_clicked;
			} else {
				var tids_arr = tids_old.split(',');
				tids_arr.splice($.inArray(tid_clicked,tids_arr),1);
				tids = tids_arr.join();
			}
		} else {
			var label_success = $('#note-tag-list a.label-success');
			var s_flag =  label_success.length > 1 || _this.hasClass('label-default');
			if(s_flag){
				tids = tid_clicked;
			} else {
				tids = '';
			}			
		}
		var data = 'tids='+tids+'&cid='+$('#cid').val();
		$.ajax({
		   type: "GET",
		   url: "/note/list/more",
		   data: data,
		   error: function(data) {
	           alert("query note error");
	       },
		   success: function(data){
			   $('#tids').val(tids);
			   $('#note-list').replaceWith(data);
			   jQuery.ias().reinitialize();
			   if(mul){
				   _this.toggleClass('label-success label-default');
			   } else {
				   label_success.removeClass('label-success');
				   label_success.addClass('label-default');
				   if(s_flag){
					   _this.removeClass('label-default');
					   _this.addClass('label-success');
				   }
			   }
		   }
		});
	});
});
/**
 * 初始化展开、收起目录
 */
function initCateCollapse(selector, cid){
	if(cid != ''){
		$('#cate'+cid).parents('.collapse').siblings('div').find('>a>span:first').toggleClass('glyphicon-minus glyphicon-plus');
		$('#cate'+cid).parents('.collapse').collapse('toggle');
	}
	selector.click(function () {
		var _this = $(this);
		var _ul = _this.parent().siblings('ul');
		if(_ul.children().length > 0){
			var _span = _this.children();
			_ul.collapse('toggle');
			_span.toggleClass('glyphicon-minus glyphicon-plus');
		}
	});
}

/**
 * 初始化目录（selector）的菜单（删除、重命名、新建子文件夹）
 */
function initCateMenu(selector){
	selector.hover(function(){
		$(this).children('.dropdown').show();
	},function(){
		$(this).children('.dropdown').hide();
	});
	selector.find(' > .dropdown > ul > li').click(function(e){
		e.preventDefault();
		var arr = $(this).children('a').attr('id').split('-');
		var id = arr[1];
		var op = arr[0];
		if(op == 'cre'){
			$('#category-edit-modal input[name=pid]').val(id);
			$('#category-edit-modal input[name=id]').val('');
			$('#category-edit-modal input[name=op]').val(op);
			$('#category-edit-modal input:last').val('');
			$('#category-edit-modal').modal();
		} else if(op == 'ren') {
			$('#category-edit-modal input[name=pid]').val('');
			$('#category-edit-modal input[name=id]').val(id);
			$('#category-edit-modal input[name=op]').val(op);
			$('#category-edit-modal input:last').val($('#cName'+id).text());
			$('#category-edit-modal').modal();
		} else if(op == 'del' && confirm("确认删除？")) {
			var data = {'oper':op, 'cid':id}; 
			$.ajax({
				type: "POST",
				url: "/note/category/oper",
				data: data,
				error: function(data) {
					alert("category edit error");
				},
				success: function(data){
					if(data.result){
						var _this = $('#cate'+id);
						if(_this.siblings().length == 0){
							_this.parent().parent().find('>div >a:first').unbind();
						}
						_this.fadeOut(); _this.remove();
					} else {
						alert(data.returnMessage);
					}
				}
			});
		}
	});
}

/**
 * 查询note count, 数字填充到selector
 */
function queryNoteCount(selector, param) {
	$.ajax({
		type: "GET",
		url: "/note/count",
		data: param,
		error: function(data) {
			console.error('query note count error');
		},
		success: function(data){
			selector.text(data);
		}
	});
}
