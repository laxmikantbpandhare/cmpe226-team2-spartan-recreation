/* eslint-disable no-script-url */
import { forwardRef } from "react";
import AddBox from "@material-ui/icons/AddBox";
import ThumbUp from "@material-ui/icons/ThumbUp"
import ThumbDown from "@material-ui/icons/ThumbDown"
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import Check from "@material-ui/icons/Check";
import ChevronLeft from "@material-ui/icons/ChevronLeft";
import ChevronRight from "@material-ui/icons/ChevronRight";
import Clear from "@material-ui/icons/Clear";
import DeleteOutline from "@material-ui/icons/DeleteOutline";
import Edit from "@material-ui/icons/Edit";
import FilterList from "@material-ui/icons/FilterList";
import FirstPage from "@material-ui/icons/FirstPage";
import LastPage from "@material-ui/icons/LastPage";
import Remove from "@material-ui/icons/Remove";
import SaveAlt from "@material-ui/icons/SaveAlt";
import Search from "@material-ui/icons/Search";
import ViewColumn from "@material-ui/icons/ViewColumn";
import React, { Component } from "react";
import axios from "axios";
// import { Link } from "react-router-dom";
import MaterialTable from "material-table";
import { API_URL } from "../../Constants";

const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => (
    <ChevronRight {...props} ref={ref} />
  )),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => (
    <ChevronLeft {...props} ref={ref} />
  )),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />),
};

const columns = [
  { title: "First Name", field: "fname", type: "string"  },
  { title: "Last Name", field: "lname", type: "string" },
  { title: "Social Security Number", field: "ssn", type: "string" , width : 400 },
  { title: "College year", field: "college_year", type: "numeric" , align : "left", width : 200},
  { title: "Session Name", field: "session_name", type: "string" , align : "left", width : 400}
];

class PendingStudentRequests extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rows: null,
      columns: columns,
    };
  }
  componentWillMount() {
    
    var coachssn = sessionStorage.getItem("ssn");
    axios.get(API_URL+`/coaches/getAllPendingRequests/${coachssn}`).then((res) => {

      let studentarr = new Array();
  
      res.data.map((row) => {

        var data ={
            "fname":row[1],
            "lname":row[2],
            "ssn":row[0],
            "college_year":row[3],
            "session_name":row[4]
        }
        studentarr.push(data);
      })
      this.setState({
        data: studentarr
      });

      console.log("STUDENT DATA", res.data);
    });
  }
  render() {
    return (
        <MaterialTable
          icons={tableIcons}
          title='Pending Student Registrations'
          columns={this.state.columns}
          data={this.state.data}
          sort
          options = {[
           { tableLayout : 'auto'}
          ]}
          actions={
              [
            {
              icon: () => <ThumbUp />,
              tooltip: "Approve student",

              onClick: (event, rowData) => {

                console.log("==================APPROVING!===================",rowData.ssn)
                const fda = sessionStorage.getItem('ssn');
                  axios
                  .post(API_URL+`/coaches/assessStudentRequest/`+rowData.ssn+`/`+rowData.session_name+`/`+`approved`)
                    .then((res) => {
                      console.log("after approval call ", res.data);
                      // const admin = sessionStorage.getItem('ssn');
                      axios.get(API_URL+`/allPendingStudents`).then((res) => {
                        let studentarr = new Array();
                        res.data.map((row) => {
                          studentarr.push(row);
                        })
                        this.setState({
                          data: studentarr
                        });                 
                        console.log("STUDENT DATA", res.data);
                      });

                    });
              }
   
            },
            {
                icon: () => <ThumbDown />,
                tooltip: "Approve student",
  
                onClick: (event, rowData) => {
  
                  console.log("==================APPROVING!===================",rowData.session_name)
                  const fda = sessionStorage.getItem('ssn');
                    axios
                      .post(API_URL+`/coaches/assessStudentRequest/`+rowData.ssn+`/`+rowData.session_name+`/`+`rejected`)
                      .then((res) => {
                        console.log("after approval call ", res.data);
                        // const admin = sessionStorage.getItem('ssn');
                        axios.get(API_URL+`/allPendingStudents`).then((res) => {
                          let studentarr = new Array();
                          res.data.map((row) => {
                            studentarr.push(row);
                          })
                          this.setState({
                            data: studentarr
                          });                 
                          console.log("STUDENT DATA", res.data);
                        });
  
                      });
                }
     
              }
          ]
        }

        />
    );
  }
}

export default PendingStudentRequests;
