// TIPO *ESTRUTURA DE UM PROD* PRA Q EU POSSA LISTAR, VER A DESCRICAO E CADASTRAR PRODS!

// O tipo "ProductResponse" foi criado pq o backend retorna um objeto c/ um "content", e dentro desse "content" eh q tem a lista de prods. Esse tipo eh somente pra listagem de prods!
export type ProductsResponse = {
    content: Product[];
    // "totalPages: number;" eh pra q eu possa fazer o componente de paginacao dps.
    totalPages: number;
}

export type Product = {
    id: number;
    name: string;
    description: string;
    price: number;
    imgUrl: string;
    date: string;
    categories: Category[];
}

export type Category = {
    id: number;
    name: string;
}