import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductsResponse } from '../../core/types/Product';
import { makeRequest } from '../../core/utils/request';
import ProductCard from './components/ProductCard';
import ProductCardLoader from './components/Loaders/ProductCardLoader';
import Pagination from '../../core/components/Pagination';
import './styles.scss';

const Catalog = () => {
    /*
        * "useState*false*" pq, logicamente falando, o valor padrao desse estado tem q ser falso, pois esse estado soh tem q existir qdo os dados estiverem sendo solicitados *qdo a pag estiver carregando *buscando dados**.
    */
    const [isLoading, setIsLoading] = useState(false);

     // PAG ATIVA NO MOMENTO.
     const [activePage, setActivePage] = useState(0);
    
    /*
        * BUSCANDO A LISTA DE PRODS DO BACKEND, QDO O COMPONENTE EH INICIALIZADO. 
        * ATENCAO! A lista de prods eh smp "undefined" qdo o componente eh inicializado pela 1a vez. Dps do componente ser inicializado pela 1a vez, a req pro backend eh feita, e dps q for concluida a req pro backend, o estado "productsResponse" eh populado pra poder fazer a listagem de prods na div de classe "catalog-products".
    */
    useEffect(() => {
        //console.log('Componente "Listagem de produtos" iniciado!');
        
        /*
            * FORMA MAIS SIMPLES DE SE FAZER UMA REQUISICAO HTTP VIA JS! 
            * O q esse codigo faz?
                R: Busca a lista de prods do backend assim q o componente for inicializado *array de dependencias *[]* vazio*.
            * O q eh esse "then**"?
                R: Eh um metodo q retorna uma promise se a req deu certo.
            * O q eh esse "response.json**"? 
                R: Eh a transformacao da promise *response* em JSON. Tenho smp q fazer isso c/ o "fetch**".
            * Pq n estou usando essa forma pra fazer requisicao HTTP via JS?
                R: Pq ela eh mt verbosa *smp tenho q converter pra JSON a resp do backend, sendo q o backend ja dah a resp em JSON* e n tem suporte nativo pra ler o progresso de upload de arquivos e pra enviar query strigs *parametros de req* *tenho q escrever mt codigo pra isso*.
        fetch('http://localhost:3000/products').then(response => response.json()).then(response => console.log(response));
        */
        
        const params = {
            // "page: activePage" eh pra ir pra pag ativa no momento *seja a 1a msm ou seja a q cliquei*
            page: activePage,
            linesPerPage: 10
        }
        /*
            * O ".data" eh do Axios e dentro dele contem o tipo "Product.ts";
            * O "finally**" eh um metodo pra eu saber qdo a requisicao termina. Preciso saber disso pra poder criar os "loaders";
            * O "setIsLoading*true*;" inicia o "loader".
        */
        setIsLoading(true);
        makeRequest({ url: '/products', params }).then(response => setProductsResponse(response.data)).finally(() => {
            // "setIsLoading*false*;" finaliza o "loader" apos a req terminar. N interessa se deu ruim ou n a req.
            setIsLoading(false);
        })
        // "activePage" como dependencia *"[activePage]"* pq toda vez q eu clicar em uma pag, o sistema tem q fazer dnv essa req *a req q esse codigo faz*, e popular a lista de itens *novos produtos nessa nova pag*. 
    }, [activePage]);

    // POPULANDO O ESTADO "productsResponse" PRA PODER LISTAR OS PRODS DINAMICAMENTE.
    const [productsResponse, setProductsResponse] = useState<ProductsResponse>(); 
    
    // "console.log..." eh pra testar no console se tá vindo a lista de prods.
    //console.log(productsResponse);

    return (
        // "{productsResponse &&...}" eh pra q as pags soh sejam renderizadas qdo houver uma resposta do backend *productsResponse*. Essa linha de codigo eh um "if". Esse "activePage" eh pra mudar a cor da bolinha qdo a pag q representa essa bolinha estiver ativa *c/ o status "active"*. 
        <div className="catalog-container">
            <h1 className="catalog-title">Catálogo de produtos</h1>
            <div className="catalog-products">
                {isLoading ? <ProductCardLoader /> : (
                    productsResponse?.content.map(product => (
                        <Link to={`/products/${product.id}`} key={product.id}>
                            <ProductCard product={product}/>
                        </Link>
                    ))
                )}
            </div>
            {productsResponse && <Pagination totalPages={productsResponse.totalPages} activePage={activePage} onChange={page => setActivePage(page)}/>}
        </div>
    );
}

export default Catalog;