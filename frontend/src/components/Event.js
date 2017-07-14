import React from "react";

require('whatwg-fetch');

export default class Event extends React.Component {

  constructor(props) {
    super(props);

    this.author = this.props.author;
    this.chatService = this.props.chatService;

    this.state = {
      city: 'Berlin',
      hidden: false,
      events: []
    };

    this.sendMessageToInterest = this.sendMessageToInterest.bind(this);
  }

  componentDidMount() {
    this.fetchEvents();
    setInterval(() => this.fetchEvents(), 2000);
  }

  fetchEvents() {
    fetch('/api/events', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300) {
          return response.json();
        } else {
          let error = new Error(response.statusText);
          error.response = response;
          throw error;
        }
      })
      .then((response) => {

        this.setState({
          events: response.events
        });
      })
      .catch((error) => {
        console.error('Failed post event:', error)
      });
  }

  submitCreateEvent(city) {
    fetch('/api/events', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        city: city,
        name: this.author
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
      .then(response => {
        let events = this.state.events;
        events.push({
          city: city,
          name: this.author
        });

        this.setState({
          events: events
        });
      })
      .catch((error) => {
        console.error('Failed post event:', error)
      });
  }

  handleSubmit(e) {
    this.submitCreateEvent(this.state.city);
    this.setState({
      hidden: true
    });

    e.preventDefault();
  }

  handleChange(e) {
    this.setState({
      city: e.target.value
    });
  }

  sendMessageToInterest(item) {
    this.chatService.sendMessage(`Hi @${item.name}, I'd like to meet you in ${item.city} ;)`, this.author)
  }

  render() {

    const formDisplay = this.state.events.filter(e => e.name === this.author).length === 0;

    let events = this.state.events.map((item, i) => {
      if (this.author === item.name) {
        return null;
      }
      return (
        <li key={i} className='clickableStyle' onClick={() => this.sendMessageToInterest(item)}>
          <div>
            <span><i className="icon glyphicon glyphicon-user"/> <strong>{item.name}</strong> from <strong>{item.city}</strong> wants to meet</span>
          </div>
        </li>);
    });

    return (
      <div className="container event_window">
        <div className="row event_list">
          <ul className="list-group">
            {events}
          </ul>
        </div>
        {formDisplay &&
        <div className="row event_form">
          <form className="form-inline" onSubmit={this.handleSubmit.bind(this)}>
            <div className="input-group">
              <label>City</label>
              <input className="form-control" type="text" value={this.state.city} onChange={this.handleChange.bind(this)}/>
            </div>
            <div className="input-group">
              <input className="form-control hidden" type="text" value={this.author} readOnly={true}/>
            </div>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <div className="input-group input-group-lg col-lg-1 col-md-1 col-sm-1">
              <button className="btn btn-primary btn-lg" type="submit">Send</button>
            </div>
          </form>
        </div>
        }
      </div>
    );
  }
}
