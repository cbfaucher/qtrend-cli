package com.quartz.qtrend.cli.model

import org.scalatest.{Matchers, FlatSpec}

class PriceSpec extends FlatSpec with Matchers {

  "Two instances" should "be equal" in {
    val p1 = new Price(12.34f)
    val p2 = new Price(12.34f)

    p1 should be (p2)
  }

  "Negative value" should "throw an exception" in {
    intercept[IllegalArgumentException] {
      new Price(-83.11f)
    }
  }

  "toString" should "return a well-formatted printout" in {
    new Price(12.34f).toString should be ("$12.34")
    new Price(0.01f).toString should be ("$0.01")
    new Price(56f).toString should be ("$56.00")
  }
}
