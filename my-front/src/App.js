import logo from './logo.svg';
import './App.css';
import React from 'react'
import axios from 'axios'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React!!
        </a>
        <TestList/>
      </header>
    </div>
  );
}

class TestList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            notes: [
                'hello', 'world!'
            ]
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/notes")
            .then(response => {
                console.log(response.data['_embedded']['noteModelList']);
                this.setState({
                    notes: response.data['_embedded']['noteModelList']
                });
            })
            .catch(reason => console.log(reason));
    }

    render() {
        let list = this.state.notes.map(note => {
            let detail = note.details ? note.details.map(detail => <li key={detail.detail}>{detail.detail}</li>) : undefined;
            let tag = note.tags ? note.tags.map(tag => <li key={tag.name}>{tag.name}</li>) : undefined;
            return (
                    <li key={note.keyword}>
                        {note.keyword}
                        <ul>
                            {detail}
                            {tag}
                        </ul>
                    </li>
                )
            }
        )

        return (
            <ul>{list}</ul>
        )
    }

}

export default App;
