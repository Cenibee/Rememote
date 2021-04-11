import './App.css';
import React, { Suspense } from 'react';
import Home from './home/Home'
import {BrowserRouter as Router, Route, Switch, Link} from 'react-router-dom'
import NoteList from './list/NoteList'

function App() {
  return (
      <div className="App">
          <header>테스트 페이지</header>
          <Router>
              <nav>
                  <Link className="App-link" to={"/"}><p>홈</p></Link>
                  <Link className="App-link" to={"/list"}><p>리스트</p></Link>
              </nav>
              <article>
                  <Suspense fallback={<div>Loading...</div>}>
                      <Switch>
                          <Route exact path="/" component={Home}/>
                          <Route path="/list" component={NoteList}/>
                      </Switch>
                  </Suspense>
              </article>
          </Router>
          <footer>this is a footer</footer>
      </div>
  );
}

export default App;
