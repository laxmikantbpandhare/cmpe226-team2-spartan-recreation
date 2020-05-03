import React, { Component } from 'react'
//import { Link } from 'react-router-dom'
import axios from 'axios';
import { API_URL } from "../../Constants";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';




class StudentEnrolledSessions extends Component {
    constructor(props) {
        super(props);
        this.state = {
               information: ''
        }
        this.ChangeHandler = this.ChangeHandler.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.OpenProperty = this.OpenProperty.bind(this);
        this.deleteSession = this.deleteSession.bind(this);
    }

    componentWillMount() {

    }

    componentDidMount(){

      var ssn = sessionStorage.getItem('ssn');
      console.log("ssn",ssn)
      axios.get(API_URL+`/sessions/sessions/${ssn}`).then( (response) => {

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

    handleChange(event) {
        this.setState({sharingType: event.target.value});
    }

    OpenProperty = (property) => {
        confirmAlert({
            title: 'Drop Session',
            message: 'Are you sure you want to delete this Enrolled Session?',
            buttons: [
              {
                label: 'Yes',
                onClick: () => {this.deleteSession(property);}
              },
              {
                label: 'No',
                onClick: () => {}
              }
            ]
          });
      };

      deleteSession = (property) => {

          let data = {
             session_id : property[8],
             ssn : sessionStorage.getItem('ssn'),
             email: sessionStorage.getItem('userEmail')
          }
          
          axios.post(API_URL+`/sessions/removes/enroll`, data).then( (response) => {

            if(response.status === 200) {
              console.log("Success!", response)
              window.location.reload();
              
            }
            else{
              console.log("Session creation failed!")
            }
    
            console.log("Sent object",this.state);
          })
        console.log("alaka baba double ",property)
      };


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
                    console.log("props",property    )
                return(
                    <div className="container">
                    <div class="property_details">
                        <div class="row">
                            <div class="col-md-7 right-side">
                                <h3>{property.propertyDescription}</h3><br></br>
                                <p class="info"><strong>Session Name :</strong> {property[0]} <strong> Section :</strong>  {property[1]} </p>
                                 <p> <strong> Session Date : </strong>  {property[5]}   <strong> Room Number: </strong>  {property[2]} 
                                    <strong> Start  Time : </strong>  {property[3]} <strong> End Time : </strong> {property[4]}</p> 
                                  
                            
                                <p class="info"> <strong> Description : </strong> {property[6]}</p>
                                <button class="btn btn-danger" name="BookButton"  onClick={() => this.OpenProperty(property)}>
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
                         <h3>No Sessions Enrolled By You till date !</h3>
                    </div>
                );
        }
        return (
            
            <div>
            <div id="mainbody">
            <div class="row">
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
        
export default StudentEnrolledSessions