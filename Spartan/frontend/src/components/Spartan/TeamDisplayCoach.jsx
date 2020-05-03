import React, { Component } from "react";
//import { Link } from 'react-router-dom'
import axios from "axios";
import { API_URL } from "../../Constants";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";

class TeamDisplayCoach extends Component {
  constructor(props) {
    super(props);
    this.state = {
      information: "",
    };
    this.ChangeHandler = this.ChangeHandler.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.OpenPopUp = this.OpenPopUp.bind(this);
  }

  componentWillMount() {}

  componentDidMount() {
    var ssn = sessionStorage.getItem("ssn");
    console.log("ssn", ssn);
    axios.get(API_URL + `/coaches/coach/${ssn}`).then((response) => {
      if (response.status === 200) {
        console.log("Success!", response);
        this.setState({
          information: response.data,
        });
      } else {
        console.log("Session creation failed!");
      }

      console.log("Sent object", this.state);
    });
  }

  handleChange(event) {
    this.setState({ sharingType: event.target.value });
  }

  OpenPopUp = (property) => {
    confirmAlert({
      title: "Drop TryOut Session",
      message: "Are you sure you want to delete this Created TryOut Session?",
      buttons: [
        {
          label: "Yes",
          onClick: () => {
            this.deleteSession(property);
          },
        },
        {
          label: "No",
          onClick: () => {},
        },
      ],
    });
  };

  deleteSession = (property) => {
    console.log("ala ka", property);
    let data = {
      session_id: property.sessionId,
      ssn: sessionStorage.getItem("ssn"),
      email: sessionStorage.getItem("userEmail"),
    };
    console.log("data be", data);
    axios.post(API_URL + `/coaches/removes/session`, data).then((response) => {
      if (response.status === 200) {
        console.log("Success!", response);
        alert(
          "Team Deleted Successfully."
        );
        window.location.reload();
      } else {
        console.log("Session creation failed!");
      }

      console.log("Sent object", this.state);
    });
  };

  ChangeHandler(e) {
    let change = {};
    change[e.target.name] = e.target.value;
    this.setState(change);
  }

  render() {
    let view = null;
    if (this.state.information.length > 0) {
      view = this.state.information.map((property) => {
        console.log("property", property);
        return (
          <div className="container">
            <hr></hr>
            <div class="property_details">
              <div class="row">
                <div class="col-md-12 right-side">
                  <h3>{property.propertyDescription}</h3>
                  <br></br>
                  <p class="info">
                    <strong>Tryout Session Name :</strong>{" "}
                    {property.team_tryOutSession}
                    <strong> Session Year : </strong> {property.year}
                    <button
                      class="btn btn-danger"
                      name="BookButton"
                      onClick={() => this.OpenPopUp(property)}
                    >
                      <span> Drop Session</span>
                    </button>
                  </p>
                </div>
              </div>
            </div>
            <hr></hr>
          </div>
        );
      });
    } else {
      view = (
        <div class="property_detials">
          <h3>No Team TryOut Sessions Created By You till date !</h3>
        </div>
      );
    }
    return (
      <div>
        <div id="mainbody">
          <div class="container main-content">
            <br></br>
            {view}
          </div>
        </div>
      </div>
    );
  }
}

export default TeamDisplayCoach;
