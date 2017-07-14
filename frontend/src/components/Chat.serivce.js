require('whatwg-fetch');

export default class ChatService {

  sendMessage(message, author) {
    fetch('/api/messages', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        author: author,
        message: message
      })
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300) {
          return response;
        } else {
          let error = new Error(response.statusText);
          error.response = response;
          throw error;
        }
      })
      .then(() => {
      })
      .then(() => {
      })
      .catch((error) => {
        console.error('Failed post message:', error)
      });
  }
}
