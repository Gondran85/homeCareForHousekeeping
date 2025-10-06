package com.jeffersongondran.home_care_housekeeping.data.model

/**
 * Modelo de dados para representar um item da lista de compras
 *
 * @param id Identificador único do item
 * @param nome Nome do item (ex: "Chicken", "Milk")
 * @param estaComprado Status se o item já foi comprado
 * @param categoriaId ID da categoria à qual o item pertence
 */
data class ItemCompra(
    val id: Int,
    val nome: String,
    var estaComprado: Boolean = false,
    val categoriaId: Int
)

/**
 * Modelo de dados para representar uma categoria de compras
 *
 * @param id Identificador único da categoria
 * @param titulo Título da categoria com emoji (ex: "🥩 Proteínas")
 * @param corFundo Cor de fundo da categoria
 * @param itens Lista de itens que pertencem a esta categoria
 * @param estaExpandida Controla se a categoria está expandida na UI
 */
data class CategoriaCompra(
    val id: Int,
    val titulo: String,
    val corFundo: Int,
    val itens: MutableList<ItemCompra> = mutableListOf(),
    var estaExpandida: Boolean = false
) {
    /**
     * Calcula o progresso da categoria (quantos itens foram comprados)
     * @return Par com (itens comprados, total de itens)
     */
    fun calcularProgresso(): Pair<Int, Int> {
        val itensComprados = itens.count { it.estaComprado }
        return itensComprados to itens.size
    }

    /**
     * Verifica se todos os itens da categoria foram comprados
     */
    fun estaCompleta(): Boolean = itens.all { it.estaComprado }
}

/**
 * Estados possíveis da tela de lista de compras
 */
sealed class EstadoListaCompras {
    object Carregando : EstadoListaCompras()
    data class Sucesso(val categorias: List<CategoriaCompra>) : EstadoListaCompras()
    data class Erro(val mensagem: String) : EstadoListaCompras()
}
