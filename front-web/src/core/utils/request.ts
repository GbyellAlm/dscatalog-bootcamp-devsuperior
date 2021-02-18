import axios, { Method } from 'axios';

// UTILITARIO DO AXIOS PRA Q EU N PRECISE ESCREVER ESSE CODIGO TODA VEZ Q EU FOR UTILIZAR O AXIOS.

type RequestParams = {
    method?: Method;
    url: string;
    data?: object;
    params?: object;
}

const BASE_URL = 'http://localhost:3000';
// " method = 'GET'" eh o valor padrao do "method". 
export const makeRequest = 
({ method = 'GET', url, data, params }:RequestParams) => {
    return axios({
        // Somente "method," pois o JS permite n escrever dnv valores q sao iguais as chaves.
        method,
        // Concatena a URL base c/ a "url opcional", ou seja, c/ a rota q o frontend estah solicitando.
        url: `${BASE_URL}${url}`,
        // Dados q o backend vai receber de POSTS e UPDATES.
        data,
        // Parametros de req.
        params
    });
}