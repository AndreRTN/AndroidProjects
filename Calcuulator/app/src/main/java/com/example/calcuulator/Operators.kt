package com.example.calcuulator

enum class Operators(s: Char) {


    SUM('+'),
    SUBTRACT('-'),
    DIVIDE('/'),
    MULTIPLY('*'),
    EQUALS('=');

    val operatorChar: Char = s



}