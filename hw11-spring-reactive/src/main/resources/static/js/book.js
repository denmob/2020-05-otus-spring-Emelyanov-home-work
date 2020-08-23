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
    location.href = '/';
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

function renderBook(book) {
  document.getElementById('input-id').value = book.id;
  document.getElementById('input-title').value = book.title;
  document.getElementById('input-date').value = formatDate(book.date);
  document.getElementById('select-author').value = book.author.id;
  document.getElementById('select-genre').value = book.genre.id;
}

function renderBookList(bookList) {
  $('#tbodyBookList').empty();
  bookList.forEach(function (book) {
    $('#tbodyBookList').append(`
               <tr>
                   <td>${book.title}</td>
                   <td>${new Date(book.date).toDateString()}</td>
                   <td>${book.author.firstName} ${book.author.lastName}</td>
                   <td>${book.genre.name}</td>
                   <td><a href="/pageBookEdit/bookId=${book.id}">Edit book</a></td>
                   <td><a href="/pageCommentList/${book.id},${book.title}">View comments</a></td>
                   <td><button onclick="deleteBook('${book.id}')">Delete book</button></td>
               </tr>
            `)
  })
}

