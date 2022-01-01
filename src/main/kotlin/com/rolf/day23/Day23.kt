package com.rolf.day23

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day23().run()
}

class Day23 : Day() {
    override fun getDay(): Int {
        return 23
    }

    override fun solve1(lines: List<String>) {
        val instructions = parseInstructions(lines)
        val computer = runComputer(instructions, mapOf("a" to 0, "b" to 0))
        println(computer.registers["b"])
    }

    override fun solve2(lines: List<String>) {
        val instructions = parseInstructions(lines)
        val computer = runComputer(instructions, mapOf("a" to 1, "b" to 0))
        println(computer.registers["b"])
    }

    private fun parseInstructions(lines: List<String>): MutableList<Instruction> {
        val instructions = mutableListOf<Instruction>()
        for (line in lines) {
            val parts = splitLine(line.replace(",", ""), " ")
            val type = parts[0]
            instructions.add(findInstruction(type, parts))
        }
        return instructions
    }

    private fun findInstruction(type: String, parts: List<String>): Instruction {
        return when (type) {
            "hlf" -> {
                Half(parts[1])
            }
            "tpl" -> {
                Triple(parts[1])
            }
            "inc" -> {
                Increment(parts[1])
            }
            "jmp" -> {
                Jump(parts[1].toInt())
            }
            "jie" -> {
                JumpIfEven(parts[1], parts[2].toInt())
            }
            "jio" -> {
                JumpIfOne(parts[1], parts[2].toInt())
            }
            else -> throw Exception("Unknown type: $type")
        }
    }

    private fun runComputer(
        instructions: MutableList<Instruction>,
        registers: Map<String, Int> = mutableMapOf("a" to 0, "b" to 0)
    ): Computer {
        val computer = Computer(registers.toMutableMap())
        computer.run(instructions)
        return computer
    }
}

data class Computer(var registers: MutableMap<String, Int> = mutableMapOf("a" to 0, "b" to 0)) {
    fun run(instructions: List<Instruction>) {
        var pointer = 0
        while (pointer < instructions.size) {
            pointer = run(pointer, instructions[pointer])
        }
    }

    fun run(pointer: Int, instruction: Instruction): Int {
        return instruction.run(pointer, registers)
    }
}

abstract class Instruction(val type: String) {
    abstract fun run(pointer: Int, registers: MutableMap<String, Int>): Int
}

data class Half(val register: String) : Instruction("hlf") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        registers[register] = registers[register]!! / 2
        return pointer + 1
    }
}

data class Triple(val register: String) : Instruction("tpl") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        registers[register] = registers[register]!! * 3
        return pointer + 1
    }
}

data class Increment(val register: String) : Instruction("inc") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        registers[register] = registers[register]!! + 1
        return pointer + 1
    }
}

data class Jump(val offset: Int) : Instruction("jmp") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        return pointer + offset
    }
}

data class JumpIfEven(val register: String, val offset: Int) : Instruction("jie") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        if (registers[register]!! % 2 == 0) {
            return pointer + offset
        }
        return pointer + 1
    }
}

data class JumpIfOne(val register: String, val offset: Int) : Instruction("jio") {
    override fun run(pointer: Int, registers: MutableMap<String, Int>): Int {
        if (registers[register]!! == 1) {
            return pointer + offset
        }
        return pointer + 1
    }
}
