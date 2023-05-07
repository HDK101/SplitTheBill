package br.scl.ifsp.splitthebill.model

class Bill(
    val name: String,
    val totalSpent: Double,
) {
    var persons: MutableList<Person> = mutableListOf<Person>()

    fun calculateSplitValue(): Double {
        return totalSpent / persons.size
    }

    fun calculatetoPayPerPerson() {
        val splitValue = calculateSplitValue()

        persons.forEach { person ->
            person.toPay = splitValue - person.spent
        }
    }

    data class Person(
        val name: String,
        val spent: Double,
        var toPay: Double
        )
}