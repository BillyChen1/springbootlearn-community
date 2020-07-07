//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);

}

function comment() {
    var commentId = $("#comment_id").val();
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if (response.code == 2000) {
                //$("#comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=279cb28370f563dc4804&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

//展开或收起二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    //根据id取到折叠的二级评论的属性
    var comments = $("#comment-" + id);

    //该方法检查每个元素中指定的类。如果不存在则添加类，如果已设置则删除之。这就是所谓的切换效果
    comments.toggleClass("in");
    $(e).toggleClass("active");

    //GET获取二级评论内容
    $.getJSON("/comment/" + id, function (data) {
        $.each(data.data.reverse(), function (index, comment) {
            var mediaLeftElement = $("<div/>", {
                "class": "media-left"
            }).append($("<img/>", {
                "class": "media-object img-rounded",
                "src": comment.user.avatarUrl
            }));

            var mediaBodyElement = $("<div/>", {
                "class": "media-body"
            }).append($("<h5/>", {
                "class": "media-heading",
                "html": comment.user.name
            })).append($("<div/>", {
                "html": comment.content
            })).append($("<div/>", {
                "class": "menu"
            }).append($("<span/>", {
                "class": "pull-right",
                "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
            })));

            var mediaElement = $("<div/>", {
                "class": "media"
            }).append(mediaLeftElement).append(mediaBodyElement);

            var commentElement = $("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
            }).append(mediaElement);

            subCommentContainer.prepend(commentElement);
        });
        //展开二级评论
        comments.addClass("in");
        // 标记二级评论展开状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");
    });


}