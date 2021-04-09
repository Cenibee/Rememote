import React from "react";
import axios from "axios";

export default class NoteList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            notes: []
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/note/list")
            .then(response => {
                let notes = response.data['_embedded']['noteModelList'].map(note => {
                    return {
                        keyword: note.keyword,
                        link: note._links.self.href
                    }
                });
                this.setState({
                    notes: notes
                });
            })
            .catch(reason => console.log(reason));
    }

    render() {
        let list = this.state.notes.map(note =>
            <NoteDetail
                key={note.link}
                link={note.link}
                keyword={note.keyword} />
        )

        return (
            <ul>{list}</ul>
        )
    }
}

class NoteDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isOpened: false,
            tags: [],
            categories: []
        }
        this.getDetails = this.getDetails.bind(this);
        this.onClick = this.onClick.bind(this);
    }

    onClick() {
        if (!this.state.isOpened) {
            axios.get(this.props.link)
                .then(response => {
                    console.log(response.data);
                    let categories = response.data.details.map(category => {
                        return {
                            category: category.category,
                            detail: category.detail
                        }
                    });
                    let tags = response.data.tags.map(tag => tag.name);
                    this.setState({
                        categories: categories,
                        tags: tags
                    })
                });
        }
        this.setState({
            isOpened: !this.state.isOpened
        });
    }

    getDetails() {
        if (!this.state.isOpened) return null;
        let tags = this.state.tags.map(tag => <li key={tag}>{tag}</li>);
        let details = this.state.categories.map(category => <li key={category.category}>{category.category}: {category.detail}</li>);
        return (
            <ul>
                {tags}
                {details}
            </ul>
        );
    }

    render() {
        return (
            <li key={this.props.link} onClick={this.onClick}>
                {this.props.keyword}
                {this.getDetails()}
            </li>
        )
    }
}