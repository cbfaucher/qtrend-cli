package com.quartz.qtrend.cli

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Created with IntelliJ IDEA.
 * User: Christian
 * Date: 2/24/14
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
object QtrendCli {

  def main(args: Array[String]) {
    val context = new AnnotationConfigApplicationContext(classOf[Application])

    val jdbc = context.getBean(classOf[JdbcTemplate])

    println(s"Number of rows: ${jdbc.queryForObject("SELECT COUNT(*) FROM prices", classOf[java.lang.Integer])}")
  }
}
