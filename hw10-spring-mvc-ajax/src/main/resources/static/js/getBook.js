const urlBook = '/api/book/edit(bookId=?)';

function getBook(bookId) {
  $.ajax({
    async: true,
    type: 'GET',
    url: urlBook.replace('?',bookId),
    success: function (book) {
      renderBook(book);
    }
  });
}
