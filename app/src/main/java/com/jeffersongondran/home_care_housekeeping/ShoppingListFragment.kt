package com.jeffersongondran.home_care_housekeeping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jeffersongondran.home_care_housekeeping.data.model.CategoriaCompra
import com.jeffersongondran.home_care_housekeeping.data.model.EstadoListaCompras
import com.jeffersongondran.home_care_housekeeping.data.model.ItemCompra
import com.jeffersongondran.home_care_housekeeping.data.repository.RepositorioListaCompras
import com.jeffersongondran.home_care_housekeeping.databinding.FragmentShoppingListBinding

/**
 * Fragment responsável pela tela de lista de compras do supermercado
 *
 * Esta tela permite ao usuário:
 * - Visualizar categorias de produtos organizadas
 * - Marcar itens como comprados
 * - Ocultar/mostrar itens já comprados
 * - Acompanhar o progresso das compras
 * - Adicionar itens personalizados
 */
class ShoppingListFragment : Fragment() {

    // === PROPRIEDADES DA CLASSE ===

    /** ViewBinding para acesso type-safe às views do layout */
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    /** Repositório para gerenciar os dados da lista de compras */
    private lateinit var repositorioCompras: RepositorioListaCompras

    /** Lista de categorias com todos os itens de compra */
    private var categorias: MutableList<CategoriaCompra> = mutableListOf()

    /** Flag para controlar se itens comprados estão ocultos */
    private var itensCompradosOcultos: Boolean = false

    // === CICLO DE VIDA DO FRAGMENT ===

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializa o ViewBinding para acessar as views do layout
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa os componentes necessários
        inicializarComponentes()

        // Configura os listeners dos botões e interações
        configurarListeners()

