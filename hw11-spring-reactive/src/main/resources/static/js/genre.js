const urlGetGenreList = '/api/genre';

function getGenreList() {
  sendGetRequest(urlGetGenreList).then((genres) => {
    return genres;
  });
}
