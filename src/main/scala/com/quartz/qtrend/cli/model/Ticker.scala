package com.quartz.qtrend.cli.model

/**
 * A ticker symbol.
 */
case class Ticker(s: String) {

  require(Option(s).exists(_.trim.length > 0), "Empty ticker.")

  private val theSymbol = s.trim.toUpperCase

  def symbol: String = theSymbol

  override def toString: String = symbol
}
