// UTILITARIO DE LISTAS. * Explicacao disso nas anotacoes da aula 0225 *Aula 25*.

// Funcao q me permite criar "loaders" de cards dinamicamente. 
export const generateList = (amount: number) => {
    // "return Array.from..." significa: "Me retorna um array a partir das chaves do array de dentro". *
    return Array.from(Array(amount).keys());
}