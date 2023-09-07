import { Workbook } from "exceljs";
import { errorToast, successToast } from "./toastSetup";

const fs = require("fs");

export function extractEmailFromXLSX(file) {
  const workbook = new Workbook();

  workbook.xlsx
    .readFile(file)
    .then(() => {
      const workSheet = workbook.getWorksheet(1);
      const emailIds = [];

      let columnIndex = -1;
      workSheet.getRow(1).eachCell((cell, colNumber) => {
        if (
          cell.value === "Email" ||
          cell.value === "email" ||
          cell.value === "email id" ||
          cell.value === "email_id" ||
          cell.value === "Email ID" ||
          cell.value === "Email_ID" ||
          cell.value === "Email Id" ||
          cell.value === "Email_Id" ||
          cell.value === "EMAIL" ||
          cell.value === "EMAILID" ||
          cell.value === "EMAIL ID" ||
          cell.value === "EMAIL_ID"
        ) {
          columnIndex = colNumber;
        }
      });

      if (columnIndex === -1) {
        errorToast(`Column with name Email or Email ID not found`);
        return;
      }

      for (let ii = 2; ii <= workSheet.rowCount; ii++) {
        const cell = workSheet.getCell(ii, columnIndex);
        emailIds.push(cell.value);
      }

      successToast("Email addresses have been extracted");
      return emailIds;
    })
    .catch((error) => {
      errorToast(error);
    });
}

export function extractEmailFromCSV(file) {
  const workbook = new Workbook();

  workbook.csv
    .readFile(file)
    .then(() => {
      const workSheet = workbook.getWorksheet(1);
      const emailIds = [];

      let columnIndex = -1;
      workSheet.getRow(1).eachCell((cell, colNumber) => {
        if (
          cell.value === "Email" ||
          cell.value === "email" ||
          cell.value === "email id" ||
          cell.value === "email_id" ||
          cell.value === "Email ID" ||
          cell.value === "Email_ID" ||
          cell.value === "Email Id" ||
          cell.value === "Email_Id" ||
          cell.value === "EMAIL" ||
          cell.value === "EMAILID" ||
          cell.value === "EMAIL ID" ||
          cell.value === "EMAIL_ID"
        ) {
          columnIndex = colNumber;
        }
      });

      if (columnIndex === -1) {
        errorToast(`Column with name Email or Email ID not found`);
        return;
      }

      for (let ii = 2; ii <= workSheet.rowCount; ii++) {
        const cell = workSheet.getCell(ii, columnIndex);
        emailIds.push(cell.value);
      }

      successToast("Email addresses have been extracted");
      return emailIds;
    })
    .catch((error) => {
      errorToast(error);
    });
}

export function extractEmailFromTSV(file) {
  const emailIds = [];

  fs.readFile(file, "utf8", (error, data) => {
    if (error) {
      errorToast(error);
      return;
    }

    const rows = data.split("\n");
    const headers = rows[0].split("\t");
    let columnIndex = -1;

    for (let ii = 0; ii < headers.length; ii++) {
      if (
        headers[ii] === "Email" ||
        headers[ii] === "email" ||
        headers[ii] === "email id" ||
        headers[ii] === "email_id" ||
        headers[ii] === "Email ID" ||
        headers[ii] === "Email_ID" ||
        headers[ii] === "Email Id" ||
        headers[ii] === "Email_Id" ||
        headers[ii] === "EMAIL" ||
        headers[ii] === "EMAILID" ||
        headers[ii] === "EMAIL ID" ||
        headers[ii] === "EMAIL_ID"
      ) {
        columnIndex = ii;
      }
    }

    if (columnIndex === -1) {
      errorToast(`Column with name Email or Email ID not found`);
      return;
    }

    for (let ii = 1; ii <= rows.length; ii++) {
      const columns = rows[ii].split("\t");
      const value = columns[columnIndex];

      emailIds.push(value);
    }

    successToast("Email addresses have been extracted");
    return emailIds;
  });
}
