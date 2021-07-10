import axios from 'axios';

const DOADOR_REST_API_URL = "http://localhost:8080/api/";

class DoadorService{

    getAll(){
        return axios.get(DOADOR_REST_API_URL + "getAll")
    }

    deleteAll(){
        return axios.get(DOADOR_REST_API_URL + "deleteAll")
    }

    cadastrarArquivo(file){
        
        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }

        return axios.post(DOADOR_REST_API_URL + "cadastrarArquivo", file, config)
    }

    pessoasPorEstado(){
        return axios.get(DOADOR_REST_API_URL + "pessoasPorEstado")
    }

    imcMedio(){
        return axios.get(DOADOR_REST_API_URL + "imcMedio")
    }

    percentualObesos(){
        return axios.get(DOADOR_REST_API_URL + "percentualObesos")
    }

    mediaIdadeSanguineo(){
        return axios.get(DOADOR_REST_API_URL + "mediaIdadeSanguineo")
    }

    possiveisDoadores(){
        return axios.get(DOADOR_REST_API_URL + "possiveisDoadores")
    }
    
}

export default new DoadorService();