const urlGetCommentList = '/api/comment/book/?';

function getCommentList(bookId) {
  sendGetRequest(urlGetCommentList.replace('?', bookId)).then((comments) => {
    renderCommentList(comments);
  });
}
