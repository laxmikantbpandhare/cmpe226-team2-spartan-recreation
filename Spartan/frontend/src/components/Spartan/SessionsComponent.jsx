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
            sessionName : "",
            capacity : "",
            section : "",
            roomNumber : "",
            startTime : "",
            endTime : "",
            sessionDate : "",
            instructorName : ""
        }
        this.enrollStudent = this.enrollStudent.bind(this)
    }

    enrollStudent() {

        const data = {
            session_id : this.state.session_id,
            capacity : this.state.capacity,
            student_ssn : sessionStorage.getItem('ssn')
        }
    }


    componentWillMount() {
        this.setState(
            {
                sessionName : "Yoga Session Day 3",
                capacity : "20",
                section : "Spartan Fitness Center, Building A",
                roomNumber : "20",
                startTime : "10:00 A.M",
                endTime : "11:30 A.M",
                sessionDate : "May 20th, 2020",
                instructorName : "John Doe"
         })
    }

    componentDidMount() {
        axios.get(API_URL +"/sessions/1")
          .then( (response) => {

            
            this.setState({
                sessionName : response.data.session_name,
                capacity : response.data.capacity,
                section : response.data.section,
                roomNumber : response.data.room_number,
                startTime : response.data.start_time,
                endTime : response.data.end_time,
                sessionDate : response.data.session_date
            })


          })
          .catch( (error) => {
            console.log(error);
          })

         
        
    }
    
 
  render(){
    const {classes} = this.props;
    const bull = <span className={classes.bullet}>•</span>;
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
                </CardContent>
            </Card>   
         </div>
      );
  }
}

const PaperC = withStyles(useStyles)(SessionDetails);
export default PaperC;
