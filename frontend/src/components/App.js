require('normalize.css/normalize.css');
require('../styles/App.css');

import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import Store from '../services/LocalStorage';

import Login from './Login';
import Chat from './Chat';
import Event from './Event';
import ChatService from './Chat.serivce';

export default class App extends React.Component {

  constructor(props) {
    super(props);

    this.store = new Store();
    this.chatService = new ChatService();
    this.state = {};
  }

  componentDidMount() {
    this.reload();
  }

  reload() {
    let name = this.store.read();
    if (name) {
      this.author = name;
      this.setState({
        showChat: true
      });
    } else {
      this.setState({
        showChat: false
      });
    }
  }

  render() {
    if (this.state.showChat) {
      return (
        <div className="wrapper">
          <Chat author={this.author} chatService={this.chatService}/>
          <Event author={this.author} chatService={this.chatService}/>
        </div>
      )
    } else {
      return (<Login store={this.store} reloadFn={this.reload.bind(this)}/>)
    }
  }
}
