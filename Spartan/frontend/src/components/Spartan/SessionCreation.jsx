import React, { Component } from "react";
// import { Link } from 'react-router-dom'
// import AuthenticationForApiService from './AuthenticationForApiService.js'
import axios from "axios";
import { API_URL } from "../../Constants";
//import { render } from "react-dom";

class SessionCreation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      //session_id: "10",
      session_name: "Spinning",
      capacity: 20,
      section: "SpartanGym",
      room_number: 32,
      start_time: "10:15",
      end_time: "12:00",
      activity_id: "2",
      instructor_ssn: "",
      session_date: "2020-08-23",
      session_description: "",
    };
    this.handleChange = this.handleChange.bind(this);
    this.submitSession = this.submitSession.bind(this);
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  submitSession = (e) => {
    // console.log(sessionStorage.userEmail);
    // console.log(sessionStorage.ssn);
    e.preventDefault();
    const data = {
      session_name: this.state.session_name,
      capacity: this.state.capacity,
      section: this.state.section,
      room_number: this.state.room_number,
      start_time: this.state.start_time,
      end_time: this.state.end_time,
      activity_id: this.state.activity_id,
      instructor_ssn: sessionStorage.getItem("ssn"),
      instructor_email: sessionStorage.getItem("userEmail"),
      session_date: this.state.session_date,
      session_description: this.state.session_description
    };

    axios.post(API_URL + "/sessions/new", data).then((response) => {
      console.log("response",response)
      if (response.status === 200) {
        alert(
          "Session Created Successfully."
        );
        this.props.history.push(`/instructorDashboard`);
      } else {
        console.log("Session creation failed!");
      }

      // console.log("Sent object", this.state);
    });

    // console.log("DATA SENT", JSON.stringify(data));
  };

  render() {
    return (
      <div>
        <div className="container-fluid">
          <br />
          <br />
          <div className="row">
            <div className="col-sm-3 col-md-3"></div>

            <div
              className="col-sm-6 col-md-6"
              style={{
                backgroundColor: "white",
                opacity: 0.9,
                filter: "Alpha(opacity=90)",
                borderRadius: "20px",
              }}
            >
              <h3>
                <br />
                Create a new session
              </h3>

              <form onSubmit={this.submitSession}>
                <div className="row">
                  <div className="col-sm-8 col-md-8">
                    <br />
                    <div className="form-group">
                      <label htmlFor="session_name">
                        <h5>Session Name</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="session_name"
                        id="session_name"
                        placeholder="Session name"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  <div class="col-sm-4 col-md-4">
                    <div class="form-group">
                      <br />
                      <label for="activity">
                        <h5>Activity</h5>
                      </label>
                      <div class="form-group">
                        <select
                          class="form-control"
                          id="activity"
                          name="activity_id"
                          onChange={this.handleChange}
                        >
                          <option value="1">Yoga</option>
                          <option value="2">Zumba</option>
                          <option value="3">Fitness</option>
                          <option value="4">Basketball</option>
                          <option value="5">Volleyball</option>
                          <option value="6">Baseball</option>
                          <option value="7">Badminton</option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div className="col-sm-1 col-md-1"></div>
                </div>

                <div className="row">
                  <div className="col-sm-12 col-md-12">
                    <div className="form-group">
                      <label htmlFor="session_description">
                        <h5>Session Description</h5>
                      </label>
                      <textarea
                        className="form-control"
                        name="session_description"
                        id="session_description"
                        rows="4"
                        placeholder="Describe this session..."
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                </div>

                <div className="row">
                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="section">
                        <h5>Location</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="section"
                        id="section"
                        placeholder="section"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="room_number">
                        <h5>Room Number</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="room_number"
                        id="room_number"
                        placeholder="Room number"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="capacity">
                        <h5>Capacity</h5>
                      </label>
                      <input
                        type="number"
                        className="form-control"
                        name="capacity"
                        id="capacity"
                        placeholder="capacity"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-2 col-md-2"></div>
                </div>

                <div className="row">
                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>Start time</h5>
                      </label>
                      <input
                        type="time"
                        className="form-control"
                        name="start_time"
                        id="start_time"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="end_time">
                        <h5>End time</h5>
                      </label>
                      <input
                        type="time"
                        className="form-control"
                        name="end_time"
                        id="end_time"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  <div className="col-sm-4 col-md-4">
                    <div className="form-group">
                      <label htmlFor="session_date">
                        <h5>Date</h5>
                      </label>
                      <input
                        type="date"
                        className="form-control"
                        name="session_date"
                        id="session_date"
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  {/* <div className="col-sm-12 col-md-12 ">
                        <div className="form-group">
                          <label htmlFor="where">
                            <h5>Password</h5>
                          </label>
                          <input
                            type="text"
                            className="form-control"
                            name="password"
                            id="password"
                            placeholder="password"
                            value={this.state.password}
                            onChange={this.handleChange}
                            required
                          />
                        </div>
                      </div> */}

                  <div className="col-sm-1 col-md-1"></div>
                </div>

                <div className="row">
                  <div className="col-sm-12 col-md-12">
                    <div className="form-group">
                      <br />
                      <input
                        type="submit"
                        className="form-control btn btn-danger"
                      />
                      <br />
                      <br />
                    </div>
                  </div>

                  <br />
                  {this.state.hasFailed && (
                    <div className="alert alert-warning">
                      User Creation Failed Check console for More Info.
                    </div>
                  )}
                  {this.state.isDuplicateUser && (
                    <div className="alert alert-warning">
                      User already exists.
                    </div>
                  )}

                  {this.state.showSuccessMessage && (
                    <div className="alert alert-warning">
                      User Created Successfully
                    </div>
                  )}
                  <br />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default SessionCreation;
