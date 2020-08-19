const urlGetCommentList = '/api/comments/?';

function getCommentList(bookId) {
  sendGetRequest(urlGetCommentList.replace('?', bookId)).then((comments) => {
    renderCommentList(comments);
  });
}

function renderCommentList(commentList) {
  $('#tbodyCommentList').empty();
  commentList.forEach(function (comment) {
    $('#tbodyCommentList').append(`
               <tr>
                   <td>${comment.commentary}</td>
                   <td>${new Date(comment.timestamp).toString()}</td>
               </tr>
            `)
  })
}
