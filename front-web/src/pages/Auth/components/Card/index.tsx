import React from 'react'
import './styles.scss'

type Props = {
    title: string;
    children: React.ReactNode;
}

// "AuthCard" como nome aqui pra facilitar o VS Code na hr do "autocomplete" ao importar esse componente. Esse nome n foi colocado como nome da pasta tb pq a pasta jah tah dentro de uma pasta chamada "Auth", então a estrutura de pastas "Auth->AuthCard" ficaria redundante (Isso ateh eh um padrao de nomeamento de pastas do React). 
const AuthCard = ({ title, children }: Props) => {
    return (
        <div className="card-base border-radius-20 auth-card">
            <h1 className="auth-card-title">{title}</h1>
            {children}
        </div>
    )
}

export default AuthCard;
