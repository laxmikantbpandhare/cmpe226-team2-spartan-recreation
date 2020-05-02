import React, { Component } from "react";
import AuthenticationForApiService from "./AuthenticationForApiService.js";
//import { recomposeColor } from "@material-ui/core";
// import GoogleLogin from "react-google-login";
// import { API_URL } from "../../Constants";
// import axios from "axios";
export const AUTHENTICATED_USER_SESSION = "authenticatedUser";
class LoginComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
      role:"Student",
      hasLoginFailed: false,
      showSuccessMessage: false,
      unregisteredUser : false
    };

    this.handleChange = this.handleChange.bind(this);
    this.loginClicked = this.loginClicked.bind(this);
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  changeHandler = (name, e) => {
    this.setState({
      [name]: e.target.value
    });
  };

  loginClicked(e) {
    e.preventDefault();

    const data = {
      email_id: this.state.email,
      password: this.state.password,
      role: this.state.role
    };
    console.log("data", data);
    if(this.state.email === "kong.li@sjsu.edu"){
      if(this.state.password === "admin"){
        sessionStorage.setItem(AUTHENTICATED_USER_SESSION, this.state.email);
        this.props.history.push(`/websitetraffic`)
      }else{
        this.setState({ hasLoginFailed: true });
      }
    }else{
        AuthenticationForApiService.authenticate(
          this.state.email,
          this.state.password,
          this.state.role
        )
          .then((response) => {
            console.log("response", response);
            AuthenticationForApiService.registerSuccessfulLogin(
              this.state.email,
              response.data.token
            );
            sessionStorage.setItem("userEmail", response.data.email_id);
            sessionStorage.setItem("ssn", response.data.ssn);
            sessionStorage.setItem("role", response.data.role);
            if(response.data.role === "Student" && response.data.valid === "valid"){
                this.props.history.push(`/search`)
            }
            else if(response.data.role === "Instructor" && response.data.valid === "valid"){
                this.props.history.push(`/instructorDashboard`)
            } 
            else if(response.data.role === "Front Desk Assistant" && response.data.valid === "valid"){
              this.props.history.push(`/pendingRegistrations`)
            } 
            else if(response.data.role === "Coach" && response.data.valid === "valid"){
              this.props.history.push(`/createteam`)
            } 
            else if(response.data.valid === "unregistered") {
              console.log("User has not been approved yet");
              this.setState({
                unregisteredUser : true
              })
            }
            else if(response.data.valid === "invalid") {
              console.log("User has not been registered yet");
              this.setState({ hasLoginFailed: true });
            }
            else {
              this.setState({ hasLoginFailed: true });
            }
            console.log("submit login called");
          })
          .catch((e) => {
            console.log("e", e);
            this.setState({ showSuccessMessage: false });
            this.setState({ hasLoginFailed: true });
          });
    }
  }

  render() {
    return (
      <>
        <br />
        <div class="container-fluid">
          <div
            class="col-sm-5 col-md-5 container"
            style={{
              backgroundColor: "white",
              opacity: 1,
              filter: "Alpha(opacity=100)",
              borderRadius: "10px",
            }}
          >
            <br />
            <h1>Login</h1>

            <form onSubmit={this.loginClicked}>
              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <br />
                  <div class="form-group">
                    <label for="where">
                      <h5>Email</h5>
                    </label>
                    <input
                      type="email"
                      class="form-control"
                      id="where"
                      placeholder="Your Email"
                      name="email"
                      value={this.state.email}
                      onChange={this.handleChange}
                    />
                  </div>
                </div>
                <div class="col-sm-1 col-md-1"></div>
              </div>

              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <div class="form-group">
                    <label for="password">
                      <h5>Password</h5>
                    </label>
                    <input
                      type="password"
                      class="form-control"
                      id="password"
                      placeholder="password"
                      name="password"
                      value={this.state.password}
                      onChange={this.handleChange}
                    />
                  </div>
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
                          <option value="Front Desk Assistant">Front Desk Assistant</option>
                        </select>
                      </div>
                    </div>
                  </div>  

              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <div class="form-group">
                    <br />
                    <input type="submit" class="form-control btn btn-danger" />
                    <br />
                    <br />
                  </div>
                </div>
                <hr />

                <br />
                {this.state.hasLoginFailed && (
                  <div className="alert alert-warning">Invalid Credentials</div>
                )}
                {this.state.showSuccessMessage && (
                  <div className="alert alert-warning">Login Successful</div>
                )}
                 {this.state.unregisteredUser && (
                  <div className="alert alert-warning">User is not approved yet</div>
                )}
                <br />
              </div>
            </form>
          </div>
        </div>
      </>
    );
  }
}

export default LoginComponent;
