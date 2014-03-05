package com.quartz.qtrend.cli.model

/**
 * A Price...
 */
case class Price(value: Float) {
  require(value >= 0.0f, "Price cannot be negative.")

  override def toString: String = "$%.2f".format(value)
}
