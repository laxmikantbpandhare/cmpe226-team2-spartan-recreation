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

class TeamTryOut extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teamName: "",
      coachName: "",
      year: "",
      pending: false,
      alreadyregistered: false,
      registrationMessage: "",
      teamDetails: [],
    };
    this.registerStudent = this.registerStudent.bind(this);
  }

  registerStudent(e) {
    console.log("entered");
    console.log(e.target.id);
    //console.log(this.event.target.id);
    console.log(sessionStorage);

    const data = {
      student_ssn: sessionStorage.getItem("ssn"),
      student_email: sessionStorage.getItem("userEmail"),
      team_name: e.target.id,
    };

    console.log("data", data);

    axios.post(API_URL + "/teamTryOut/isregister", data).then((response) => {
      console.log("Registration status", response);
      if (response.data) {
        alert(
          "You have registered for tryout session."
        );
        this.setState({
          alreadyregistered: true,
        });
      }
    })
    .catch((error) => {
      alert(
        "There is an error in registration."
      );
    });
  }

  componentDidMount() {
    axios
      .get(API_URL + "/teamTryOut/teamDetails")
      .then((response) => {
        console.log(response.data);
        this.setState({
          teamDetails: response.data,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  OpenProperty = (property) => {
    this.props.history.push(`/teamTryOut/isregister/${property.propertyId}`);
  };

  render() {
    const { classes } = this.props;
    console.log("I am here in block", this.state.teamDetails);
    let eachTeam = this.state.teamDetails.map((team) => {
      console.log("team", team);
      return (
        <tr>
          <td id="header"> {team[0]}</td>
          <td id="header"> {team[1]} </td>
          <td id="header"> {team[2]} </td>
          <CardActions>
            <button id={team[0]} onClick={this.registerStudent.bind(this)}>
              Register
            </button>
          </CardActions>
        </tr>
      );
    });

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
                    <TableCell>TEAM NAME</TableCell>
                    <TableCell>COACH NAME</TableCell>
                    <TableCell>YEAR</TableCell>
                    <TableCell>TEAM TRYOUT REGISTRATION</TableCell>
                  </TableRow>
                  {eachTeam}
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

const PaperC = withStyles(useStyles)(TeamTryOut);
export default PaperC;
