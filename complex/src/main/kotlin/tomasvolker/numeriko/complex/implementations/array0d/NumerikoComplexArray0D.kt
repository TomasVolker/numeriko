package tomasvolker.numeriko.complex.implementations.array0d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.MutableComplexArray0D

class NumerikoComplexArray0D(
        var data: Complex
): MutableComplexArray0D {

    override fun getValue(): Complex = data

    override fun setValue(value: Complex) {
        data = value
    }


}