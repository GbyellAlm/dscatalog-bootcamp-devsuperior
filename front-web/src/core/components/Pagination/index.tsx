import React from 'react';
import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg';
import { generateList } from 'core/utils/list';
import './styles.scss';

/*
    * "onChange: (item: number) => void;" eh uma funcao do "onClick" da div de classe "pagination-item", pra pegar a bolinha q cliquei e colocar o status "active" nela *pintar ela de azul*.
    * Como q se le ela?
        R: "onChange" *uma funcao* q recebe um "item" *esse "item" eh do tipo number* q n retorna nada.     
*/ 
type Props = {
    totalPages: number;
    activePage: number;
    onChange: (item: number) => void;
}

const Pagination = ({ totalPages, activePage, onChange }: Props) => {
    
    // CRIANDO AS PAGS DINAMICAMENTE
    const items = generateList(totalPages);

    /*
        * CONFIGURANDO A SETA "VOLTAR PAG" PRA FICAR ATIVA SOMENTE DA PAG 2 EM DIANTE.
        * "totalPages > 0" significa o backend ter mandado um "totalPages" maior q 0 msm.
    */
    const previousClass = totalPages > 0 && activePage > 0 ? 'page-active' : 'page-inactive';
    
    // CONFIGURANDO A SETA "AVANCAR PAG" PRA FICAR ATIVA SOMENTE QDO A PAG ATIVA + 1 FOR MENOR Q O "totalPages". Ex: pag ativa = 1, 1 + 1 = 2, totalPages = 3 \\SETA ATIVA!!//.
    const nextClass = (activePage + 1) < totalPages ? 'page-active' : 'page-inactive';

    return (
        // "{item + 1}" eh pra criar os numeros das bolinhas das pags. O "+ 1" eh pq o item eh uma lista, e tda lista comeca c/ 0 *n qro q apareca pag 0 pro usu*.
        // "onClick={() => onChange(activePage - 1)}" eh pra fazer a seta "voltar pag" voltar pra pag anterior.
        // "onClick={() => onChange(activePage + 1)}" eh pra fazer a seta "avancar pag" avancar pra prox pag.
        <div className="pagination-container">
            <ArrowIcon className={`pagination-previous ${previousClass}`} onClick={() => onChange(activePage - 1)}/>
            {items.map(item => (
                <div className={`pagination-item ${item === activePage ? 'active' : ''}`} key={item} onClick={() => onChange(item)}>
                    {item + 1}
                </div>
            ))}
            <ArrowIcon className={`pagination-next ${nextClass}`} onClick={() => onChange(activePage + 1)}/>
        </div>
    );
}

export default Pagination;