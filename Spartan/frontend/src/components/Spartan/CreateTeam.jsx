import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
//import { Button } from "@material-ui/core";
import logo from "../../images/spartanLogo.png";
import CardMedia from "@material-ui/core/CardMedia";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
// import TableHead from '@material-ui/core/TableHead';
import TableRow from "@material-ui/core/TableRow";
import axios from "axios";
import { API_URL } from "../../Constants";

const useStyles = (theme) => ({
  root: {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",

    "& > *": {
      margin: theme.spacing(1, "auto"),
      width: theme.spacing(1, "auto"),
      height: theme.spacing(1, "auto"),
    },
  },

  media: {
    height: 0,
    paddingTop: "30%", // 16:9
  },

  btn: {
    align: "center",
  },

  forImg: {
    maxWidth: "5%",
    maxHeight: "5%",
    display: "block",
    marginLeft: "auto",
    marginRight: "auto",
  },
});

class CreateTeam extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teamDetails: [],
      activity: "",
      teamname: "",
      teamname1: "",
      teamname2: "",
      teamname3: "",
      teamname4: ""
    };
    this.handleChange = this.handleChange.bind(this);
    this.registerStudent = this.registerStudent.bind(this);
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  registerStudent = property => {


      var data = "";
    if(property === "Basketball"){
      if(this.state.teamname1 === "")
      {
        alert(
          "Please enter Teamname."
        );
      }

      data = {
        session_id : "11",
        team_name: this.state.teamname1,
        activity_id: 4,
        student_ssn: sessionStorage.getItem("ssn"),
        student_email: sessionStorage.getItem("userEmail"),
      };

    }
    else if(property === "Football"){
      if(this.state.teamname2 === "")
      {
        alert(
          "Please enter Teamname."
        );
      }
      data = {
        session_id : "11",
        team_name: this.state.teamname2,
        activity_id: 5,
        student_ssn: sessionStorage.getItem("ssn"),
        student_email: sessionStorage.getItem("userEmail"),
      };
    }
    else if(property === "Volleyball"){
      if(this.state.teamname3 === "")
      {
        alert(
          "Please enter Teamname."
        );
      }
      data = {
        session_id : "11",
        team_name: this.state.teamname3,
        activity_id: 6,
        student_ssn: sessionStorage.getItem("ssn"),
        student_email: sessionStorage.getItem("userEmail"),
      };
    }
    else if(property === "Badminton"){
      if(this.state.teamname4 === "")
      {
        alert(
          "Please enter Teamname."
        );
      }
      data = {
        session_id : "11",
        team_name: this.state.teamname4,
        activity_id: 7,
        student_ssn: sessionStorage.getItem("ssn"),
        student_email: sessionStorage.getItem("userEmail"),
      };
    }



    axios.post(API_URL + "/coaches/newTryOutSession", data).then((response) => {
      // console.log("Registration status", response);
      if (response.status === 200) {
        this.setState({
          alreadyregistered: true,
        });
        alert(
          "Team Created Successfully."
        );
      }
      else{
        alert(
          "Error in TryOut Team Creation."
        );
      }
    })
    .catch((e) => {
      alert(
        "Error in TryOut Team Creation."
      );
    });;

  }

  componentDidMount() {
    axios
      .get(API_URL + "/teamTryOut/getactivity")
      .then((response) => {
        // console.log(response.data);
        this.setState({
          teamDetails: response.data,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }


  render() {
    const { classes } = this.props;
    // console.log("I am here in block", this.state.teamDetails);
    // let eachTeam = this.state.teamDetails.map((team) => {
    //     if(team[0] !== "Yoga" && team[0] !== "Zumba" && team[0] !== "Fitness"){
    //   return (
    //     <tr>
    //             <td>
    //               <div class="col-sm-12 col-md-12">
    //                 <label for="where">
    //                 </label>
    //                 <input
    //                   type="teamname"
    //                   class="form-control"
    //                   id={team[0]} 
    //                   placeholder="Team Name"
    //                   name="teamname"
    //                   value={this.state.teamname}
    //                   onChange={this.handleChange}
    //                 />
    //               </div>
    //             </td>

    //       <td id="header"> {team[0]} </td>
    //       <td>
    //       <CardActions>
    //         <button id={team[0]} onClick={() => this.registerStudent(team)}>
    //           Registers
    //         </button>
    //       </CardActions>
    //       </td>
    //     </tr>
    //   );
    // }
    // });


    return (
      <div className={classes.root}>
        <Card className={classes.root} elevation={20}>
          <CardContent backgroundColor="grey">
            <CardMedia className={classes.media} image={logo} title="Logo" />
            {/* <img src = {logo} className = {classes.forImg}  /> */}
            <Typography style={{ textAlign: "center" }}></Typography>

            <TableContainer component={Paper}>
              <Table className={classes.table} aria-label="simple table">
                <TableBody>
                  <TableRow>
                    <TableCell>ENTER TEAM NAME</TableCell>
                    <TableCell>ACTIVITY NAME</TableCell>
                    <TableCell>TEAM TRYOUT REGISTRATION</TableCell>
                  </TableRow>
                      {/* {eachTeam} */}
                        
                      <tr>
                          <td>
                            <div class="col-sm-12 col-md-12">
                              <label for="where">
                              </label>
                              <input
                                type="teamname1"
                                class="form-control"
                                id="teamname1"
                                placeholder="Team Name"
                                name="teamname1"
                                value={this.state.teamname1}
                                onChange={this.handleChange}
                              />
                            </div>
                          </td>

                          <td id="header"> Basketball </td>
                          <td>
                          <CardActions>
                            <button id="Basketball" onClick={() => this.registerStudent("Basketball")}>
                              Create Team
                            </button>
                          </CardActions>
                          </td>
                        </tr>

                        <tr>
                          <td>
                            <div class="col-sm-12 col-md-12">
                              <label for="where">
                              </label>
                              <input
                                type="teamname2"
                                class="form-control"
                                id="teamname2"
                                placeholder="Team Name"
                                name="teamname2"
                                value={this.state.teamname2}
                                onChange={this.handleChange}
                              />
                            </div>
                          </td>

                          <td id="header"> Football </td>
                          <td>
                          <CardActions>
                            <button id="Football" onClick={() => this.registerStudent("Football")}>
                            Create Team
                            </button>
                          </CardActions>
                          </td>
                        </tr>



                        <tr>
                          <td>
                            <div class="col-sm-12 col-md-12">
                              <label for="where">
                              </label>
                              <input
                                type="teamname3"
                                class="form-control"
                                id="teamname3"
                                placeholder="Team Name"
                                name="teamname3"
                                value={this.state.teamname3}
                                onChange={this.handleChange}
                              />
                            </div>
                          </td>

                          <td id="header"> Volleyball </td>
                          <td>
                          <CardActions>
                            <button id="Volleyball" onClick={() => this.registerStudent("Volleyball")}>
                            Create Team
                            </button>
                          </CardActions>
                          </td>
                        </tr>


                        <tr>
                          <td>
                            <div class="col-sm-12 col-md-12">
                              <label for="where">
                              </label>
                              <input
                                type="teamname4"
                                class="form-control"
                                id="teamname4"
                                placeholder="Team Name"
                                name="teamname4"
                                value={this.state.teamname4}
                                onChange={this.handleChange}
                              />
                            </div>
                          </td>

                          <td id="header"> Badminton </td>
                          <td>
                          <CardActions>
                            <button id="Badminton" onClick={() => this.registerStudent("Badminton")}>
                            Create Team
                            </button>
                          </CardActions>
                          </td>
                        </tr>



                </TableBody>
              </Table>
            </TableContainer>
            <br />
            {this.state.pending && (
              <div className="alert alert-warning">
                You have been successfully register to try out session!
              </div>
            )}
            <br />
          </CardContent>
        </Card>
      </div>
    );
  }
}

const PaperC = withStyles(useStyles)(CreateTeam);
export default PaperC;
