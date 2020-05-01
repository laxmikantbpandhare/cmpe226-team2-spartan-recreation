import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AuthenticatedRoute from "./AuthenticatedRoute";
import LoginComponent from "./LoginComponent";
import ErrorComponent from "./ErrorComponent";
import HeaderComponent from "./HeaderComponent";
//import FooterComponent from "./FooterComponent";
import LogoutComponent from "./LogoutComponent";
import DashBoard from "./DashBoard";
import SignUP from "./SignUp";
import SerchSessions from "./SearchSessions";
import SearchResults from "./SearchResults";
import SessionsComponent from "./SessionsComponent";
import SessionCreation from "./SessionCreation";
import TeamTryOut from "./TeamTryOut";
import SessionDisplayInstructor from "./SessionDisplayInstructor";
import StudentEnrolledSessions from "./StudentEnrolledSessions";
// import PendingRegistrations from "./PendingRegistrations"

// import AssetDisplay from "./AssetDisplay.jsx";

class OpenHome extends Component {
  render() {
    return (
      <div className="OpenHomeApp">
        <Router>
          <>
            <HeaderComponent />
            <Switch>
              <Route path="/" exact component={DashBoard} />
              <Route path="/signup" exact component={SignUP} />
              <Route path="/search" exact component={SerchSessions} />
              <Route path="/search/searchResults" component={SearchResults} />
              <Route path="/login" component={LoginComponent} />
              <Route path="/instructorDashboard/sessionCreation" component={SessionCreation}/>
              <Route path="/sessionDetails" exact component={SessionsComponent}/>
              <Route path="/instructorDashboard" component={SessionDisplayInstructor} />
              <Route path="/StudentEnrolledSessions" component={StudentEnrolledSessions} />   
              <Route path="/teamTryOut" component={TeamTryOut} />
              <Route path="/teamTryOut" component={TeamTryOut} />
              <Route path="/login" component={LoginComponent} />
              <Route path = "/sessionCreation" exact component = {SessionCreation}/>
              <Route path = "/sessionDetails" exact component = {SessionsComponent} />
              <AuthenticatedRoute path="/logout" component={LogoutComponent} />
              <Route component={ErrorComponent} />
            </Switch>
          </>
        </Router>
      </div>
    );
  }
}

export default OpenHome;
