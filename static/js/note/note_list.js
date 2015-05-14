// 初始化滚动分页
function initias(){
	var ias = jQuery.ias({
	  container:  '#note-list',
	  item:       '.note-item',
	  pagination: '#pagination',
	  next:       '.next'
	});
	// Add a loader image which is displayed during loading
	ias.extension(new IASSpinnerExtension());
	// Add a link after page 2 which has to be clicked to load the next page
	ias.extension(new IASTriggerExtension({offset: 1}));
	// Add a text when there are no more pages left to load
	ias.extension(new IASNoneLeftExtension({text: "You reached the end"}));
	ias.on('rendered', function(items) {
	    var $items = $(items);
	    console.log($items.length);
		initNoteItem($items);
	});
}

function initNoteItem(selector) {
	var confirm_msg = "确认删除？该操作不可撤销！";
	selector.find('a.del').click(function(){
		var id = $(this).siblings('input').val();
		if(confirm(confirm_msg)){
			$.ajax({
				type: "POST",
				url: "/note/delete",
				data: 'id='+id,
				error: function(data) {
					alert('delete note error');
				},
				success: function(data){
					$('#note'+id).fadeOut();
				}
			});
		}
	});
	selector.find('a.star').click(function(){
		var _this = $(this); 
		var id = _this.siblings('input').val();
		var _span = _this.children('span');
		var starred = _span.hasClass('glyphicon-star-empty');
		$.ajax({
			type: "POST",
			url: "/note/star",
			data: {'id': id, 'starred': starred},
			error: function(data) {
				alert('star note error');
			},
			success: function(data){
				if(data.result){
					if(starred){
						_span.attr('title','取消星标');	
					} else {
						_span.attr('title','添加星标');
					}
					_span.toggleClass('glyphicon-star');
					_span.toggleClass('glyphicon-star-empty');
				} else {
					alert(data.returnMessage);
				}
			}
		});
	});
}
