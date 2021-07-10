import React from "react"
import { Alert } from '@material-ui/lab'
import { Collapse, IconButton } from '@material-ui/core'
import CloseIcon from '@material-ui/icons/Close'
import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(theme => ({
    root: {
        width: '20%',
        position: 'absolute',
        right: 0,
        zIndex: theme.zIndex.modal,
        top: theme.spacing(10),
        '& > * + *': {
            marginTop: theme.spacing(2),
        },
        [theme.breakpoints.down('xs')]: {
            width: '100%',
            top: 0
		},
    },
}))

export default function Alerts({ timeout = 3000, ...props}) {
    const classes = useStyles()
    const [open, setOpen] = React.useState(true)

    React.useEffect(() => {
        const timer = setTimeout(() => { setOpen(false) }, (timeout))
        return () => clearTimeout(timer)
    }, [timeout])

    return (
        <div className={classes.root}>
            <Collapse in={open}>
                <Alert variant="filled" severity={ props.tipoAlerta } 
                    action={
                        <IconButton aria-label="fechar" color="inherit" size="small" onClick={() => setOpen(false)}>
                                <CloseIcon fontSize="inherit" />
                        </IconButton>}>
                    { props.mensagem }
                </Alert>
            </Collapse>
        </div>
    )
}