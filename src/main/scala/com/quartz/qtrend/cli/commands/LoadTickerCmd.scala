package com.quartz.qtrend.cli.commands

import com.quartz.qutils.commands.CommandContext
import com.quartz.qtrend.cli.{PropertiesField, UserPropertiesField}
import com.quartz.qtrend.cli.model.dao.{TickerDaoField}
import org.joda.time.LocalDate
import java.net.URL
import java.io.{InputStreamReader, BufferedReader}
import scala.io.Source
import com.quartz.qtrend.cli.model.{Price, Quote, Ticker}
import org.joda.time.format.DateTimeFormat

/**
 * Load tickers command
 */
object LoadTickerCmd extends TickerDaoField with UserPropertiesField with PropertiesField {

  private[this] val userPropLastFromDate = "load.from-date"
  private[this] val userPropLastToDate = "load.to-date"

  def apply(implicit ctx: CommandContext) {

    ctx(0).map(Ticker) match {
      case Some(ticker) if tickerDao.isTickerExisting(ticker) =>
        println(s"Use 'update ticker', because $ticker already exists!")

      case Some(ticker) => loadTicker(ticker)

      case None => println(s"Missing ticker: ${ctx.command.toString()}")
    }
  }

  private def loadTicker(ticker: Ticker)(implicit ctx: CommandContext) {
    import com.quartz.qutils.QUserPropertiesHelper._
    import com.quartz.qutils.HumanInputUtils._

    val from  = userProperties.using(userPropLastFromDate, new LocalDate().plusYears(-5)) {
      d => askDate("From: ", d)
    }
    val to    = userProperties.using(userPropLastToDate, new LocalDate()) {
      d => askDate("To: ", d)
    }

    println(s"Will load $ticker from $from to $to.")

    Option(properties.getProperty("yahoo.update-ticker.url")) match {

      case Some(url) => {
        val completedUrl = url.replaceAllLiterally("$TICKER$", ticker.toString)
          .replaceAllLiterally("$START_YEAR$", from.year().get().toString)
          .replaceAllLiterally("$START_MONTH$", from.monthOfYear().get().toString)
          .replaceAllLiterally("$START_DAY$", from.dayOfMonth().get().toString)
          .replaceAllLiterally("$END_YEAR$", to.year().get().toString)
          .replaceAllLiterally("$END_MONTH$", to.monthOfYear().get().toString)
          .replaceAllLiterally("$END_DAY$", to.dayOfMonth().get().toString)

        println(s"Will invoke $completedUrl")

        //  todo: openConnection(proxy)...
        val quotes = Source.fromInputStream(new URL(completedUrl).openConnection(/*proxy*/).getInputStream)
          .getLines()
          .toSeq
          .tail     //  first row is column names -- ignore
          .map( toQuote(ticker) )
          .sortWith{
            case (d1, d2) => d1.date.isBefore(d2.date)
          }

        println(s"Saved ${ tickerDao.saveAll(quotes) } quotes for $ticker")
      }

      case None => println("ERROR: Missing Yahoo URL for updating!")
    }
  }

  private def toQuote(ticker: Ticker)(line: String): Quote = {
    val elements = line.split(Array(',', ';'))
    Quote(ticker,
          LocalDate.parse(elements(0), DateTimeFormat.forPattern("yyyy-MM-dd")),
          Price(elements(1).toFloat),
          Price(elements(2).toFloat),
          Price(elements(3).toFloat),
          Price(elements(4).toFloat),
          elements(5).toLong
    )
  }
}
