import React, { Component } from 'react';

import Spartan from './components/Spartan/Spartan'
import './App.css';
import './bootstrap.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        {/*<Counter/>*/}
        <Spartan />
      </div>
    );
  }
}


export default App;