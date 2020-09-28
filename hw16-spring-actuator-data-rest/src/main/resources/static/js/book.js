const urlGetBookList = '/api/book';
const urlGetBook = '/api/book/?';
const urlDeleteBook = '/api/book/?';
const urlSaveBook = '/api/book';

function getBookList() {
  sendGetRequest(urlGetBookList).then((books) => {
    renderBookList(books);
  });
}

function getBook(bookId) {
  sendGetRequest(urlGetBook.replace('?', bookId)).then((book) => {
    renderBook(book);
  });
}

function deleteBook(bookId) {
  sendDeleteRequest(urlDeleteBook.replace('?', bookId)).then(() => {
    getBookList();
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

  sendSaveObject(urlSaveBook, type, book).then(() => {
    location.href = '/book/list';
    getBookList();
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

