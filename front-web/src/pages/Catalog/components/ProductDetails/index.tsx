import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ReactComponent as ArrowIcon } from '../../../../core/assets/images/arrow.svg';
import { ReactComponent as ProductImage } from '../../../../core/assets/images/product.svg'
import ProductPrice from '../../../../core/components/ProductPrice';
import { Product } from '../../../../core/types/Product';
import { makeRequest } from '../../../../core/utils/request';
import './styles.scss';

/*
    * type ParamsType = {productId: string;} eh pra criar pro ReactJS o parametro de requisicao *q nesse caso eh o id do prod. Eh atraves dele q vou buscar os dados do prod*;
        - Em "productId: string;", o tipo "string" eh pq os parametros de req sao strings.
*/
type ParamsType = {
    productId: string;
}

const ProductDetails = () => {
    const { productId } = useParams<ParamsType>();
    
    // BUSCANDO OS DADOS DO PROD, QDO O COMPONENTE EH INICIALIZADO.
    useEffect(() => {
        //console.log('Componente "Detalhes do produto" iniciado!');

        makeRequest({ url: `/products/${productId}` }).then(response => setProduct(response.data));

    }, [productId]);
    // "productId" como dependencia pq preciso do id do produto pra solicitar as infos do prod pro backend. *Qdo o componente for inicializado, o id ja vai existir*.

    const [product, setProduct] = useState<Product>();
    
    // "console.log..." eh pra testar no console se tá vindo os dados do prod.
    //console.log(product);
    
    return (
        <div className="product-details-container">
            <div className="card-base border-radius-20 product-details">
                <Link to ="/products" className="product-details-goback">
                    <ArrowIcon className="icon-goback"/>
                    <h1 className="text-goback">voltar</h1>
                </Link>
                <div className="row">
                    <div className="col-6 pr-5">
                        <div className="product-details-card text-center">
                            <img src={product?.imgUrl} alt={product?.name} className="product-details-image"/>
                        </div>
                        <h1 className="product-details-name">{product?.name}</h1>
                        { product?.price && <ProductPrice price = {product?.price}/> }
                    </div>
                    <div className="col-6 product-details-card">
                        <h1 className="product-description-title">Descrição do produto</h1>
                        <p className="product-description-text">
                            {product?.description}
                        </p>
                    </div>
                </div>
            </div>
        </div>    
    );
};
// "{ product?.price &&..." tah explicado nas "anotacoes aula 22 e 23".
export default ProductDetails;