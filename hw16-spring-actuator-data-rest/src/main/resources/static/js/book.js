const urlGetBookList = '/rest/book';
const urlGetBook = '/rest/book/?';
const urlDeleteBook = '/rest/book/?';
const urlPostRestBook = '/rest/book';
const urlPutApiBook = '/api/book';

function getBookList() {
  sendGetRequest(urlGetBookList).then((books) => {
    renderBookList(books._embedded.book);
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

function saveApiBook(type, bookId, bookTitle, bookDate, authorId, genreId) {
  const authorTemp = {};
  const genreTemp = {};
  authorTemp.id = authorId;
  genreTemp.id = genreId;

  const bookTemp = createEntityBook(bookId, bookTitle, bookDate, authorTemp, genreTemp);

  sendSaveObject(urlPutApiBook, type, bookTemp).then(() => {
    location.href = '/book/list';
    getBookList();
  });
}

function saveRestBook(type, bookId, bookTitle, bookDate, author, genre) {
  let authorTemp = {};
  let genreTemp = {};
  authorTemp.firstName = author.slice(0, author.indexOf(';'));
  authorTemp.lastName = author.slice(author.indexOf(';') + 1, author.length);
  genreTemp.name = genre;

  const bookTemp = createEntityBook(bookId, bookTitle, bookDate, authorTemp, genreTemp);

  sendSaveObject(urlPostRestBook, type, bookTemp).then(() => {
    location.href = '/book/list';
    getBookList();
  });
}

function createEntityBook(bookId, bookTitle, bookDate, authorTemp, genreTemp) {
  const bookTemp = {};
  bookTemp.id = bookId;
  bookTemp.title = bookTitle;
  bookTemp.date = bookDate;
  bookTemp.author = authorTemp;
  bookTemp.genre = genreTemp;
  return bookTemp;
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

