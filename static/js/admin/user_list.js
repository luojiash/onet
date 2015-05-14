
/**
 * activate or inactivate user
 */
function bindLabel(){
	$('a.label').click(function(e){
		e.preventDefault();
		_this = $(this);
		var active = true;
		if(_this.hasClass('label-success')){
			active = false;
		}
		var userId = _this.parents('tr').children(':first').text();
		$.ajax({
		   type: "POST",
		   url: "/admin/user/activation",
		   data: {'userId':userId, 'active':active},
		   error: function(data) {
	           alert("error");
	       },
		   success: function(data){
			   if (data.result) {
				   if(active) {
					   _this.text('Active');
					   _this.removeClass("label-default");
					   _this.addClass("label-success");
				   }else{
					   _this.text('Inactive');
					   _this.removeClass("label-success");
					   _this.addClass("label-default");
				   }
				   
			   } else {
				   alert(data.returnMessage);
			   }
		   }
		});
	});
}
/**
 * change user role
 */
function bindDropdown(){
	$('.dropdown-menu.role a').click(function(e){
		e.preventDefault();
		_this = $(this);
		_role = _this.parent().parent().prev();
		var currentRole = _role.text().trim();
		var role = _this.text();
		if(currentRole != role){
			var userId = _this.parents('tr').children(':first').text();
			$.ajax({
			   type: "POST",
			   url: "/admin/user/role",
			   data: {'userId':userId, 'role':role},
			   error: function(data) {
		           alert("change role error");
		       },
			   success: function(data){
				   if (data.result) {
					   _role.html(role+' <span class="caret"></span>');
				   } else {
					   alert(data.returnMessage);
				   }
			   }
			});
		}
	});
}

function simplePage(total,currentPage,pageSize){
    $('#pagination').pagination({
        items: total,
        itemsOnPage: pageSize,
        currentPage: currentPage,
        onPageClick: function(index, e) {
        	e.preventDefault();
			var keyWord = $('#keyWord').val();
			ajaxPage(index, keyWord);
		}
    });
}

function ajaxPage(page, keyWord){
	$.ajax({
		url : "/admin/user/pagination",
		data : 'page=' + page+'&u='+keyWord,
		type : 'GET',
		success : function(data) {
			$('tbody').html(data);
		}
	});
}