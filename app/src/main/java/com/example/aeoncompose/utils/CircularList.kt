package com.example.aeoncompose.utils

class CircularList(
    private val content: List<Int>
) : List<Int> {
    override val size: Int = Int.MAX_VALUE
    override fun contains(element: Int): Boolean = content.contains(element = element)
    override fun containsAll(elements: Collection<Int>): Boolean = content.containsAll(elements)
    override fun get(index: Int): Int = content[index % content.size]
    override fun indexOf(element: Int): Int = content.indexOf(element)
    override fun isEmpty(): Boolean = content.isEmpty()
    override fun iterator(): Iterator<Int> = content.iterator()
    override fun lastIndexOf(element: Int): Int = content.lastIndexOf(element)
    override fun listIterator(): ListIterator<Int> = content.listIterator()
    override fun listIterator(index: Int): ListIterator<Int> = content.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<Int> = content.subList(fromIndex, toIndex)
}