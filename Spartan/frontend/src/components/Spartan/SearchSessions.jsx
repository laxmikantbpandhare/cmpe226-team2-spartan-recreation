import React, { Component } from "react";
import { Redirect } from "react-router";
import axios from "axios";
import { API_URL } from "../../Constants";

class SearchSessions extends Component {
  constructor(props) {
    super(props);
    this.state = {
      location: "",
      endDate: "",
      startDate: "",
      sharingType: "",
      propertyType: "",
      propertyDescription: "",
      wifi: "",
      priceRange: "",
      responseData: "",
      currentdate: "",
      datefound: false,
    };
    this.ChangeHandler = this.ChangeHandler.bind(this);
    this.SearchButton = this.SearchButton.bind(this);
  }

  SearchButton = (e) => {
    e.preventDefault();

    // console.log("session email",sessionStorage.userEmail);
    // console.log("session ssn",sessionStorage.ssn);

    var date1 = new Date(this.state.startDate);
    var date2 = new Date(this.state.endDate);
    var difference = date2 - date1;
    var days = difference / (24 * 3600 * 1000);

    // now get the current date    from backend

    var utcstartDate = new Date(
      date1.getTime() + date1.getTimezoneOffset() * 60000
    );
    var utcendDate = new Date(
      date1.getTime() + date1.getTimezoneOffset() * 60000
    );
    var date3 = new Date(this.state.currentdate.slice(0, 10));
    var utcCurrentDate = new Date(
      date3.getTime() + date3.getTimezoneOffset() * 60000
    );

    // console.log("selected date", utcstartDate);
    // console.log("current date from backend", utcCurrentDate);
    var difference1 = utcstartDate - utcCurrentDate;
    var days1 = difference1 / (24 * 3600 * 1000);

    var difference2 = utcendDate - utcCurrentDate;
    var days2 = difference2 / (24 * 3600 * 1000);

    // console.log("Days1 data", days1);

    if (this.state.location === "") alert("Session Name is Empty");
    else if (this.state.startDate === "") alert("Start Date is Empty");
    else if (this.state.endDate === "") alert("End Date is Empty");
    else if (days1 > 365 || days2 > 365)
      alert(
        "You cannot book for next Year. Please try adjusting your search by changing your dates."
      );
    else if (days <= 0)
      alert(
        "End Date date cannot be same or less than Start Date date. Please try adjusting your search by changing your dates."
      );
    else if (days1 < 0)
      alert(
        "Start Date cannot be less that Today's date. Please try adjusting your search by changing your dates."
      );
    else {
      const data = {
        city: this.state.location,
        startDate: this.state.startDate,
        endDate: this.state.endDate,
        propertyDescription: this.state.propertyDescription,
      };

      localStorage.setItem("product_details", JSON.stringify(data));

      axios
        .post(API_URL + `/sessions/search`, data)
        .then((response) => {
          if (response.status === 200) {
            this.setState({
              responseData: response.data,
            });
            // console.log(response);
            if (!response.data) {
              alert("No Available Sessions for your Search!");
            }
          } else {
            this.setState({
              flag: false,
            });
          }
        })
        .catch((err) => {
          alert(err);
        });
    }
  };

  ChangeHandler(e) {
    let change = {};
    change[e.target.name] = e.target.value;
    this.setState(change);
  }

  // componentDidMount(){

  //         axios.get(API_URL + `/admin/date/`)
  //         .then(response => {
  //             console.log("Status Code : ", response.status);
  //             if (response.status === 200) {
  //                 this.setState({
  //                     currentdate: response.data,
  //                     datefound: true
  //                 })
  //                 console.log("response data",response.data)

  //                 if (!response.data) {
  //                     alert("No Available Properties")
  //                 }
  //             }
  //             else {
  //                 this.setState({
  //                     flag: false
  //                 })
  //             }
  //         })
  //         .catch(err => {
  //             alert(err);
  //         });
  // }

  render() {
    let redirectvar = null;
    if (this.state.responseData) {
      redirectvar = (
        <Redirect
          to={{
            pathname: "/search/searchResults",
            state: {
              responseData1: this.state.responseData,
            },
          }}
        />
      );
    }

    return (
      <div>
        {redirectvar}
        <div class="container-fluid">
          <br />
          <br />
          <div class="row">
            <div class="col-sm-1 col-md-1"></div>

            <div
              class="col-sm-5 col-md-5"
              style={{
                backgroundColor: "white",
                opacity: 1,
                filter: "Alpha(opacity=50)",
                borderRadius: "10px",
              }}
            >
              <br />
              <h2>
                Book Sessions, Events on{" "}
                <mark class="skyblue">Spartan Recreation</mark>
              </h2>

              <form>
                <div class="row">
                  <div class="col-sm-12 col-md-12">
                    <br />
                    <div class="form-group">
                      <label for="where">
                        <h5>Session Name *</h5>
                      </label>
                      <input
                        type="text"
                        onChange={this.ChangeHandler}
                        class="form-control"
                        placeholder="Anywhere"
                        name="location"
                        id="location"
                      />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6 col-md-6">
                    <div class="col-sm-8 col-md-8">
                      <label for="where">
                        <h5>Start Date *</h5>
                      </label>
                      <input
                        onChange={this.ChangeHandler}
                        type="date"
                        name="startDate"
                        id="startDate"
                        class="form-control js-Date"
                      />
                      <i
                        class="icon-calendar form-control-icon"
                        aria-hidden="true"
                      ></i>
                    </div>
                  </div>

                  <div class="col-sm-6 col-md-6">
                    <div class="col-sm-8 col-md-8">
                      <label for="where">
                        <h5>End Date *</h5>
                      </label>
                      <input
                        onChange={this.ChangeHandler}
                        type="date"
                        name="endDate"
                        id="endDate"
                        class="form-control js-Date"
                      />
                    </div>
                  </div>
                </div>
                <br />
                <div class="row">
                  <div class="col-sm-12 col-md-12">
                    <div class="form-group">
                      <label for="where">
                        <h5>Description</h5>
                      </label>
                      <textarea
                        type="noter-text-area"
                        onChange={this.ChangeHandler}
                        class="form-control"
                        placeholder="Describe the Place Here!"
                        name="propertyDescription"
                        id="propertyDescription"
                      ></textarea>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-12 col-md-12">
                    <div class="form-group">
                      <br />
                      <input
                        type="submit"
                        onClick={this.SearchButton}
                        class="form-control btn btn-danger"
                      />
                      <br />
                      <br />
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default SearchSessions;
