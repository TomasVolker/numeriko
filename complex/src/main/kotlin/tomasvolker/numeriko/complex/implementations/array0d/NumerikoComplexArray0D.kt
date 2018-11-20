package tomasvolker.numeriko.complex.implementations.array0d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D

class NumerikoComplexArray0D(
        var data: Complex
): DefaultMutableComplexArray0D() {

    override fun getValue(): Complex = data

    override fun setValue(value: Complex) {
        data = value
    }


}