package com.example.laboratorio_1.ejercicios

class Calculadora(
    val marca: String,
    val vidaUtil: Int,
    var precio: Double
) {

    fun add(a: Int, b: Int) = a + b

    fun substrat(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int): Int {
        require(b != 0) { "EXPLOSIÓN DEL UNIVERSO ¡BOOOOM!" }
        return a / b
    }
}