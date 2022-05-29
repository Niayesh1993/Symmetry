package xyz.zohre.data.discovery.trackSearch

import xyz.zohre.data.model.Sessions
import javax.inject.Inject

class SearchDataSource@Inject constructor() {

    fun search(cities: List<Sessions>, element: String, fromIndex: Int = 0, toIndex: Int = cities.size): Int {


        if (cities.isNullOrEmpty()) return -1
        else {
            rangeCheck(cities.size, fromIndex, toIndex)

            var low = fromIndex
            var high = toIndex - 1

            while (low <= high) {

                val mid = (low + high).ushr(1) // safe from overflows
                val midVal = cities[mid]

                if (mid == high) {
                    return if (midVal.name.take(element.length).compareTo(element, true) == 0)
                        mid
                    else -1
                }


                val cmp = midVal.name.take(element.length).compareTo(element, true)
                when {
                    cmp < 0 -> {
                        low = if (mid == high-1) mid+1 else mid
                    }
                    cmp > 0 -> high = mid
                    else -> return mid
                } // key found

            }
            return -1  // key not found
        }
    }

    private fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
        when {
            fromIndex > toIndex -> throw IllegalArgumentException("fromIndex ($fromIndex) is greater than toIndex ($toIndex).")
            fromIndex < 0 -> throw IndexOutOfBoundsException("fromIndex ($fromIndex) is less than zero.")
            toIndex > size -> throw IndexOutOfBoundsException("toIndex ($toIndex) is greater than size ($size).")
        }
    }
}