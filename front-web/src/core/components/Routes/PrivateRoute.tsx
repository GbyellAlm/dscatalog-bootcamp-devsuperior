import { Redirect, Route } from 'react-router-dom';
import { isAuthenticated } from '../../utils/auth';

type Props = {
    children: React.ReactNode;
    path: string;
}

const PrivateRoute = ({ children, path }: Props) => {
    return (
        <Route
            path={path}
            render={({ location }) =>
                isAuthenticated() ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: "/auth/login",
                            // "state" armazena aonde o usuario clicou antes do sistema verificar q ele (o usuario) n esta mais autenticado.
                            state: { from: location }
                        }}
                    />
                )
            }
        />
    );
}

export default PrivateRoute;
