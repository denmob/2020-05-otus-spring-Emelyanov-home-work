const urlGetCommentList = '/api/comment/list/?';


function getCommentList(bookId) {
  $.ajax({
    async: true,
    type: 'GET',
    url: urlGetCommentList.replace('?', bookId),
    success: function (commentList) {
      renderCommentList(commentList);
    }
  });
}
