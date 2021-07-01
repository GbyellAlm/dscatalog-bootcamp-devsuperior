import jwtDecode from 'jwt-decode';
import history from './history';

export const CLIENT_ID = 'dscatalog';
export const CLIENT_SECRET = 'dscatalog123';

type LoginResponse = {
    access_token: string;
    token_type: string;
    expires_in: number;
    scope: string;
    userFirstName: string;
    userId: number;
}

type Role = 'ROLE_OPERATOR' | 'ROLE_ADMIN';

type AccessToken = {
    exp: number;
    user_name: string;
    authorities: Role[];
}

// SALVA OS DADOS DE SESSAO DO USU (AQUELES DADOS Q APARECEM COMO RESPOSTA DA REQ DE AUTENTICACAO NO POSTMAN) NO NAVEGADOR.
export const saveSessionData = (loginResponse: LoginResponse) => {
    localStorage.setItem('authData', JSON.stringify(loginResponse));
}

/*
    * RECUPERA OS DADOS DE SESSAO DO USU; 
    * Esse "?? '{}'" eh pq o "sessionData" do "parsedSessionData" fica sublinhado em vermelho, pq posso passar um valor q n existe no "getItem" do "sessionData". Dai, p/ arrumar esse erro, basta dizer pro "getItem" q se ele tiver um valor q n existe, botar um objeto vazio (exatamente isso: "?? '{}'");
    * "Null coalescing" eh o nome do operador "??" (pesquisar na net se quiser aprender mais sobre).
*/
export const getSessionData = () => {
    const sessionData = localStorage.getItem('authData') ?? '{}';
    const parsedSessionData = JSON.parse(sessionData);

    // Esse "as LoginResponse" eh basicamente p/ transformar os dados vindos no "parsedSessionData" no formato do "LoginResponse". Pq q tem q fazer isso? R: Pq o tipo do "parsedSessionData" eh "any", pois o "parse" do "JSON.parse" n transforma p/ esse formato do "LoginResponse". Esse "as" se chama "type casting".
    return parsedSessionData as LoginResponse;
}

export const getAccessTokenDecoded = () => {
    const sessionData = getSessionData();

    try {
        const tokenDecoded = jwtDecode(sessionData.access_token);
        return tokenDecoded as AccessToken;
    } catch (error) {
        return {} as AccessToken;
    }
}

export const isTokenValid = () => {
    const { exp } = getAccessTokenDecoded();

    return Date.now() <= exp * 1000;
}

export const isAuthenticated = () => {
    const sessionData = getSessionData();

    return sessionData.access_token && isTokenValid();
}

export const logout = () => {
    localStorage.removeItem('authData');
    history.replace('/auth/login');
}
