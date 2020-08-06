const urlBooks = '/api/book/list';

$(function () {
  $.ajax({
    async: true,
    type: 'GET',
    url: urlBooks,
    success: function (books) {
      renderBookList(books);
    }
  });
});
