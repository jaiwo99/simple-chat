import React from 'react';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

require('whatwg-fetch');

export default class Chat extends React.Component {

  constructor(props) {
    super(props);

    this.author = this.props.author;

    this.state = {
      message: '',
      chatlogs: []
    };
  }

  connect() {
    let socket = new SockJS('/apiws/chat');

    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({},
      () => { // Success
        this.stompClient.subscribe('/topic/chat', (response) => {
          this.addMessage(response.body);
        });
      },
      (error) => { // Error
        console.error('Connection reset; Attempt reconnect in some second(s)');
        console.error(error);
      }
    );
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  addMessage(data) {
    let messages = this.state.chatlogs;
    messages.push(JSON.parse(data));
    this.setState({
      chatlogs: messages
    });
  }

  submitMessage(message) {
    fetch('/api/messages', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        author: this.author,
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
      .then(() => {})
      .then(() => {})
      .catch((error) => {
        console.error('Failed post message:', error)
      });
  }

  handleSubmit(e) {
    this.submitMessage(this.state.message);
    this.setState({
      message: ''
    });

    e.preventDefault();
  }

  handleChange(e) {
    this.setState({
      message: e.target.value
    });
  }

  componentDidMount() {
    this.connect();
  }

  componentWillUnmount() {
    this.disconnect();
  }

  render() {
    let chatlogs = this.state.chatlogs.map((item, i) => {
      return (
        <li key={i}>
          <div>{item.createdDate}</div>
          <div>{item.author}</div>
          <div>{item.message}</div>
        </li>);
    });

    return (<div className="container chat">
      <div className="row chatlog">
        <ul>
          {chatlogs}
        </ul>
      </div>
      <div className="row chatinput">
        <form className="form" onSubmit={this.handleSubmit.bind(this)}>
          <input type="text" value={this.state.message} onChange={this.handleChange.bind(this)}/>
          <button type="submit">Send</button>
        </form>
      </div>
    </div>);
  }
}
