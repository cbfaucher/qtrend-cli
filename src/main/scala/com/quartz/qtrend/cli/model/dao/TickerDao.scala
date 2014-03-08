package com.quartz.qtrend.cli.model.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.jdbc.core.JdbcTemplate
import com.quartz.qtrend.cli.Spring
import com.quartz.qtrend.cli.model.{Quote, Ticker}
import org.springframework.transaction.annotation.Transactional

/**
 * DAO for [[com.quartz.qtrend.cli.model.Ticker]]-related database queries
 */
trait TickerDao {
  def isTickerExisting(ticker: Ticker): Boolean
  def saveAll(tickers: Seq[Quote]): Int
}

@Component
class TickerDaoImpl extends TickerDao {

  @Autowired var jdbc: JdbcTemplate = _

  def isTickerExisting(ticker: Ticker): Boolean =
    jdbc.queryForObject(
      "SELECT COUNT(ID) FROM QUOTES WHERE TICKER=?",
      classOf[Integer],
      ticker.toString) > 0

  @Transactional
  def saveAll(quotes: Seq[Quote]): Int = {
    import com.quartz.qutils.Converters._

    if (quotes.isEmpty) throw new DaoException("No quote to save.")
    val ticker = quotes.head.ticker
    if (quotes.filterNot(_.ticker == ticker).nonEmpty) throw new DaoException(s"Quotes must be for the same ticker: $ticker")

    var lastSeq = jdbc.queryForObject("SELECT MAX(ENTRY_ID) FROM QUOTES WHERE TICKER=?", classOf[Int], ticker.toString)

    quotes.map {
      quote => {
        lastSeq += 1
        jdbc.update(
          "INSERT INTO QUOTES (DATE, EXCHANGE, ENTRY_ID, TICKER, OPEN_PRICE, HIGH_PRICE, LOW_PRICE, CLOSE_PRICE, VOLUME) "
            + "VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?)",
          quote.date.toTimestamp,
          "FIXME", lastSeq:jlInt, quote.ticker.toString,
          quote.open.value:jlFloat, quote.high.value:jlFloat, quote.low.value:jlFloat, quote.close.value:jlFloat,
          quote.volume:jlLong)
      }
    }.sum
  }
}

trait TickerDaoField {

  implicit val tickerDao: TickerDao = Spring(classOf[TickerDaoImpl])
}