        // Carrega os dados da lista de compras
        carregarDadosListaCompras()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpa a referência do binding para evitar vazamentos de memória
        _binding = null
    }

    // === MÉTODOS DE INICIALIZAÇÃO ===

    /**
     * Inicializa os componentes necessários para o funcionamento da tela
     */
    private fun inicializarComponentes() {
        // Cria uma instância do repositório de dados
        repositorioCompras = RepositorioListaCompras()

        // Configurações iniciais da interface
        configurarInterfaceInicial()
    }

    /**
     * Configura o estado inicial da interface do usuário
     */
    private fun configurarInterfaceInicial() {
        // Inicializa o resumo com valores zerados
        atualizarResumoCompras(0, 0, 0)

        // Configura a visibilidade inicial dos elementos
        binding.progressIndicator.visibility = View.GONE
    }

    // === CONFIGURAÇÃO DE LISTENERS ===

    /**
     * Configura todos os listeners de interação da tela
     */
    private fun configurarListeners() {
        configurarBotaoAlternarVisibilidade()
        configurarBotaoAdicionarItem()
    }

    /**
     * Configura o comportamento do botão de alternar visibilidade dos itens comprados
     */
    private fun configurarBotaoAlternarVisibilidade() {
        binding.btnToggleVisibilityPurchased.setOnClickListener {
            alternarVisibilidadeItensComprados()
        }
    }

    /**
     * Configura o comportamento do botão de adicionar item personalizado
     */
    private fun configurarBotaoAdicionarItem() {
        binding.fabAddCustomItem.setOnClickListener {
            // TODO: Implementar diálogo para adicionar item personalizado
            exibirMensagem("Funcionalidade de adicionar item em desenvolvimento!")
        }
    }

    // === CARREGAMENTO E GERENCIAMENTO DE DADOS ===

    /**
     * Carrega os dados da lista de compras e atualiza a interface
     */
    private fun carregarDadosListaCompras() {
        // Mostra o indicador de carregamento
        alterarEstadoCarregamento(EstadoListaCompras.Carregando)

        try {
            // Obtém as categorias do repositório
            val categoriasCarregadas = repositorioCompras.obterCategorias()

            // Atualiza a lista local
            categorias.clear()
            categorias.addAll(categoriasCarregadas)

            // Atualiza a interface com sucesso
            alterarEstadoCarregamento(EstadoListaCompras.Sucesso(categorias))

        } catch (erro: Exception) {
            // Trata erros no carregamento
            alterarEstadoCarregamento(EstadoListaCompras.Erro("Erro ao carregar lista: ${erro.message}"))
        }
    }

    /**
     * Gerencia os diferentes estados de carregamento da tela
     *
     * @param estado O estado atual (Carregando, Sucesso ou Erro)
     */
    private fun alterarEstadoCarregamento(estado: EstadoListaCompras) {
        when (estado) {
            is EstadoListaCompras.Carregando -> {
                // Mostra indicador de carregamento
                binding.progressIndicator.visibility = View.VISIBLE
                binding.scrollViewCategories.visibility = View.GONE
            }

            is EstadoListaCompras.Sucesso -> {
                // Esconde carregamento e mostra conteúdo
                binding.progressIndicator.visibility = View.GONE
                binding.scrollViewCategories.visibility = View.VISIBLE

                // Constrói a interface com as categorias
                construirInterfaceCategorias(estado.categorias)
            }

            is EstadoListaCompras.Erro -> {
                // Esconde carregamento e mostra erro
                binding.progressIndicator.visibility = View.GONE
                exibirMensagem(estado.mensagem)
            }
        }
    }

    // === CONSTRUÇÃO DA INTERFACE ===

    /**
     * Constrói dinamicamente a interface das categorias de compras
     *
     * @param categorias Lista de categorias para exibir
     */
    private fun construirInterfaceCategorias(categorias: List<CategoriaCompra>) {
        // Limpa o container antes de adicionar novas views
        binding.layoutCategoriesContainer.removeAllViews()

        // Cria uma view para cada categoria
        categorias.forEach { categoria ->
            criarViewCategoria(categoria)
        }

        // Atualiza o resumo geral das compras
        atualizarResumoGeral()
    }

    /**
     * Cria a view para uma categoria específica
     *
     * @param categoria A categoria para criar a view
     */
    private fun criarViewCategoria(categoria: CategoriaCompra) {
        // Infla o layout da categoria
        val viewCategoria = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_shopping_category, binding.layoutCategoriesContainer, false)

        // Configura o cabeçalho da categoria
        configurarCabecalhoCategoria(viewCategoria, categoria)

        // Configura os itens da categoria
        configurarItensCategoria(viewCategoria, categoria)

        // Adiciona a view ao container
        binding.layoutCategoriesContainer.addView(viewCategoria)
    }

    /**
     * Configura o cabeçalho de uma categoria (título, progresso, expansão)
     */
    private fun configurarCabecalhoCategoria(viewCategoria: View, categoria: CategoriaCompra) {
        // Encontra as views do cabeçalho
        val tvTitulo = viewCategoria.findViewById<TextView>(R.id.tvCategoryTitle)
        val tvProgresso = viewCategoria.findViewById<TextView>(R.id.tvCategoryProgress)
        val layoutCabecalho = viewCategoria.findViewById<LinearLayout>(R.id.layoutCategoryHeader)

        // Configura o título
        tvTitulo.text = categoria.titulo

        // Configura a cor de fundo
        val cor = ContextCompat.getColor(requireContext(), categoria.corFundo)
        layoutCabecalho.setBackgroundColor(cor)

        // Atualiza o progresso
        atualizarProgressoCategoria(tvProgresso, categoria)

        // Configura o clique para expandir/colapsar
        layoutCabecalho.setOnClickListener {
            alternarExpansaoCategoria(viewCategoria, categoria)
        }
    }

    /**
     * Configura os itens (CheckBoxes) de uma categoria
     */
    private fun configurarItensCategoria(viewCategoria: View, categoria: CategoriaCompra) {
        val layoutItens = viewCategoria.findViewById<LinearLayout>(R.id.layoutCategoryItems)

        // Remove itens existentes
        layoutItens.removeAllViews()

        // Adiciona um CheckBox para cada item
        categoria.itens.forEach { item ->
            criarCheckboxItem(layoutItens, item, categoria)
        }
    }

    /**
     * Cria um CheckBox para um item específico
     */
    private fun criarCheckboxItem(container: LinearLayout, item: ItemCompra, categoria: CategoriaCompra) {
        val checkbox = CheckBox(requireContext()).apply {
            text = item.nome
            isChecked = item.estaComprado
            textSize = 16f
            setPadding(32, 16, 16, 16)

            // Configura o listener para mudanças de estado
            setOnCheckedChangeListener { _, estaChecado ->
                // Atualiza o modelo de dados
                item.estaComprado = estaChecado

                // Atualiza a interface
                atualizarAposAlteracaoItem(categoria)

                // Salva o estado
                salvarEstadoAtual()
            }
        }

        container.addView(checkbox)
    }

    // === ATUALIZAÇÃO DA INTERFACE ===

    /**
     * Atualiza a interface após alteração em um item
     */
    private fun atualizarAposAlteracaoItem(categoria: CategoriaCompra) {
        // Atualiza o progresso da categoria específica
        atualizarProgressoCategoriaNaView(categoria)

        // Atualiza o resumo geral
        atualizarResumoGeral()

        // Aplica filtro de visibilidade se necessário
        if (itensCompradosOcultos) {
            aplicarFiltroVisibilidade()
        }
    }

    /**
     * Atualiza o resumo geral de todas as compras
     */
    private fun atualizarResumoGeral() {
        val totalItens = categorias.sumOf { it.itens.size }
        val itensComprados = categorias.sumOf { categoria ->
            categoria.itens.count { it.estaComprado }
        }
        val itensRestantes = totalItens - itensComprados

        atualizarResumoCompras(totalItens, itensComprados, itensRestantes)
    }

    /**
     * Atualiza os TextViews do resumo de compras
     */
    private fun atualizarResumoCompras(total: Int, comprados: Int, restantes: Int) {
        binding.tvTotalItems.text = getString(R.string.total_items_format, total)
        binding.tvCompletedItems.text = getString(R.string.completed_items_format, comprados)
        binding.tvRemainingItems.text = getString(R.string.remaining_items_format, restantes)
    }

    /**
     * Atualiza o progresso de uma categoria específica
     */
    private fun atualizarProgressoCategoria(tvProgresso: TextView, categoria: CategoriaCompra) {
        val (comprados, total) = categoria.calcularProgresso()
        tvProgresso.text = getString(R.string.category_progress_format, comprados, total)
    }

    /**
     * Atualiza o progresso de uma categoria na view já criada
     */
    private fun atualizarProgressoCategoriaNaView(categoria: CategoriaCompra) {
        // Encontra a view da categoria no container
        for (i in 0 until binding.layoutCategoriesContainer.childCount) {
            val viewCategoria = binding.layoutCategoriesContainer.getChildAt(i)
            val tvProgresso = viewCategoria.findViewById<TextView>(R.id.tvCategoryProgress)

            // Verifica se é a categoria correta (pode usar tags ou outros métodos)
            // Por simplicidade, atualiza baseado na posição
            if (i < categorias.size && categorias[i].id == categoria.id) {
                atualizarProgressoCategoria(tvProgresso, categoria)
                break
            }
        }
    }

    // === FUNCIONALIDADES DE INTERAÇÃO ===

    /**
     * Alterna entre expandir e colapsar uma categoria
     */
    private fun alternarExpansaoCategoria(viewCategoria: View, categoria: CategoriaCompra) {
        val layoutItens = viewCategoria.findViewById<LinearLayout>(R.id.layoutCategoryItems)

        categoria.estaExpandida = !categoria.estaExpandida

        layoutItens.visibility = if (categoria.estaExpandida) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    /**
     * Alterna a visibilidade dos itens comprados
     */
    private fun alternarVisibilidadeItensComprados() {
        itensCompradosOcultos = !itensCompradosOcultos

        // Atualiza o texto do botão
        atualizarTextoBotaoVisibilidade()

        // Aplica ou remove o filtro
        if (itensCompradosOcultos) {
            aplicarFiltroVisibilidade()
            exibirMensagem(getString(R.string.items_hidden_message))
        } else {
            removerFiltroVisibilidade()
            exibirMensagem(getString(R.string.all_items_shown_message))
        }
    }

    /**
     * Atualiza o texto do botão de alternar visibilidade
     */
    private fun atualizarTextoBotaoVisibilidade() {
        val textoButton = if (itensCompradosOcultos) {
            getString(R.string.show_all_items)
        } else {
            getString(R.string.hide_purchased)
        }
        binding.btnToggleVisibilityPurchased.text = textoButton
    }

    /**
     * Aplica filtro para ocultar itens comprados
     */
    private fun aplicarFiltroVisibilidade() {
        for (i in 0 until binding.layoutCategoriesContainer.childCount) {
            val viewCategoria = binding.layoutCategoriesContainer.getChildAt(i)
            val layoutItens = viewCategoria.findViewById<LinearLayout>(R.id.layoutCategoryItems)

            // Oculta CheckBoxes marcados
            for (j in 0 until layoutItens.childCount) {
                val checkbox = layoutItens.getChildAt(j) as? CheckBox
                checkbox?.let { cb ->
                    cb.visibility = if (cb.isChecked) View.GONE else View.VISIBLE
                }
            }
        }
    }

    /**
     * Remove filtro, mostrando todos os itens
     */
    private fun removerFiltroVisibilidade() {
        for (i in 0 until binding.layoutCategoriesContainer.childCount) {
            val viewCategoria = binding.layoutCategoriesContainer.getChildAt(i)
            val layoutItens = viewCategoria.findViewById<LinearLayout>(R.id.layoutCategoryItems)

            // Mostra todos os CheckBoxes
            for (j in 0 until layoutItens.childCount) {
                val checkbox = layoutItens.getChildAt(j) as? CheckBox
                checkbox?.visibility = View.VISIBLE
            }
        }
    }

    // === PERSISTÊNCIA E SALVAMENTO ===

    /**
     * Salva o estado atual da lista de compras
     */
    private fun salvarEstadoAtual() {
        try {
            repositorioCompras.salvarEstadoItens(categorias)
        } catch (erro: Exception) {
            exibirMensagem("Erro ao salvar: ${erro.message}")
        }
    }

    // === MÉTODOS UTILITÁRIOS ===

    /**
     * Exibe uma mensagem Toast para o usuário
     *
     * @param mensagem A mensagem a ser exibida
     */
    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
    }
}
