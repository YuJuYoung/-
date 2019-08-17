$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();

	var queryString = $(".answer-write").serialize();
	var url = $(".answer-write").attr("action");

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}

function onError() {
	alert("로그인이 필요합니다");
}

function onSuccess(data, status) {
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.name,
		    data.formattedCreateDate, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").prepend(template);

	$(".answer-write textarea").val("");
	$(".qna-comment-count strong").text(data.question.countOfAnswer);
}

$(".link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var url = deleteBtn.attr("action");

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			
		},
		success : function(data, status) {
			if (data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};