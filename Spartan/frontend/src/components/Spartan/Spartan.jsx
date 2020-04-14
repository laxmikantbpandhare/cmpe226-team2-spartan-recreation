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
import SerchSessions from "./SearchSessions"

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
              {/* <Route path="/search/searchResults" component={SearchResults} />
              <Route path="/search/searchResult/:propertyId" component={SearchResultDetails} /> */}
              <Route path="/login" component={LoginComponent} />
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
