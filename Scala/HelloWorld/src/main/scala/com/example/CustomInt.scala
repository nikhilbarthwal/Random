package com.example

class CustomInt(private val value: Int) {

  // Method to add another CustomInt
  def +(other: CustomInt): CustomInt = new CustomInt(this.value + other.value)

  // Method to subtract another CustomInt
  def -(other: CustomInt): CustomInt = new CustomInt(this.value - other.value)

  // Method to multiply with another CustomInt
  def *(other: CustomInt): CustomInt = new CustomInt(this.value * other.value)

  // Method to divide by another CustomInt
  def /(other: CustomInt): CustomInt = new CustomInt(this.value / other.value)

  // Method to get the underlying Int value
  def intValue: Int = value

  // Method to convert to String
  override def toString: String = value.toString

}

object CustomInt {

  // Factory method to create a CustomInt
  def apply(value: Int): CustomInt = new CustomInt(value)

  def main(args: Array[String]): Unit = {
    val num1 = CustomInt(10)
    val num2 = CustomInt(5)

    println(s"num1 = $num1")
    println(s"num2 = $num2")

    val sum = num1 + num2
    println(s"Sum = $sum")

    val difference = num1 - num2
    println(s"Difference = $difference")

    val product = num1 * num2
    println(s"Product = $product")

    val quotient = num1 / num2
    println(s"Quotient = $quotient")

    val intValue = sum.intValue
    println(s"Sum as Int = $intValue")
  }
}
