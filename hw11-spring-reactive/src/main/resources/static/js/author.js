const urlGetAuthorList = '/api/author';

function getAuthorList() {
  sendGetRequest(urlGetAuthorList).then((authors) => {
    return authors;
  });
}
