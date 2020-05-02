import React, { Component } from 'react'
import axios from 'axios'
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography"
import {API_URL} from '../../Constants'
import Toolbar from "@material-ui/core/Toolbar";
import AppBar from "@material-ui/core/AppBar";

const mystyle = {
  display : 'flex',
  height : "230px",
  width : "234px"
}

export default class WebsiteTraffic extends Component {


  constructor(props) {
    super(props) 
    this.state = {
      dataAPI : [],
      dataAcitivity : []
    }

    this.loadDatabase = this.loadDatabase.bind(this)
  }

  loadDatabase() {

    axios.post(API_URL+"/loadSampleData").then( (response) => {
        console.log("Enrollment response",response);
        if(response.status === 200) {
          alert(
            "Successfully Loaded Data into Database."
          );
        }
        else {
          alert(
            "There is some issue with the loading of data into Database."
          );
        }

    })
}

  componentWillMount() {
    axios.get(API_URL+"/apidata").then((res) => {
        var temp = res.data.split(",")
        temp = temp.map( x => {
          return parseInt(x,10);
        })
        this.setState({
          dataAPI : temp
        })
    })

    axios.get(API_URL+"/activityData").then((res) => {
      console.log("ACTIVITY" , res.data)
      var xtemp = res.data.split(",")
      xtemp = xtemp.map( x => {
        return parseInt(x,10);
      })
      this.setState({
        dataAcitivity : xtemp
      })
  })
  }

  render() {
    return (
      <div>
    
      <div style = {{display : "flex" , justifyContent : "center"}}>
        <AppBar >
          {/* <Toolbar>
            <Typography variant='h6'>
              Spartan Recreation Website Traffic
            </Typography>
          </Toolbar> */}
          </AppBar>
        <Paper style={{backgroundColor : "#76a8f2",padding : "30px", margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h5'>
          API <br/>HITS
          </Typography>
        </Paper>

        <Paper style={{padding : "30px", margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h5'>
            Search <br/>
            {this.state.dataAPI[0]}
          </Typography>
        </Paper>

        <Paper style={{padding : "30px" , margin : "10px" , height : "auto", width : "auto"}}>
        <Typography variant = 'h5'>
            Enroll<br/>
            {this.state.dataAPI[1]}
          </Typography>        
        </Paper>

        <Paper style={{padding : "30px" , margin : "10px" , height : "auto", width : "auto"}}>
        <Typography variant = 'h5'>
            Create <br/>
            {this.state.dataAPI[2]}
          </Typography> 
        </Paper>
        </div>

        <div style = {{display : "flex" , justifyContent : "center"}}>
          <Paper style={{backgroundColor : "#76a8f2",padding : "30px", margin : "10px" , height : "auto", width : "auto"}}>
            <Typography variant = 'h5'>
            POPULARITY<br/>SCORE
            </Typography>
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Yoga <br/>
              {this.state.dataAcitivity[0]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Zumba <br/>
              {this.state.dataAcitivity[1]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Fitness <br/>
              {this.state.dataAcitivity[2]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Basketball <br/>
              {this.state.dataAcitivity[3]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Football <br/>
              {this.state.dataAcitivity[4]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Volleyball <br/>
              {this.state.dataAcitivity[5]}
            </Typography> 
          </Paper>

          <Paper style={{padding : "20px" , margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h6'>
          Badminton <br/>
              {this.state.dataAcitivity[6]}
            </Typography> 
          </Paper>

        </div>
        <div class="col-sm-3 col-md-3 ">
                  <div class="form-group">
                    <br />
                    <button id="Volleyball"class="form-control btn btn-danger" onClick={this.loadDatabase}>
                            Load Data Into Database
                    </button>
                    <br />
                    <br />
                  </div>
                  </div>
      </div>
    )
  }
}
