package com.example.laboratorio_1.ejercicios

// Ejercicio 1
class Computadora(
    var ram: Int,
    var almacenamiento: Int,
    var sistemaOperativo: String,
    var programasInstalados: MutableList<String>
) {

    fun turnOn(): String {
        return "Computadora encendida"
    }

    fun turnOff(): String {
        return "Computadora apagada"
    }

    fun changeRAM(nuevaRAM: Int) {
        ram = nuevaRAM
    }

    fun changeSO(nuevoSO: String) {
        sistemaOperativo = nuevoSO
    }

    fun programs2026(): List<String> {
        return programasInstalados.filter { it.contains("2026") }
    }
}