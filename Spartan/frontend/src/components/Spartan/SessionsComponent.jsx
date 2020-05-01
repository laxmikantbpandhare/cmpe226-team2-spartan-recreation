import React from 'react';
import {  withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Card from '@material-ui/core/Card'
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CardActions from '@material-ui/core/CardActions';
import { Button } from '@material-ui/core';
import logo from "../../images/spartanLogo.png";
import CardMedia from '@material-ui/core/CardMedia';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
// import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import axios from "axios";
import { API_URL } from '../../Constants'


const useStyles = (theme) => ({
  root: {   
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent :'center',
    
    '& > *': {
      margin: theme.spacing(1,'auto'),
      width: theme.spacing(1,'auto'),
      height: theme.spacing(1, 'auto')  
    }  
  },

  media: {
    height: 0,
    paddingTop: '30%', // 16:9
  },

  btn : {
      align : "center"
  },

  forImg : {
      maxWidth : '5%' ,
      maxHeight : '5%',
      display: 'block',
      marginLeft: 'auto',
      marginRight: 'auto'
  }
});


class SessionDetails extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            session_id : "",
            sessionName : "",
            capacity : "",
            section : "",
            roomNumber : "",
            startTime : "",
            endTime : "",
            sessionDate : "",
            instructorName : "",
            enrolled : false,
            waitlisted : false,
            enrollmentMessage : "",
            errorMessage : ""
        }
        this.enrollStudent = this.enrollStudent.bind(this)
    }

    enrollStudent() {

        const data = {
            session_id : this.state.session_id,
            capacity : this.state.capacity,
            student_ssn : sessionStorage.getItem('ssn'),
            email: sessionStorage.getItem('userEmail')
        }

        axios.post(API_URL+"/sessions/enroll" , data).then( (response) => {
            console.log("Enrollment response",response);
            if(response.data.includes('Enrolled')) {
                this.setState({
                    enrolled : true,
                    enrollmentMessage : response.data.split('-')[1]
                })
            }
            else if(response.data.includes('Waitlisted')) {
                this.setState({
                    waitlisted : true,
                    enrollmentMessage : response.data.split('-')[1]
                })
            }
            else {
                this.setState({
                    errorMessage : true,
                    enrollmentMessage : response.data
                })
            }

        })
    }


    // componentWillMount() {
    //     this.setState(
    //         {
    //             sessionName : "Yoga Session Day 3",
    //             capacity : "20",
    //             section : "Spartan Fitness Center, Building A",
    //             roomNumber : "20",
    //             startTime : "10:00 A.M",
    //             endTime : "11:30 A.M",
    //             sessionDate : "May 20th, 2020",
    //             instructorName : "John Doe"
    //      })
    // }

    componentDidMount() {
        console.log("WINDOW",window);
        let search = window.location.search;
        let params = new URLSearchParams(search);
        let session_id_props = params.get('session_id');
        axios.get(API_URL +"/sessions/"+session_id_props)
          .then( (response) => {

            console.log("RESPONSE" , response.data);
            this.setState({
                session_id : session_id_props,
                sessionName : response.data.session_name,
                capacity : response.data.capacity,
                section : response.data.section,
                roomNumber : response.data.room_number,
                startTime : response.data.start_time,
                endTime : response.data.end_time,
                sessionDate : response.data.session_date
            })

            axios.get(API_URL+"/getInstructorName/"+response.data.instructor_ssn).then( (out) =>{
                console.log("INST NAME" , out)
                this.setState({
                    instructorName : out.data
                })
            })

          })
          .catch( (error) => {
            console.log(error);
          })  
          
          
    }
    
 
  render(){
    console.log("PROPS",this.props)
    const {classes} = this.props;
    // const bull = <span className={classes.bullet}>•</span>;
    return (
         <div className={classes.root}>
            <Card className={classes.root} elevation = {20}>

                
                <CardContent backgroundColor = 'grey'>
                <CardMedia
                    className={classes.media}
                    image={logo}
                    title='Logo'
                />
                    {/* <img src = {logo} className = {classes.forImg}  /> */}

                    <Typography style={{ textAlign: "center" }}>
                    </Typography>

                    <TableContainer component={Paper}>
                        <Table className={classes.table} aria-label="simple table">
                            <TableBody>
                                <TableRow>
                                    <TableCell>Session Name</TableCell>
                                    <TableCell>{this.state.sessionName}</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>Capacity</TableCell>
                                    <TableCell>{this.state.capacity}</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>Timings</TableCell>
                                    <TableCell>{this.state.startTime} to {this.state.endTime}</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>Location</TableCell>
                                    <TableCell>{this.state.section} Room {this.state.roomNumber}</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>Date</TableCell>
                                    <TableCell>{this.state.sessionDate}</TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell>Instructor Name</TableCell>
                                    <TableCell>{this.state.instructorName}</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer> 
                    <CardActions>
                        <Button onClick={this.enrollStudent} className = {classes.btn} >Enroll</Button>
                    </CardActions>
                    <br />
                {this.state.enrolled && (
                  <div className="alert alert-warning">You have been successfully enrolled! You are at number {this.state.enrollmentMessage}</div>
                )}
                 <br />
                {this.state.waitlisted && (
                  <div className="alert alert-warning">You have been waitlisted! You are at number {this.state.enrollmentMessage}</div>
                )}
                 {this.state.errorMessage && (
                  <div className="alert alert-warning">{this.state.enrollmentMessage}</div>
                )}
                </CardContent>
            </Card>   
         </div>
      );
  }
}

const PaperC = withStyles(useStyles)(SessionDetails);
export default PaperC;
