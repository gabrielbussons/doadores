import React from 'react';
import DoadorService from '../services/DoadorService';
import Table from './Table';
import { Container } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepButton from '@material-ui/core/StepButton';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import Alerts from './Alerts';

const useStyles = makeStyles((theme) => ({
    root: {
      width: '100%',
      marginTop: '30px'
    },
    button: {
      marginRight: theme.spacing(1),
    },
    completed: {
      display: 'inline-block',
    },
    instructions: {
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
    },
    instructionsRight: {
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
      float: 'right'
    },
    instructionsLeft: {
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
      float: 'left'
    },
    instructionsCenter: {
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
      float: 'center'
    },
    gridFilename: {
      color: 'red',
      marginBottom: theme.spacing(3),
    },
    centerGrid: {
      marginTop: theme.spacing(10),
    }
  }));

export default function Doador(){

    const [status, setStatus] = React.useState("");
    const [candidatos, setCandidatos] = React.useState(0);
    const [verificador, setVerificador] = React.useState(true);
    const [profileImage, setProfileImage] = React.useState('');
    const [arquivo, setArquivo] = React.useState('');
    const [arquivoNome, setArquivoNome] = React.useState('');
    const [listaPessoasEstados, setListaPessoasEstados] = React.useState([]);
    const [listaImcMedio, setListaImcMedio] = React.useState([]);
    const [listaPercObeses, setListaPercObesos] = React.useState([]);
    const [listaMediaIdadeSang, setListaMediaIdadeSang] = React.useState([]);
    const [listaPossiveisDoadores, setListaPossiveisDoadores] = React.useState([]);

    const classes = useStyles();
    const [activeStep, setActiveStep] = React.useState(0);
    const [completed, setCompleted] = React.useState({});

    const totalSteps = () => {
      return steps.length;
    };
  
    const completedSteps = () => {
      return Object.keys(completed).length;
    };
  
    const isLastStep = () => {
      return activeStep === totalSteps() - 1;
    };
  
    const allStepsCompleted = () => {
      return completedSteps() === totalSteps();
    };
  
    const handleNext = () => {
      const newActiveStep = isLastStep() && !allStepsCompleted()
          ? steps.findIndex((step, i) => !(i in completed)) : activeStep + 1;
      setActiveStep(newActiveStep);
    };
  
    const handleBack = () => {
      setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };
  
    const handleStep = (step) => () => {
      setActiveStep(step);
    };

    const getFile = (e) => {

      setArquivo(e.target.files[0]);
      setArquivoNome(e.target.files[0].name);
    }

    const excluirLista = () => {
      DoadorService.deleteAll().then(() => {
        init();
        setStatus({
          mensagem: "Doadores excluídos com sucesso!",
          tipoAlerta: "success",
          key: Math.random()
        })
      });
    }

      const init = () => {
       DoadorService.getAll().then(res => {
          if(res.data && res.data.length > 0){
              setVerificador(false)
              setCandidatos(res.data.length);
          }else {
            setVerificador(true)
            setArquivo(null)
            setArquivoNome(null)
          }
      });

      DoadorService.pessoasPorEstado().then(res => {
          setListaPessoasEstados(res.data);
      });

      DoadorService.imcMedio().then(res => {
          setListaImcMedio(res.data);
      });

      DoadorService.percentualObesos().then(res => {
          setListaPercObesos(res.data);
      });

      DoadorService.mediaIdadeSanguineo().then(res => {
          setListaMediaIdadeSang(res.data);
      });

      DoadorService.possiveisDoadores().then(res => {
          setListaPossiveisDoadores(res.data);
      });
    }

    const submitFile = () => {

          debugger
          
          if(arquivo != null && arquivo != '' && arquivoNome != ''){
            if(arquivoNome.includes('.json')){
              const formData = new FormData();
              formData.append('file', arquivo);

              DoadorService.cadastrarArquivo(formData).then(() => {
                init();
                setStatus({
                  mensagem: "Doadores cadastrados com sucesso!",
                  tipoAlerta: "success",
                  key: Math.random()
                })
              }).catch(e => {
                setStatus({
                  mensagem: e,
                  tipoAlerta: "error",
                  key: Math.random()
                })
              });
            } else{
              setStatus({
                mensagem: "Arquivo inválido. Selecione um arquivo .json",
                tipoAlerta: "error",
                key: Math.random()
              })
            }
          } else{
            setStatus({
              mensagem: "Selecione um arquivo.",
              tipoAlerta: "error",
              key: Math.random()
            })
          }
      
    }

    const steps = getSteps();

    function getSteps() {
        return ['Doadores por estado', 'IMC médio ', 'Percentual obesos', 'Média tipo sanguineo', 'Possíveis doadores'];
      }
      
    function getStepContent(step) {
        switch (step) {
          case 0:
            return <Table lista = {listaPessoasEstados} descricao = {"Estado"} valor = {"Quantidade candidatos"}
            titulo = {"Candidatos em cada estado do país"}/>
          case 1:
            return <Table lista = {listaImcMedio} descricao = {"Faixa de idade"} valor = {"IMC médio"} 
            titulo = {"IMC médio em cada faixa de idade"} toFixed/>
          case 2:
            return <Table lista = {listaPercObeses} descricao = {"Sexo"} valor = {"Percentual Obesos"} 
            titulo = {"Percentual de obesos entre os homens e mulheres"} toFixed/>
          case 3:
            return <Table lista = {listaMediaIdadeSang} descricao = {"Tipo Sanguíneo"} valor = {"Média idade"} 
            titulo = {"Média de idade para cada tipo sanguíneo"} toFixed/>
          case 4:
            return <Table lista = {listaPossiveisDoadores} descricao = {"Tipo Sanguíneo"} valor = {"Possíveis doadores "} 
            titulo = {"Possíveis doadores para cada tipo sanguíneo receptor"}/>
          default:
            return '';
        }
      }


    React.useEffect(() => {
      
      init();

    }, [])

    

    return (
        <Grid>
            <Container disableGutters component="section" maxWidth="md" className={classes.root}>
                {status ? <Alerts mt={8} key={status.key} mensagem={status.mensagem} tipoAlerta={status.tipoAlerta} /> : null}
                {verificador ?
                  <React.Fragment>
                    <Grid className={classes.centerGrid}>
                      <Grid>
                        <p><h3>Por favor, cadastre uma lista de doadores.</h3></p>
                      </Grid>
                      <Grid>
                        <Button color="primary" variant="contained" component="label" id={1} startIcon={<CloudUploadIcon />}>
                                Selecionar arquivo
                                <input type="file" style={{ display: "none" }} onChange={e => getFile(e)}/>
                        </Button>
                      </Grid>
                      <Grid>Arquivo selecionado: <span className={classes.gridFilename}>{arquivoNome}</span></Grid>
                      <Grid className={classes.instructions}>
                        <Button color="primary" variant="contained" component="label" onClick={submitFile}>
                                Enviar
                        </Button>
                      </Grid>
                    </Grid>
                  </React.Fragment> :
                    <React.Fragment>
                    <Grid>
                      <div className={classes.instructionsCenter}>Número candidatos cadastrados: {candidatos}</div>
                    </Grid>
                    <Stepper nonLinear activeStep={activeStep}>
                        {steps.map((label, index) => (
                        <Step key={label}>
                            <StepButton onClick={handleStep(index)} completed={completed[index]}>
                            {label}
                            </StepButton>
                        </Step>
                        ))}
                    </Stepper>
                    <div>      
                      <Grid className={classes.instructions}>
                        <Typography className={classes.instructions}>{getStepContent(activeStep)}</Typography>
                        <div className={classes.instructionsLeft}>
                        <Button variant="contained" color="secondary" onClick={excluirLista} className={classes.button} >
                            EXCLUIR LISTA
                        </Button>
                        </div>
                        <div className={classes.instructionsRight}>
                        <Button disabled={activeStep === 0} variant="contained" onClick={handleBack} className={classes.button}>
                            Voltar
                        </Button>
                        <Button variant="contained" color="primary" onClick={handleNext} className={classes.button} >
                            Próximo
                        </Button>
                        </div>
                      </Grid>
                    </div>
                    </React.Fragment>
                }
            </Container>
        </Grid>
    )
    
    
}