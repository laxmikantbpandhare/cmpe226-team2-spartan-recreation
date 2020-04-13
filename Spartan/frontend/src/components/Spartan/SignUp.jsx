import React, { Component } from "react";
// import { Link } from 'react-router-dom'
// import AuthenticationForApiService from './AuthenticationForApiService.js'
import axios from "axios";
import { API_URL } from "../../Constants";

class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: sessionStorage.getItem("googleName"),
      lastname: "",
      role: "",
      email: sessionStorage.getItem("googleEmail"),
      signup_status: "",
      hasFailed: false,
      showSuccessMessage: false,
      phoneno: "",
      country: "",
      city: "",
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

  submitSignUp = (e) => {
    let role = "";
    if (this.state.email.includes("@sjsu.edu")) {
      role = "host";
    } else {
      role = "user";
    }

    console.log("submit login called");
    //  var headers = new Headers();
    //prevent page from refresh
    e.preventDefault();
    const data = {
      emailid: this.state.email,
      lastname: this.state.lastname,
      firstname: this.state.firstname,
      city: this.state.city,
      phoneno: this.state.phoneno,
      country: this.state.country,
      password: this.state.password,
    };
    console.log("data", data);
    //set the with credentials to true
    //axios.defaults.withCredentials = true;
    //make a post request with the user data
    axios.post(API_URL + "/persons", data).then((response) => {
      console.log("Status Code : ", response.status);
      if (response.status === 200) {
        console.log(response.data);
        this.setState({
          signup_status: response.data.message,
          showSuccessMessage: true,
        });
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
                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>First Name</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="firstname"
                        id="firstname"
                        placeholder="Your First Name"
                        value={this.state.firstname}
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
                        type="lastname"
                        className="form-control"
                        name="lastname"
                        id="lastname"
                        placeholder="lastname"
                        value={this.state.lastname}
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
                        <h5>City</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="city"
                        id="city"
                        placeholder="city"
                        value={this.state.city}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>

                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>Phone Number</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="phoneno"
                        id="phoneno"
                        placeholder="phoneno"
                        value={this.state.phoneno}
                        onChange={this.handleChange}
                        pattern="[0-9]{10}"
                        required
                      />
                    </div>
                  </div>

                  <div className="col-sm-6 col-md-6">
                    <div className="form-group">
                      <label htmlFor="where">
                        <h5>Country</h5>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        name="country"
                        id="country"
                        placeholder="country"
                        value={this.state.country}
                        onChange={this.handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="col-sm-6 col-md-6">
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
