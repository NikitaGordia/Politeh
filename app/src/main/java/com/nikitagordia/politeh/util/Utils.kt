package com.nikitagordia.politeh.util

fun flex(str: String): String {
    val res = StringBuilder("")
    str.forEach { res.append(flex(it)) }
    if (res.length == 2) res.append("-")
    return res.toString()
}

fun flex(ch : Char): Char {
    val upper = ch.isUpperCase()
    val result = when(ch.toLowerCase()) {
        'ы' -> 'і'
        'и' -> 'і'
        'q' -> 'к'
        'w' -> 'в'
        'e' -> 'і'
        'r' -> 'р'
        't' -> 'т'
        'y' -> 'у'
        'u' -> 'у'
        'i' -> 'і'
        'o' -> 'о'
        'p' -> 'п'
        'a' -> 'а'
        's' -> 'с'
        'd' -> 'д'
        'f' -> 'ф'
        'g' -> 'г'
        'h' -> 'х'
        'j' -> 'ж'
        'k' -> 'к'
        'l' -> 'л'
        'z' -> 'з'
        'x' -> 'х'
        'c' -> 'ц'
        'v' -> 'в'
        'b' -> 'б'
        'n' -> 'н'
        'm' -> 'м'
        else -> ch
    }
    return if (upper) result.toUpperCase() else result
}