require('normalize.css/normalize.css');
require('styles/App.css');

import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import React from 'react';

class AppComponent extends React.Component {
  render() {
    return (
      <div className="index">
        <h1 className="jumbotron">It works</h1>
      </div>
    );
  }
}

AppComponent.defaultProps = {
};

export default AppComponent;
