package br.com.everis.kotlin
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun main(){
    println("Sistema de entrevista everis")
    println(getDateTime())
    // Utilização de Null Safe , Exception com o Try Catch.
    var  codProjeto: Int? = null
    var nomeCand: String =""
    var salCand: Double = 0.00
    println("Executar entrevista:")
    try {
        println("Digite o código do Projeto: ")
        codProjeto = readLine()!!.toInt() //vai pegar a excecao e tratar ela. Usa esse cara para pegar a Exception.
    } catch (e: Exception){
        println("erro: ${e.message}") }
    //finally { println("essa parte do codig sempre vai rodar em exception ou nao") }

    // Carrega informações do Projeto
    if (codProjeto == 341) {
        val projetoItau = projetoEveris(nome = "Itau Unibanco", qtdeMembros = 350, pais = "Brasil", faturamento = 500000000.00)
    } else {
        println("Por favor indicar um projeto válido")
    }

    val vagaDispoinível = cargo("Engenheiro de Software")
    vagaDispoinível.displayCargo("Analista de AWS.")
    vagaDispoinível.displayCOD(8272728)

    // Processo de entrevista:
    // Entrevistador:
    val daniel = funcionarioEveris(matricula = 999, salario = 20000.00, projeto = 341, nome = "Daniel Alquere")
    var beneficioEntrevistador: Double = daniel.valeAlimentacao()
    println("o ticket que ${daniel.nome} ganha eh de: $beneficioEntrevistador")

    // Entrevistado:
    try {
        println("Digite o nome do entrevistado: ")
        nomeCand = readLine()!! //vai pegar a excecao e tratar ela. Usa esse cara para pegar a Exception.
    } catch (e: Exception){
        println("erro: ${e.message}") }

    try {
        println("Digite o salario pretendido do entrevistado: ")
        salCand = readLine()!!.toDouble() //vai pegar a excecao e tratar ela. Usa esse cara para pegar a Exception.
    } catch (e: Exception){
        println("erro: ${e.message}") }

    data class entrevistadoCargo(var nome: String, var salarioPretendido: Double)
    var entExt = entrevistadoCargo(nomeCand, salCand)


    // - Lambda
    // calculo de engargos do candidato em dolar, mais impostos ==> funcao lambida com it
    val calculaSalarioEncargo: (salario: Double) -> Double = bonus@ {
        salario ->
        //falar para o lambda que nao queremos retornar a ultima linha.
        if (salario > 5000) {
            return@bonus salario*5.99 + 300}
        (salario*5.99+1000)
    } // --> this is a curly brace.
    var encargosSalario = calculaSalarioEncargo(entExt.salarioPretendido).plus10b()
    println("Os encargos e impostos do candidato eh: $encargosSalario")

}

// Existe uma classe para tipo de Projeto dentro da Consultoria
open class projetoEveris(
        open var nome: String,
        open var qtdeMembros: Int,
        open var pais: String,
        open var faturamento: Double)


// Existe uma classe para os funcionarios da empresa que participam do processo ou indicam candidatos.
open class funcionarioEveris (
        open val nome: String,
        open val matricula: Int,
        open val projeto: Int,
        open val salario: Double) : beneficios {

    // Calculo da bonificação de caso o funcionario seja contratado.
    open fun calculaBonus(bonus: Double) : Double {
        return this.salario + bonus }

    override fun valeAlimentacao(): Double {
        if (this.salario > 5000.00) {
            return 500.00
        } else {
            return 300.00 }
    }

}

interface beneficios  {
    fun valeAlimentacao(): Double
}


////// ----- uso de abstract ------ ///////
abstract class Person(name: String) {
    init {
        println("Processando cargo: $name.") }
    fun displayCOD(codeVaga: Int) {
        println("Codigo Vaga eh: $codeVaga.") }
    abstract fun displayCargo(descCargo: String)
}

class cargo(name: String): Person(name) {
    override fun displayCargo(descCargo: String) {
        println(descCargo) } }

// High Extension.
fun Double.plus10b(): Double = this+100.55

// Funcao de data.
fun getDateTime(): String {
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val date = Date()
    return dateFormat.format(date)
}