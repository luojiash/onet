var init = false;
var replyForm;
function initReplyBtn(btnSelector) {
	btnSelector.click(function(){
		if (replyForm == undefined) replyForm = $('.reply-box');
		var _this = $(this);
		if(_this.hasClass('active')) {
			replyForm.hide();
		} else {
			$('.reply-btn').removeClass('active');
    		replyForm.insertAfter(_this.parent()).show();
    		var pid = _this.siblings('input').val();
    		replyForm.find('>form>div>input[name=pid]').val(pid);
    		if(!init){
        		var inlineeditor = new Simditor({
        		    textarea: $('#inline-reply-content'),
        		    toolbar: ['bold','italic','blockquote','code','link','hr'],
        		    toolbarFloat: false,
        		});
        		init = true;
    		}
		}
		_this.toggleClass('active');
	});
}

function initForm(form) {
	form.submit(function(e){
		e.preventDefault();
		var _this = $(this);
		$.ajax({
			type: 'POST',
			url: '/article/comment/add',
			data: $(this).serialize(),
			error: function(){
				alert('add comment error!');
			},
			success: function(data){
				if(data.result){
					var now=new Date();
					var date = now.getFullYear()+'-'+(now.getMonth()+1)+'-'+now.getDate()+' '+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
					var cont = $('textarea', _this).val();
					var comment = $('<li class="" id="comment'+data.returnCode+'"> <div> <div><b>@'+$('.glyphicon-user:first').parent().text()+'</b></div> <div> <p>'+cont+'</p> </div> <div class="action"> <span>'+date+'</span> <a href="javascript:void(0);" class="reply-btn"><span class="glyphicon glyphicon-share-alt"></span> 回复</a> <input class="hidden" value="'+data.returnCode+'"> </div> </div> <hr> <ul>  </ul> </li>');
					if(_this.hasClass('common')) {
						_this.parent().hide();
						comment.prependTo(_this.parent().parent().siblings('ul'));
					} else {
						comment.appendTo($('#comment-list > ul'));
					}
					var btn = $('.reply-btn', comment);
					initReplyBtn(btn);
				} else {
					alert(data.returnMessage);
				}
			}
		});
	});
}

function loadComment(articleId, commentContainer) {
	$.ajax({
		type: 'GET',
		url: '/article/comment/'+articleId,
		error: function(){
			alert('get comment error!');
		},
		success: function(data){
			commentContainer.append(data);
		}
	});
}

function initArticleItem(selector, trash) {
	if(trash) {
		var toTrash = false;
		var confirm_msg = "确认删除？该操作不可撤销！";
	} else {
		var toTrash = true;
		var confirm_msg = "确认删除到回收站？";
	}
	selector.find('a.del').click(function(){
		var id = $(this).siblings('input').val();
		if(confirm(confirm_msg)){
			$.ajax({
				type: "POST",
				url: "/article/delete",
				data: 'aid='+id+'&toTrash='+toTrash,
				error: function(data) {
					alert('delete article error');
				},
				success: function(data){
					if(toTrash)	$('#article'+id+" > h2 >span").text('（已删除）');
					else $('#article'+id).fadeOut().remove();
					toastr.success('删除成功');
				}
			});
		}
	});
	selector.find('a.restore').click(function(){
		var id = $(this).siblings('input').val();
		$.ajax({
			type: "POST",
			url: "/article/trash/restore",
			data: {'aid': id},
			error: function(data) {
				alert('restore article error');
			},
			success: function(data){
				$('#article'+id).fadeOut();
			}
		});
	});
	selector.find('a.share').click(function(){
		var id = $(this).siblings('input').val();
		$.ajax({
			type: "POST",
			url: "/article/share/create",
			data: {'articleId': id},
			error: function(data) {
				alert('share article error');
			},
			success: function(data){
				var a = $('.modal-body > div > a');
				a.attr('href', data.returnCode);
				a.text(data.returnCode);
				$('.modal').modal();
			}
		});
	});
	
	selector.find('a.cancelshare').click(function(){
		var id = $(this).siblings('input').val();
		$.ajax({
			type: "POST",
			url: "/article/share/cancel",
			data: {'articleId': id},
			error: function(data) {
				alert('cancel share article error');
			},
			success: function(data){
				$('#article'+id+" > h2 >span").text('（已取消分享）');
			}
		});
	});
}
