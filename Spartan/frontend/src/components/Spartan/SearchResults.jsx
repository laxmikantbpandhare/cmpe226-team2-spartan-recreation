import React, { Component } from 'react'
// import { Link } from 'react-router-dom'
//import axios from 'axios';



class SearchResults extends Component {
    constructor(props) {
        super(props);
        this.state = {
            location: '',
            endDate: '',
            startDate:'',
            sharingType:'Full',
            propertyType:'Apartment',
            propertyDescription: '',
            wifi : 'true',
            priceRange : '1 to 100',
            information: '',
            datatest:false
        }
        this.ChangeHandler = this.ChangeHandler.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentWillMount() {

    }

    componentDidMount(){

        if(this.props.location.state){
            this.setState({
                information: this.props.location.state.responseData1,
            })
        }
    }

    OpenProperty = property => {
        // console.log("PROPERTY",property)
        this.setState({
            datatest: true,
        })
        this.props.history.push({
            pathname : "/sessionDetails",
            search : "?session_id="+property.session_id
        });
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

        let displayImage = null;
        let view = null;
        if(this.state.information.length>0)
        {
            view = this.state.information.map(property => {    
                    if(!property.picture)
                    {
                        // console.log("data",property.city)
                        displayImage = (
                            <div>
                                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <div class="carousel-item active">
                                            <img src = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/SJSU_Seal.svg/1200px-SJSU_Seal.svg.png" height="300" width="420" alt="description"/>
                                        </div>
                                    </div>
                                 </div>
                            </div>
                        )
                    }
                    else if(property.picture)
                    {
                        displayImage = (
                            <div>
                                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <div class="carousel-item active">
                                            <img src = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/SJSU_Seal.svg/1200px-SJSU_Seal.svg.png" height="300" width="420" alt="description"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )
                    }

                return(
                    <div className="container">
                    <div class="property_details">
                        <div class="row">
                            <div class="col-md-5">
                                {displayImage} 
                            </div>
                            <div class="col-md-7 right-side">
                            <hr></hr>
                                <h3>{property.propertyDescription}</h3><br></br>
                                <p class="info">Session Name : {property.session_name}</p>
                                <p class="info"> Session Capacity : {property.capacity}</p>
                                <p class="info"> Session Description : {property.session_description}</p>
                                {/* <p class="price">$ {property.Tariff} per night</p> */}

                                <hr></hr>
                                <button class="btn btn-danger" name="BookButton"  onClick={() => this.OpenProperty(property)}>
                                    <span>Session Details</span>
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
                        <h3><b>No results !!</b></h3>
                         <h3>To get more results, try adjusting your search by changing your dates.</h3>
                    </div>
                );
        }
        // console.log("CHECK THE DATA");
        // console.log(this.state.information);
        return (
            
            <div>
                   {/* {redirectvar} */}
            <div id="mainbody">
                    <div class="container main-content">
                        <br></br>
                        {view}
                    </div>     
                </div>

            </div>
                )
            }
        }
        
export default SearchResults