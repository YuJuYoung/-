$(".answer-write input[type=submit]").on("click", function(e) {
	e.preventDefault();

	var queryString = $(".answer-write").serialize();
	var url = $(".answer-write").attr("action");

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function() {
			alert("로그인이 필요합니다");
		},
		success : function(data, status) {
		    var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(data.writer.name, data.formattedCreateDate, data.contents, data.question.id, data.id);
			
			$(".qna-comment-slipp-articles").prepend(template);
			$(".answer-write textarea").val("");
			$(".qna-comment-count strong").text(data.question.countOfAnswer);
		}
	});
});

$(document).on("click", "a.link-delete-article-answer", function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(request, error) {
			alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
		},
		success : function(data, status) {
			if (data.valid) {
				deleteBtn.closest("article").remove();

				$(".qna-comment-count strong").text($(".qna-comment-count strong").text() - 1);
			} else {
				alert(data.errorMessage);
			}
		}
	});
});

$(document).on("click", "a.link-modify-article-answer", function updateAnswer(e) {
	e.preventDefault();

	var modifyBtn = $(this);
	var url = modifyBtn.attr("href");
	var queryString = modifyBtn.serialize();

	$.ajax({
		type : 'get',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function() {
			alert("로그인 중이 아니거나 자신의 것이 아닙니다");
		},
		success : function(data, status) {
			var updateTemplate = $("#updateAnswerTemplate").html().format(data.question.id, data.id, data.contents);

			modifyBtn.closest("article").append(updateTemplate);
			
			var contents = modifyBtn.closest("div").prev().detach();
			var selectors = modifyBtn.closest("div").detach();

			$(".answer-update input[type=submit]").on("click", function(e) {
				e.preventDefault();

				var queryString2 = $(".answer-update").serialize();
				var url = $(".answer-update").attr("action");

				$.ajax({
					type : 'put',
					url : url,
					data : queryString2,
					dataType : 'json',
					error : function() {
						alert("오류 발생");
					},
					success : function(data, status) {
						contents.children('p').text(data.contents);

						$(".answer-update").before(contents).before(selectors);
						$(".answer-update").remove();
					}
				});
			});
		}
	});
});

$(".user-delete button[type=submit]").on("click", function(e) {
	e.preventDefault();
	
	var url = $(".user-delete").attr("action");
	var queryString = $(".user-delete").serialize();
	
	$.ajax({
		type : 'delete',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(request, error) {
			alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
		},
		success : function(data) {
			if (data.valid) {
				location.href = "http://localhost:8080";
			} else {
				alert(data.errorMessage);
			}
		}
	});
});

$(".user-login input[type=submit]").on("click", function(e) {
	e.preventDefault();

	var url = $(".user-login").attr("action");
	var queryString = $(".user-login").serialize();

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(xhr, status) {
			alert("오류 발생");
		},
		success : function(data, status) {
			if (data.valid) {
				location.href = "http://localhost:8080";
			} else {
				alert(data.errorMessage);
			}
		}
	});
});

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};