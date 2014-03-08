package com.quartz.qtrend.cli.model

import org.joda.time.LocalDate

/**
 * Model for a quote, for a given ticker
 */
sealed trait Quote {
  def ticker: Ticker

  def orderNum: Int

  def date: LocalDate

  def open: Price
  def high: Price
  def low: Price
  def close: Price

  def volume: Long
}

private[model] class NewQuote(val ticker: Ticker,
               val date: LocalDate,
               val open: Price,
               val high: Price,
               val low: Price,
               val close: Price,
               val volume: Long) extends Quote {

  def orderNum: Int = throw new IllegalStateException("Should not be called prior to be saved.")

  override def toString: String = s"""$ticker ${date.toString("yyyy-MM-dd")} $open $high $low $close $volume"""
}

object Quote {
  def apply(ticker: Ticker, date: LocalDate, open: Price, high: Price, low: Price, close: Price, volume: Long): Quote =
    new NewQuote(ticker, date, open, high, low, close, volume)
}


