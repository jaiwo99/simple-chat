import React from 'react';

export default class Login extends React.Component {

  constructor(props) {
    super(props);

    this.store = this.props.store;
    this.reload = this.props.reloadFn;

    this.state = {
      author: ''
    };
  }

  render() {
    return (<div className="login">
        <form onSubmit={this.login.bind(this)}>
          <input type="text" value={this.state.author} onChange={this.handleChange.bind(this)}/>
          <button type="submit">Enter</button>
        </form>
      </div>
    );
  }

  login(e) {
    this.store.save(this.state.author);
    this.reload();

    e.preventDefault();
  }

  handleChange(e) {
    this.setState({
      author: e.target.value
    });
  }
}
