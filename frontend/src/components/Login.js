import React from "react";

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
    return (<div className="container">
        <h1 className="jumbotron">Simple Chat</h1>
        <div className="login">
          <h3>Please enter your name here:</h3>
          <form onSubmit={this.login.bind(this)} className="form-inline">
            <div className="form-group">
              <label className="sr-only" htmlFor="author">Enter your name here: </label>
              <input id="author" className="form-control" type="text" value={this.state.author} onChange={this.handleChange.bind(this)}/>
            </div>
            &nbsp;&nbsp;
            <div className="form-group">
              <button className="btn btn-primary" type="submit">Enter</button>
            </div>
          </form>
        </div>
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
