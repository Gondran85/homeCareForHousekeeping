# 🏠 HomeCare Planner - Aplicativo de Organização Doméstica

<p align="center">
  <img src="https://img.shields.io/badge/build-passing-brightgreen" alt="Build Status">
  <img src="https://img.shields.io/github/issues/jeffersongondran/Home_care_Housekeeping" alt="Issues">
  <img src="https://img.shields.io/github/forks/jeffersongondran/Home_care_Housekeeping" alt="Forks">
  <img src="https://img.shields.io/github/stars/jeffersongondran/Home_care_Housekeeping" alt="Stars">
  <img src="https://img.shields.io/github/license/jeffersongondran/Home_care_Housekeeping" alt="License">
</p>

---

![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?logo=kotlin)
![ViewBinding](https://img.shields.io/badge/ViewBinding-Enabled-blue)
![Navigation](https://img.shields.io/badge/Navigation-Component-green)

---

## 📌 Sobre o Projeto

O **HomeCare Planner** é um aplicativo Android completo para organização doméstica que centraliza o gerenciamento de tarefas diárias, planejamento de refeições e lista de compras. Desenvolvido com foco na praticidade e eficiência para facilitar a rotina doméstica.

*"Organize your home, meals, and shopping — all in one place."*

---

## ✨ Funcionalidades Principais

### 📋 Daily Jobs (Tarefas Diárias)
- **Checklist organizada por períodos**: Manhã, Tarde, Fim de tarde e Noite
- **Tarefas pré-definidas**: Preparar café da manhã, arrumar camas, limpeza geral
- **Progresso visual**: Acompanhamento do percentual de tarefas concluídas
- **Data atual**: Exibição automática da data do dia
- **Persistência**: Estado das tarefas salvo localmente

### 🍽️ Food Plan (Planejamento de Refeições)
- **Seleção de semanas**: Navegação entre diferentes semanas do ano
- **Organização por dias**: Segunda a domingo com planejamento visual
- **Upload de fotos**: Câmera integrada e galeria para fotos dos pratos
- **Grid de fotos**: Visualização em grade das refeições planejadas
- **Armazenamento local**: Fotos salvas no dispositivo com FileProvider

### 🛒 Shopping List (Lista de Compras)
- **Categorias organizadas**: Proteínas, Laticínios, Frutas, Vegetais, etc.
- **Sistema de progresso**: Barra de progresso por categoria
- **Filtros inteligentes**: Ocultar/mostrar itens já comprados
- **Interface expansível**: Categorias colapsáveis para melhor organização
- **Itens personalizados**: Adicionar novos itens às categorias

---

## 🛠️ Tecnologias Utilizadas

- **Kotlin** (100% Kotlin) + AndroidX
- **View Binding** para acesso type-safe às views
- **Navigation Component** para navegação entre fragments
- **Bottom Navigation** com três abas principais
- **RecyclerView** com GridLayoutManager e LinearLayoutManager
- **FileProvider** para compartilhamento seguro de arquivos
- **Camera Integration** (Camera + Gallery)
- **Material Design Components**

---

## 📊 Arquitetura do Projeto

### 🏗️ Padrão de Arquitetura
- **Arquitetura modular** com separação de responsabilidades
- **Repository Pattern** para abstração de dados
- **Data Models** bem estruturados com documentação
- **Fragment-based UI** com Single Activity

### 📱 Componentes Principais

#### Activities
- `MainActivity.kt` - Activity principal com Bottom Navigation

#### Fragments
- `DailyJobsFragment.kt` - Gerenciamento de tarefas diárias
- `FoodPlanFragment.kt` - Planejamento de refeições com fotos
- `ShoppingListFragment.kt` - Lista de compras categorizada

#### Adapters
- `PhotoAdapter.kt` - Grid de fotos das refeições
- `WeekSelectionAdapter.kt` - Seleção de semanas no calendário

#### Data Layer
- `ShoppingModels.kt` - Modelos de dados para compras
- `ShoppingRepository.kt` - Repositório para dados de compras

---

## 🗂️ Estrutura de Dados

### Lista de Compras
```kotlin
data class ItemCompra(
    val id: Int,
    val nome: String,
    var estaComprado: Boolean = false,
    val categoriaId: Int
)

data class CategoriaCompra(
    val id: Int,
    val titulo: String,
    val corFundo: Int,
    val itens: MutableList<ItemCompra>,
    var estaExpandida: Boolean = false
)
```

### Funcionalidades dos Modelos
- Cálculo automático de progresso por categoria
- Verificação de completude das categorias
- Estados de expansão para UI dinâmica

---

## 📱 Interface do Usuário

### 🎨 Design System
- **Material Design 3** com tema personalizado
- **Bottom Navigation** com ícones intuitivos
- **Cards** para organização visual das informações
- **Progress Bars** para feedback visual
- **Floating Action Buttons** para ações principais

### 📋 Telas Principais

#### Daily Jobs
- Checklist organizado por horários do dia
- Checkbox interativo com feedback visual
- Contador de progresso das tarefas
- Data atual no cabeçalho

#### Food Plan
- Seletor de semanas com calendário
- Grid de fotos 2x2 por dia da semana
- Integração com câmera e galeria
- Dialog para seleção de semanas

#### Shopping List  
- Categorias com cores diferenciadas
- Itens organizados com checkboxes
- Barra de progresso por categoria
- Filtro para ocultar itens comprados

---

## 🔧 Configuração e Instalação

### Pré-requisitos
- Android Studio Hedgehog | 2023.1.1+
- Kotlin 1.9+
- Android SDK 30+ (compileSdk 36)
- Gradle 8.0+

### Dependências Principais
```kotlin
implementation("androidx.core:core-ktx")
implementation("androidx.appcompat:appcompat")
implementation("com.google.android.material:material")
implementation("androidx.navigation:navigation-fragment-ktx")
implementation("androidx.navigation:navigation-ui-ktx")
implementation("androidx.fragment:fragment-ktx")
```

### Permissões Necessárias
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

---

## 🚀 Como Executar

1. **Clone o repositório**
```bash
git clone https://github.com/jeffersongondran/Home_care_Housekeeping.git
cd Home_care_Housekeeping
```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta do projeto

3. **Sincronize as dependências**
   - Aguarde o Gradle sync automático

4. **Execute o aplicativo**
   - Conecte um device Android ou use emulador
   - Run → Run 'app' ou Ctrl+Shift+F10

---

## 📸 Funcionalidades de Foto

### FileProvider Configuration
```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

### Integração Camera + Gallery
- **Camera nativa**: Captura direta pelo aplicativo
- **Galeria**: Seleção de fotos existentes
- **Armazenamento seguro**: FileProvider para URLs seguras
- **Grid dinâmico**: RecyclerView com GridLayoutManager

---

## 🎯 Casos de Uso

### Para Donas de Casa
- Organizar rotina diária de limpeza e cuidados
- Planejar refeições da semana com registro visual
- Gerenciar lista de compras por categorias

### Para Famílias
- Coordenar tarefas domésticas entre membros
- Documentar refeições para controle nutricional
- Otimizar compras no supermercado

### Para Organizadores Profissionais
- Template para clientes organizarem rotinas
- Ferramenta de acompanhamento de progresso
- Sistema de planejamento visual eficiente

---

## 🔄 Estados e Fluxos

### Daily Jobs Flow
1. Usuario visualiza tarefas do dia atual
2. Marca tarefas como concluídas
3. Progresso é atualizado automaticamente
4. Estado persiste entre sessões

### Food Plan Flow
1. Usuário seleciona semana desejada
2. Escolhe dia para adicionar foto
3. Câmera/Galeria para capturar/selecionar
4. Foto é salva e exibida no grid

### Shopping List Flow
1. Categorias carregadas automaticamente
2. Usuário marca itens como comprados
3. Progresso calculado em tempo real
4. Filtros aplicados conforme preferência

---

## 🛡️ Boas Práticas Implementadas

### Código
- **Null Safety** com Kotlin
- **View Binding** para type safety
- **Documentação KDoc** extensiva
- **Separation of Concerns** clara

### UI/UX
- **Material Design Guidelines**
- **Accessibility** com content descriptions
- **Responsive Layout** para diferentes telas
- **Intuitive Navigation** com Bottom Nav

### Performance
- **Lazy Loading** de imagens
- **Efficient RecyclerViews** com ViewHolders
- **Memory Management** adequado para fotos
- **State Management** otimizado

---

## 📋 Próximas Funcionalidades

- 🔔 **Notificações**: Lembretes para tarefas diárias
- ☁️ **Backup na Nuvem**: Sincronização de dados
- 👥 **Multi-usuário**: Compartilhamento familiar
- 📊 **Relatórios**: Analytics de produtividade
- 🎨 **Temas**: Personalização visual
- 🗓️ **Calendário**: Integração com agenda
- 🛒 **Lista Compartilhada**: Sincronização de compras

---

## 👨‍💻 Desenvolvedor

**Jefferson Gondran**
- GitHub: [@jeffersongondran](https://github.com/jeffersongondran)
- LinkedIn: [Jefferson Gondran](https://linkedin.com/in/jeffersongondran)

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor, siga estes passos:

1. Fork o projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

<p align="center">
  <strong>HomeCare Planner</strong> - Simplifique sua rotina doméstica! 🏠✨
</p>
