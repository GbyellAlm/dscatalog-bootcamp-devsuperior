import React from 'react';
import './styles.scss';

type Props = {
    price: number;
}

// FORMATANDO O PRECO Q VEIO DO BACKEND.
const formatPrice = (price: number) => {
    /*
        * "Intl" significa "internacionalizacao";
        * "pt-br" eh a minha localizacao *o formatPrice jah consegue saber c/ isso pra ql moeda o valor tem q ser convetido*;
        * "minimumFractionDigits: 2" eh a definicao da qtde de casas apos a virgula *definicao dos centavos*;
        * ATENCAO! Esse n eh o jeito padrao do "numberFormat" formatar precos. Usei esse jeito pois meu sist ja tem o cifrao no frontend *o jeito padrao devolve tb o cifrao alem do preco formatado*. Nas anotacoes da aula 21 tem o link c/ esse jeito padrao. 
    */
    return new Intl.NumberFormat('pt-br', {minimumFractionDigits: 2}).format(price);
}

const ProductPrice = ({ price }: Props) => (
    <div className="product-price-container">
        <span className="product-currency">R$</span>
        <h3 className="product-price">{ formatPrice(price)}</h3>
    </div>
);

export default ProductPrice;