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
      .then(() => {
      })
      .then(() => {
      })
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
        <li key={i} className={item.author === this.author ? 'list-group-item-success': 'list-group-item-warning'}>
          <div>
            <span className="label label-primary ">{item.author}</span>
            &nbsp;
            <span className="label label-default">{item.createdDate}</span>
          </div>
          <div>
            <div>
              <h4>
                {item.message}
              </h4>
            </div>
          </div>
        </li>);
    });

    return (<div className="chat_window">
      <div className="top_menu chat_head">
        <div className="title"> Simple Chat</div>
      </div>
      <ul className="messages list-group chat_body"> {chatlogs}</ul>
      <div className="bottom_wrapper clearfix chat_foot">
        <form className="form-inline" onSubmit={this.handleSubmit.bind(this)}>
          <div className="input-group input-group-lg col-lg-10 col-md-10 col-sm-10">
            <input className="form-control" type="text" value={this.state.message} onChange={this.handleChange.bind(this)}/>
          </div>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <div className="input-group input-group-lg col-lg-1 col-md-1 col-sm-1">
            <button className="btn btn-primary btn-lg" type="submit">Send</button>
          </div>
        </form>
      </div>
    </div>);
  }
}
