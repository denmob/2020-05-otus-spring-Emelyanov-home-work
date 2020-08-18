const urlGetAuthorList = '/api/author';

function getAuthorList() {
  sendGetRequest(urlGetAuthorList).then((authors) => {
    renderAuthorList(authors);
  });
}

function renderAuthorList(authorList) {
  $('#select-author').empty();
  authorList.forEach(function (author) {
    $('#select-author').append(new Option(author.firstName + ' ' + author.lastName, author.id));
  })
}
