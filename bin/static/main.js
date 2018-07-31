function doSearch(value, name) {
	$('#searchForm')[0].submit();
}

function goToHomepage() {
	window.location.href = "./";
}
function goToIndexpage() {
	window.location.href = "/";
}

function openAddBookDialog() {
	console.log('add new book');
	$('#dialog-add-book')
			.dialog(
					{
						title : '添加新的百科',
						width : 500,
						height : 200,
						closed : false,
						cache : false,
						modal : true,
						content : '<form id="form-new-book" action="/wikibook/add" method="post" style="margin-top: 50px; margin-left: 50px">'
								+ '<input id="ti-filepath" name="path" style="width: 350px;" class="easyui-textbox" data-options="label:\'ZIM文件路径：\',labelWidth: 110, required: true">'
								+ '</form>',
						buttons : [
								{
									text : '确认',
									handler : function() {
										$('#dialog-add-book').dialog('close',
												false);
										$('#form-new-book')
												.form(
														'submit',
														{
															success : function(
																	data) {
																var data = eval('('
																		+ data
																		+ ')');
																if (data.status == 'success') {
																	location
																			.reload();
																} else {
																	$.messager
																			.alert(
																					'添加ZIM文件失败',
																					data.msg);
																}
															}
														});
									}
								},
								{
									text : '取消',
									handler : function() {
										$('#dialog-add-book').dialog('close',
												false);
									}
								} ]
					});
}
function removeBook(bookid) {
	$.messager.confirm('确认', '确定删除此ZIM文件吗？', function(r) {
		if (r) {
			$.ajax({
				url : '/wikibook/delete?bookid=' + bookid,
				method : 'GET',
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data.status == 'success') {
						location.reload();
					} else {
						$.messager.alert('删除ZIM文件失败', data.msg);
					}
				}
			});
		}
	});
	console.log('remove');
}

function askForIndex(bookid) {
	$.messager.confirm('创建索引', '此ZIM尚未创建索引，是否现在创建？', function(r) {
		if (r) {
			$.ajax({
				url : '/index/?bookid=' + bookid,
				method : 'GET',
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data.status == 'success') {
						location.href = '/wiki/' + bookid + '/article/';
					} else {
						$.messager.alert('创建索引失败', data.msg);
					}
				}
			});
		} else {
			location.href = '/wiki/' + bookid + '/article/';
		}
	});
}

// 分页查询
function pageQuery(page, size) {
	location.href = location.pathname + "?q=" + $('#history')[0].value
			+ "&page=" + page + "&size=" + size;
}
