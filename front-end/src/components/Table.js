import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});


export default function DenseTable(props) {
  const classes = useStyles();

  return (
    <TableContainer component={Paper}>
      <h2>{props.titulo}</h2>
      <Table className={classes.table} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>{props.descricao}</TableCell>
            <TableCell align="right">{props.valor}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {props.lista.map((row) => (
            <TableRow key={row.descricao}>
              <TableCell component="th" scope="row">
                {row.descricao}
              </TableCell>
              <TableCell align="right">{row.valor ? (props.toFixed ? row.valor.toFixed(2) : row.valor) : 0}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}