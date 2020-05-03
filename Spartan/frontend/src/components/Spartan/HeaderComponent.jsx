import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import AuthenticationForApiService from './AuthenticationForApiService.js'
import { API_URL } from '../../Constants'
import axios from 'axios';

class HeaderComponent extends Component {
    
    constructor(props) {
        super(props)
        
        this.state = {
            date: '',
            hours:0,
            mins:0

        }
       
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    async componentDidMount() {
        try {
          setInterval(async () => {
            axios
            .get(API_URL + "/admin/time", {
                headers: { "Content-Type": "application/json" }
            }).then(response => {
                
                if (response.status === 200) {
                    this.setState({

                       date: response.data
                    })

                }
                else {
     
                }
            })
            .catch(err => {
                console.log(err);

            });
          }, 3000);
        } catch(e) {
          console.log(e);
        }
  }



    render() {
        const isUserLoggedIn = AuthenticationForApiService.isUserLoggedIn();
        var role = sessionStorage.getItem('role');
        // console.log(role);
        let display = null;
        if(role === "Student"){
                display = (
                    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/search" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/search">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/StudentEnrolledSessions">Enrolled Sessions</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/teamTryOut">Team Tryouts</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
                )
        }
        else if(role === "Front Desk Assistant"){

            display = (
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/pendingRegistrations" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/pendingRegistrations">Home</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            )

        }
        else if(role === "Instructor"){

            display = (
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/instructorDashboard" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/instructorDashboard">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/instructorDashboard">Sessions</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            )

        }
        else if(role === "Coach"){

            display = (
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/createteam" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/createteam">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/teamdisplaycoach">TryOut Teams</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/pendingStudentRequests">Student Requests</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            )

        }
        else if(role === "Admin" && isUserLoggedIn){

            display = (
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/websitetraffic" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/websitetraffic">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/appLogs">Application Logs</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            )

        }
        else{

            display = (
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <div><img src="logo.png" height="40" width="55" alt="Logo"></img> <a href="/" className="navbar-brand">Spartan Recreation</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/">Home</Link></li>}
                        <li><Link className="nav-link" to=""><font color="red">Current Time: {this.state.date}</font></Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/signup"  onClick={AuthenticationForApiService.logout}>Sign Up</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login" >Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationForApiService.logout}>Logout</Link></li>}
                    </ul>
                </nav>
            )

        }
        return (
            <header>
                {display}
            </header>
        )
    }
}

export default HeaderComponent