const urlGetCommentList = '/api/comments/?';

function getCommentList(bookId) {
  sendGetRequest(urlGetCommentList.replace('?', bookId)).then((comments) => {
    renderCommentList(comments);
  });
}
