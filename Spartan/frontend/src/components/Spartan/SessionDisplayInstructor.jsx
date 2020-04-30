import React, { Component } from 'react'
//import { Link } from 'react-router-dom'
import axios from 'axios';
import { API_URL } from "../../Constants";



class SessionDisplayInstructor extends Component {
    constructor(props) {
        super(props);
        this.state = {
               information: ''
        }
        this.ChangeHandler = this.ChangeHandler.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.OpenProperty = this.OpenProperty.bind(this);
    }

    componentWillMount() {

    }

    componentDidMount(){

      var ssn = sessionStorage.getItem('ssn');
      console.log("ssn",ssn)
      axios.get(API_URL+`/sessions/instructor/${ssn}`).then( (response) => {

        if(response.status === 200) {
          console.log("Success!", response)
          this.setState({
            information: response.data  ,
        })
          
        }
        else{
          console.log("Session creation failed!")
        }

        console.log("Sent object",this.state);
      })

    }


      OpenProperty = (e) => {
        console.log("clocked")
        this.props.history.push(`/instructorDashboard/sessionCreation`);
      };

    handleChange(event) {
        this.setState({sharingType: event.target.value});
    }


    ChangeHandler(e) {
        let change = {}
        change[e.target.name] = e.target.value
        this.setState(change)
    }

    render() {

        let view = null;
        if(this.state.information.length>0)
        {
            view = this.state.information.map(property => {    

                return(
                    <div className="container">
                    <div class="property_details">
                        <div class="row">
                            <div class="col-md-7 right-side">
                            <hr></hr>
                                <h3>{property.propertyDescription}</h3><br></br>
                                <p class="info"><strong>Session Name :</strong> {property.session_name} <strong> Session Capacity :</strong>  {property.capacity} </p>  
                                <p class="info"> <strong> Session Description :</strong> {property.session_description}</p>
                                <button class="btn btn-danger" name="BookButton"  onClick={this.OpenProperty}>
                                    <span>Drop Session</span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr></hr>
                    </div>
                )
            })
        }
        else
        {
            view = (
                    <div class="property_detials">
                         <h3>No Sessions Created By You till date !</h3>
                    </div>
                );
        }
        return (
            
            <div>
                   {/* {redirectvar} */}
            <div id="mainbody">
            <div class="row">
                    <div class="col-sm-4 offset-4 offset-sm-5 mt-2">
                        <button className="btn btn-primary btn-style font-weight-bold" onClick = {this.OpenProperty}>
                            <i class="fas fa-plus"></i> &nbsp;Create Session
                        </button>
                    </div>
                    <br/><br/>
                </div>
                    <div class="container main-content">
                        <br></br>
                        {view}
                    </div>     
                </div>

            </div>
                )
            }
        }
        
export default SessionDisplayInstructor