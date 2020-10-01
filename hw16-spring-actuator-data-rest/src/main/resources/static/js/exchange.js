async function sendGetRequest(url = '') {
  const response = await fetch(url, {
    method: 'GET',
  });
  return await response.json();
}

async function sendDeleteRequest(url = '') {
  const response = await fetch(url, {
    method: 'DELETE',
  });
  return await response.text();
}

async function sendSaveObject(url = '', methodType, data = {}) {
  const response = await fetch(url, {
    method: methodType,
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
  return await response.json();
}
