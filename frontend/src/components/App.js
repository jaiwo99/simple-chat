require('normalize.css/normalize.css');
require('../styles/App.css');

import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import Store from '../services/LocalStorage';

import Login from './Login';
import Chat from './Chat';

export default class App extends React.Component {

  constructor(props) {
    super(props);

    this.store = new Store();
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
      return (<Chat author={this.author}/>)
    } else {
      return (<Login store={this.store} reloadFn={this.reload.bind(this)}/>)
    }
  }
}
