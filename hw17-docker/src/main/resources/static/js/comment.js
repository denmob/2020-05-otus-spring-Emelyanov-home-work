const urlGetCommentList = '/rest/comment/search/find-all-by-bookId?bookId=*';

function getCommentList(bookId) {
  sendGetRequest(urlGetCommentList.replace('*', bookId)).then((comments) => {
    renderCommentList(comments._embedded.comment);
  });
}
