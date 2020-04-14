import React, { Component } from "react";
import AuthenticationForApiService from "./AuthenticationForApiService.js";
// import GoogleLogin from "react-google-login";
// import { API_URL } from "../../Constants";
// import axios from "axios";

class LoginComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
      role:"Student",
      hasLoginFailed: false,
      showSuccessMessage: false,
    };

    this.handleChange = this.handleChange.bind(this);
    this.loginClicked = this.loginClicked.bind(this);
    // this.responseGoogle = this.responseGoogle.bind(this);
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
        sessionStorage.setItem("userEmail", response.data.email);
        sessionStorage.setItem("userId", response.data.id);
        sessionStorage.setItem("userRole", response.data.role);
        sessionStorage.setItem("userName", response.data.name);
        sessionStorage.setItem("JWT", response.data.token);
        sessionStorage.setItem("verified", response.data.verified);
        /*if(response.data.role === "user"){
                    this.props.history.push(`/welcomeuser/${response.data.name}`)
                }
                if(response.data.role === "host"){
                    this.props.history.push(`/hostdashboard/${response.data.name}`)
                }
                else{
                    //this.props.history.push(`/welcome/${response.data.name}`)
                    console.log("submit login called")
                }*/   
        if(response.data){
          this.props.history.push(`/search`)
        }
        console.log("submit login called");
      })
      .catch((e) => {
        console.log("e", e);
        this.setState({ showSuccessMessage: false });
        this.setState({ hasLoginFailed: true });
      });
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

                {/* <div class="col-sm-12 col-md-12">
                  <div class="form-group">
                    <GoogleLogin
                      clientId="624602059574-qsv45kcgn89v376114ql2ps2t5rljfd7.apps.googleusercontent.com"
                      buttonText="Login"
                      onSuccess={this.responseGoogle}
                      onFailure={this.responseGoogle}
                      cookiePolicy={"single_host_origin"}
                    >
                      <span> Login with Google</span>
                    </GoogleLogin>
                  </div>
                </div> */}
                <br />
                {this.state.hasLoginFailed && (
                  <div className="alert alert-warning">Invalid Credentials</div>
                )}
                {this.state.showSuccessMessage && (
                  <div className="alert alert-warning">Login Successful</div>
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
