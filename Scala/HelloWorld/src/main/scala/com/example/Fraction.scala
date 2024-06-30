package com.example

import scala.annotation.tailrec

class Fraction private(val numerator: BigInt, val denominator: BigInt) {

  require(denominator != 0, "Denominator cannot be zero")

  // Constructor to create a Fraction
  def this(numerator: BigInt) = this(numerator, 1)

  // Method to add two fractions
  def +(other: Fraction): Fraction = {
    val commonDenominator = denominator * other.denominator
    val newNumerator = numerator * other.denominator + other.numerator * denominator
    Fraction(newNumerator, commonDenominator)
  }

  // Method to subtract two fractions
  def -(other: Fraction): Fraction = this + (-other)

  // Method to negate the fraction
  def unary_- : Fraction = Fraction(-numerator, denominator)

  // Method to multiply two fractions
  def *(other: Fraction): Fraction = Fraction(numerator * other.numerator, denominator * other.denominator)

  // Method to divide two fractions
  def /(other: Fraction): Fraction = {
    require(other.numerator != 0, "Division by zero")
    Fraction(numerator * other.denominator, denominator * other.numerator)
  }

  // Method to simplify the fraction
  def simplify: Fraction = {
    val gcdValue = numerator.gcd(denominator)
    Fraction(numerator / gcdValue, denominator / gcdValue)
  }

  override def toString: String = s"$numerator / $denominator"

}

object Fraction {

  // Factory method to create a Fraction
  def apply(numerator: BigInt, denominator: BigInt): Fraction = {
    new Fraction(numerator, denominator).simplify
  }

  // Factory method to create a Fraction with a single BigInt (whole number)
  def apply(numerator: BigInt): Fraction = new Fraction(numerator)

  def main(args: Array[String]): Unit = {
    val f1 = Fraction(1, 2)
    val f2 = Fraction(3, 4)

    println(s"f1 = $f1")
    println(s"f2 = $f2")

    val sum = f1 + f2
    println(s"Sum = $sum")

    val difference = f1 - f2
    println(s"Difference = $difference")

    val product = f1 * f2
    println(s"Product = $product")

    val quotient = f1 / f2
    println(s"Quotient = $quotient")
  }
}
