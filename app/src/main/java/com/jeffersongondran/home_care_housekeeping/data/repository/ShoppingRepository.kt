package com.jeffersongondran.home_care_housekeeping.data.repository

import com.jeffersongondran.home_care_housekeeping.R
import com.jeffersongondran.home_care_housekeeping.data.model.CategoriaCompra
import com.jeffersongondran.home_care_housekeeping.data.model.ItemCompra

/**
 * Repositório responsável por fornecer os dados da lista de compras
 *
 * Este repositório simula uma fonte de dados local, mas poderia facilmente
 * ser estendido para trabalhar com APIs remotas ou banco de dados local
 */
class RepositorioListaCompras {

    /**
     * Obtém todas as categorias de compras com seus respectivos itens
     *
     * @return Lista de categorias organizadas com todos os itens
     */
    fun obterCategorias(): List<CategoriaCompra> {
        return listOf(
            criarCategoriaProteinas(),
            criarCategoriaVegetais(),
            criarCategoriaFrutas(),
            criarCategoriaPaes(),
            criarCategoriaLaticinios(),
            criarCategoriaTemperos(),
            criarCategoriaLimpeza()
        )
    }

    /**
     * Salva o estado atual dos itens de compra
     * Em uma implementação real, isso persistiria os dados
     */
    fun salvarEstadoItens(categorias: List<CategoriaCompra>) {
        // TODO: Implementar persistência com SharedPreferences ou Room Database
        // Por enquanto, apenas simula o salvamento
        println("Salvando ${categorias.size} categorias com ${categorias.sumOf { it.itens.size }} itens")
    }

    // Métodos privados para criar cada categoria com seus itens

    private fun criarCategoriaProteinas(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 1,
            titulo = "🥩 Proteínas",
            corFundo = R.color.soft_orange
        )

        categoria.itens.addAll(listOf(
            ItemCompra(1, "Frango", false, 1),
            ItemCompra(2, "Carne Bovina", false, 1),
            ItemCompra(3, "Peixe", false, 1),
            ItemCompra(4, "Ovos", false, 1),
            ItemCompra(5, "Embutidos", false, 1)
        ))

        return categoria
    }

    private fun criarCategoriaVegetais(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 2,
            titulo = "🥦 Vegetais",
            corFundo = R.color.mint_green
        )

        categoria.itens.addAll(listOf(
            ItemCompra(6, "Tomates", false, 2),
            ItemCompra(7, "Cenouras", false, 2),
            ItemCompra(8, "Alface", false, 2),
            ItemCompra(9, "Cebolas", false, 2),
            ItemCompra(10, "Alho", false, 2),
            ItemCompra(11, "Batatas", false, 2)
        ))

        return categoria
    }

    private fun criarCategoriaFrutas(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 3,
            titulo = "🍎 Frutas",
            corFundo = R.color.secondary_variant
        )

        categoria.itens.addAll(listOf(
            ItemCompra(12, "Maçãs", false, 3),
            ItemCompra(13, "Bananas", false, 3),
            ItemCompra(14, "Laranjas", false, 3),
            ItemCompra(15, "Uvas", false, 3)
        ))

        return categoria
    }

    private fun criarCategoriaPaes(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 4,
            titulo = "🥖 Pães & Grãos",
            corFundo = R.color.light_purple
        )

        categoria.itens.addAll(listOf(
            ItemCompra(16, "Pão", false, 4),
            ItemCompra(17, "Arroz", false, 4),
            ItemCompra(18, "Macarrão", false, 4),
            ItemCompra(19, "Farinha", false, 4)
        ))

        return categoria
    }

    private fun criarCategoriaLaticinios(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 5,
            titulo = "🧀 Laticínios",
            corFundo = R.color.sky_blue
        )

        categoria.itens.addAll(listOf(
            ItemCompra(20, "Leite", false, 5),
            ItemCompra(21, "Queijo", false, 5),
            ItemCompra(22, "Iogurte", false, 5),
            ItemCompra(23, "Manteiga", false, 5)
        ))

        return categoria
    }

    private fun criarCategoriaTemperos(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 6,
            titulo = "🧂 Temperos & Despensa",
            corFundo = R.color.mint_green
        )

        categoria.itens.addAll(listOf(
            ItemCompra(24, "Óleo", false, 6),
            ItemCompra(25, "Sal", false, 6),
            ItemCompra(26, "Pimenta", false, 6),
            ItemCompra(27, "Molho de Tomate", false, 6),
            ItemCompra(28, "Feijão", false, 6)
        ))

        return categoria
    }

    private fun criarCategoriaLimpeza(): CategoriaCompra {
        val categoria = CategoriaCompra(
            id = 7,
            titulo = "🧽 Limpeza & Casa",
            corFundo = R.color.soft_orange
        )

        categoria.itens.addAll(listOf(
            ItemCompra(29, "Detergente", false, 7),
            ItemCompra(30, "Sabão de Louça", false, 7),
            ItemCompra(31, "Esponjas", false, 7),
            ItemCompra(32, "Sacos de Lixo", false, 7)
        ))

        return categoria
    }
}
