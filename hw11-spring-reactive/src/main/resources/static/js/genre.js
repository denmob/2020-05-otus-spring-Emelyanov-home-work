const urlGetGenreList = '/api/genre';

function getGenreList() {
  sendGetRequest(urlGetGenreList).then((genres) => {
    renderGenreList(genres);
  });

  function renderGenreList(genreList) {
    $('#select-genre').empty();
    genreList.forEach(function (genre) {
      $('#select-genre').append(new Option(genre.name, genre.id));
    })
  }
}
