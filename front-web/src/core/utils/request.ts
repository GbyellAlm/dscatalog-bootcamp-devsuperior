import axios, { Method } from 'axios';
import qs from 'qs';
import { CLIENT_ID, CLIENT_SECRET, getSessionData, logout } from './auth';
import history from './history';

// UTILITARIO DO AXIOS PRA Q EU N PRECISE ESCREVER ESSE CODIGO TODA VEZ Q EU FOR UTILIZAR O AXIOS.

type RequestParams = {
    method?: Method;
    url: string;
    data?: object | string;
    params?: object;
    headers?: object;
}

type LoginData = {
    username: string;
    password: string;
}

const BASE_URL = 'http://localhost:3000';

axios.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if (error.response.status === 401) {
        logout();
    }
    return Promise.reject(error);
});

// " method = 'GET'" eh o valor padrao do "method". 
export const makeRequest =
    ({ method = 'GET', url, data, params, headers }: RequestParams) => {
        return axios({
            // Somente "method," pois o JS permite n escrever dnv valores q sao iguais as chaves.
            method,
            // Concatena a URL base c/ a "url opcional", ou seja, c/ a rota q o frontend estah solicitando.
            url: `${BASE_URL}${url}`,
            // Dados q o backend vai receber de POSTS e UPDATES.
            data,
            // Parametros de req.
            params,
            headers
        });
    }

export const makeLogin = (loginData: LoginData) => {
    const token = `${CLIENT_ID}:${CLIENT_SECRET}`;

    const headers = {
        // "btoa(token)", esse "btoa" significa "byte to asc". Ele serve p/ criar o base 64 do client_id e do client_secret.
        Authorization: `Basic ${window.btoa(token)}`,
        // "'Content-Type'"" entre aspas simples pq no JS, qdo temos uma variavel q contem um caracter especial (traco, virgula, etc), temos q por ela entre aspas simples.
        'Content-Type': 'application/x-www-form-urlencoded'
    }

    /*
        * "...loginData" significa td q tem no "loginData";
        * Ao inves de instalar o "qs" e seus tipos p/ fazer a req do login p/ o backend, eu poderia simplesmente fazer assim: "const payload = `username=${loginData.username}.com&password=${loginData.password}&grant_type=password`;" (q eh o q o "qs" se transforma por debaixo dos panos (olhar "Form Data -> View Source")). O "qs" foi usado p/ mostrar p/ gnt q ele existe, e q eh bastante usado pela comunidade (entretanto, segundo o prof "instalar libs so pra fazer uma coisinha simples dessas, n eh legal, mas dxa o codigo mais profissional").
    */
    const payload = qs.stringify({ ...loginData, grant_type: 'password' });
    return makeRequest({ url: '/oauth/token', data: payload, method: 'POST', headers });
}

// O "makePrivateRequest" PERMITE FAZER COISAS Q PRECISAM DE AUTENTICACAO, POS AUTENTICACAO (CRIAR PRODUTO, ATUALIZAR, DELETAR...)
export const makePrivateRequest = ({ method = 'GET', url, data, params }: RequestParams) => {
    const sessionData = getSessionData()

    const headers = {
        'Authorization': `Bearer ${sessionData.access_token}`
    }

    return makeRequest({ method, url, data, params, headers })
}