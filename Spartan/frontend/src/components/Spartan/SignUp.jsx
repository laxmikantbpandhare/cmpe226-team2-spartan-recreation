import React, { Component } from "react";
// import { Link } from 'react-router-dom'
// import AuthenticationForApiService from './AuthenticationForApiService.js'
import axios from "axios";
import { API_URL } from "../../Constants";

class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fname: sessionStorage.getItem("googleName"),
      lname: "",
      role: "Student",
      email: sessionStorage.getItem("googleEmail"),
      ssn: "",
      signup_status: "",
      hasFailed: false,
      isDuplicateUser: false,
      showSuccessMessage: false,
      // phoneno: "",
      college_year: "",
      // city: "",
      expiryDate: "",
      password: "",
    };
    this.submitSignUp = this.submitSignUp.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  componentWillMount() {}

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  changeHandler = (name, e) => {
    this.setState({
      [name]: e.target.value,
    });
  };

  submitSignUp = (e) => {
    // let role = "";
    // if (this.state.email.includes("@sjsu.edu")) {
    //   role = "host";
    // } else {
    //   role = "user";
    // }

    console.log("submit login called");
    //  var headers = new Headers();
    //prevent page from refresh
    e.preventDefault();
    const data = {
      email_id: this.state.email,
      ssn: this.state.ssn,
      lname: this.state.lname,
      fname: this.state.fname,
      college_year: this.state.college_year,
      password: this.state.password,
      user_role: this.state.role,
    };
    console.log("data", data);
    //set the with credentials to true
    //axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios.post(API_URL + "/persons", data).then((response) => {
      console.log("Status Code : ", response.status);
      if (response.status === 200) {
        console.log(response.data);
        if (response.data) {
          this.setState({
            signup_status: response.data.message,
            showSuccessMessage: true,
          });
        } else {
          this.setState({
            signup_status: "User already exists",
            isDuplicateUser: true,
          });
        }
      } else {
        console.log(response.data.error);
        this.setState({
          signup_status: response.data.error,
          hasFailed: true,
        });
      }
    });
  };

  render() {
    return (
      <div>
        <div className="container-fluid">
          <br />
          <br />
          <div className="row">
            <div className="col-sm-1 col-md-1"></div>

            <div
              className="col-sm-5 col-md-5"
              style={{
                backgroundColor: "white",
                opacity: 0.9,
                filter: "Alpha(opacity=90)",
                borderRadius: "10px",
              }}
            >
              <h3>
                Sign Up{" "}
                {!(sessionStorage.getItem("googleEmail") === null) && (
                  <div>Via Google.</div>
                )}
              </h3>
              {!(sessionStorage.getItem("googleEmail") === null) && (
                <p>
                  Your Account is Not with Us. Please Enter Mandatory Details
                </p>
              )}
              <form onSubmit={this.submitSignUp}>
                <div className="row">
                  <div className="col-sm-12 col-md-12">
                    <br />
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>Email ID</h5>
                      </label>
                      <input
                        type="email"
                        className="form-control"
                        name="email"
                        id="email"
                        placeholder="Your Email"
                        value={this.state.email}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-1 col-md-1"></div>
                </div>
                <div className="row">
                  <div className="col-sm-12 col-md-12">
                    <br />
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>SSN</h5>
                      </label>
                      <input
                        type="ssn"
                        className="form-control"
                        name="ssn"
                        id="ssn"
                        placeholder="Your SSN"
                        value={this.state.ssn}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-1 col-md-1"></div>
                </div>

                <div className="row">
                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>First Name</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="fname"
                        id="fname"
                        placeholder="Your First Name"
                        value={this.state.fname}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>Last Name</h5>
                      </label>
                      <input
                        type="lname"
                        className="form-control"
                        name="lname"
                        id="lname"
                        placeholder="lname"
                        value={this.state.lname}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-1 col-md-1"></div>
                </div>

                <div className="row">
                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>College Year</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="college_year"
                        id="college_year"
                        placeholder="college_year"
                        value={this.state.college_year}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  <div class="col-sm-6 col-md-6">
                    <div class="form-group">
                      <label for="where">
                        <h5>Role</h5>
                      </label>
                      <div class="form-group">
                        <select
                          class="form-control"
                          id="role"
                          onChange={this.changeHandler.bind(this, "role")}
                        >
                          <option value="Student">Student</option>
                          <option value="Coach">Coach</option>
                          <option value="Instructor">Instructor</option>
                          <option value="Front Desk Assistant">
                            Front Desk Assistant
                          </option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div className="col-sm-12 col-md-12 ">
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
                  </div>

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

export default SignUp;
