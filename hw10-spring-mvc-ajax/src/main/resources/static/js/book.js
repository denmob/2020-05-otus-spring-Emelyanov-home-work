const urlGetBookList = '/api/books';
const urlGetBook = '/api/book/?';
const urlDeleteBook = '/api/book/?';
const urlSaveBook = '/api/book';

function getBookList() {
  $.ajax({
    async: true,
    type: 'GET',
    url: urlGetBookList,
    success: function (books) {
      renderBookList(books);
    }
  });
}

function getBook(bookId) {
  $.ajax({
    async: true,
    type: 'GET',
    url: urlGetBook.replace('?', bookId),
    success: function (book) {
      renderBook(book);
    }
  });
}

function deleteBook(bookId) {
  $.ajax({
    type: "DELETE",
    url: urlDeleteBook.replace('?', bookId),
    success: function () {
      getBookList();
    }
  });
}


function saveBook(type, bookId, bookTitle, bookDate, authorId, genreId) {
  const book = {};
  const author = {};
  const genre = {};

  book.id = bookId;
  book.title = bookTitle;
  book.date = bookDate;
  author.id = authorId;
  book.author = author;
  genre.id = genreId;
  book.genre = genre;

  $.ajax({
    type: type,
    url: urlSaveBook,
    data: JSON.stringify(book),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function () {
      location.href = '/';
      getBookList();
    }
  });
}

function formatDate(date) {
  let d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

  if (month.length < 2)
    month = '0' + month;
  if (day.length < 2)
    day = '0' + day;

  return [year, month, day].join('-');
}
