import React, { Component } from 'react'
import axios from 'axios'
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography"
import {API_URL} from '../../Constants'
import Toolbar from "@material-ui/core/Toolbar";
import AppBar from "@material-ui/core/AppBar";


export default class AppLogs extends Component {

  constructor(props) {
    super(props) 
    this.state = {
        data : []
    }
  }

  componentWillMount() {
    axios.get(API_URL+"/getAppLogs").then((res) => {
        // console.log("LOGS",res.data)
        var temp = res.data.split("$")
        this.setState({
            data : temp
        })
    
    })
  }

  render() {
    return (
      <div>

        <Paper style={{backgroundColor: "#85b9f2",padding : "30px", margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h5'>
            Application Logs <br/>
          </Typography>
        </Paper>
        <Paper style={{padding : "30px", margin : "10px" , height : "auto", width : "auto"}}>
          <Typography variant = 'h7'>
            {
                this.state.data.map(x => {
                    return <li>{x}</li>
                })
            }
          </Typography>
        </Paper>
      </div>
    )
  }
}
